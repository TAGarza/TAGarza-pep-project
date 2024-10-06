package Service;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    public MessageDAO messDAO;

    // create a MessageDAO
    public MessageService(){
        messDAO = new MessageDAO();
    }

     // constructor for when MessageDAO is provided
     public MessageService(MessageDAO messDAO){
        this.messDAO = messDAO;
    }

    // create new message
    public Message createMessage(Message message){
        // make sure posted_by is a listed user
        // make sure message_text > 255 characters
        // make sure message_text is not blank
        if(message.getMessage_text() != "" && message.getMessage_text().length() <= 255){
            return messDAO.createMessage(message);
        }
        return null;

    }
    
}
