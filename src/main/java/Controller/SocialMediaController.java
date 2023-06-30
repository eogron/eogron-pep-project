package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.*;
import Service.*;
import java.util.LinkedList;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
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
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesFromAccountHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.patch("messages/{message_id}", this::updateMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account createdAccount = accountService.createAccount(account);
        if (createdAccount != null) {
            ctx.json(mapper.writeValueAsString(createdAccount));
        } else {
            ctx.status(400);
        }
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        Account acc = m.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.login(acc);
        if (loginAccount != null) {
            ctx.json(m.writeValueAsString(loginAccount));
        } else {
            ctx.status(401);
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.createMessage(msg);
        if (createdMessage != null) {
            ctx.json(mapper.writeValueAsString(createdMessage));
        } else {
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        LinkedList<Message> messages = messageService.getAllMessages();
        ctx.json(m.writeValueAsString(messages));
    }

    private void getMessagesFromAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        LinkedList<Message> messages = messageService.getAllMessages(account_id);
        ctx.json(m.writeValueAsString(messages));
    }

    private void getMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message msg = messageService.getMessage(message_id);
        if (msg != null) {
            ctx.json(m.writeValueAsString(msg));
        } else {
            ctx.status(200);
        }
    }

    private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        String message_text = m.readValue(ctx.body(), Message.class).message_text;
        Message msg = messageService.updateMessage(message_id, message_text);
        if (msg != null) {
            ctx.json(m.writeValueAsString(msg));
        } else {
            ctx.status(400);
        }
    }

    private void deleteMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message msg = messageService.deleteMessage(message_id);
        if (msg != null) {
            ctx.json(m.writeValueAsString(msg));
        } else {
            ctx.status(200);
        }
    }
}