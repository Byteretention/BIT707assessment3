/*
 * BIT707
 * Assessment 3
 * Student ID: 5032962
 */
package todolist;

import java.time.LocalDate;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tarrynt Whitty
 */
public class ToDoControllerTest {
    //connect to testing database
    String url = "C://sqlite/db/ToDotesting.db";
    
    public ToDoControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testConnectToDB() {
        System.out.println("Testing connection to DB\n~~");
        
        //create Database controller
        ToDoController test = new ToDoController();
        
        System.out.println("Attemping to connect to:" + url);
        try{
            test.connectToDB(url, 0);
        } catch (Exception e){
            System.out.println("Failed to connect to : " + url);
            fail("URL: " + url + "\nNo database exists at this address");
        }
        System.out.println("Connected.\nPass\n---");
        assertTrue( true);
    }
    
    @Test
    public void testGetTaskfromDB() {
        System.out.println("testing getTaskfromDB\n~~");
        //create Database controller
        ToDoController test = new ToDoController();
        try{
            test.connectToDB(url, 0);
        } catch (Exception e){
            System.out.println("Failed to connect to : " + url);
            fail("URL: " + url + "\nNo database exists at this address");
        }
        
        List<Task> tasks = test.getTasksfromDB();
        
        for (Task item : tasks){
            System.out.println("" + 
                item.getID() + " " + 
                item.getTitle() + " " + 
                item.getDueDate() + " " + 
                item.getCompletion() + " " + 
                item.getOwnerId() + " " + 
                item.getDesc()
                );
        }
        
        if (tasks == null){
            fail("tasks returned null");
        }
        
        System.out.println("---");
    }
    
    
    
    @Test
    public void testUpdateTaskstoDB() {
        System.out.println("updateTaskstoDB");
        ToDoController instance = new ToDoController();
        boolean expResult = false;
        boolean result = instance.updateTaskstoDB();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
    


    @Test
    public void testUpdateTasktoDB() {
        System.out.println("Testing updateTasks\n~~");
        
        //create Database controller
        ToDoController test = new ToDoController();
        try{
            test.connectToDB(url, 0);
        } catch (Exception e){
            System.out.println("Failed to connect to : " + url);
            fail("URL: " + url + "\nNo database exists at this address");
        }
        
        //vaules we putting into database
        int expectedid = 1;
        String expectedTitle = "test task 1";
        LocalDate expectedDate = LocalDate.now();
        int expectedownerid = 3321;
        String expectedDesc= "testdir";
        
        
        
        //create a dumby task, it will edit the database.
        Task input = new Task(
            expectedid, 
            expectedTitle, 
            expectedDate,
            expectedownerid, 
            expectedDesc );
        test.updateTasktoDB(input);
        
        //print input to database
        System.out.println("" + 
                input.getID() + " " + 
                input.getTitle() + " " + 
                input.getDueDate() + " " + 
                input.getCompletion() + " " + 
                input.getOwnerId() + " " + 
                input.getDesc()
                );
        
        
        
        //output from database
        Task output = test.getTaskfromDB( 1);        
        System.out.println("" + 
                output.getID() + " " + 
                output.getTitle() + " " + 
                output.getDueDate() + " " + 
                output.getCompletion() + " " + 
                output.getOwnerId() + " " + 
                output.getDesc()
                );
        
        //check all vaules are correct
        assertEquals(input.getID(),output.getID());
        assertEquals(input.getDueDate(),output.getDueDate());
        assertEquals(input.getCompletion(),output.getCompletion());
        assertEquals(input.getOwnerId(),output.getOwnerId());
        assertEquals(input.getDesc(),output.getDesc());
        
        System.out.println("---");
        

    }
    
    @Test
    public void testGetTimeFromNow() {
        //System.out.println("GetTimeFromNow");
        int days = 0;
        ToDoController instance = new ToDoController();
        LocalDate expResult = null;
        LocalDate result = instance.GetTimeFromNow(days);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testCreateTask() {
        //System.out.println("createTask");
        LocalDate date = null;
        int owner = 0;
        String title = "";
        String desc = "";
        ToDoController instance = new ToDoController();
        instance.createTask(date, owner, title, desc);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetAllTasks() {
        System.out.println("Test getAllTasks");
        //create Database controller
        ToDoController test = new ToDoController();
        try{
            test.connectToDB(url, 0);
        } catch (Exception e){
            System.out.println("Failed to connect to : " + url);
            fail("URL: " + url + "\nNo database exists at this address");
        }
        
        List<Task> tasks = test.getTasksfromDB();
        
        for (Task item : tasks){
            System.out.println("" + 
                item.getID() + " " + 
                item.getTitle() + " " + 
                item.getDueDate() + " " + 
                item.getCompletion() + " " + 
                item.getOwnerId() + " " + 
                item.getDesc()
                );
        }
        
        if (tasks == null){
            fail("tasks returned null");
        }
        
        System.out.println("---");
    }

    @Test
    public void testGetTaskByID() {
        //System.out.println("getTaskByID");
        int taskID = 0;
        ToDoController instance = new ToDoController();
        Task expResult = null;
        Task result = instance.getTaskByID(taskID);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testUpdateTaskCompletion() {
        //System.out.println("updateTaskCompletion");
        int taskID = 0;
        boolean newstatus = false;
        ToDoController instance = new ToDoController();
        boolean expResult = false;
        boolean result = instance.updateTaskCompletion(taskID, newstatus);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testToggleTaskCompletion() {
        //System.out.println("toggleTaskCompletion");
        int taskID = 0;
        ToDoController instance = new ToDoController();
        boolean expResult = false;
        boolean result = instance.toggleTaskCompletion(taskID);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testUpdateTaskDesc() {
        //System.out.println("updateTaskDesc");
        int taskID = 0;
        String newdesc = "";
        ToDoController instance = new ToDoController();
        boolean expResult = false;
        boolean result = instance.updateTaskDesc(taskID, newdesc);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testUpdateDueDate() {
        //System.out.println("updateDueDate");
        LocalDate newdate = null;
        ToDoController instance = new ToDoController();
        boolean expResult = false;
        boolean result = instance.updateDueDate(newdate);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }


    
}
