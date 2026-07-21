Public class Thediningphilosophers {

    private final ReentrantLock[] forks;
    private final Semaphore semaphore;

    public DiningPhilosophers() {
        forks = new ReentrantLock[5];
        for (int i = 0; i < 5; i++) {
            forks[i] = new ReentrantLock();
        }
        semaphore = new Semaphore(4);
    }

    public void wantsToEat(
            int philosopher,
            Runnable pickLeftFork,
            Runnable pickRightFork,
            Runnable eat,
            Runnable putLeftFork,
            Runnable putRightFork) throws InterruptedException {

        int left = philosopher;
        int right = (philosopher + 1) % 5;

        semaphore.acquire();

        forks[left].lock();
        forks[right].lock();

        try {
            pickLeftFork.run();
            pickRightFork.run();

            eat.run();

            putRightFork.run();
            putLeftFork.run();
        } finally {
            forks[right].unlock();
            forks[left].unlock();
            semaphore.release();
        }
    }
}