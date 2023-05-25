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

    public ToDoController() {

    }

    //the ui should ask the user how many days from now they wish to have the due date
    public LocalDate GetTimeFromNow(int days) {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(days);
        return tomorrow;
    }

    //interface with SQL database
    //connect to database
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
    public List<Task> getallTasks(int OwnerID) {
        //we can also use this to update the intrunal list of tasks
        this.Tasks = connection.getAllTasks(OwnerID);
        return Tasks;
    }

    public Task getTask(int taskid, int OwnerID) {
        return connection.getTask(taskid, OwnerID );
    }

    //update single task
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

    public void removeTask(int taskID) {
        try {
            connection.removeItem(taskID);
        } catch (IllegalArgumentException e) {
            throw e;
        }

    }
    
    
    //for testing create task 4.
    public void createtask4() {
        connection.createtask4();
    }

}
