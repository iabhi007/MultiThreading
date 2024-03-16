package org.abhishek.concurrent_collections;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierDemo {

    static class CyclicBarrierWorker implements Runnable{
        private int id;

        private CyclicBarrier barrier;

        public CyclicBarrierWorker(int id, CyclicBarrier barrier) {
            this.id = id;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println("Started thread with ID "+ id);
                Thread.sleep(2000);
                barrier.await();
                System.out.println("This execution will be blocked until all threads resume");
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(5 , () -> {
            System.out.println("This will be executed one the barrier is reset");
        });
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for(int i = 0 ; i < 15 ; i++) {
            executorService.execute(new CyclicBarrierWorker(i+1, barrier));
        }

        // stopping the executor service.
        try {
            executorService.shutdown();
            if(!executorService.awaitTermination(10000, TimeUnit.SECONDS)){
                executorService.shutdownNow();
            }
        } catch (Exception exception) {
            executorService.shutdownNow();
        }
    }
}
