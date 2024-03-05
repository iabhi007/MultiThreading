package org.abhishek.executors;

import java.util.concurrent.*;

public class ExecutorDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //The easiest way to create ExecutorService is to use one of the factory methods of the Executors class.
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Runnable runnable = () -> {
            try {
                System.out.println("Runnable task started");
                Thread.sleep(2000);
                System.out.println("Runnable task finished");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Callable<String> callable = () -> {
            try {
                System.out.println("Callable task started... ");
                Thread.sleep(3000);
                System.out.println("Callable task finished... ");
                return "Returned_fromCallable";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Future<?> submit = executorService.submit(runnable);
        System.out.println("before runnable future get() method");
        //Calling the get() method while the task is still running will cause execution to
        // block until the task properly executes and the result is available.
        System.out.println(submit.get());
        System.out.println("after runnable future get() method");


        Future<String> callableFuture = executorService.submit(callable);
        System.out.println("before callable future get() method");
        System.out.println(callableFuture.get());
        System.out.println("after callable future get() method");


        // Callable with exception
        Callable<String> callableWithException = () -> {
            int i = 10/0;
            return "this will not be returned";
        };

        Future<String> stringFutureWithException = executorService.submit(callableWithException);
        //In case of running a Callable using an ExecutorService,
        // the exceptions are collected in the Future object.
        // We can check this by making a call to the Future.get() method.
        //In the above test, the ExecutionException is thrown since we are passing an invalid number.
        // We can call the getCause() method on this exception object to get the original checked exception.
        System.out.println("The exception from the callable won't be thrown till we invoke get()");
        try {
            System.out.println(stringFutureWithException.get());
        } catch (Exception exp) {
            System.out.println("The exception thrown is "+ exp.getClass());
            System.out.println("The underlying exception thrown is "+ exp.getCause().getClass());

        }


        /**
         * To properly shut down an ExecutorService, we have the shutdown() and shutdownNow() APIs.
         * The shutdown() method does not cause immediate destruction of the ExecutorService.
         * It will make the ExecutorService stop accepting new tasks and shut down after all
         * running threads finish their current work:
         *
         * The shutdownNow() method tries to destroy the ExecutorService immediately,
         * but it does not guarantee that all the running threads will be stopped at the same time:
         *
         * This method returns a list of tasks that are waiting to be processed.
         * It is up to the developer to decide what to do with these tasks.
         *
         * One good way to shut down the ExecutorService (which is also recommended by Oracle)
         * is to use both of these methods combined with the awaitTermination() method
         *
         * If we don't shut down the executorService,
         * an app could reach its end but not be stopped because a waiting
         * ExecutorService will cause the JVM to keep running.
         */
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
