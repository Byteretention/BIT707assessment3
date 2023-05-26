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
     * Entry Point
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DailyTasks day = new DailyTasks();
        day.setVisible(true);
    }
}
