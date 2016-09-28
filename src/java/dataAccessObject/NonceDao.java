/*
 * This class contains methods to manipulate nonce
 */
package dataAccessObject;

import DbConnect.DbConnection;
import model.Nonce;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Siwei Jiao
 */
public class NonceDao {
    private Connection connection;
    private String nonceval = "";
    Statement st = null;
    ResultSet rs = null;

    String sql = "";
    int autoKey = 0;
    
    public NonceDao() {
        //connect to database and select the record
        connection = DbConnection.getConnection();	
    }
       
    /**
     * create a new nonce that related to a user account
     * @param accoutInfoID
     * @return String Nonce value
     */
    public String getNewNonce(int accoutInfoID) {
        //delete all old nonce of the user
        deleteNonceByUserID(accoutInfoID);
        
             SecureRandom srnd = new SecureRandom();
    /*This BigInteger constructor is used to construct a randomly generated BigInteger, 
      uniformly distributed over the range 0 to (2^maxBitLength - 1), inclusive. 
       mysql signed BIGINT ranges from  -9223372036854775808 to 9223372036854775808, equal to 2^63 − 1
    */
        int maxBitLength = 63;
        BigInteger val = new BigInteger(maxBitLength, srnd);
        BigDecimal nonceValue = new BigDecimal(val);
        
        TimeStampDao timeStampDao = new TimeStampDao();
        long timeStampsID = timeStampDao.setUpTimeStamp();
                
        try {
                sql = "INSERT INTO INFSCI2731.nonce (nonce, timestamps_id, account_info_id) values (?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  
                // Parameters start with 1
                ps.setBigDecimal(1, nonceValue);               
                ps.setLong(2, timeStampsID);               
                ps.setInt(3, accoutInfoID);               
                ps.executeUpdate();
                
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()) {
                    return nonceValue.toPlainString();
                }else
                    return "error";
                
        } catch (SQLException e) {
                e.printStackTrace();
            return "error";            
        }         
                     
    }
    
    /**
     * get Nonce object which include all db nonce info related to a user account by nonce id 
     * @param nonceID
     * @return Nonce object
     */
    public Nonce getNonceByNonceValue(String nonceval) {
        Nonce nonce = new Nonce();

        try{
            //prepare and execute search query
            sql = "SELECT * FROM INFSCI2731.nonce WHERE nonce = ?"; 
            PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1,nonceval) ;                
                rs = ps.executeQuery() ;

            while (rs.next()) { 
                nonce.setNonceID(rs.getInt("id"));
                nonce.setNonceValue(rs.getBigDecimal("nonce"));
                nonce.setTimeStampsID(rs.getInt("timestamps_id"));
                nonce.setAccountInfoID(rs.getInt("account_info_id"));                        
            }

        rs.close();

        } catch (SQLException e) {
                e.printStackTrace();
        }    
               
        return nonce;
    }
    
    /**
     * get nonce id by its value
     * @param nonceval
     * @return nonce id
     */
    public int getIDByNonceValue(String nonceval) {

        try{
            //prepare and execute search query
            sql = "SELECT id FROM INFSCI2731.nonce WHERE nonce = ?"; 
            PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1,nonceval) ;                
                rs = ps.executeQuery() ;

            if(rs.next()) {
                return rs.getInt("id");
            }else
                return 0 ;


        } catch (SQLException e) {
                e.printStackTrace();
                return -1;
        }    
               
    }
    
    /**
     * this method delete all nonce related to a certain user
     * also delete all related timestamp
     * @param accoutInfoID 
     */
    public void deleteNonceByUserID(int accoutInfoID) {
        //get old timestamp id before deletion
        int timestampid = getNonceTimeStampID(accoutInfoID);
        
        //delete all nonce related to a certain user
        try{
            //prepare and execute search query
            sql = "DELETE FROM INFSCI2731.nonce WHERE account_info_id = ?"; 
            PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1,accoutInfoID);                
                ps.executeUpdate() ;

        } catch (SQLException e) {
                e.printStackTrace();
        }   
        
        //delete old timestamp in timestamps table
        TimeStampDao timeStampDao = new TimeStampDao();
        timeStampDao.deleteTimeStampByID(timestampid);
    }  
    
    /**
     * get timestamp id of a nonce by user id
     * since deleting old nonce before creating one, there's only one old nonce in db related to a user
     * @param accoutInfoID
     * @return timestamp id
     */
    public int getNonceTimeStampID(int accoutInfoID) {
              
        try{
            //prepare and execute search query
            sql = "SELECT timestamps_id FROM INFSCI2731.nonce WHERE account_info_id = ?"; 
            PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1,accoutInfoID);                
                rs = ps.executeQuery() ;

            if(rs.next()) {
                return rs.getInt("timestamps_id");
            }else
                return 0 ;
            
        } catch (SQLException e) {
                e.printStackTrace();
                return -1;
        }          
    }
    
    /**
     * get creation time of a nonce
     * @param nonce
     * @return Timestamp create_time of nonce
     */
    public Timestamp getNonceCreatetime(String nonce) {
        
        Timestamp timestamp = null;
        
        try {
            sql = "SELECT timestamps.create_time FROM nonce, timestamps WHERE nonce.timestamps_id = timestamps.id AND nonce = ?";
            PreparedStatement ps = connection.prepareStatement(sql);  
            ps.setString(1, nonce);
            
            ResultSet rs = ps.executeQuery();           
            
            if (rs.next()) {
                timestamp = rs.getTimestamp("create_time");
            }
  
        } catch (SQLException e) {
                e.printStackTrace();
        }   
        
        return timestamp;
    }
    
//    public List<Integer> getAllNonceTimeStampID (int accoutInfoID) {
//        List<Integer> ids = new ArrayList<Integer>();  
//        
//        try{
//            //prepare and execute search query
//            sql = "SELECT timestamps_id FROM INFSCI2731.nonce WHERE account_info_id = ?"; 
//            PreparedStatement ps = connection.prepareStatement(sql);
//                ps.setInt(1,accoutInfoID);                
//                rs = ps.executeQuery() ;
//
//            while (rs.next()) { 
//                int id = rs.getInt("account_info_id");
//                ids.add(id);
//            }
//
//        rs.close();
//
//        } catch (SQLException e) {
//                e.printStackTrace();
//        }  
//        
//        return ids;
//    }

}
