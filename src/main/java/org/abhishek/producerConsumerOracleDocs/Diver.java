package org.abhishek.producerConsumerOracleDocs;

public class Diver {
    public static void main(String[] args) {
        System.out.println("Starting Producer Consumer!!!");
        Messenger messenger = new Messenger();
        new Thread(new ConsumerMessenger(messenger, 5)).start();
        new Thread(new ProducerMessenger(messenger, 2)).start();

        System.out.println("Exiting Main");
    }
}
