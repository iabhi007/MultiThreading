package org.abhishek.waitandnotify;

/**
 *
 * Q 1. Print odd and even values in separate threads
 * Here we have used class level intrinsic lock
 * <p>
 */
public class EvenOddPrinter {

    public static class EvenOdd {

        private static int value = 1;

        public static synchronized void printOdd() {
            try {
                while(true) {
                    Thread.sleep(500);

                    while(value % 2 == 0 ) {
                        EvenOdd.class.wait();
                    }
                    System.out.printf("%s -> %d%n", Thread.currentThread().getName(), value);
                    value++;
                    EvenOdd.class.notifyAll();
                }
            }catch (Exception exp) {
                exp.printStackTrace();
            }
        }

        public static synchronized void printEven() {
            try {
                while(true) {
                    Thread.sleep(500);

                    while(value % 2 != 0 ) {
                        EvenOdd.class.wait();
                    }
                    System.out.printf("%s -> %d%n", Thread.currentThread().getName(), value);
                    value++;
                    EvenOdd.class.notifyAll();
                }
            }catch (Exception exp) {
                exp.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(EvenOdd::printOdd);
        Thread t2 = new Thread(EvenOdd::printEven);

        t1.setName("ODD");
        t2.setName("EVEN");

        t1.start();
        t2.start();

    }


}
