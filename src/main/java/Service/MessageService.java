package Service;

import Model.*;
import DAO.*;

public class MessageService {
    private MessageDAO messageDAO;
    // private AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        // accountDAO = new AccountDAO();
    }
    /*
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
        accountDAO = new AccountDAO();
    }
    public MessageService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
        messageDAO = new MessageDAO();
    }
    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO) {
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }
    */
    

    public Message createMessage(Message msg) {
        if (msg.message_text == "" || msg.message_text.length() >= 255) {
            return null;
        } else {
            return messageDAO.addMessage(msg);
        }
    }
}
