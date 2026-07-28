import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.text.SimpleDateFormat;

public class ChatServer {

    private static final int PORT = 5000;
    private static final ConcurrentHashMap<String, ClientHandler> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println(" Real-Time Chat Server starting on port " + PORT);
        System.out.println("=================================================");

        ExecutorService pool = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening... waiting for clients.");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket);
                pool.execute(handler);
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }

    private static String timestamp() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    private static void broadcast(String message, String excludeUser) {
        for (Map.Entry<String, ClientHandler> entry : clients.entrySet()) {
            if (!entry.getKey().equals(excludeUser)) {
                entry.getValue().sendMessage(message);
            }
        }
    }

    private static void broadcastUserList() {
        String userList = "USERLIST:" + String.join(",", clients.keySet());
        for (ClientHandler handler : clients.values()) {
            handler.sendMessage(userList);
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void sendMessage(String msg) {
            if (out != null) {
                out.println(msg);
            }
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Enter your username:");
                username = in.readLine();

                if (username == null || username.trim().isEmpty()) {
                    username = "Guest" + socket.getPort();
                }

                while (clients.containsKey(username)) {
                    out.println("USERNAME_TAKEN");
                    username = in.readLine();
                    if (username == null) {
                        socket.close();
                        return;
                    }
                }

                clients.put(username, this);
                out.println("USERNAME_OK:" + username);

                System.out.println("[" + timestamp() + "] " + username + " joined the chat.");
                broadcast("SYSTEM:" + username + " has joined the chat!", username);
                broadcastUserList();

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("[" + timestamp() + "] " + username + ": " + message);

                    if (message.startsWith("@")) {
                        int spaceIdx = message.indexOf(' ');
                        if (spaceIdx > 1) {
                            String targetUser = message.substring(1, spaceIdx);
                            String privateMsg = message.substring(spaceIdx + 1);
                            ClientHandler target = clients.get(targetUser);
                            if (target != null) {
                                target.sendMessage("PRIVATE:" + timestamp() + ":" + username + ":" + privateMsg);
                                out.println("PRIVATE_SENT:" + timestamp() + ":" + targetUser + ":" + privateMsg);
                            } else {
                                out.println("SYSTEM:User '" + targetUser + "' not found or offline.");
                            }
                        }
                    } else {
                        broadcast("MSG:" + timestamp() + ":" + username + ":" + message, null);
                    }
                }

            } catch (IOException e) {
                System.out.println("Connection lost with " + username);
            } finally {
                if (username != null) {
                    clients.remove(username);
                    broadcast("SYSTEM:" + username + " has left the chat.", username);
                    broadcastUserList();
                    System.out.println("[" + timestamp() + "] " + username + " disconnected.");
                }
                try {
                    socket.close();
                } catch (IOException ignored) {}
            }
        }
    }
}