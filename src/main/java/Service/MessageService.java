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
}
