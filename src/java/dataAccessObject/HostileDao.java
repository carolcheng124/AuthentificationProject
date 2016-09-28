/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccessObject;

import DbConnect.DbConnection;
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

import model.HostileStructure;

/**
 *
 * @author Zhirun Tian
 */
public class HostileDao {

    private Connection connection;
    private String nonceval = "";
    Statement st = null;
    ResultSet rs = null;

    String sql = "";
    int autoKey = 0;

    private HostileStructure hostileEntry;

    public HostileDao() {
        //connect to database and select the record
        connection = DbConnection.getConnection();
    }



    public List<HostileStructure> GetHostileFromLogDB() {

        HostileStructure temphostileEntry = new HostileStructure();
        List<HostileStructure> HostileList = new ArrayList<HostileStructure>();

        Connection connection;
        PreparedStatement preparedStatement;

        TimeStampDao timeStampDao = new TimeStampDao();
        long timeStampsID = timeStampDao.setUpTimeStamp();

        try {
            connection = DbConnection.getConnection();

            String sql = "SELECT * FROM infsci2731.activity_log where description = '[hostile]'";

            preparedStatement = connection.prepareStatement(sql);
            //here should have 3 variables to distinguish where are the entry from( security question, login ,reset password....)
//            String SysSource = "[hostile]";
//            preparedStatement.setString(1, SysSource);

            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.next()) {
                return null;
            } else {
                while (true) {

                    String ipaddr = rs.getString("ip_addr");
                    System.out.println(rs.getString("ip_addr"));

                    temphostileEntry.setIPAddress(rs.getString("ip_addr"));
                    temphostileEntry.setSYSTEM_SOURCE(rs.getString("system_source"));
                    HostileList.add(temphostileEntry);
                    
                    if(!rs.next())
                    {
                        break;
                    }

                }
            }

            return HostileList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int WriteHostileToDB(int countAttempts, String IPAddress, String SYSTEM_SOURCE) {
        Connection connection;
        PreparedStatement preparedStatement;

        TimeStampDao timeStampDao = new TimeStampDao();
        long timeStampsID = timeStampDao.setUpTimeStamp();

        try {
            connection = DbConnection.getConnection();

            String sql = "INSERT INTO infsci2731.activity_log (ip_addr,system_source,activity_count,timestamps_id,description,account_info_id) VALUES (?,?,?,?,?,?)";

            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, IPAddress);
            preparedStatement.setString(2, SYSTEM_SOURCE);
            preparedStatement.setInt(3, countAttempts);
            preparedStatement.setLong(4, timeStampsID);
            preparedStatement.setString(5, "[Hostile]");
            preparedStatement.setInt(6, -1);
            preparedStatement.executeUpdate();

            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
