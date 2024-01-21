package org.abhishek.atomic_variables;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * https://www.baeldung.com/java-atomic-variables
 * https://docs.oracle.com/javase/tutorial/essential/concurrency/atomicvars.html
 *
 * CompareAndSet vs incrementAndGet
 * public final int incrementAndGet() {
 *     for (;;) {
 *         int current = get();
 *         int next = current + 1;
 *         if (compareAndSet(current, next))
 *             return next;
 *     }
 * }
 */
public class AtomicVariableDemo {

    static class AtomicVariablesWorker {
        private final AtomicInteger atomicInteger = new AtomicInteger(0);

        public void increment () {
            for(int i = 0 ; i < 14 ; i++) {
                System.out.println("Thread in increment -> "+ Thread.currentThread().getName());
                atomicInteger.incrementAndGet();
            }
        }

        public void decrement() {
            for(int i = 0 ; i < 12 ; i++) {
                System.out.println("Thread in decrement -> "+ Thread.currentThread().getName());
                atomicInteger.decrementAndGet();
            }
        }

        public int getInteger() {
            return atomicInteger.get();
        }
    }

    /**
     * Below shows that the threads are not synchronized but the variable is performing
     * atomic operations and the results are guaranteed to be as expected.
     * @param args
     * @throws InterruptedException
     */

    public static void main(String[] args) throws InterruptedException {
        AtomicVariablesWorker worker = new AtomicVariablesWorker();
        System.out.println("Starting demo for Atomic variables");

        System.out.println("Current value " + worker.getInteger());

        Thread t1 = new Thread(worker::increment, "incre");
        Thread t2 = new Thread(worker::decrement, "decre");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("After value " + worker.getInteger());

        System.out.println("Exiting demo for Atomic variables");
    }
}
