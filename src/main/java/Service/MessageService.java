package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.LinkedList;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }
    

    public Message createMessage(Message msg) {
        if (msg.message_text == "" || msg.message_text.length() >= 255) {
            return null;
        } else {
            return messageDAO.addMessage(msg);
        }
    }

    public LinkedList<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public LinkedList<Message> getAllMessages(int account_id) {
        return messageDAO.getAllMessages(account_id);
    }

    public Message getMessage(int message_id) {
        return messageDAO.getMessage(message_id);
    }

    public Message updateMessage(int message_id, String message_text) {
        if (message_text == "" || message_text.length() >= 255) {
            return null;
        }
        int res = messageDAO.updateMessage(message_id, message_text);
        if (res <= 0) {
            return null;
        }
        return messageDAO.getMessage(message_id);
    }

    public Message deleteMessage(int message_id) {
        Message msg = messageDAO.getMessage(message_id);
        int res = messageDAO.deleteMessage(message_id);
        if (res <= 0) {
            return null;
        }
        return msg;
    }
}
