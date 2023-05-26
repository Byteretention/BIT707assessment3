/*
 * BIT707
 * Assessment 3
 * Student ID: 5032962
 */
package todolist;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tarrynt Whitty
 */
public class databaseConnector {

    String ConnectionURL;
    
    /**
     * creates the DB connector, you will need to call this.connect() before doing anything
     * @param url (the url or file path to our database)
     */
    public databaseConnector(String url) {
        this.ConnectionURL = url;
    }

    /**
     * Create a connection to the Database. using the input string as the path
     *
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

    /**
     *Run after creating the object. will attemp to connect to the database
     * Throws Exception when it can not connect
     */
    public void testConnect() {
        try {
            Connection conn = this.connect();
            String sqlQuery = "SELECT taskNumber FROM Task";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            conn.close();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Url not correct");
        }
    }

    /**
     * Update a given task in the database
     * @param taskID, ID of the task we are looking for
     * @param date, Date of the task we wish to update to
     * @param ownerID, owner ID we wish to update to if we plan to change it
     * @param taskStatus, for us to change if the task is complete or not
     * @param taskDesc, extra information for the task
     */
    public void updateTask(int taskID, LocalDate date, int ownerID, int taskStatus, String taskDesc) {
        //create a formater to convert date time to string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String formattedDate = date.format(formatter);

        String sqlQuery = "UPDATE Task SET dueDate = ?, ownderID = ?, taskStatus = ?, taskDesc = ? WHERE taskNumber = ?";

        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, formattedDate);
            stmt.setInt(2, ownerID);
            stmt.setInt(3, taskStatus);
            stmt.setString(4, taskDesc);
            stmt.setInt(5, taskID);
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Find a task in the database
     * @param taskID, task id we are looking for
     * @param OwnerID, owner id (if the owner does not own that task, it will not return even if a task exists for that id)
     * @return Task based of id and owner id
     */
    public Task getTask(int taskID, int OwnerID) {

        String sqlQuery = "SELECT taskNumber, dueDate, ownderID, taskStatus, taskName, taskDesc From Task Where taskNumber = ? AND ?";

        //task details to be filled
        int expectedid = taskID;
        String expectedTitle = "";
        LocalDate expectedDate = null;
        int expectedownerid = 0;
        String expectedDesc = "";
        boolean expectedstatus = false;
        //formatter to make the string for date back into LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setInt(1, taskID);
            stmt.setInt(2, OwnerID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                expectedDate = LocalDate.parse(rs.getString("dueDate"), formatter);
                expectedownerid = rs.getInt("ownderID");
                if (rs.getInt("taskStatus") == 1) {
                    expectedstatus = true;
                }
                expectedTitle = rs.getString("taskName");
                expectedDesc = rs.getString("taskDesc");
            }

            conn.close();

        } catch (SQLException e) {
            throw new IllegalArgumentException("Url not correct");
        }

        Task output = new Task(
                expectedid,
                expectedTitle,
                expectedDate,
                expectedownerid,
                expectedDesc);
        output.setCompletion(expectedstatus);

        return output;
    }

    //get all tasks from the database

    /**
     * return all tasks own by user
     * @param OwnerID, Owner ID we want to get all tasks for
     * @return List of all tasks base off user id
     */
    public List<Task> getAllTasks(int OwnerID) {

        List<Task> tasks = new ArrayList<Task>();

        String sqlQuery = "SELECT taskNumber, dueDate, ownderID, taskStatus, taskName, taskDesc From Task WHERE ownderID = " + Integer.toString(OwnerID) + " ORDER BY dueDate";
        //formatter to make the string for date back into LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        //try to connect to db and get list of tasks
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);

            while (rs.next()) {
                //build task based off what sql database gives us
                Task item = new Task(
                        rs.getInt(1),
                        rs.getString(5),
                        LocalDate.parse(rs.getString(2), formatter),
                        rs.getInt(3),
                        rs.getString(6)
                );
                //make sure completetion is correct
                boolean status = false;
                if ( rs.getInt(4) == 1) {
                    status = true;
                }
                item.setCompletion(status);

                //add the item to the list
                tasks.add(item);
            }

            conn.close();

        } catch (SQLException e) {
            throw new IllegalArgumentException("Url not correct");
        }

        return tasks;
    }

    /**
     * Add a new task to the database
     * @param TaskID, taskID, will most likely be ignored but controller still needs to find correct id
     * @param title, Title, the tasks title, make sure you dont give a null string
     * @param date, Date, this will be converted to a string as SQLite does not handle date time
     * @param owner, Owners ID
     * @param desc, extra information about the task
     */
    public void insertTask(int TaskID, String title, LocalDate date, int owner, String desc) {
        //create a formater to convert date time to string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String formattedDate = date.format(formatter);

        //our Query
        String sqlQuery = "INSERT INTO Task "
                + "VALUES ("
                + TaskID + ", "
                + "'" + formattedDate + "', "
                + owner + ", "
                + 0 + ", "
                + "'" + title + "', "
                + "'" + desc + "')";
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sqlQuery);
            conn.close();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Url not correct");
        }

    }

    //remove a entry from the Db

    /**
     * Deletes a task from the database based off its ID
     * (unsafe)
     * @param TaskID Id of task we wish to remove from database
     */
    public void removeItem(int TaskID) {

        //our Query
        String sqlQuery = "DELETE FROM task WHERE taskNumber = ?";

        //try connect to the database
        try {
            Connection conn = this.connect();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setInt(1, TaskID);
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    //get next id from sql database. we will use this to create ids in task objects

    /**
     * will return the next free to use ID in the database
     * use this to create a new task to make sure you find a free ID
     * @return, next free ID
     */
    public int getNextID() {

        //our Query
        String sqlQuery = "SELECT MAX(taskNumber) FROM Task";

        //try connect to the database
        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlQuery)) {

            // loop through the result set and return the next id if we were to make a db entry
            return rs.next() ? rs.getInt(1) + 1 : 1;

        } catch (SQLException e) {
            throw new IllegalArgumentException("Does not Exist");
        }
        //if we get -1 we failed somwhere down the line.
    }

    //this only exists to allow for testing

    /**
     * Purely used during testing. Junit test requires task with ID of 4 to exist
     */
    public void createtask4() {
        LocalDate date = LocalDate.now();
        //create a formater to convert date time to string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String formattedDate = date.format(formatter);
        //our Query
        String sqlQuery = "INSERT INTO Task "
                + "VALUES ("
                + 4 + ", "
                + "'" + formattedDate + "', "
                + 111 + ", "
                + 0 + ", "
                + "'" + "to be removed" + "', "
                + "'" + "aaaaaaaaaaa" + "')";
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sqlQuery);
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }

    }
}
