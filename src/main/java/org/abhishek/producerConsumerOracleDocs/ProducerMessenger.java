package org.abhishek.producerConsumerOracleDocs;

public class ProducerMessenger implements Runnable{
    Messenger messenger;
    int times;

    public ProducerMessenger(Messenger messenger, int times){
        this.messenger = messenger;
        this.times = times;
    }

    @Override
    public void run()  {
        String messages[] = {"A","B","C","D"};
        Thread.currentThread().setName("Producer_");
        for(int i = 0; i < messages.length ; ++i) {
            String message = messages[i];
            messenger.createMessage(message);
            try{
                Thread.sleep(times*1000);
            } catch (InterruptedException e) {

            }

        }
        messenger.createMessage("DONE");
    }


}
