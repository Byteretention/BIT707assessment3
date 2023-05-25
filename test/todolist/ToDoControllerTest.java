/*
 * BIT707
 * Assessment 3
 * Student ID: 5032962
 */
package todolist;

import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

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
    public static void setUpClass() throws Exception {
        System.out.println("Set Up\n~~");
        String url = "C://sqlite/db/ToDotesting.db";
        ToDoController test = new ToDoController();
        try {
            test.connect(url, 0);
        } catch (Exception e) {
            System.out.println("Failed to connect to : " + url);
            fail("URL: " + url + "\nNo database exists at this address");
        }

        //for this test we need to check the DB if task 4 exists. if it does not. we passed
        //create the entry that test update task and test get task expects
        String expectedTitle = "test task 1";
        LocalDate expectedDate = LocalDate.now();
        int expectedownerid = 3321;
        String expectedDesc = "testdir";
        //create task 1 and 2
        test.createTask(expectedDate, expectedownerid, expectedTitle, expectedDesc);
        expectedTitle = "test task 2";
        expectedDate = LocalDate.now();
        expectedownerid = 3321;
        expectedDesc = "testdir2";
        test.createTask(expectedDate, expectedownerid, expectedTitle, expectedDesc);
        
        
        //create task 4
        test.createtask4();

        //get task ID 4
        Task output = test.getTask(4, 331111121);
        System.out.println(""
                + output.getID() + " "
                + output.getTitle() + " "
                + output.getDueDate() + " "
                + output.getCompletion() + " "
                + output.getOwnerId() + " "
                + output.getDesc()
        );
        
        //check task 1 and 2
        output = test.getTask(1, 3321);
        System.out.println(""
                + output.getID() + " "
                + output.getTitle() + " "
                + output.getDueDate() + " "
                + output.getCompletion() + " "
                + output.getOwnerId() + " "
                + output.getDesc()
        );
        output = test.getTask(2, 3321);
        System.out.println(""
                + output.getID() + " "
                + output.getTitle() + " "
                + output.getDueDate() + " "
                + output.getCompletion() + " "
                + output.getOwnerId() + " "
                + output.getDesc()
        );

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("Tear Down\n~~");
        String url = "C://sqlite/db/ToDotesting.db";
        ToDoController test = new ToDoController();
        try {
            test.connect(url, 0);
        } catch (Exception e) {
            System.out.println("Failed to connect to : " + url);
            fail("URL: " + url + "\nNo database exists at this address");
        }

        List<Task> all1 = test.getallTasks(1);
        List<Task> all2 = test.getallTasks(2);
        List<Task> all3 = test.getallTasks(3321);
        List<Task> all4 = test.getallTasks(331111121);
        List<Task> all5 = test.getallTasks(111);
        
        for (Task item : all1) {
            test.removeTask(item.getID());
        }
        for (Task item : all2) {
            test.removeTask(item.getID());
        }
        for (Task item : all3) {
            test.removeTask(item.getID());
        }
        for (Task item : all4) {
            test.removeTask(item.getID());
        }
        for (Task item : all5) {
            test.removeTask(item.getID());
        }
        

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
        try {
            test.connect(url, 0);
        } catch (Exception e) {
            System.out.println("Failed to connect to : " + url);
            fail("URL: " + url + "\nNo database exists at this address");
        }
        System.out.println("Connected.\nPass\n---");
        assertTrue(true);
    }

    @Test
    public void testGetallTasks() {
        System.out.println("testing getTaskfromDB\n~~");
        //create Database controller
        ToDoController test = new ToDoController();
        try {
            test.connect(url, 0);
        } catch (Exception e) {
            System.out.println("Failed to connect to : " + url);
            fail("URL: " + url + "\nNo database exists at this address");
        }
        int OwnerID = 1;
        List<Task> tasks = test.getallTasks(OwnerID);

        for (Task item : tasks) {
            System.out.println(""
                    + item.getID() + " "
                    + item.getTitle() + " "
                    + item.getDueDate() + " "
                    + item.getCompletion() + " "
                    + item.getOwnerId() + " "
                    + item.getDesc()
            );
        }

        if (tasks == null) {
            fail("tasks returned null");
        }

        System.out.println("---");
    }

    @Test
    public void testGetTask() {
        System.out.println("Testing getTask\n~~");
        //create Database controller
        ToDoController test = new ToDoController();
        try {
            test.connect(url, 0);
        } catch (Exception e) {
            System.out.println("Failed to connect to : " + url);
            fail("URL: " + url + "\nNo database exists at this address");
        }

        //get a single task
        //vaules we putting into database
        int expectedid = 2;
        String expectedTitle = "test task 2";
        LocalDate expectedDate = LocalDate.now();
        int expectedownerid = 3321;
        String expectedDesc = "testdir2";
        //output from database
        Task output = test.getTask(2, 3321);
        System.out.println(""
                + output.getID() + " "
                + output.getTitle() + " "
                + output.getDueDate() + " "
                + output.getCompletion() + " "
                + output.getOwnerId() + " "
                + output.getDesc()
        );
        //check all vaules are correct
        assertEquals(expectedid, output.getID());
        assertEquals(expectedDate, output.getDueDate());
        assertEquals(expectedownerid, output.getOwnerId());
        assertEquals(expectedDesc, output.getDesc());
        System.out.println("---");
    }

    @Test
    public void testUpdateTask() {
        System.out.println("Testing updateTask\n~~");

        //create Database controller
        ToDoController test = new ToDoController();
        try {
            test.connect(url, 0);
        } catch (Exception e) {
            System.out.println("Failed to connect to : " + url);
            fail("URL: " + url + "\nNo database exists at this address");
        }

        //vaules we putting into database
        int expectedid = 1;
        String expectedTitle = "test task 1";
        LocalDate expectedDate = LocalDate.now();
        int expectedownerid = 3321;
        String expectedDesc = "testdir";

        //create a dumby task, it will edit the database.
        Task input = new Task(
                expectedid,
                expectedTitle,
                expectedDate,
                expectedownerid,
                expectedDesc);
        test.updateTask(input);

        //print input to database
        System.out.println(""
                + input.getID() + " "
                + input.getTitle() + " "
                + input.getDueDate() + " "
                + input.getCompletion() + " "
                + input.getOwnerId() + " "
                + input.getDesc()
        );

        //output from database
        Task output = test.getTask(1, 3321);
        System.out.println(""
                + output.getID() + " "
                + output.getTitle() + " "
                + output.getDueDate() + " "
                + output.getCompletion() + " "
                + output.getOwnerId() + " "
                + output.getDesc()
        );

        //check all vaules are correct
        assertEquals(input.getID(), output.getID());
        assertEquals(input.getDueDate(), output.getDueDate());
        assertEquals(input.getCompletion(), output.getCompletion());
        assertEquals(input.getOwnerId(), output.getOwnerId());
        assertEquals(input.getDesc(), output.getDesc());

        System.out.println("---");

    }

    @Test
    public void testCreateTask() {
        System.out.println("Test createTask\n~~");

        ToDoController test = new ToDoController();
        try {
            test.connect(url, 0);
        } catch (Exception e) {
            System.out.println("Failed to connect to : " + url);
            fail("URL: " + url + "\nNo database exists at this address");
        }
        String expectedTitle = "testcreatetask";
        LocalDate expectedDate = LocalDate.now();
        int expectedownerid = 331111121;
        String expectedDesc = "aaaaaaaaaa";
        int result = test.createTask(expectedDate, expectedownerid, expectedTitle, expectedDesc);
        if (result > 0) {
            assertTrue(true);
            Task output = test.getTask(result, expectedownerid);
            System.out.println(""
                    + output.getID() + " "
                    + output.getTitle() + " "
                    + output.getDueDate() + " "
                    + output.getCompletion() + " "
                    + output.getOwnerId() + " "
                    + output.getDesc()
            );
        } else {
            fail("Create task failed errorcode: " + result);
        }
        System.out.println("---");
    }

    @Test
    public void testCheckifFailedwithblankname() {
        System.out.println("Test Blank Title\n~~");
        String expectedTitle = "";
        LocalDate expectedDate = LocalDate.now();
        int expectedownerid = 3321;
        String expectedDesc = "testdir";
        //create Database controller
        ToDoController test = new ToDoController();
        try {
            test.connect(url, 0);
        } catch (Exception e) {
            System.out.println("Failed to connect to : " + url);
            fail("URL: " + url + "\nNo database exists at this address");
        }
        if (test.createTask(expectedDate, expectedownerid, expectedTitle, expectedDesc) == -1) {
            assertTrue(true);
        } else {
            fail("function returned a error");
        }
        System.out.println("---");

    }

    @Test
    public void testRemoveTask() {
    }

    @Test
    public void testCreatetask4() {
    }
}
