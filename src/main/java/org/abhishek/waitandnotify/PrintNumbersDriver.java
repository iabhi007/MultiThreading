package org.abhishek.waitandnotify;

/**
 *  * Q 2. Given multiple threads..print values in synchronized manner..
 *  Here we will use a custom lock instead of intrinsic lock
 */
public class PrintNumbersDriver {

    public static int numberOfThreads = 13;

    public static class NumberPrinter implements Runnable{

        // custom object for locking
        private static final Object lock = new Object();
        private static int counter = 1;

        // turn should be initialized to zero to match with order which range from 0 to numberOfThreads -1
        private static int turn = 0;

        private final int order;

        public NumberPrinter(int order) {
            this.order = order;
        }

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    while(true) {
                        while(turn != order ) {
                            // wait() is invoked on the lock which has been acquired by current thread, else
                            //java.lang.IllegalMonitorStateException: current thread is not owner
                            // is thrown if we do this.wait() and we have locked on lock object as here.
                            lock.wait();
                        }
                        System.out.printf("%s -> %d\n", Thread.currentThread().getName(), counter);
                        counter++;
                        turn = ((turn +1) % numberOfThreads);
                        // similar as with wait above
                        lock.notifyAll();
                        // after the below statement is executed then only the lock is released
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public static void main(String[] args) throws InterruptedException {

            Thread[] threads = new Thread[numberOfThreads];
            for(int i = 0 ; i < numberOfThreads ; i++) {
                threads[i] = new Thread(new NumberPrinter(i));
                threads[i].setName("Thread-"+ (i+1));
                threads[i].start();
            }
            System.out.println("Exiting main thread");
        }

    }




}
