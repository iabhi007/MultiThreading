package org.abhishek.executors;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class CallableDemo {

    static class Processor implements Callable<String> {

        private int id;

        public Processor(int id) {
            this.id = id;
        }
        @Override
        public String call() throws Exception {
            System.out.println("Inside processor with id "+ id);
            Thread.sleep(1000);
            return "id : "+ id + " executed by thread :" + Thread.currentThread().getName();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<String>> list = new LinkedList<>();

        System.out.println("Submitting the tasks to executor thread pool ......");
        for(int i = 0 ; i < 15 ; i++) {
            list.add(executor.submit(new Processor(i+1)));
        }
        System.out.println("Submitted. but tasks are not executed till now.. executing those by invoking get(),");
        for(Future<String > future: list) {
            System.out.println(future.get());
        }

        // shutting off the executor
        executor.shutdown();
        if(!executor.awaitTermination(10000, TimeUnit.MILLISECONDS)){
            executor.shutdownNow();
        }
        System.out.println("Executor service shutdown completed !!!");
    }
}
