package DAO;

import Util.ConnectionUtil;
import Model.Message;
import java.sql.*;

public class MessageDAO {
    public Message addMessage(Message msg) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, msg.posted_by);
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
}
