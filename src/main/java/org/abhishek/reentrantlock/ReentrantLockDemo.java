package org.abhishek.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {


    public static class Worker {
        private static int counter = 0;

        // define a lock
        Lock lock = new ReentrantLock();

        public void addByOne() {
            for(int i = 0 ; i < 10000; i++) {
                counter++;
            }
        }

        // uses reentrant lock for synchronization
        public void addByOneSynchronized() {
            try {
                lock.lock();
                addByOne();
            } finally {
                lock.unlock();
            }
        }

        public int getCounter() {
            return counter;
        }

        public void reset() {
            counter =0;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();

        Thread t1 = new Thread(worker::addByOne);
        Thread t2 = new Thread(worker::addByOne);

        System.out.println("Starting both the threads without synchronization...");
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("the value with un synchronized is "+ worker.getCounter());

        Thread t3 = new Thread(worker::addByOneSynchronized);
        Thread t4 = new Thread(worker::addByOneSynchronized);

        worker.reset();
        System.out.println("Starting both the threads with synchronization...");
        t3.start();
        t4.start();

        t3.join();
        t4.join();
        System.out.println("the value with synchronized is "+ worker.getCounter());



    }
}
