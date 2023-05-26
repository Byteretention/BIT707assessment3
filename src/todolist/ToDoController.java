/*
 * BIT707
 * Assessment 3
 * Student ID: 5032962
 */
package todolist;

//imports
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tarrynt Whitty
 */
public class ToDoController {

    int LoggedOwnerID;
    List<Task> Tasks;
    databaseConnector connection;

    /**
     *Creates the controller. make sure to call connect on it next
     */
    public ToDoController() {

    }

    //the ui should ask the user how many days from now they wish to have the due date

    /**
     * Will return a LocalDate x number of days from now
     * @param days, how many days from now do we want a date for 
     * @return, returns future date
     */
    public LocalDate GetTimeFromNow(int days) {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(days);
        return tomorrow;
    }

    //interface with SQL database
    //connect to database

    /**
     * Connect to the database.
     * Throws Exception on failure to connect
     * @param URL, address or file path to our database
     * @param userID, user id of logged in
     */
    public void connect(String URL, int userID) {
        connection = new databaseConnector(URL);
        try {
            connection.testConnect();
        } catch (IllegalArgumentException e) {
            throw e;
        }
        this.LoggedOwnerID = userID;
    }

    //get all tasks

    /**
     * return all tasks owned by a user
     * @param OwnerID, user id we wish to get all tasks for
     * @return a List of all tasks a user owns
     */
    public List<Task> getallTasks(int OwnerID) {
        //we can also use this to update the intrunal list of tasks
        this.Tasks = connection.getAllTasks(OwnerID);
        return Tasks;
    }

    /**
     * returns a single task as requested, we can only find tasks that a exact user owns however
     * @param taskid, task id for task we wish to find
     * @param OwnerID, owners id. 
     * @return the task we are trying to get
     */
    public Task getTask(int taskid, int OwnerID) {
        return connection.getTask(taskid, OwnerID );
    }

    /**
     *update single task
     * @param input, given this a Task class, it will get all the information from it and call the sqldatabase to update it
     */
    public void updateTask(Task input) {
        //get task id
        int taskid = input.getID();
        //get Date
        LocalDate date = input.getDueDate();
        //get ownerID
        int ownerid = input.getOwnerId();
        //get status, if true set it to 1, if false set it to 0
        int status = 0;
        if (input.getCompletion()) {
            status = 1;
        }
        //get task descipt
        String taskDescipt = input.getDesc();

        //update the task
        connection.updateTask(
                taskid,
                date,
                ownerid,
                status,
                taskDescipt);
    }

    //for create Task to work we need to get a ID from the database
    private int getNextID() {
        return connection.getNextID();
    }

    //controller. remember to update the sql database.
    //make sure that title is not null

    /**
     * Create a new task in the database. will findthe ID on its own.
     * @param date, due date for the new task
     * @param owner, owner of the new task
     * @param title, title for the new task. will return -1 if task has no name
     * @param desc, extra information for task
     * @return a int with error code. -1 for no name -2 for if it fails to get ID from database
     */
    public int createTask(LocalDate date, int owner, String title, String desc) {
        //chcek if a name is null. if it is return -1 and make the ui error out
        if (title == "") {
            return -1;
        }

        //get the ID for the next Task. if negative 1 error out. return -2
        int TaskID = this.getNextID();
        if (TaskID == -1) {
            return -2;
        }

        //create the task
        connection.insertTask(TaskID, title, date, owner, desc);
        return TaskID;
    }

    /**
     * Delete a task from the database based off the ID given
     * (unsafe)
     * @param taskID the ID of the task we wish to remove
     */
    public void removeTask(int taskID) {
        try {
            connection.removeItem(taskID);
        } catch (IllegalArgumentException e) {
            throw e;
        }

    }
    
    
    //for testing create task 4.

    /**
     *Creates task 4 for testing
     */
    public void createtask4() {
        connection.createtask4();
    }

}
