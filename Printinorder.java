Public class Printinorder {
    // 0 permits means any thread calling acquire() will block immediately
    private final Semaphore sem1 = new Semaphore(0);
    private final Semaphore sem2 = new Semaphore(0);

    public Foo() {
        // Constructor remains empty
    }

    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        
        // Release 1 permit to unblock sem1
        sem1.release(); 
    }

    public void second(Runnable printSecond) throws InterruptedException {
        // Wait until sem1 gets a permit from first()
        sem1.acquire(); 
        
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        
        // Release 1 permit to unblock sem2
        sem2.release(); 
    }

    public void third(Runnable printThird) throws InterruptedException {
        // Wait until sem2 gets a permit from second()
        sem2.acquire(); 
        
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}