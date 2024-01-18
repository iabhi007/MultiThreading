package org.abhishek.volatileKeyword;

/**
 * Difference Between Static and Volatile :
 *
 * Static Variable: If two Threads(suppose t1 and t2) are accessing the same object and updating a
 * variable which is declared as static then it means t1 and t2 can make their own local copy of the same
 * object(including static variables) in their respective cache, so update made by t1 to the static variable
 * in its local cache wont reflect in the static variable for t2 cache .
 *
 * Static variables are used in the context of Object where update made by one object would reflect in all
 * the other objects of the same class but not in the context of Thread where update of one thread to the
 * static variable will reflect the changes immediately to all the threads (in their local cache).
 *
 * Volatile variable: If two Threads(suppose t1 and t2) are accessing the same object and updating a
 * variable which is declared as volatile then it means t1 and t2 can make their own local cache of the
 * Object except the variable which is declared as a volatile .
 * So the volatile variable will have only one main copy which will be updated by different threads and
 * update made by one thread to the volatile variable will immediately reflect to the other Thread
 *
 * Also, below demo the suggested way of stopping of the threads as the thread stop() method is deprecated.
 * The reason is :  Thread.stop is being deprecated because it is inherently unsafe.
 * Stopping a thread causes it to unlock all the monitors that it has locked.
 * (The monitors are unlocked as the ThreadDeath exception propagates up the stack.)
 * If any of the objects previously protected by these monitors was in an inconsistent state,
 * other threads might view these objects in an inconsistent state. Such objects are said to be damaged.
 *
 */
public class VolatileDemo {

    static class Worker implements Runnable {

        /**
         * The volatile keyword in Java is used to mark a Java variable as “being stored in main memory”.
         * Every thread that accesses a volatile variable will read it from main memory, and not from the
         * CPU cache. This way, all threads see the same value for the volatile variable.
         */
        public volatile static boolean terminate;

        @Override
        public void run() {
            while(!terminate) {
                System.out.printf("%s working..\n", Thread.currentThread().getName());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.printf("%s terminated..\n", Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0 ; i < 20 ; i++) {
            Thread thread = new Thread(new Worker());
            thread.setName(""+(i+1));
            thread.start();
        }

        Thread.sleep(3000);
        Worker.terminate = true;
        System.out.println("Stopped the threads\n");
    }
 }
