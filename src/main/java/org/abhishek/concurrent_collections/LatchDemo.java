package org.abhishek.concurrent_collections;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LatchDemo {

    static class LatchWorker implements Runnable{
        private int id;
        private CountDownLatch latch;

        public LatchWorker(int id, CountDownLatch latch) {
            this.id = id;
            this.latch = latch;
        }

        @Override
        public void run() {
            System.out.println("Started the task with Thread id :"+ id);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // this doesnt block the execution.. it only just decreases the count of the latch..
            latch.countDown();
            System.out.println("Thread "+id+" is not blocked and thread will continue with execution... ");
            System.out.println("The latch count "+ latch.getCount());
        }
    }


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for(int i = 0 ; i < 5 ;++i) {
            executorService.execute(new LatchWorker(i+1, latch));
        }

        System.out.println("Started to wait for countdown latch..");
        // this makes the invoking thread to wait till the value of countdown latch reaches 0.
        latch.await();
        System.out.println("Finished waiting for countdown latch..");

        // Shutting down the executor service...
        try {
            executorService.shutdownNow();
            if(!executorService.awaitTermination(3, TimeUnit.SECONDS)){
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdownNow();
        }
    }
}
