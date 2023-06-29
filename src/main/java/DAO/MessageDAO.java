package DAO;

import Util.ConnectionUtil;
import Model.Message;
import java.sql.*;
import java.util.LinkedList;

public class MessageDAO {
    public Message addMessage(Message msg) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, msg.posted_by);
            preparedStatement.setString(2, msg.message_text);
            preparedStatement.setLong(3, msg.time_posted_epoch);
            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int msg_id = (int) pkeyResultSet.getLong(1);
                return new Message(msg_id, msg.posted_by, msg.message_text, msg.time_posted_epoch);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public LinkedList<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            LinkedList<Message> messages = new LinkedList<Message>();
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message msg = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(msg);
            }
            return messages;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
