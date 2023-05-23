/*
 * BIT707
 * Assessment 3
 * Student ID: 5032962
 */
package todolist;

import java.sql.*;
        
/**
 *
 * @author Tarrynt Whitty
 */
public class databaseConnector {
    
    /**
     * Create a connection to the Database. using the input string as the path
     * @return connection, if null no connection was made
     */
    private Connection connect(String url) {
        
        //create the sql connection screen taking in the path provided
        url = "jdbc:sqlite:" + url;
        //create the connection we will return
        Connection conn = null;
        
        //try to connect to the database. if failed print why
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        //return conn.
        return conn;
    }
        
    /**
     * select all and print all rows in task table
     */
    public void printAllFromTask(String url){
        
        //the sql querry we will send to the databse
        String sqlQuery = "SELECT taskNumber, taskName FROM Task";
        
        //try to connect to the database using the url given
        //if we connect use the connection to create a sqlquerry given earlyer
        //then loop though the result and print all the rows in the database
        // if failed print why
        try (Connection conn = this.connect(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sqlQuery)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("taskNumber") +  "\t" + 
                                   rs.getString("taskName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    //testing functions
    
    //print only tasknumberfrom database
    public void printTaskNumber(){
                
        //the sql querry we will send to the databse
        String sqlQuery = "SELECT taskNumber FROM Task";
        
        //try to connect to the database using the url given
        //if we connect use the connection to create a sqlquerry given earlyer
        //then loop though the result and print all the rows in the database
        // if failed print why
        try (Connection conn = this.connect("C://sqlite/db/ToDo.db");
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sqlQuery)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("taskNumber"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //print only tasknumberfrom database
    public void printTaskName(){
                
        //the sql querry we will send to the databse
        String sqlQuery = "SELECT taskName FROM Task";
        
        //try to connect to the database using the url given
        //if we connect use the connection to create a sqlquerry given earlyer
        //then loop though the result and print all the rows in the database
        // if failed print why
        try (Connection conn = this.connect("C://sqlite/db/ToDo.db");
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sqlQuery)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("taskName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    //get next id from sql database. we will use this to create ids in task objects
    public int getNextID(){
        
        //our Query
        String sqlQuery = "SELECT MAX(taskNumber) FROM Task";
                
        //try connect to the database
        try (Connection conn = this.connect("C://sqlite/db/ToDo.db");
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sqlQuery)){
            
            // loop through the result set and return the next id if we were to make a db entry
            return rs.next() ? rs.getInt(1) +1 : 1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //if we get -1 we failed somwhere down the line.
        return -1;
    }
}
