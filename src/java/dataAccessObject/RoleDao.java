
package dataAccessObject;

import DbConnect.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *This class is to create a DAO for role
 * @author Hanwei Cheng
 */
public class RoleDao {
    private Connection connection;
    Statement st = null;
    ResultSet rs = null;
    String sql = "";
    
     public RoleDao() {
        //connect to database and select the record
        connection = DbConnection.getConnection();
        System.out.println("==RoleDao connection==");
    }
     
     //get the id number for who is normal user
    public int getUserID(){
            try{
                //prepare and execute search query
                sql = "SELECT id FROM INFSCI2731.access_role WHERE role = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, "User") ;                
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
        //get the id number for who is administrator
        public int getAdminID(){
        
         try{
                //prepare and execute search query
                sql = "SELECT id FROM INFSCI2731.access_role WHERE role = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, "Administrator") ;                
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
        
         //get the id number for who is administrator
        public int getSuperAdminID(){
       
         try{
                //prepare and execute search query
                sql = "SELECT id FROM INFSCI2731.access_role WHERE role = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, "Super Administrator") ;                
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
 
}
     
