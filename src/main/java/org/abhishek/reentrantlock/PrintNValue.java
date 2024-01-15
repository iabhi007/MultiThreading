package org.abhishek.reentrantlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Demo to print N number with m threads without intrinsic lock.
 *
 * One important thing is to first take a lock() and then unlock() in finally block.
 * In between we can perform any statements, if we forget to unlock() this will
 * cause DeadLock.
 * check @class : ReentrantLockDemo
 */
public class PrintNValue {

    private static final int numOfThreads = 12;
    public static class Worker implements Runnable {

        private static final Lock lock = new ReentrantLock();
        private static final Condition condition = lock.newCondition();

        private static int counter = 1;

        private static int turn;

        private final int order;

        public Worker(int order) {
            this.order = order;
        }


        @Override
        public void run() {
            while(true) {
                try {
                    // FIRST STEP We need to first lock the lock
                    lock.lock();
                    // Then wait on the condition
                    while(order != turn) {
                        condition.await();
                    }
                    System.out.printf("%s -> %d\n", Thread.currentThread().getName(), counter);
                    // SignalAll equivalent to notifyAll()
                    condition.signalAll();
                    Thread.sleep(400);
                    counter++;
                    turn = (turn+1) % numOfThreads;
                } catch (Exception exp) {
                    exp.printStackTrace();
                } finally {
                    // LAST AND IMPORTANT STEP to unlock the lock
                    // else we would be having deadlock.
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();

        System.out.println("Creating and starting the threads");
        for(int i = 0 ; i < numOfThreads ; i++) {
            threads.add(new Thread(new Worker(i)));
            threads.get(i).setName("Thread "+(i+1));
        }

        for(int i = 0 ; i < numOfThreads ; i++) {
            threads.get(i).start();
        }
    }

}
