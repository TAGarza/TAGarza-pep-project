package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    // create message
    public Message createMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            System.out.println(message.getMessage_text() + "\n" + message.getTime_posted_epoch());
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

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

    // get message by it's id 
    public Message messageById(int message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message);
            //System.out.println("message id: " + message.getMessage_id());

            //preparedStatement.executeQuery();
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                return new Message(message, rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            } 
            
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }

    // get all messages
    /*public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //System.out.println(message.getMessage_text() + "\n" + message.getTime_posted_epoch());

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), 
                                            rs.getInt("posted_by"), 
                                            rs.getString("message_text"), 
                                            rs.getLong("time_posted_epoch"));
                messages.add(message);
                
            } 
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }*/

    // delete message by it's id 
    /*public Message deleteByMessageId(int message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM message WHERE message_id = ? ";
            //PreparedStatement preparedStatement = connection.prepareStatement(sql);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
           
            preparedStatement.setInt(1, message);

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                //int generated_message_id = (int) pkeyRS.getLong(1);
                return new Message(, messageById(message).getPosted_by(), messageById(message).getMessage_text(), messageById(message).getTime_posted_epoch());
            } 
        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }*/
    
}
