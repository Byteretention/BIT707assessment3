/*
 * BIT707
 * Assessment 3
 * Student ID: 5032962
 */
package todolist;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
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
    String HEADERCOLOR = "#4527A0";

    int NumberoftodayTasks;
    int NumberofallTasks;

    //our Panels for this screen
    JPanel DailyTaskPanel;
    JPanel CalanderView;
    
    //Our scrollers, we need to remove and remake them each upate
    JScrollPane dailyScroll;
    JScrollPane calanderScroll;
    //current hidden status of each panel
    boolean dailyshown = true;
    boolean calandershown = false;
    
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
        try {
            control.connect(url, 0);
        } catch (Exception e) {
            System.out.println("Failed to connect to : " + url);
        }

        //crate our panels
        createDaily();
        createCalander();
        createNavi();

        //hide and show the default pannels
        DailyTaskPanel.setVisible(dailyshown);
        CalanderView.setVisible(calandershown);

  
        //pack and set size
        this.pack();
        this.setResizable(false);
        this.setBounds(0, 0, WIDTH + 6, HEIGHT + 58);
        this.setLocationRelativeTo(null);
        this.setTitle("Daily Tasks");

    }
    
    
    private void updatePanels(){
        //this is to redraw the screen.
        //we need to remove the old panels and then remake them
        this.remove(dailyScroll);
        this.remove(calanderScroll);
        //recreate the old Panels
        //crate our panels
        createDaily();
        createCalander();
        //hide and show the default pannels
        DailyTaskPanel.setVisible(dailyshown);
        CalanderView.setVisible(calandershown);
        
        this.revalidate();
        this.repaint();
    }
    
    //we need to recreate the calander pannel each time we update it
    //return the scrollPane cause we need to edit it later
    private void createCalander(){
        WIDTH = 415;
        HEIGHT = 500;
              
        //create the pannel
        //we dont need to delete the old one cause jvm garbage collect should delete it
        CalanderView = new JPanel();
        //create all the buttons and add them to the JPanel
        AddTasksToCalander();
        
        //set the layout for the panel
        //set the layout for the all tasks panel
        CalanderView.setLayout(new GridLayout(NumberofallTasks, 1));
        CalanderView.setBackground(Color.decode(MAINBGCOLOR));
        JScrollPane CalanderScrollPlan = new JScrollPane(CalanderView);
        CalanderScrollPlan.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        CalanderScrollPlan.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        //if we are ment to be showing this panel, have it onscreen 0, 0
        //otherwise put it off screen
        int x = 0;
        int y = 30;
        if(!calandershown){
            x = 9000;
            y = 9000;
        }
        CalanderScrollPlan.setBounds(x, y, WIDTH, HEIGHT);
        //add the panel to the form
        this.add(CalanderScrollPlan);
        this.calanderScroll = CalanderScrollPlan;
        
        
        
    }

    private void AddTasksToCalander() {

        tasks = control.getallTasks(1);
        NumberofallTasks = 0;

        //formatter for date strings
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        for (Task item : tasks) {

            //build the buttons text. remove ID later
            String status = item.getCompletion() ? "Done" : "ToDo";
     
            JButton butt = new JButton(
                    status + "         "
                    + "ID: (" + item.getID() + ")        "
                    + item.getTitle() + "         "
                    + "Due Date: (" + item.getDueDate().format(formatter) + ")"
            );
            butt.setBackground(Color.decode(BUTTONCOLOR));
            butt.setFocusPainted(false);
            butt.setContentAreaFilled(true);
            CalanderView.add(butt);
            NumberofallTasks++;
        }

    }
    
    //create the Daily Panel
    //return scroll panel cause we need to edit it later
    private void createDaily(){
        WIDTH = 415;
        HEIGHT = 500;
        //
        DailyTaskPanel = new JPanel();
        
        //update buttons
        AddTasksToDaily();

        //set the layout for the daily panel
        DailyTaskPanel.setLayout(new GridLayout(NumberoftodayTasks, 1));
        DailyTaskPanel.setBackground(Color.decode(MAINBGCOLOR));
        JScrollPane DailyScrollPlan = new JScrollPane(DailyTaskPanel);
        DailyScrollPlan.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        DailyScrollPlan.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //if we are ment to be showing this panel, have it onscreen 0, 0
        //otherwise put it off screen
        int x = 0;
        int y = 30;
        if(!dailyshown){
            x = 9000;
            y = 9000;
        }
        DailyScrollPlan.setBounds(x, y, WIDTH, HEIGHT);
        //add it to the panel
        this.add(DailyScrollPlan);
        
        //return it
        this.dailyScroll = DailyScrollPlan;    
    }
    
    

    private void AddTasksToDaily() {

        //make a list of all tasks
        //make a list of buttons to assing later
        tasks = control.getallTasks(1);
        NumberoftodayTasks = 0;

        LocalDate today = LocalDate.now();

        //formatter for date strings
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        for (Task item : tasks) {

            if (today == item.getDueDate()) {
                //build the buttons text. remove ID later
                JButton butt = new JButton(
                        "ID: (" + item.getID() + ")        "
                        + item.getTitle() + "         "
                        + "Due Date: (" + item.getDueDate().format(formatter) + ")"
                );
                butt.setBackground(Color.decode(BUTTONCOLOR));
                butt.setFocusPainted(false);
                butt.setContentAreaFilled(true);
                DailyTaskPanel.add(butt);
                NumberoftodayTasks++;
            }

        }

    }
    
    
        
    private void createNavi(){
                        //create panel to handle selecting of what place we wish to navigate too
        JPanel NavigationPanel = new JPanel();
        NavigationPanel.setLayout(new GridLayout(1, 2));
        NavigationPanel.setBounds(0, 0, WIDTH, 30);
        NavigationPanel.setBackground(Color.decode(HEADERCOLOR));
        //Navigation Panel will have two buttons
        JButton Dailyselector = new JButton("Daily Veiw");
        JButton Calenderselector = new JButton("All Tasks");
        //add the buttons to the navigation bar
        NavigationPanel.add(Dailyselector);
        NavigationPanel.add(Calenderselector);

        //give the buttons actions
        //due to the nature of jpanel we need to move panels away 
        //and move them back to render them
        //correctly
        Dailyselector.addActionListener((java.awt.event.ActionEvent evt) -> {
            
            dailyshown = true;
            calandershown = false;
            //show daily, hide calander
            dailyScroll.setBounds(0, 30, WIDTH, HEIGHT);
            DailyTaskPanel.setVisible(dailyshown);
            calanderScroll.setBounds(900, 30, WIDTH, HEIGHT);
            CalanderView.setVisible(calandershown);
        });
        Calenderselector.addActionListener((java.awt.event.ActionEvent evt) -> {
            dailyshown = false;
            calandershown = true;
            //show daily, hide calander
            dailyScroll.setBounds(900, 30, WIDTH, HEIGHT);
            DailyTaskPanel.setVisible(dailyshown);
            calanderScroll.setBounds(0, 30, WIDTH, HEIGHT);
            CalanderView.setVisible(calandershown);
        });
        
        
        
        //test update button
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener((java.awt.event.ActionEvent evt) -> {
            updatePanels();
        });
        NavigationPanel.add(refresh);
        
        
        
        // add the pannels
        this.add(NavigationPanel);
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
