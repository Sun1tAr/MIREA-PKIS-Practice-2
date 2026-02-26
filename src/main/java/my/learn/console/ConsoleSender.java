package my.learn.console;

import my.learn.model.message.Message;

public class ConsoleSender {

    public void sendMessage(Message message){
        System.out.println(message.getMessage());
    }

}
