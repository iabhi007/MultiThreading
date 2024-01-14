package org.abhishek.producerConsumer;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    
    public static class ProducerConsumer {

        private static final Object lock = new Object();

        // Define upper limit on capacity of the message list
        private static final int listSize = 5;

        //Holder of messages
        List<Integer> messages = new ArrayList<>();

        public void produce() {
            synchronized (lock) {
                try {
                    while(true) {
                        while(listSize == messages.size()){
                            lock.wait();
                        }
                        for(int i = 0 ; i < listSize; i++) {
                            Thread.sleep(250);
                            System.out.println("Producing .." + (i+1));
                            messages.add(i+1);
                        }
                        Thread.sleep(1000);
                        lock.notify();
                    }
                 } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
        }

        public void consume() {
            synchronized (lock) {
                try {
                    while(true) {
                        while(messages.size() == 0) {
                            lock.wait();
                        }
                        for(int i = 0 ; i < listSize; i++) {
                            Thread.sleep(250);
                            System.out.println("Consuming ..." + messages.remove(0));
                        }
                        Thread.sleep(2000);
                        lock.notify();
                    }
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
        }

    }

    // Driver method

    public static void main(String[] args) throws InterruptedException {
        ProducerConsumer producerConsumer = new ProducerConsumer();


        Thread producer = new Thread(producerConsumer::produce);
        Thread consumer = new Thread(producerConsumer::consume);

        System.out.println("Starting producer and consumers ...");
        consumer.start();
        producer.start();

        producer.join();
        consumer.join();

    }
}
