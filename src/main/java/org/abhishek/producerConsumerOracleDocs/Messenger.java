package org.abhishek.producerConsumerOracleDocs;

/**
 * Below producer consumer is example of guarded block
 * https://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html
 *
 * Thread Notification:
 * There is a second notification method, notify(), which wakes up a single thread.
 * Because notify doesn't allow you to specify the thread that is woken up,
 * it is useful only in massively parallel applications — that is, programs with a large number of threads,
 * all doing similar chores. In such an application, you don't care which thread gets woken up.
 */
public class Messenger {

    private String message;
    private boolean empty = true;

    private final Object lock = new Object();

    // Producer code
    public void createMessage(String message) {
        /**
         * Why we used a lock object and performed all wait and synchronisation as well as notifyAll on same lock.
         * When a thread invokes wait(), it must own the intrinsic lock for the lock — otherwise an error is thrown.
         * The error is : java.lang.IllegalMonitorStateException
         * Invoking wait inside a synchronized method is a simple way to acquire the intrinsic lock.
         *
         * When wait is invoked, the thread releases the lock and suspends execution.
         * At some future time, another thread will acquire the same lock and invoke Object.notifyAll,
         * informing all threads waiting on that lock that something important has happened.
         * Difference between thread notification is mentioned above.
         */

        synchronized (lock) {
            /**
             * Always invoke wait inside a loop that tests for the condition being waited for.
             * Don't assume that the interrupt was for the particular condition you were waiting for,
             * or that the condition is still true.
             * The reason being the behavior or notify and notifyAll methods as below,
             * A more efficient guard invokes Object.wait to suspend the current thread.
             * The invocation of wait does not return until another thread has issued a notification(via notify or notifyAll)
             * that some special event may have occurred — though not necessarily the event this thread is waiting for
             */
            while(!empty) {
                try{
                    lock.wait();
                } catch (InterruptedException e){

                }
            }
            System.out.println("From "+ Thread.currentThread().getName()+ " -> "+message);
            // toggle the flag and set the message and notifyAll
            empty = false;
            this.message = message;
            lock.notifyAll();
        }
    }

    //consumer code
    public String receiveMessage( ) {
        synchronized (lock) {
            /**
             * Always invoke wait inside a loop that tests for the condition being waited for.
             * Don't assume that the interrupt was for the particular condition you were waiting for,
             * or that the condition is still true.
             * The reason being the behavior or notify and notifyAll methods as below,
             * A more efficient guard invokes Object.wait to suspend the current thread.
             * The invocation of wait does not return until another thread has issued a notification(via notify or notifyAll)
             * that some special event may have occurred — though not necessarily the event this thread is waiting for
             */
            while (empty){
                try{
                    lock.wait();
                }catch (InterruptedException e) {

                }
            }

            System.out.println("From "+ Thread.currentThread().getName()+ " -> "+message);
            // toggle the flag and consumer the message and notify the producer
            empty = true;
            lock.notifyAll();
            return  this.message;
        }
    }





}
