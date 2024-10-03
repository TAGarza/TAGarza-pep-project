package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        //app.get("example-endpoint", this::exampleHandler);

        // account
        app.post("/register", this::postAccountHandler); // create account
        app.post("/login", this::loginAccountHandler);   // verify login

        // message
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



}