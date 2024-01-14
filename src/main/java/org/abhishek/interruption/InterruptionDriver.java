package org.abhishek.interruption;

public class InterruptionDriver {

    private static void printThread(String message) {
        //Returns a reference to the currently executing thread object
        String threadName = Thread.currentThread().getName();
        System.out.printf("From thread-(%s) with message : %s\n", threadName, message);
    }

    private static class MySimpleThread implements Runnable {
        @Override
        public void run() {
            String[] messages = {
              "Hello world !!!",
              "I am new to thread programming",
              "trying to be master in threads",
              "I know it will require lot of practice",
              "but I hope one day i will be expert",
              "and i will be more proud of myself"
            };

            for(int i = 0 ; i< messages.length; i++){
                printThread(messages[i] +" Am i interrupted -> "+ Thread.currentThread().isInterrupted());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {
                    // the reason why below line prints false because
                    // while using sleep() method if any thread has interrupted the current thread.
                    // The interrupted status of the current thread is cleared when this exception is thrown.
                    //Hence, the value printed below is false.
                    printThread("In exp block value of interrupted :: "+Thread.currentThread().isInterrupted());
                    printThread("I wasn't finished yet :( ");
                    return;
                }

            }
        }
    }


    //driver function
    public static void main(String[] args) throws InterruptedException{
        long startTime = System.currentTimeMillis();
        int patience = 5; // in seconds

        printThread(" Starting the executions");
        Thread thread1 = new Thread(new MySimpleThread());
        Thread thread2 = new Thread(new MySimpleThread());

        //starting the thread
        thread1.start();
        thread2.start();

        boolean flag = true;
        while(flag) {

            //Tests if this thread is alive. A thread is alive if it has been started and has not yet died.
            if(thread1.isAlive() && (System.currentTimeMillis() - startTime) > patience*1000) {
                printThread("Interrupting "+ thread1.getName());
                /**
                 * An interrupt is an indication to a thread that it should stop what it is doing and do something else.
                 * A thread sends an interrupt by invoking interrupt on the Thread object for the thread to be interrupted.
                 * Nice read : https://docs.oracle.com/javase/tutorial/essential/concurrency/interrupt.html
                 */
                thread1.interrupt();
                flag = false;
            }

            int sleepTime = 2000;
            printThread(" trying to sleep for "+ sleepTime +" ms.");
            thread1.join(sleepTime);
        }

        /**
         * We can join or interrupt a dead thread as below.
         */
        printThread("Am I alive "+ thread1.getName() + "? -> "+ thread1.isAlive());
        thread1.join();
        /**
         * The join method allows one thread to wait for the completion of another.
         * If t is a Thread object whose thread is currently executing,
         * t.join();
         * causes the current thread to pause execution until t's thread terminates.
         * https://docs.oracle.com/javase/tutorial/essential/concurrency/join.html
         */
        thread2.join();
        printThread("finally finished !!!");
    }

}
