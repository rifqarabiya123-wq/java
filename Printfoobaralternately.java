public class Printfoobaralternately {
    private final int n;
    private final Semaphore fooSem = new Semaphore(1); // Starts allowed
    private final Semaphore barSem = new Semaphore(0); // Starts blocked

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            fooSem.acquire(); // Decrement to 0, blocks subsequent foo calls
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            barSem.release(); // Unblocks bar()
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            barSem.acquire(); // Waits for foo() to finish
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            fooSem.release(); // Unblocks foo()
        }
    }
}