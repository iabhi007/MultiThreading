package org.abhishek.semaphores;

import java.util.concurrent.Semaphore;

/**
 * Using semaphores to print even-odd integers.
 * Below example uses two semaphores to signal other thread.
 *
 * The below example also works with one semaphore if we increment the counter (commented-out)
 * in the if block. But using two semaphore acts more like what semaphores are meant for i.e.
 * signalling the other threads.
 */
public class BinarySemaphoreDemo {


    static class BinarySemaphoreWorker {
        private static final Semaphore semaphoreOdd = new Semaphore(1);
        private static final Semaphore semaphoreEven = new Semaphore(0);

        private static int counter = 1;

        public static int getCounter() {
            return counter;
        }

        public static void setCounter(int counter) {
            BinarySemaphoreWorker.counter = counter;
        }

        public static void printOdd () {
            try {
                while (true){
                    semaphoreOdd.acquire();
                    if(counter % 2 == 1) {
                        System.out.printf("%s -> %d\n", Thread.currentThread().getName(), counter);
                        Thread.sleep(1000);
                        //counter++;
                    }
                    counter++;
                    semaphoreEven.release();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        public static void printEven () {
            try {
                while(true) {
                    semaphoreEven.acquire();
                    if(counter % 2 == 0) {
                        System.out.printf("%s -> %d\n", Thread.currentThread().getName(), counter);
                        Thread.sleep(1000);
                        //counter++;
                    }
                    counter++;
                    semaphoreOdd.release();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting the demo using semaphores");

        new Thread(BinarySemaphoreWorker::printOdd, "Odd thread").start();
        new Thread(BinarySemaphoreWorker::printEven, "Even thread").start();

        System.out.println("Exiting the demo using semaphores");
    }


}
