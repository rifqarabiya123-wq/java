public class Throneinheritance {
    // Map to store a parent and their children in order of birth
    private Map<String, List<String>> familyTree;
    // Set to keep track of deceased members
    private Set<String> deadMembers;
    // Store the starting king's name
    private String king;

    public ThroneInheritance(String kingName) {
        this.familyTree = new HashMap<>();
        this.deadMembers = new HashSet<>();
        this.king = kingName;
        // Initialize the king's entry in the family tree
        this.familyTree.put(kingName, new ArrayList<>());
    }
    
    public void birth(String parentName, String childName) {
        // Add the child to the parent's list of children
        this.familyTree.get(parentName).add(childName);
        // Initialize an empty children list for the new child
        this.familyTree.put(childName, new ArrayList<>());
    }
    
    public void death(String name) {
        // Mark the person as dead
        this.deadMembers.add(name);
    }
    
    public List<String> getInheritanceOrder() {
        List<String> order = new ArrayList<>();
        // Perform a pre-order DFS starting from the king
        dfs(king, order);
        return order;
    }

    private void dfs(String current, List<String> order) {
        // If the current person is alive, add them to the inheritance list
        if (!deadMembers.contains(current)) {
            order.add(current);
        }
        
        // Recursively visit all children in order of birth
        List<String> children = familyTree.get(current);
        if (children != null) {
            for (String child : children) {
                dfs(child, order);
            }
        }
    }
}