/*
 * BIT707
 * Assessment 3
 * Student ID: 5032962
 */
package todolist;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
        
/**
 *
 * @author Tarrynt Whitty
 */
public class databaseConnector {
    
    String ConnectionURL;
    
    
    public databaseConnector(String url){ 
        this.ConnectionURL = url;
    }
    
    /**
     * Create a connection to the Database. using the input string as the path
     * @return connection, if null no connection was made
     */
    private Connection connect() {
        
        //create the sql connection screen taking in the path provided
        String url = "jdbc:sqlite:" + ConnectionURL;
        //create the connection we will return
        Connection conn = null;
        
        //try to connect to the database. if failed print why
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Url not correct");
        }
       
        return conn;
    }
    
    public void testConnect(){
        try{
            Connection conn = this.connect();
            String sqlQuery = "SELECT taskNumber FROM Task";
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sqlQuery);
            conn.close();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Url not correct");
        }
    }
        
    public void updateTask(int taskID, String date, int ownerID, int taskStatus, String taskDesc)
    {
        String sqlQuery = "UPDATE Task SET dueDate = ?, ownderID = ?, taskStatus = ?, taskDesc = ? WHERE taskNumber = ?";
        
        try 
        {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1,date);
            stmt.setInt(2, ownerID);
            stmt.setInt(3, taskStatus);
            stmt.setString(4,taskDesc);
            stmt.setInt(5, taskID);
            stmt.executeUpdate();
            conn.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }     
    }
    
    public Task getTask(int taskID){
        
        String sqlQuery = "SELECT taskNumber, dueDate, ownderID, taskStatus, taskName, taskDesc From Task Where taskNumber = " + Integer.toString(taskID);
        
        //task details to be filled
        int expectedid = taskID;
        String expectedTitle = "";
        LocalDate expectedDate = null;
        int expectedownerid = 0;
        String expectedDesc= "";
        boolean expectedstatus = false;
        //formatter to make the string for date back into LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        
        
        try 
        {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sqlQuery);
            
            while (rs.next()){
                    expectedDate = LocalDate.parse(rs.getString("dueDate"), formatter);
                    expectedownerid = rs.getInt("ownderID");
                    if (rs.getInt("taskStatus") == 1){
                       expectedstatus = true; 
                    }
                    expectedTitle = rs.getString("taskName");
                    expectedDesc = rs.getString("taskDesc");
            }
            
            conn.close();
            
        }catch (SQLException e){
            System.out.println(e.getMessage());
        } 
        
        Task output = new Task(
            expectedid, 
            expectedTitle, 
            expectedDate,
            expectedownerid, 
            expectedDesc );
        output.setCompletion(expectedstatus);
        
        return output;
    }
    
    
    //get next id from sql database. we will use this to create ids in task objects
    public int getNextID(){
        
        //our Query
        String sqlQuery = "SELECT MAX(taskNumber) FROM Task";
                
        //try connect to the database
        try (Connection conn = this.connect();
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
