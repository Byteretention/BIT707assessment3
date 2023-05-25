/*
 * BIT707
 * Assessment 3
 * Student ID: 5032962
 */
package todolist;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Tarrynt Whitty
 */
public class DailyTasks extends javax.swing.JFrame {

    ToDoController control;
    List<Task> tasks;
    String url = "C://sqlite/db/ToDo.db";
    
    //window style settings.
    //size
    int WIDTH = 415;
    int HEIGHT = 500;
    //Colors of objects
    String MAINBGCOLOR = "#FFFFFF";
    String BUTTONCOLOR = "#EEEEEE";
    
    
    int numberoftasks;
    
    
    
    //our Panels for this screen
    JPanel DailyTaskPanel;
    JPanel CalanderView;
    
    /**
     * Creates new form DailyTasks
     */
    public DailyTasks() {
        //generataed code
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DailyTasks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DailyTasks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DailyTasks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DailyTasks.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        initComponents();  
        
        
        //create controller and connect to database
        control = new ToDoController();
        try{
            control.connect(url, 0);
        } catch (Exception e){
            System.out.println("Failed to connect to : " + url);
        }
        
        //crate our panels
        DailyTaskPanel = new JPanel();
        CalanderView = new JPanel();
        
        //update buttons
        AddTasksToDaily();
        //AddTasksToCalander();
        
        //set up a scroll plane for the buttons

        
        //set the layout for the daily panel
        DailyTaskPanel.setLayout(new GridLayout(numberoftasks,1));
        
        DailyTaskPanel.setBackground(Color.decode(MAINBGCOLOR));
        JScrollPane DailyScrollPlan = new JScrollPane(DailyTaskPanel);
        DailyScrollPlan.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        DailyScrollPlan.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        DailyScrollPlan.setBounds(0, 30, WIDTH, HEIGHT);
        
        
        //set the layout for the all tasks panel
        CalanderView.setLayout(new GridLayout(numberoftasks,1));
        CalanderView.setBackground(Color.decode(MAINBGCOLOR));
        JScrollPane CalanderPlan = new JScrollPane(CalanderView);
        CalanderPlan.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        CalanderPlan.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        CalanderPlan.setBounds(0, 30, WIDTH, HEIGHT);
        
        //create panel to handle selecting of what place we wish to navigate too
        JPanel NavigationPanel = new JPanel();
        NavigationPanel.setLayout(new GridLayout(1,2));
        NavigationPanel.setBounds(0, 0, WIDTH, 30);
        NavigationPanel.setBackground(Color.decode(MAINBGCOLOR));
        //Navigation Panel will have two buttons
        JButton Dailyselector = new JButton("Daily Veiw");
        JButton Calenderselector = new JButton("All Tasks");
        //add the buttons to the navigation bar
        NavigationPanel.add(Dailyselector);
        NavigationPanel.add(Calenderselector);
        
        //give the buttons actions
        Dailyselector.addActionListener((java.awt.event.ActionEvent evt) -> {
            //show daily, hide calander
            DailyTaskPanel.setVisible(true);
            CalanderView.setVisible(false);
        });
        Calenderselector.addActionListener((java.awt.event.ActionEvent evt) -> {
            //show daily, hide calander
            DailyTaskPanel.setVisible(false);
            CalanderView.setVisible(true);
        });

        
        
        //set up the main window
        
        // add the pannels
        this.add(DailyScrollPlan);
        this.add(NavigationPanel);
        
        //pack and set size
        this.pack();
        this.setResizable(false);
        
        this.setBounds(0,0,WIDTH+6, HEIGHT+58);
        this.setLocationRelativeTo(null);
        this.setTitle("Daily Tasks");
        
    }
    
    
    
    private void AddTasksToDaily(){
                
        //make a list of all tasks
        //make a list of buttons to assing later
        tasks = control.getallTasks(1);
        
        //formatter for date strings
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        
        for (Task item : tasks)  {
            //build the buttons text. remove ID later
            JButton butt = new JButton( 
                    "ID: (" +item.getID()+ ")        " + 
                    item.getTitle()+ "         " +
                    "Due Date: (" + item.getDueDate().format(formatter) + ")" 
                    );
            butt.setBackground(Color.decode(BUTTONCOLOR));  
            butt.setFocusPainted(false);
            butt.setContentAreaFilled(true);
            DailyTaskPanel.add(butt);
            System.out.println("" + 
                item.getID() + " " + 
                item.getTitle() + " " + 
                item.getDueDate() + " " + 
                item.getCompletion() + " " + 
                item.getOwnerId() + " " + 
                item.getDesc()
                );
            numberoftasks++;
        } 
        
        
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 452, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 644, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
