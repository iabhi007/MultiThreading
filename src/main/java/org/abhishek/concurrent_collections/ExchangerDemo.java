package org.abhishek.concurrent_collections;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ThreadLocalRandom;


/**
 * The Exchanger class in Java can be used to share objects between two threads of type T.
 * The class provides only a single overloaded method exchange(T t).
 *
 * When invoked exchange waits for the other thread in the pair to call it as well.
 * At this point, the second thread finds the first thread is waiting with its object.
 * The thread exchanges the objects they are holding and signals the exchange, and now they can return.
 */
public class ExchangerDemo {

    static class OddEvenExchanger {

        private final Exchanger<String> exchanger;

        public OddEvenExchanger(Exchanger<String> exchanger) {
            this.exchanger = exchanger;
        }

        public void exchangeFromA() {
            try {
                while (true) {
                    String valueFromB = exchanger.exchange("From A");
                    System.out.println("This will be blocked till exchange in A");
                    System.out.println("print from A ->>> "+valueFromB);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void exchangeFromB() {
            try {
                while (true) {
                    Thread.sleep(2000);
                    System.out.println("This will be waiting till exchange in B");
                    String valueFromA = exchanger.exchange("From B");
                    System.out.println("This will be blocked till exchange in B");
                    System.out.println("print from B ->>> "+valueFromA);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        OddEvenExchanger evenExchanger = new OddEvenExchanger(exchanger);

        new Thread(evenExchanger::exchangeFromA).start();
        new Thread(evenExchanger::exchangeFromB).start();


    }
}
