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
    
    /**
     *the code constructing this must call the database connecter to find out what the next ID will be
     * @param id, id for the task
     * @param title, title for the task. please ensure this is not null
     * @param date, date the task is due to be completed by
     * @param owner, owner of task. ensures users can only see their own tasks
     * @param taskDesc, extra information about task
     */
    public Task(int id, String title, LocalDate date, int owner, String taskDesc){
        this.TaskID = id;
        this.Title = title;
        this.DueDate = date;
        this.AssignedOwnerID = owner;
        this.TaskDesc = taskDesc;
        this.Status = false;
    }
    
    //just flip the current state of status

    /**
     *will flip the current state of status completion
     */
    public void toggleCompleteion()
    {
        this.Status = !this.Status;
    }
    
    //setters
     /**
     * Set status of task to exact value rather then flipping it.
     * @param statustobe the new status of completion we wish for
     */
    public void setCompletion(boolean statustobe){
        this.Status = statustobe;
    }
    //task desc

    /**
     * Set the extra information about the task if need be
     * @param desc the new extra information for the task
     */
    public void setDesc(String desc){
        this.TaskDesc = desc;
    }
    //Due Date

    /**
     * change the due by date for the task.
     * @param date the new date to complete task by
     */
    public void setDueDate(LocalDate date){
        this.DueDate = date;
    }
    
    
    //getters
    //id

    /**
     * Returns the ID of the task. 
     * @return returns task id
     */
    public int getID(){
        return this.TaskID;
    }
    //status

    /**
     * returns the current status of the Task. if complete or not
     * @return returns status
     */
    public boolean getCompletion(){
        return this.Status;
    }
    //title

    /**
     * Returns the Task title
     * @return returns task title
     */
    public String getTitle(){
        return this.Title;
    }
    //duedate

    /**
     * Returns the Date the task must be completed by
     * @return returns date to complete by
     */
    public LocalDate getDueDate(){
        return this.DueDate;
    }
    //owner

    /**
     * Returns the Owners ID for this task e.g. who owns this task
     * @return returns id of owner of task
     */
    public int getOwnerId(){
        return this.AssignedOwnerID;
    }
    
    /**
     * returns any extra information this task might have
     * @return returns extra information for task
     */
    public String getDesc(){
        return this.TaskDesc;
    }
    
}
