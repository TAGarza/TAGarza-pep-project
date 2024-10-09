package Service;

import java.util.List;

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

    // retrieve message by it's id
    public Message messageById(int message){
        return messDAO.messageById(message);
    }

    // retrieve all messages
    public List<Message> getAllMessages(){
        return messDAO.getAllMessages();
    }

    // delete message by it's id
    public Message deleteByMessageId(int message){
        return messDAO.messageById(message);
    }

     // update message by it's id
     public Message updateByMessageId(Message message){
        // update iff message id exists and
        // new message != "" and  <= 255 char
        if(message.getMessage_text() != "" && message.getMessage_text().length() <= 255){
            return messDAO.messageById(message.getMessage_id());
        }
        return null;
           
    }
    // retrieve all messages from an account
    public List<Message> getAllMessagesFromAccount(int id){
        return messDAO.getAllMessagesFromAccount(id);
    }
}
