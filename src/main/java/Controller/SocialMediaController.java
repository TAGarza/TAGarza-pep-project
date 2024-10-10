package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
        
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // account
        app.post("/register", this::postAccountHandler); // create account
        app.post("/login", this::loginAccountHandler);   // verify login
        app.get("/accounts/{account_id}", this::allMessagesByAccountHandler); // get all messages from a user

        // message
        app.post("/messages", this::createMessageHandler);// create message
        app.get("/messages/{message_id}", this::messageByIdHandler); // get message by ID
        app.get("/messages/", this::getAllMessagesHandler); // get all messages
        app.delete("/messages/{message_id}", this::deleteByMessageIdHandler); // delete message by id
        app.patch("/messages/{message_id}", this::updateByMessageIdHandler); // update message
        app.get("/accounts/{account_id}/messages", this::allMessagesByAccountHandler); // get all messages from a user
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     
    private void exampleHandler(Context context) {
        context.json("sample text");
    }*/

    // for account creation
    private void postAccountHandler(Context context) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.createAccount(account);

        if(addedAccount!=null){
            context.json(mapper.writeValueAsString(addedAccount));
            context.status(200);
        }else{
            context.status(400);
        }
        //context.json("sample text");
    }

    // for login verification
    private void loginAccountHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account verifiedAccount = accountService.verifyAccount(account);

        if(verifiedAccount!=null){
            context.json(mapper.writeValueAsString(verifiedAccount));
            context.status(200);
        }else{
            context.status(401);
        }
    }

    // for message creation
    private void createMessageHandler(Context context) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);

        if(createdMessage!=null){
            context.json(mapper.writeValueAsString(createdMessage));
            context.status(200);
        }else{
            context.status(400);
        }

    }

    // for retrieving message by it's id
    private void messageByIdHandler(Context context) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message retrievedMessage = messageService.messageById(Integer.parseInt(context.pathParam("message_id")));
        
        if(retrievedMessage!=null){
            context.status(200).json(mapper.writeValueAsString(retrievedMessage));
        }
        else{
            context.status(200);
        }


    }

    // for retrieving all messages
    private void getAllMessagesHandler(Context context) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        List<Message> messages = messageService.getAllMessages();
        
        if(messages!=null){
            context.status(200).json(mapper.writeValueAsString(messages));
        }
        else{
            context.status(200);
        }
    }

    // for deleting a message by it's id
    private void deleteByMessageIdHandler(Context context) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message deletedMessage = messageService.deleteByMessageId(Integer.parseInt(context.pathParam("message_id")));

        if(deletedMessage!=null){
            context.status(200).json(mapper.writeValueAsString(deletedMessage));
        }
        else{
            context.status(200);
        }
    }
    // for updating a message by it's id
    private void updateByMessageIdHandler(Context context) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message newText = mapper.readValue(context.body(), Message.class);;
        Message updatedMessage = messageService.updateByMessageId(id, newText);
        Message messageId = messageService.messageById(id);
        
        if(updatedMessage != null){
            context.status(200).json(mapper.writeValueAsString(updatedMessage));
        }
        else{
            context.status(400);
        }
    }

    // for retrieving all messages from an account
    private void allMessagesByAccountHandler(Context context) throws JsonMappingException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        List<Message> messages = messageService.getAllMessagesFromAccount(Integer.parseInt(context.pathParam("account_id")));
        int user = Integer.parseInt(context.pathParam("account_id"));

        if(messages!=null){
            context.status(200).json(mapper.writeValueAsString(messages));
            //context.json(messages);
        }
        else{
            context.status(200);
        }
    }
}
