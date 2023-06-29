package DAO;

import Util.ConnectionUtil;
import Model.Account;
import java.sql.*;

public class AccountDAO {
    public Account addAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.username);
            preparedStatement.setString(2, account.password);
            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int account_id = (int) pkeyResultSet.getLong(1);
                return new Account(account_id, account.username, account.password);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.executeQuery();

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}