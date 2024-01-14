package org.abhishek.daemon;

public class DaemonDriver {

    /**
     * This the meant to be a daemon thread which are low priority threads.
     * These threads are terminated by JVM as soon as all non-daemon
     * threads finished their execution.
     */
    public static class DaemonWorker implements Runnable {
        @Override
        public void run() {
            for(int i = 0 ; i < 1000 ; i++) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Daemon Thread working...");
            }
        }
    }


    /**
     * Below is a normal non-daemon thread.
     */
    public static class NormalWorker implements Runnable {

        @Override
        public void run() {
            for(int i = 0 ; i < 5 ; i++) {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Worker Thread executing...");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread daemonThread = new Thread(new DaemonWorker());
        Thread normalThread = new Thread(new NormalWorker());

        // setting as daemon thread
        daemonThread.setDaemon(true);

        daemonThread.start();
        normalThread.start();

        System.out.println(String.format("is Daemon %s -> %s", Thread.currentThread().getName(), Thread.currentThread().isDaemon()));
        System.out.println(String.format("is Daemon %s -> %s", daemonThread.getName(), daemonThread.isDaemon()));
        System.out.println(String.format("is Daemon %s -> %s", normalThread.getName(), normalThread.isDaemon()));


        /**
         * Thread.sleep causes the current thread to suspend execution for a specified period.
         * So main thread will be put to sleep here by below statement.
         * Also, main declares that it throws InterruptedException.
         * This is an exception that sleep throws when another thread interrupts
         * the current thread while sleep is active.
         */
        Thread.sleep(1000);
        System.out.println("Main thread finished execution...");

    }


}
