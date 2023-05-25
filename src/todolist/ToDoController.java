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
    
    
    public ToDoController(){
        
    }
    
    //interface with SQL database
    
    //connect to database
    public void connectToDB(String URL, int userID){
        connection = new databaseConnector(URL);
        try{           
            connection.testConnect();
        }catch (IllegalArgumentException e){
            throw e;
        }
        this.LoggedOwnerID = userID;  
    }
    
    //get all tasks
    public List<Task> getTasksfromDB(){  
        //we can also use this to update the intrunal list of tasks
        this.Tasks = connection.getAllTasks();
        return Tasks;
    }
    
        
    public Task getTaskfromDB(int taskid){      
        return connection.getTask(taskid); 
    }
    
    //Update list of tasks owned by persons
    public boolean updateTaskstoDB(){
        
        return false;
    }
    
    //update single task
    public void updateTasktoDB(Task input){
        //get task id
        int taskid = input.getID();
        //create a formater to convert date time to string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String formattedDate = input.getDueDate().format(formatter);
        //get ownerID
        int ownerid = input.getOwnerId();
        //get status, if true set it to 1, if false set it to 0
        int status = 0;
        if(input.getCompletion()){
            status = 1;
        }
        //get task descipt
        String taskDescipt = input.getDesc();
        
        //update the task
        connection.updateTask(
                taskid,
                formattedDate,
                ownerid,
                status,
                taskDescipt);
    }
    
    //the ui should ask the user how many days from now they wish to have the due date
    public LocalDate GetTimeFromNow(int days)
    {
        //LocalDate today = LocalDate.now();
	//LocalDate tomorrow = today.plusDays(1);
        //return now to allow for building.
        return LocalDate.now();
    } 
    
    //controller. remember to update the sql database.
    //make sure that title is not null
    public void createTask(LocalDate date, int owner, String title, String desc)
    {
        
    }
    //Effectively just a wrapper for accessing the database.
    public List<Task> getAllTasks(){ 
        return this.getTasksfromDB();
    }
    //Effectlyive just a wapper for accessing the database
    public Task getTaskByID(int taskID){
        return this.getTaskfromDB(taskID);
    }
    
    //update tasks
    //completion
    public boolean updateTaskCompletion(int taskID, boolean newstatus){
        
        return false;
    }
    //toggle completion
    public boolean toggleTaskCompletion(int taskID){
        
        return false;
    }
    
    //desc
    public boolean updateTaskDesc(int taskID, String newdesc){
        
        return false;
    }
    
    //Date
    public boolean updateDueDate(LocalDate newdate){
        
        return false;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
