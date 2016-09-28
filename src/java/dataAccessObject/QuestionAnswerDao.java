/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccessObject;

import DbConnect.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.TimeStamp;

/**
 *This class is to create DAO for question_answer
 * @author Hanwei Cheng
 */
public class QuestionAnswerDao {
    private int id;
    private String answer;
    private int account_info_id;
    private int security_question_id;
    private long timestamps_id;
    private Connection connection;
    Statement st = null;
    ResultSet rs = null;
    String sql = "";
    
     public QuestionAnswerDao() {
        //connect to database and select the record
        connection = DbConnection.getConnection();
        System.out.println("==AnswerDao connection==");
    }
    
    public int saveQuestionAnswer(String answer, int account_info_id, int security_question_id){
        // create new timeStamp
        TimeStamp time = new TimeStamp();
        long timestamps_id = time.getTimeStampsID();
        
        try {
                sql = "INSERT INTO INFSCI2731.security_question_answer(answer, account_info_id, security_question_id, timestamps_id) values (?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  
                // Parameters start with 1
                ps.setString(1, answer);               
                ps.setInt(2, account_info_id);               
                ps.setInt(3, security_question_id);  
                ps.setLong(4, timestamps_id);
                ps.executeUpdate();
                
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()) {
                    int autoKey = rs.getInt(1);
                    return autoKey;
                } else
                    return -1;

        } catch (SQLException e) {
                e.printStackTrace();
            return -1;            
        }         
        
    }
    
}
