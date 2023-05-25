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
    
    @Test
    public void testGetTimeFromNow() {
        int days = 2;
        ToDoController instance = new ToDoController();
        LocalDate today = LocalDate.now();
        LocalDate expResult = today.plusDays(days);
        LocalDate result = instance.GetTimeFromNow(days);
        assertEquals(expResult, result);
    }

    @Test
    public void testConnect() {
        System.out.println("Testing connection to DB\n~~");
        
        //create Database controller
        ToDoController test = new ToDoController();
        
        System.out.println("Attemping to connect to:" + url);
        try{
            test.connect(url, 0);
        } catch (Exception e){
            System.out.println("Failed to connect to : " + url);
            fail("URL: " + url + "\nNo database exists at this address");
        }
        System.out.println("Connected.\nPass\n---");
        assertTrue( true);
    }
    
    @Test
    public void testGetallTasks() {
        System.out.println("testing getTaskfromDB\n~~");
        //create Database controller
        ToDoController test = new ToDoController();
        try{
            test.connect(url, 0);
        } catch (Exception e){
            System.out.println("Failed to connect to : " + url);
            fail("URL: " + url + "\nNo database exists at this address");
        }
        
        List<Task> tasks = test.getallTasks();
        
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
    public void testGetTask() {
        System.out.println("Testing getTask\n~~");
        //create Database controller
        ToDoController test = new ToDoController();
        try{
            test.connect(url, 0);
        } catch (Exception e){
            System.out.println("Failed to connect to : " + url);
            fail("URL: " + url + "\nNo database exists at this address");
        }
        
        //get a single task
        
        //vaules we putting into database
        int expectedid = 1;
        String expectedTitle = "test task 1";
        LocalDate expectedDate = LocalDate.now();
        int expectedownerid = 3321;
        String expectedDesc= "testdir";
        //output from database
        Task output = test.getTask( 1);        
        System.out.println("" + 
                output.getID() + " " + 
                output.getTitle() + " " + 
                output.getDueDate() + " " + 
                output.getCompletion() + " " + 
                output.getOwnerId() + " " + 
                output.getDesc()
                );
        //check all vaules are correct
        assertEquals(expectedid,output.getID());
        assertEquals(expectedDate,output.getDueDate());
        assertEquals(expectedownerid,output.getOwnerId());
        assertEquals(expectedDesc,output.getDesc());
        System.out.println("---");
    }
    
    
    @Test
    public void testUpdateTask() {
        System.out.println("Testing updateTask\n~~");
        
        //create Database controller
        ToDoController test = new ToDoController();
        try{
            test.connect(url, 0);
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
        test.updateTask(input);
        
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
        Task output = test.getTask( 1);        
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
    public void testCreateTask() {
        System.out.println("Test createTask\n~~");
        
        ToDoController test = new ToDoController();
        try{
            test.connect(url, 0);
        } catch (Exception e){
            System.out.println("Failed to connect to : " + url);
            fail("URL: " + url + "\nNo database exists at this address");
        }
        String expectedTitle = "testcreatetask";
        LocalDate expectedDate = LocalDate.now();
        int expectedownerid = 331111121;
        String expectedDesc= "aaaaaaaaaa";
        int result = test.createTask(expectedDate,expectedownerid,expectedTitle ,expectedDesc);
        if( result > 0){
            assertTrue(true); 
            Task output = test.getTask( result);        
            System.out.println("" + 
                output.getID() + " " + 
                output.getTitle() + " " + 
                output.getDueDate() + " " + 
                output.getCompletion() + " " + 
                output.getOwnerId() + " " + 
                output.getDesc()
                );
        }else{
            fail("Create task failed errorcode: " + result);
        }
        System.out.println("---");
    }
    
    @Test
    public void testCheckifFailedwithblankname(){
        System.out.println("Test Blank Title\n~~");
        String expectedTitle = "";
        LocalDate expectedDate = LocalDate.now();
        int expectedownerid = 3321;
        String expectedDesc= "testdir";
        //create Database controller
        ToDoController test = new ToDoController();
        try{
            test.connect(url, 0);
        } catch (Exception e){
            System.out.println("Failed to connect to : " + url);
            fail("URL: " + url + "\nNo database exists at this address");
        }
        if(test.createTask(expectedDate,expectedownerid,expectedTitle ,expectedDesc) == -1){
           assertTrue(true); 
        }
        else{
            fail("function returned a error");
        }
        System.out.println("---");
        
    }
    
    @Test
    public void testRemoveTask() {
        System.out.println("Test Remove Task\n~~");

        ToDoController test = new ToDoController();
        try{
            test.connect(url, 0);
        } catch (Exception e){
            System.out.println("Failed to connect to : " + url);
            fail("URL: " + url + "\nNo database exists at this address");
        }
        //create task 4
        test.createtask4();
        
        //get task ID 4
        Task output = test.getTask( 4);    
        System.out.println("" + 
            output.getID() + " " + 
            output.getTitle() + " " + 
            output.getDueDate() + " " + 
            output.getCompletion() + " " + 
            output.getOwnerId() + " " + 
            output.getDesc()
        );
        //for this test we need to check the DB if task 4 exists. if it does not. we passed
    }
}
