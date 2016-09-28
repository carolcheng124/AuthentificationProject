package dataAccessObject;

import java.lang.*;
import java.security.*;
import java.sql.*;
import java.util.Arrays;

import DbConnect.DbConnection;

public class AuthenticationDao {
	
    private Connection connection;
    Statement st = null;
    ResultSet rs = null;
    String sql = "";
    String sqlCallforAccountID,sqlCallForHash;
    
    public AuthenticationDao(){
    		connection = DbConnection.getConnection();
    }
    
    //function would validateUser based on the provided username and password
    // Success : Account_ID of User is returned
    // Failure : NULL Value is returned
	public Integer validateUser(String username, String passwd){
		
		byte[] retrievedHash;
		String retrievedSalt;
		Integer account_info_id;
	   
		try{
	    	sqlCallforAccountID = "Select id from account_info where email_addr = ?";
	    	PreparedStatement preparedStatement = connection.prepareStatement(sqlCallforAccountID);
	    	preparedStatement.setString(1, username);
	    	rs = preparedStatement.executeQuery();
	    	if(rs.next()){
	    		
	    		account_info_id = rs.getInt("id");
	    		System.out.println("Account ID: "+account_info_id);
	    		
	    		sqlCallForHash = "Select hash, password_salt "
		    			+ "from authentication "
		    			+ "where account_info_id = ? and active = 1";
	    		
	    		preparedStatement = connection.prepareStatement(sqlCallForHash);  
		    	preparedStatement.setInt(1, account_info_id);
		    	rs = null;
		    	rs = preparedStatement.executeQuery();
		    	
		    	if(rs.next()){
		    		retrievedHash = rs.getBytes("hash");
		    		retrievedSalt = rs.getString("password_salt");
		    		byte[] calculatedHash = computeHash(passwd, retrievedSalt);
		    		if(MessageDigest.isEqual(calculatedHash, retrievedHash)){
		    			//passwords matched
		    			return account_info_id;
		    		}else{
		    			//Password does not match..
		    			System.out.print("Passwd does not match");
		    			return -1; //-1 indicates pw does not match
		    		}
		    	}else{
		    		//Password does not exist
		    		System.out.print("Passwd does not exist");
		    		return -3;
		    	}
	    		
	    	}else{
	    		//User name does not exist
	    		System.out.print("Username does not exist");
	    		return -2; //-2 indicates user entered email does not exist
	    	}
	    }catch(Exception e){
	    	e.printStackTrace();
	    	return -1;
	    }
		
	}
	
	//function computes MD5 hash for given salt and password
	// Returns byte[] for computed hash
	public byte[] computeHash(String passwd, String salt){
		
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] passwdBytes = passwd.getBytes();
			byte[] saltBytes = salt.getBytes();
			md.update(passwdBytes);
			md.update(saltBytes);//Updates the digest using the specified array of bytes
			byte[] hash = md.digest();//Completes the hash computation by performing final operations such as padding.
			return hash;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
        
        
 /**
 *These method is to create a new record for authentication table
 * @author: Hanwei Cheng
 */
        public int createAuthentication(String hash, String password_salt, int account_info_id, boolean active, long timestamps_id){
             try {
                sql = "INSERT INTO INFSCI2731.authentication(hash, password_salt, account_info_id, active, timestamps_id) values (?, ?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);  
                // Parameters start with 1
                ps.setString(1, hash);               
                ps.setString(2, password_salt);               
                ps.setInt(3, account_info_id); 
                ps.setBoolean(4, active);
                ps.setLong(5, timestamps_id);
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
