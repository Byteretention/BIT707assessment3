/*
 * BIT707
 * Assessment 3
 * Student ID: 5032962
 */
package todolist;

//imports
import java.time.LocalDate;

/**
 *Currently just a basic class to allow us to write tests
 * @author Tarrynt Whitty
 */
public class Task {
    //have a static id counter accross all tasks
    private int TaskID;
    private String Title;
    private LocalDate DueDate;
    private int AssignedOwnerID;
    private boolean Status;
    private String TaskDesc;
    
    //the code constructing this must call the database connecter to find out what the next ID will be
    public Task(int id, String title, LocalDate date, int owner, String taskDesc){
        this.TaskID = id;
        this.Title = title;
        this.DueDate = date;
        this.AssignedOwnerID = owner;
        this.TaskDesc = taskDesc;
        this.Status = false;
    }
    
    //just flip the current state of status
    public void toggleCompleteion()
    {
        this.Status = !this.Status;
    }
    
    //setters
    //status
    public void setCompletion(boolean statustobe){
        
    }
    //task desc
    public void setDesc(String desc){
        
    }
    //Due Date
    public void setDueDate(LocalDate date){
        
    }
    
    
    //getters
    //id
    public int getID(){
        return this.TaskID;
    }
    //status
    public boolean getCompletion(){
        return this.Status;
    }
    //title
    public String getTitle(){
        return this.Title;
    }
    //duedate
    public LocalDate getDueDate(){
        return this.DueDate;
    }
    //owner
    public int getOwnerId(){
        return this.AssignedOwnerID;
    }
    
    public String getDesc(){
        return this.TaskDesc;
    }
    
}
