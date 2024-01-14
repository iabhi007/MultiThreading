package org.abhishek.producerConsumerOracleDocs;

public class ConsumerMessenger implements Runnable{

    Messenger messenger;
    int times;

    public ConsumerMessenger(Messenger messenger, int times){
        this.messenger = messenger;
        this.times = times;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Consumer_");
        String message = "";
        while (!message.equals("DONE")) {
            message = messenger.receiveMessage();
            try{
                Thread.sleep(times*1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
