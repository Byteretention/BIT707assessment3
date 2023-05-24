/*
 * BIT707
 * Assessment 3
 * Student ID: 5032962
 */
package todolist;

import java.time.LocalDate;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tarrynt Whitty
 */
 public class TaskTest {
    
     
    public TaskTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Test 
    public void atestClassCreation(){
        System.out.println("---\nTesting Creation of Task Object\n~~");
        
        //expected outcomes
        int expectedid = 1;
        String expectedTitle = "test task 1";
        LocalDate expectedDate = LocalDate.now();
        int expectedOwnerID = 3321;
        String expectedTaskDesc = "this is a task to do the thing";
        
        System.out.println("Creating object with predefined vaules");
        
        //create task with expected outcomes
        Task testone = new Task(
                expectedid, 
                expectedTitle, 
                expectedDate,
                expectedOwnerID, 
                expectedTaskDesc );
        //check all vaules are correct
        if(expectedid != testone.getID()){
            fail("ID Does not match expected");
        }else{
           System.out.println("ID passed"); 
        }
        
        if (expectedTitle != testone.getTitle()){
            fail("Title Does not match expected");
        }else{
           System.out.println("Title passed"); 
        }
        if (expectedDate != testone.getDueDate()){
            fail("Date Does not match expected");
        }else{
           System.out.println("Date due passed"); 
        }
        if (expectedOwnerID != testone.getOwnerId()){
            fail("OwnerID Does not match expected");
        }else{
           System.out.println("Owner ID passed"); 
        }
        if (expectedTaskDesc != testone.getDesc()){
            fail("Task Desc Does not match expected");
        }else{
           System.out.println("Task Desc passed"); 
        }
        if (testone.getCompletion() != false){
            fail("Task Status is not false");
        }else{
           System.out.println("Task Status passed"); 
        }
        System.out.println("All Passed"); 
        System.out.println("Ending Constuctor Test\n---"); 
        //pass the test if they are
        assertTrue(true);   
    }
    

    @Test
    public void btestToggleCompleteion() {
        System.out.println("Testing Completeion Toggle\n~~");
        int expectedid = 1;
        String expectedTitle = "test task 1";
        LocalDate expectedDate = LocalDate.now();
        int expectedOwnerID = 3321;
        String expectedTaskDesc = "this is a task to do the thing";
        
        System.out.println("Creating object with predefined vaules");
        
        //create task with expected outcomes
        Task instance = new Task(
                expectedid, 
                expectedTitle, 
                expectedDate,
                expectedOwnerID, 
                expectedTaskDesc );
        
        System.out.println("Status should be false by default");
        System.out.println("Status: " + instance.getCompletion());
        if(instance.getCompletion() != false){
            fail("Status is incorrect by default. check constuctor test");
        }
        
        
        System.out.println("Toggleing. New result should be true now");
        instance.toggleCompleteion();
        System.out.println("Status: " + instance.getCompletion());
        if(instance.getCompletion() != true){
            fail("Status did not toggle");
        }
        
        
        System.out.println("Toggleing. New result should be false now");
        instance.toggleCompleteion();
        System.out.println("Status: " + instance.getCompletion());
        if(instance.getCompletion() != false){
            fail("Status did not toggle");
        }
        
        System.out.println("Passed\n---"); 
        assertTrue(true);  
    }

    @Test
    public void btestSetCompletion() {
        System.out.println("Testing Set Completeion\n~~");
        int expectedid = 1;
        String expectedTitle = "test task 1";
        LocalDate expectedDate = LocalDate.now();
        int expectedOwnerID = 3321;
        String expectedTaskDesc = "this is a task to do the thing";
        
        System.out.println("Creating object with predefined vaules");
        
        //create task with expected outcomes
        Task instance = new Task(
                expectedid, 
                expectedTitle, 
                expectedDate,
                expectedOwnerID, 
                expectedTaskDesc );
        
        
        System.out.println("Status should be false by default");
        System.out.println("Status: " + instance.getCompletion());
        if(instance.getCompletion() != false){
            fail("Status is incorrect by default. check constuctor test");
        }
        
        System.out.println("Setting status to true");
        instance.setCompletion(true);
        System.out.println("Status: " + instance.getCompletion());
        if(instance.getCompletion() != true){
            fail("Status did not Set Correctly");
        }
        
        
        System.out.println("Setting status to false");
        instance.setCompletion(false);
        System.out.println("Status: " + instance.getCompletion());
        if(instance.getCompletion() != false){
            fail("Status did not Set Correctly");
        }
        
        System.out.println("Passed\n---"); 
        assertTrue(true); 
    }

    @Test
    public void btestSetDesc() {
        System.out.println("testing setDesc\n~~");
        int expectedid = 1;
        String expectedTitle = "test task 1";
        LocalDate expectedDate = LocalDate.now();
        int expectedOwnerID = 3321;
        String expectedTaskDesc = "this is a task to do the thing";
        
        System.out.println("Creating object with predefined vaules");
        
        //create task with expected outcomes
        Task instance = new Task(
                expectedid, 
                expectedTitle, 
                expectedDate,
                expectedOwnerID, 
                expectedTaskDesc );
        
        System.out.println("Setting new desc");
        String desc = "new string here";
        instance.setDesc(desc);
        String result = instance.getDesc();
        System.out.println("desc: " + result);
        
        if(desc != result){
            fail("Desc was not updated");
        }
        
        System.out.println("Passed\n---"); 
        assertTrue(true); 
    }

    @Test
    public void btestSetDueDate() {
        System.out.println("Testing Set Due Date\n~~");
        int expectedid = 1;
        String expectedTitle = "test task 1";
        LocalDate expectedDate = LocalDate.now();
        int expectedOwnerID = 3321;
        String expectedTaskDesc = "this is a task to do the thing";
        
        System.out.println("Creating object with predefined vaules");
        
        //create task with expected outcomes
        Task instance = new Task(
                expectedid, 
                expectedTitle, 
                expectedDate,
                expectedOwnerID, 
                expectedTaskDesc );
        
        //two dates we can test with
        LocalDate pastDate = LocalDate.of(1990, 01, 01);
        LocalDate futureDate = LocalDate.of(2050, 01, 01);
        
        System.out.println("Setting date to future date");
        instance.setDueDate(futureDate);
        System.out.println("date: " + instance.getDueDate());
        if(futureDate != instance.getDueDate()){
            fail("Date was not updated");
        }
        
        System.out.println("Setting date to past date");
        instance.setDueDate(pastDate);
        System.out.println("date: " + instance.getDueDate());
        if(pastDate != instance.getDueDate()){
            fail("Date was not updated");
        }
         
        System.out.println("Passed\n---"); 
        assertTrue(true); 
    }

    @Test
    public void btestGetID() {
        System.out.println("testing getID\n~~");
        
        int expResult = 0;
        String expectedTitle = "test task 1";
        LocalDate expectedDate = LocalDate.now();
        int expectedOwnerID = 3321;
        String expectedTaskDesc = "this is a task to do the thing";
        
        System.out.println("Creating object with predefined vaules");
        
        //create task with expected outcomes
        Task instance = new Task(
                expResult, 
                expectedTitle, 
                expectedDate,
                expectedOwnerID, 
                expectedTaskDesc );
        
        int result = instance.getID();
        System.out.println("Task ID: " + result);
        if (expResult == result){
            System.out.println("Passed");
        }
        System.out.println("---");
        
        assertEquals(expResult, result);
    }

    @Test
    public void btestGetCompletion() {
        System.out.println("testing GetCompletion\n~~");
        
        int expectedid = 0;
        String expectedTitle = "test task 1";
        LocalDate expectedDate = LocalDate.now();
        int expectedOwnerID = 3321;
        String expectedTaskDesc = "this is a task to do the thing";
        
        System.out.println("Creating object with predefined vaules");
        
        //create task with expected outcomes
        Task instance = new Task(
                expectedid, 
                expectedTitle, 
                expectedDate,
                expectedOwnerID, 
                expectedTaskDesc );
        
        
        System.out.println("setting status to true for tseting");
        boolean expResult = true;
        instance.setCompletion(expResult);     
        boolean result = instance.getCompletion();
        System.out.println("Task status: " + result);
        
        if (expResult == result){
            System.out.println("Passed");
        }
        System.out.println("---");
        assertEquals(expResult, result);

    }

    @Test
    public void btestGetTitle() {
        System.out.println("testing getTitle\n~~");
        int expectedid = 0;
        String expResult = "test123";
        LocalDate expectedDate = LocalDate.now();
        int expectedOwnerID = 3321;
        String expectedTaskDesc = "this is a task to do the thing";
        
        System.out.println("Creating object with predefined vaules");
        
        //create task with expected outcomes
        Task instance = new Task(
                expectedid, 
                expResult, 
                expectedDate,
                expectedOwnerID, 
                expectedTaskDesc );
       
        String result = instance.getTitle();
        System.out.println("Title: " + result);
        
        if (expResult == result){
            System.out.println("Passed");
        }
        
        System.out.println("---");
        assertEquals(expResult, result);
    }

    @Test
    public void btestGetDueDate() {
        System.out.println("testing getDueDate\n~~");
        int expectedid = 0;
        String expectedTitle = "test task 1";
        LocalDate expResult = LocalDate.of(2050, 01, 01);
        int expectedOwnerID = 3321;
        String expectedTaskDesc = "this is a task to do the thing";
        
        System.out.println("Creating object with predefined vaules");
        
        //create task with expected outcomes
        Task instance = new Task(
                expectedid, 
                expectedTitle, 
                expResult,
                expectedOwnerID, 
                expectedTaskDesc );
        
       
        
        LocalDate result = instance.getDueDate();
        System.out.println("Date: " + result);
        
        if (expResult == result){
            System.out.println("Passed");
        }
        
        System.out.println("---");
        assertEquals(expResult, result);
    }

    @Test
    public void btestGetOwnerId() {
        System.out.println("testing getOwnerId\n~~");
        int expectedid = 1;
        String expectedTitle = "test task 1";
        LocalDate expectedDate = LocalDate.now();
        int expResult = 3321;
        String expectedTaskDesc = "this is a task to do the thing";
        
        System.out.println("Creating object with predefined vaules");
        
        //create task with expected outcomes
        Task instance = new Task(
                expectedid, 
                expectedTitle, 
                expectedDate,
                expResult, 
                expectedTaskDesc );
        
        
        int result = instance.getOwnerId();
        System.out.println("ownerid : " + result);
        if (expResult == result){
            System.out.println("Passed");
        }
               
        assertEquals(expResult, result);
    }

    @Test
    public void btestGetDesc() {
        System.out.println("testing getDesc\n~~");
        int expectedid = 1;
        String expectedTitle = "test task 1";
        LocalDate expectedDate = LocalDate.now();
        int expectedownerid = 3321;
        String expResult = "testdir";
        
        System.out.println("Creating object with predefined vaules");
        
        //create task with expected outcomes
        Task instance = new Task(
                expectedid, 
                expectedTitle, 
                expectedDate,
                expectedownerid, 
                expResult );
        
        String result = instance.getDesc();
        System.out.println("desc: " + result);
        
        if (expResult == result){
            System.out.println("Passed");
        }
        assertEquals(expResult, result);
    }
        
}
