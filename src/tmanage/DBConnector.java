/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmanage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author martin
 */
public class DBConnector {
    private static boolean connected = false;
    private static Connection connection;    
    public static Connection Connect()
    {
        if (connected){
            return connection;
        }            
        //registering driver
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("Driver registered succesfully");
        } catch (Exception ex) {
            System.out.println("Error registering dirver");
        }
        
        //opening the connection
        Connection conn = null;
        try {
            conn =
                DriverManager.getConnection("jdbc:mysql://localhost/time_management?" +
                                            "user=root&password=Maradiel");
            System.out.println("Connected to the DB");

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        
        connection = conn;
        connected = true;
        return conn;
    }
    
    public static Connection getConnection(){
        return connection;
    }
}
