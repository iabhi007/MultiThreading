package org.abhishek.concurrent_collections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class BlockingQueueDemo {

    static class ProducerBQ implements Runnable{
        private BlockingQueue<Integer> blockingQueue;

        @Override
        public void run() {
            try {
                for(int i = 0 ; i < Integer.MAX_VALUE ; i++) {
                    System.out.println("Producing "+ i);
                    blockingQueue.put(i);
                    Thread.sleep(200);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public ProducerBQ(BlockingQueue<Integer> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }
    }


    static class ConsumerBQ implements Runnable {

        private BlockingQueue<Integer> blockingQueue;

        public ConsumerBQ(BlockingQueue<Integer> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            try {
                while(true) {
                    System.out.println("consuming "+ blockingQueue.take());
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> blockingQueue =
                new LinkedBlockingDeque<>(10);
        Thread producer = new Thread(new ProducerBQ(blockingQueue));
        Thread consumer = new Thread(new ConsumerBQ(blockingQueue));

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();
    }
}
