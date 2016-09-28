/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import model.TimeStamp;
/**
 *
 * @author Siwei Jiao
 */
public class ActivityLogDao {
    private Connection connection;
    ResultSet rs = null;

    String sql = "";
    int autoKey = 0;
    
    public ActivityLogDao() {
        //connect to database and select the record
        connection = DbConnection.getConnection();	
    }
    
    public int logAccessAttempt(String ip, String sysSource, String desc) {
         
        TimeStamp time = new TimeStamp();
        long timeStampsID = time.getTimeStampsID();
                
        //prepare and execute search query
        try{   
                sql = "INSERT INTO INFSCI2731.activity_log (ip_addr, system_source, activity_count, timestamps_id, description, account_info_id) "
                        + "values (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  
                ps.setString(1, ip);          
                ps.setString(2, sysSource);          
                ps.setInt(3, 1); 
                ps.setLong(4, timeStampsID);               
                ps.setString(5, desc); 
                ps.setInt(6, 0); 
                           
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();

                //check if query returns one valid result 
                if(rs.next())
                    return autoKey = rs.getInt(1);
                else
                    return 0;

            }catch (Exception e) {
                    System.out.println(e.getMessage()) ;
                    return -1;
            }        
    }
    
    /**
     * 
     * @param ip
     * @param sysSource
     * @param accoutInfoID
     * @return 
     */
    public int logNewAccountCreated(String ip, String sysSource, int accoutInfoID) {
         
        TimeStamp time = new TimeStamp();
        long timeStampsID = time.getTimeStampsID();
        String desc = "new account created";
                
        //prepare and execute search query
        try{   
                sql = "INSERT INTO INFSCI2731.activity_log (ip_addr, system_source, activity_count, timestamps_id, description, account_info_id) "
                        + "values (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  
                ps.setString(1, ip);          
                ps.setString(2, sysSource);          
                ps.setInt(3, 1); 
                ps.setLong(4, timeStampsID);               
                ps.setString(5, desc); 
                ps.setInt(6, accoutInfoID); 
                           
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();

                //check if query returns one valid result 
                if(rs.next())
                    return autoKey = rs.getInt(1);
                else
                    return 0;

            }catch (Exception e) {
                    System.out.println(e.getMessage()) ;
                    return -1;
            }        
    }
    
    /**
     * 
     * @param ip
     * @param sysSource
     * @param userEneteredEmail
     * @return 
     */
    public int logEmailFailedLoginAttempt(String ip, String sysSource, String userEneteredEmail) {
         
        TimeStamp time = new TimeStamp();
        long timeStampsID = time.getTimeStampsID();
        String desc = "(login)non exist email: ";
                
        //prepare and execute search query
        try{   
                sql = "INSERT INTO INFSCI2731.activity_log (ip_addr, system_source, activity_count, timestamps_id, description, account_info_id) "
                        + "values (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  
                ps.setString(1, ip);          
                ps.setString(2, sysSource);          
                ps.setInt(3, 1); 
                ps.setLong(4, timeStampsID);               
                ps.setString(5, desc+userEneteredEmail); 
                ps.setInt(6, 0); 
                           
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();

                //check if query returns one valid result 
                if(rs.next())
                    return autoKey = rs.getInt(1);
                else
                    return 0;

            }catch (Exception e) {
                    System.out.println(e.getMessage()) ;
                    return -1;
            }        
    }
    
    /**
     * 
     * @param ip
     * @param sysSource
     * @param desc
     * @param accoutInfoID
     * @return 
     */
    public int logPwFailedLoginAttempt(String ip, String sysSource, String desc, int accoutInfoID) {
         
        TimeStamp time = new TimeStamp();
        long timeStampsID = time.getTimeStampsID();
                
        //prepare and execute search query
        try{   
                sql = "INSERT INTO INFSCI2731.activity_log (ip_addr, system_source, activity_count, timestamps_id, description, account_info_id) "
                        + "values (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  
                ps.setString(1, ip);          
                ps.setString(2, sysSource);          
                ps.setInt(3, 1); 
                ps.setLong(4, timeStampsID);               
                ps.setString(5, desc); 
                ps.setInt(6, accoutInfoID); 
                           
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();

                //check if query returns one valid result 
                if(rs.next())
                    return autoKey = rs.getInt(1);
                else
                    return 0;

            }catch (Exception e) {
                    System.out.println(e.getMessage()) ;
                    return -1;
            }        
    }
    
    /**
     * 
     * @param ip
     * @param sysSource
     * @param accoutInfoID
     * @return 
     */
    public int logSucceedLogon(String ip, String sysSource, int accoutInfoID) {
         
        TimeStamp time = new TimeStamp();
        long timeStampsID = time.getTimeStampsID();
        String desc = "successfully logon";
                
        //prepare and execute search query
        try{    
                sql = "INSERT INTO INFSCI2731.activity_log (ip_addr, system_source, activity_count, timestamps_id, description, account_info_id) "
                        + "values (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  
                ps.setString(1, ip);          
                ps.setString(2, sysSource);          
                ps.setInt(3, 1); 
                ps.setLong(4, timeStampsID);               
                ps.setString(5, desc); 
                ps.setInt(6, accoutInfoID); 
                           
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();

                //check if query returns one valid result 
                if(rs.next())
                    return autoKey = rs.getInt(1);
                else
                    return 0;

            }catch (Exception e) {
                    System.out.println(e.getMessage()) ;
                    return -1;
            }        
    }
    
    /**
     * 
     * @param ip
     * @param sysSource
     * @param userEneteredEmail
     * @return 
     */
    public int logEmailFailedOnForgotPw(String ip, String sysSource, String userEneteredEmail) {
         
        TimeStamp time = new TimeStamp();
        long timeStampsID = time.getTimeStampsID();
        String desc = "(forgot pw)non exist email: ";
                
        //prepare and execute search query
        try{   
                sql = "INSERT INTO INFSCI2731.activity_log (ip_addr, system_source, activity_count, timestamps_id, description, account_info_id) "
                        + "values (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  
                ps.setString(1, ip);          
                ps.setString(2, sysSource);          
                ps.setInt(3, 1); 
                ps.setLong(4, timeStampsID);               
                ps.setString(5, desc+userEneteredEmail); 
                ps.setInt(6, 0); 
                           
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();

                //check if query returns one valid result 
                if(rs.next())
                    return autoKey = rs.getInt(1);
                else
                    return 0;

            }catch (Exception e) {
                    System.out.println(e.getMessage()) ;
                    return -1;
            }        
    }
    
    /**
     * 
     * @param ip
     * @param sysSource
     * @param accoutInfoID
     * @return 
     */
    public int logAnswerSecurQuestFailedOnForgotPw(String ip, String sysSource, int accoutInfoID) {
         
        TimeStamp time = new TimeStamp();
        long timeStampsID = time.getTimeStampsID();
        String desc = "(forgot pw)failed answer securQuest";
                
        //prepare and execute search query
        try{   
                sql = "INSERT INTO INFSCI2731.activity_log (ip_addr, system_source, activity_count, timestamps_id, description, account_info_id) "
                        + "values (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  
                ps.setString(1, ip);          
                ps.setString(2, sysSource);          
                ps.setInt(3, 1); 
                ps.setLong(4, timeStampsID);               
                ps.setString(5, desc); 
                ps.setInt(6, accoutInfoID); 
                           
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();

                //check if query returns one valid result 
                if(rs.next())
                    return autoKey = rs.getInt(1);
                else
                    return 0;

            }catch (Exception e) {
                    System.out.println(e.getMessage()) ;
                    return -1;
            }        
    }
    
    /**
     * 
     * @param accoutInfoID
     * @return 
     */
    public int checkResetPwSentLink(int accoutInfoID) {
        String desc = "(forgot pw)reset pw : sent link";

        try{
            //prepare and execute search query
            sql = "SELECT id FROM INFSCI2731.activity_log WHERE account_info_id = ? AND description = ?"; 
            PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1,accoutInfoID) ;                
                ps.setString(2,desc) ;                
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
     * 
     * @param ip
     * @param sysSource
     * @param accoutInfoID
     * @return 
     */
    public int logPreResetPwOnForgotPw(String ip, String sysSource, int accoutInfoID) {
         
        TimeStamp time = new TimeStamp();
        long timeStampsID = time.getTimeStampsID();
        String desc = "(forgot pw)reset pw : sent link";
                
        //prepare and execute search query
        try{   
                sql = "INSERT INTO INFSCI2731.activity_log (ip_addr, system_source, activity_count, timestamps_id, description, account_info_id) "
                        + "values (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  
                ps.setString(1, ip);          
                ps.setString(2, sysSource);          
                ps.setInt(3, 1); 
                ps.setLong(4, timeStampsID);               
                ps.setString(5, desc); 
                ps.setInt(6, accoutInfoID); 
                           
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();

                //check if query returns one valid result 
                if(rs.next())
                    return autoKey = rs.getInt(1);
                else
                    return 0;

            }catch (Exception e) {
                    System.out.println(e.getMessage()) ;
                    return -1;
            }        
    }
    
    /**
     * 
     * @param ip
     * @param sysSource
     * @param desc
     * @param accoutInfoID
     * @return 
     */
    public int logForgotPwResult(String ip, String sysSource, String desc, int accoutInfoID) {
         
        TimeStamp time = new TimeStamp();
        long timeStampsID = time.getTimeStampsID();
                
        //prepare and execute search query
        try{   
                sql = "INSERT INTO INFSCI2731.activity_log (ip_addr, system_source, activity_count, timestamps_id, description, account_info_id) "
                        + "values (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  
                ps.setString(1, ip);          
                ps.setString(2, sysSource);          
                ps.setInt(3, 1); 
                ps.setLong(4, timeStampsID);               
                ps.setString(5, desc); 
                ps.setInt(6, accoutInfoID); 
                           
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();

                //check if query returns one valid result 
                if(rs.next())
                    return autoKey = rs.getInt(1);
                else
                    return 0;

            }catch (Exception e) {
                    System.out.println(e.getMessage()) ;
                    return -1;
            }        
    }
    
    /**
     * 
     * @param desc
     * @param logID
     * @return 
     */
    public int updatePreResetPwRecord(String desc, int logID) {
               
        String preDesc = "(forgot pw)reset pw : sent link";
                
        //prepare and execute search query
        try{               
                sql = "UPDATE INFSCI2731.activity_log SET description = ? WHERE id = ?";
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  
                ps.setString(1, preDesc + ", " + desc); 
                ps.setInt(2, logID); 
                           
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();

                //check if query returns one valid result 
                if(rs.next())
                    return autoKey = rs.getInt(1);
                else
                    return 0;

            }catch (Exception e) {
                    System.out.println(e.getMessage()) ;
                    return -1;
            }        
    }
    
    /**
     * Role Based Permission check
     * @param ip
     * @param sysSource
     * @param desc
     * @param accoutInfoID
     * @return 
     */
    public int logRBPCheck(String ip, String sysSource, String desc, int accoutInfoID) {
         
        TimeStamp time = new TimeStamp();
        long timeStampsID = time.getTimeStampsID();
                
        //prepare and execute search query
        try{   
                sql = "INSERT INTO INFSCI2731.activity_log (ip_addr, system_source, activity_count, timestamps_id, description, account_info_id) "
                        + "values (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  
                ps.setString(1, ip);          
                ps.setString(2, sysSource);          
                ps.setInt(3, 1); 
                ps.setLong(4, timeStampsID);               
                ps.setString(5, desc); 
                ps.setInt(6, accoutInfoID); 
                           
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();

                //check if query returns one valid result 
                if(rs.next())
                    return autoKey = rs.getInt(1);
                else
                    return 0;

            }catch (Exception e) {
                    System.out.println(e.getMessage()) ;
                    return -1;
            }        
    }
    
    public ResultSet getActivityLog(String term, String column, String sort, String limit, String method) {
        try{
            sql = "SELECT activity_log.id, ip_addr, system_source, activity_count, description, account_info_id, create_time, update_time"
                    + " FROM activity_log, timestamps WHERE activity_log.timestamps_id = timestamps.id";
            sql += " AND " + column + " " + method + " ?"; 
            sql += " ORDER BY activity_log.id " + sort + " LIMIT ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, (method.equals("LIKE") ? "%" + term + "%" : term));
            ps.setInt(2, Integer.parseInt(limit));
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch(SQLException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public ResultSet getActivityLog(int limit) {
        try{
            sql = "SELECT activity_log.id, ip_addr, system_source, activity_count, description, account_info_id, create_time, update_time"
                    + " FROM activity_log, timestamps WHERE activity_log.timestamps_id = timestamps.id ORDER BY activity_log.id DESC LIMIT ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch(SQLException | NumberFormatException e) {;
            return null;
        }
    }
    
    /**
     * log activity such as profile, role changes 
     * @param ip
     * @param sysSource
     * @param desc
     * @param accoutInfoID
     * @return 
     */
    public int logUpdateActivity(String ip, String sysSource, String desc, int accoutInfoID) {
         
        TimeStamp time = new TimeStamp();
        long timeStampsID = time.getTimeStampsID();
                
        //prepare and execute search query
        try{   
                sql = "INSERT INTO INFSCI2731.activity_log (ip_addr, system_source, activity_count, timestamps_id, description, account_info_id) "
                        + "values (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  
                ps.setString(1, ip);          
                ps.setString(2, sysSource);          
                ps.setInt(3, 1); 
                ps.setLong(4, timeStampsID);               
                ps.setString(5, desc); 
                ps.setInt(6, accoutInfoID); 
                           
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();

                //check if query returns one valid result 
                if(rs.next())
                    return autoKey = rs.getInt(1);
                else
                    return 0;

            }catch (Exception e) {
                    System.out.println(e.getMessage()) ;
                    return -1;
            }        
    }
    
}
