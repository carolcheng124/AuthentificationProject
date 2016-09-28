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

import model.Role;

/**
 *
 * @author Zhirun Tian Hanwei Cheng
 */
public class RBACDao {

    private Connection connection;
    private String nonceval = "";
    Statement st = null;
    ResultSet rs = null;

    String sql = "";
    int autoKey = 0;

    public RBACDao() {
        connection = DbConnection.getConnection();
    }

    public List<Integer> getRolebyPath(String ResourcePath) {
        try {
            //prepare and execute search query
            sql = "SELECT infsci2731.access_role.id FROM infsci2731.access_resource_has_access_role inner join infsci2731.access_role inner join infsci2731.access_resource where infsci2731.access_resource_has_access_role.access_role_id = infsci2731.access_role.id and infsci2731.access_resource_has_access_role.access_resource_id = infsci2731.access_resource.id and infsci2731.access_resource.resource = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ResourcePath);
            rs = ps.executeQuery();

            List<Integer> roleID = new ArrayList<Integer>();
            while (rs.next()) {
                roleID.add(rs.getInt("id"));
            }   

            rs.close();
            
            return roleID;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
