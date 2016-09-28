/*
 * set up mysql database connection 
 */
package DbConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Siwei Jiao
 */
public class DbConnection {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null)
            return connection;
        else {
            try {
                //connect db
                String driver = "com.mysql.jdbc.Driver";
                /**
                 * comment or uncomment the following urls to set the one you need
                 */
//                String url = "jdbc:mysql://localhost:3306/INFSCI2731";
                String url = "jdbc:mysql://localhost:8889/INFSCI2731";
                String user= "root";
                String password = "root";
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
              
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
//                Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException e) {
                e.printStackTrace();
            } 
            return connection;
        }

    }
}
