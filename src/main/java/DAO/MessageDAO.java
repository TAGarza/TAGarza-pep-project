package DAO;
import java.sql.*;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    // create message
    public Message createMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (message_text, time_posted_epoch) VALUES (?, NOW())";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, message.getMessage_text());

            preparedStatement.executeUpdate();
            ResultSet pkeyRS = preparedStatement.getGeneratedKeys();
            if(pkeyRS.next()){
                int generated_message_id = (int) pkeyRS.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            } 
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}
