/*
 * This class contains methods to manipulate timestamp
 */
package dataAccessObject;

import java.sql.Connection;
import DbConnect.DbConnection;
import java.math.BigInteger;
import java.sql.*;
import java.util.Date;
import model.TimeStamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Siwei Jiao
 */
public class TimeStampDao {
    private Connection connection;
        Statement st = null;
        ResultSet rs = null;
               
        String sql = "";
        long autoKey;

        
        public TimeStampDao() {
            //connect to database and select the record
            connection = DbConnection.getConnection();	
        }
        
        /**
         * create a new timestamp by using current time
         * @return the auto generated key of the newly inserted db record
         */
        public long setUpTimeStamp() {
            Date dateTime = new Date(); //Allocates a Date object and initializes it so that it represents the time at which it was allocated, measured to the nearest millisecond.
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");            
//            String createTime = sdf.format(dateTime);
            
            try {
                sql = "INSERT INTO INFSCI2731.timestamps (create_time, update_time) values (?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  
                // Parameters start with 1
                ps.setTimestamp(1, new Timestamp(dateTime.getTime()));
                ps.setTimestamp(2, new Timestamp(dateTime.getTime())); 
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();

                if(rs.next())
                    return autoKey = rs.getLong(1);//retrieve the colume(1) value from the current row
                else
                    return 0;
                
		} catch (SQLException e) {
			e.printStackTrace();
                    return -1;
		}          
        }
        
        /**
         * delete timestamp by its id
         * @param id 
         */
        public void deleteTimeStampByID(int id) {
        
            try{
                //prepare and execute search query
                sql = "DELETE FROM INFSCI2731.timestamps WHERE id = ?"; 
                PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setInt(1,id);                
                    ps.executeUpdate() ;

            } catch (SQLException e) {
                    e.printStackTrace();
            }   

        }
        
        /**
         * 
         * @param id 
         */
        public void update_timestamp(int id) {
            Date now = new Date();
            try { 
                sql = "UPDATE INFSCI2731.timestamps SET update_time = ? where id = ? ";
                PreparedStatement ps = connection.prepareStatement(sql);  
                ps.setTimestamp(1, new Timestamp(now.getTime()));
                ps.setInt(2,id) ;                
                ps.executeUpdate();  //executeUpdate didn't return anything

            } catch (SQLException e) {
                    e.printStackTrace();
            }
        }
        
        /**
         * 
         * @param accountID 
         */
        public void updateOldPasswordTimestamp(int accountID) {
            List<Integer> timestampIDs = new ArrayList<Integer>();  
            //get all old password timestamp ids
            try{
                sql = "SELECT timestamps_id FROM INFSCI2731.authentication WHERE account_info_id = ? and active = 0"; 
                PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setInt(1,accountID) ;                
                    rs = ps.executeQuery() ; //executeQuery return the resultSet

                while (rs.next()) { 
                    timestampIDs.add(rs.getInt("timestamps_id"));                               
                }
                
                //clean up environment
                rs.close();
                ps.close();
                connection.close(); 

            } catch (SQLException e) {
                    e.printStackTrace();
            }    

            //update update_time in timestamps table for each id
            for(Integer id : timestampIDs) {
                update_timestamp(id);
            }           
        }
        
        /**
         * update update_time in timestamps table of activities like profile, role update
         * @param accountID 
         */
        public void updateTimestampOnProfileChange(int accountID) {
            //get timestamp id of account_info
            int timestampID = 0;
            try{
                sql = "SELECT timestamps_id FROM INFSCI2731.account_info WHERE id = ?"; 
                PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setInt(1,accountID) ;                
                    rs = ps.executeQuery() ;

                if(rs.next()) { 
                    timestampID = rs.getInt("timestamps_id");                               
                }
                //clean up environment
                rs.close();
                ps.close();
                connection.close(); 

            } catch (SQLException e) {
                    e.printStackTrace();
            }    

            //update update_time in timestamps table
            if(timestampID > 0) {
                update_timestamp(timestampID);               
            }
                       
        }
        
        
        
}
