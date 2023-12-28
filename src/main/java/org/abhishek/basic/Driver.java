package org.abhishek.basic;

/**
 * we can create threads either with Runnable interfaces or with Thread classes
 * as below. what approach to prefer?
 *
 * Usually using the Runnable interface approach is preferred.
 * if we extends Thread then we can’t extend any other class (usually a huge disadvantage)
 * because in Java a given class can extends one class exclusively
 *
 * a class may implement more interfaces as well -
 * so implementing the Runnable interface can do no harm in the software logic (now or in the future)
 */
public class Driver {

    static class Runner1 implements Runnable {

        @Override
        public void run() {
            for(int i = 0 ; i < 10 ; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Runner1 " +i);
            }
        }
    }

    static class Runner2 extends Thread {
        @Override
        public void run() {
            for(int i = 0 ; i < 10 ; i++) {
                System.out.println("Runner2 " +i);
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runner1());
        Thread t2 = new Runner2();

        Thread t3 = new Thread(() -> {
            for(int i = 0 ; i < 10 ; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Runner3 " + i);
            }
        });

        // Starting the thread
        t1.start();
        t2.start();
        t3.start();

        try {
            // the current thread which is the main thread
            // will wait for mentioned thread to finish the execution.
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("The main method is terminated ..... ");

    }
}
