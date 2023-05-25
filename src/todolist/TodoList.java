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
        DailyTasks day = new DailyTasks();
        day.setVisible(true);
    }
    
    
    
    private static void testingDates(){
        System.out.println("---");
        System.out.println("Checking if date code works correctly");
               // creating current local date using now() method and which will return the
        // curren date.
        LocalDate currentDate = LocalDate.now();
 
        // LocalDate to SQL date using valueOf() method.
        Date sqlDate = Date.valueOf(currentDate);
 
        // printing
        System.out.println("With current local date");
        System.out.println("LocalDate : " + currentDate);
        System.out.println("SQL Date : " + sqlDate);
        System.out.println("---");
        
        // working with different dates.
        LocalDate pastDate = LocalDate.of(1990, 01, 01);
        LocalDate futureDate = LocalDate.of(2050, 01, 01);
         
        // converting Local dates to sql dates
        Date pastSqlDate = Date.valueOf(pastDate);
        Date futureSqlDate = Date.valueOf(futureDate);
        System.out.println("With different local dates");
        System.out.println("Past LocalDate : " + pastDate);
        System.out.println("Past SQL Date : " + pastSqlDate);
         
        System.out.println("Future LocalDate : " + futureDate);
        System.out.println("Future SQL Date : " + futureSqlDate); 
        
    }
}
