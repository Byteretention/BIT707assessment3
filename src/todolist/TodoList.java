/*
 * BIT707
 * Assessment 3
 * Student ID: 5032962
 */
package todolist;


import java.sql.Date;
import java.time.LocalDate;


/**
 *
 * @author Tarrynt Whitty
 */
public class TodoList {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //create a database connector to use to query the database
        databaseConnector test = new databaseConnector();
        //select and print all rows from the task table
        System.out.println("Print all From Task");
        test.printAllFromTask("C://sqlite/db/ToDo.db");
        //just a spacer
        System.out.println("");
        
        //print taskNumber from task
        System.out.println("\nPrint taskNumber from task");
        test.printTaskNumber();
        
        //print taskName from task
        System.out.println("\nPrint taskName from task");
        test.printTaskName();    
        
        System.out.println(test.getNextID());
        
        
        // creating current local date using now() method and which will return the
        // curren date.
        LocalDate currentDate = LocalDate.now();
 
        // LocalDate to SQL date using valueOf() method.
        Date sqlDate = Date.valueOf(currentDate);
 
        // printing
        System.out.println("With current local date");
        System.out.println("LocalDate : " + currentDate);
        System.out.println("SQL Date : " + sqlDate);
        
        // working with different dates.
        LocalDate pastDate = LocalDate.of(1990, 01, 01);
        LocalDate futureDate = LocalDate.of(2050, 01, 01);
         
        // converting Local dates to sql dates
        Date pastSqlDate = Date.valueOf(pastDate);
        Date futureSqlDate = Date.valueOf(futureDate);
         
        System.out.println("\nWith different local dates");
        System.out.println("Past LocalDate : " + pastDate);
        System.out.println("Past SQL Date : " + pastSqlDate);
         
        System.out.println("Future LocalDate : " + futureDate);
        System.out.println("Future SQL Date : " + futureSqlDate);
    }
    
}
