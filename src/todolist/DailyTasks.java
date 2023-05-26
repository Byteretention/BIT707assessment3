/*
 * BIT707
 * Assessment 3
 * Student ID: 5032962
 */
package todolist;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

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

    //globals for the task edit plane.
    boolean taskEditOpen = false;
    int EXPANDEDWIDTH = 900;
    JPanel EditPanel;

    //jank date syste
    //max number of dates per month
    //we are not going to account for leap years. that code is too complex and we dont have the time to get it working
    int MAXDAYS[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

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
        //dumby, will be deleted
        EditPanel = new JPanel();

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

    private void updatePanels() {
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

        if (taskEditOpen) {
            this.setBounds(0, 0, EXPANDEDWIDTH + 6, HEIGHT + 58);
        } else {
            this.setBounds(0, 0, WIDTH + 6, HEIGHT + 58);
        }
        this.setLocationRelativeTo(null);

        this.revalidate();
        this.repaint();
    }

    //we need to recreate the calander pannel each time we update it
    //return the scrollPane cause we need to edit it later
    private void createCalander() {
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
        if (!calandershown) {
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
            //panel to hold our row for task informaiton
            JPanel itempannel = new JPanel();
            itempannel.setLayout(null);
            itempannel.setBackground(Color.decode(MAINBGCOLOR));

            //build the buttons text. remove ID later
            String status = item.getCompletion() ? "Done" : "ToDo";

            //format title to prevent it from being too long
            String snippedtitle = item.getTitle();
            snippedtitle = snippedtitle + "                                                 ";
            if (snippedtitle.length() >= 17) {
                snippedtitle = snippedtitle.substring(0, 15);
                if (snippedtitle.endsWith(" ")) {
                    snippedtitle = snippedtitle + "     ";
                } else {
                    snippedtitle = snippedtitle + "...";
                }
            }

            //create the button to go on the left
            String buttontext
                    = status + "         "
                    + snippedtitle;

            JButton butt = new JButton(
                    buttontext
            );

            butt.setBackground(Color.decode(BUTTONCOLOR));
            butt.setFocusPainted(false);
            butt.setContentAreaFilled(true);
            butt.setHorizontalAlignment(SwingConstants.LEFT);
            butt.setBounds(0, 0, WIDTH - 150, 35);

            //add action to button to allow us to open a edit panel if we select a task
            butt.addActionListener((java.awt.event.ActionEvent evt) -> {
                createEditor(item);
            });

            //create the label to go on the right
            Label dueDate = new Label("Due Date: (" + item.getDueDate().format(formatter) + ")");
            dueDate.setBounds(WIDTH - 150 + 3, 0, WIDTH - 80, 35);

            itempannel.add(butt);
            itempannel.add(dueDate);
            CalanderView.add(itempannel);
            NumberofallTasks++;
        }

    }

    //create the Daily Panel
    //return scroll panel cause we need to edit it later
    private void createDaily() {
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
        if (!dailyshown) {
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
            if (LocalDate.now().equals(item.getDueDate())) {
                //panel to hold our row for task informaiton
                JPanel itempannel = new JPanel();
                itempannel.setLayout(null);
                itempannel.setBackground(Color.decode(MAINBGCOLOR));

                //build the buttons text. remove ID later
                String status = item.getCompletion() ? "Done" : "ToDo";

                //format title to prevent it from being too long
                String snippedtitle = item.getTitle();
                snippedtitle = snippedtitle + "                                                 ";
                if (snippedtitle.length() >= 17) {
                    snippedtitle = snippedtitle.substring(0, 15);
                    if (snippedtitle.endsWith(" ")) {
                        snippedtitle = snippedtitle + "     ";
                    } else {
                        snippedtitle = snippedtitle + "...";
                    }
                }

                //create the button to go on the left
                String buttontext
                        = status + "         "
                        + snippedtitle;

                JButton butt = new JButton(
                        buttontext
                );

                butt.setBackground(Color.decode(BUTTONCOLOR));
                butt.setFocusPainted(false);
                butt.setContentAreaFilled(true);
                butt.setHorizontalAlignment(SwingConstants.LEFT);
                butt.setBounds(0, 0, WIDTH - 150, 35);

                //add action to button to allow us to open a edit panel if we select a task
                butt.addActionListener((java.awt.event.ActionEvent evt) -> {
                    createEditor(item);
                });

                //create the label to go on the right
                Label dueDate = new Label("Due Date: (" + item.getDueDate().format(formatter) + ")");
                dueDate.setBounds(WIDTH - 150 + 3, 0, WIDTH - 80, 35);
                
                itempannel.add(butt);
                itempannel.add(dueDate);
                DailyTaskPanel.add(itempannel);
                NumberoftodayTasks++;
            }

        }

    }

    private void createEditor(Task item) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        //remove the old Panel;
        this.remove(EditPanel);
        //set flag for edit panel active to true
        taskEditOpen = true;

        //create the panel
        EditPanel = new JPanel();
        //set the panel size
        EditPanel.setBounds(415, 0, 485, HEIGHT + 30);
        EditPanel.setBackground(Color.decode(MAINBGCOLOR));

        //for this panel we need to do a lot of fun with grids
        EditPanel.setLayout(null);
        Border blackline = BorderFactory.createLineBorder(Color.black);
        EditPanel.setBorder(blackline);

        //crate label for if task is done or not
        JButton Status = new JButton(item.getCompletion() ? "Done" : "ToDo");
        Status.setBounds(5, 5, 60, 35);
        Status.setBackground(Color.decode(BUTTONCOLOR));
        EditPanel.add(Status);
        //set Button to be Toggleable between complete and not complete
        Status.addActionListener((java.awt.event.ActionEvent evt) -> {
            item.toggleCompleteion();
            Status.setText(item.getCompletion() ? "Done" : "ToDo");
        });;

        //Create label for Task ID
        Label TaskID = new Label("Task ID: " + item.getID(), Label.CENTER);
        TaskID.setBounds(68, 5, 80, 35);
        TaskID.setBackground(Color.decode(BUTTONCOLOR));
        EditPanel.add(TaskID);

        //Task Title
        String snippedtitle = item.getTitle();
        snippedtitle = snippedtitle + "                     ";
        if (snippedtitle.length() >= 23) {
            snippedtitle = snippedtitle.substring(0, 21);
            if (snippedtitle.endsWith(" ")) {
                snippedtitle = snippedtitle + "     ";
            } else {
                snippedtitle = snippedtitle + "...";
            }
        }

        Label TaskTitle = new Label(snippedtitle);
        TaskTitle.setBounds(204, 5, 160, 35);
        TaskTitle.setBackground(Color.decode(BUTTONCOLOR));
        EditPanel.add(TaskTitle);

        //owner name would go here but we dont have atable for all the owners/users so we will just display
        //owner id 
        Label OwnerName = new Label(Integer.toString(item.getOwnerId()), Label.RIGHT);
        OwnerName.setBounds(442, 5, 40, 35);
        OwnerName.setBackground(Color.decode(BUTTONCOLOR));
        EditPanel.add(OwnerName);

        //Task Desc
        JTextArea taskDesc = new JTextArea();
        taskDesc.setBounds(3, 41, 479, 300);
        taskDesc.setText(item.getDesc());
        EditPanel.add(taskDesc);
        
        //get date from task
        String taskdatetoedit = item.getDueDate().format(formatter);
        String[] dateparts = taskdatetoedit.split("/");
        int dayint = Integer.valueOf(dateparts[0]);
        int monthint = Integer.valueOf(dateparts[1]);
        int yearint = Integer.valueOf(dateparts[2]);
        //create date entry
        SpinnerModel daymax = new SpinnerNumberModel(dayint, 1, 31, 1);
        JSpinner Day = new JSpinner(daymax);
        SpinnerModel monthmax = new SpinnerNumberModel(monthint, 1, 12, 1);
        JSpinner Month = new JSpinner(monthmax);
        SpinnerModel yearmax = new SpinnerNumberModel(yearint, 1996, 3030, 1);
        JSpinner Year = new JSpinner(yearmax);
        
        
        

        Day.setBounds(3, 300 + 41 + 3, 60, 35);
        Month.setBounds(66, 300 + 41 + 3, 60, 35);
        Year.setBounds(129, 300 + 41 + 3, 90, 35);

        EditPanel.add(Day);
        EditPanel.add(Month);
        EditPanel.add(Year);

        //Button to force entry to update if client is done editing
        JButton update = new JButton("Update Task");
        update.setBounds(239, 492, 120, 35);
        update.setBackground(Color.decode(BUTTONCOLOR));
        //make it so clicking update, updates the sql database
        update.addActionListener((java.awt.event.ActionEvent evt) -> {
            //build the date string
            String Dateoutput = "";
            if ((Integer) Day.getValue() > MAXDAYS[(Integer) Month.getValue() - 1]) {
                Dateoutput = MAXDAYS[(Integer) Month.getValue() - 1] + "/";
                if ((Integer) Month.getValue() <= 10) {
                    Dateoutput = Dateoutput + "0" + (Integer) Month.getValue() + "/" + (Integer) Year.getValue();
                } else {
                    Dateoutput = Dateoutput + (Integer) Month.getValue() + "/" + (Integer) Year.getValue();
                }

                JOptionPane.showMessageDialog(this, "Date to high for Selected month.\nAssuming Requested input should be: " + Dateoutput);

            } else {
                if ((Integer) Day.getValue() <= 10) {
                    Dateoutput = "0" + (Integer) Day.getValue() + "/";
                } else {
                    Dateoutput = (Integer) Day.getValue() + "/";
                }
                if ((Integer) Month.getValue() <= 10) {
                    Dateoutput = Dateoutput + "0" + (Integer) Month.getValue() + "/" + (Integer) Year.getValue();
                } else {
                    Dateoutput = Dateoutput + (Integer) Month.getValue() + "/" + (Integer) Year.getValue();
                }
            }
            
            LocalDate datetoset = LocalDate.parse(Dateoutput, formatter);
            item.setDueDate(datetoset);

            item.setDesc(taskDesc.getText());
            control.updateTask(item);
            updatePanels();
        });;
        EditPanel.add(update);
        //button to close edit menu if they do not wish to keep editing
        JButton close = new JButton("Canel");
        close.setBounds(362, 492, 120, 35);
        close.setBackground(Color.decode(BUTTONCOLOR));
        //make it so clicking close, closes the edit ui.
        close.addActionListener((java.awt.event.ActionEvent evt) -> {
            taskEditOpen = false;
            updatePanels();
        });
        EditPanel.add(close);

        //add the panel
        this.add(EditPanel);
        //run a update since we changed things
        updatePanels();
    }

    private void createNavi() {
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
            updatePanels();
        });
        Calenderselector.addActionListener((java.awt.event.ActionEvent evt) -> {
            dailyshown = false;
            calandershown = true;
            //show daily, hide calander
            dailyScroll.setBounds(900, 30, WIDTH, HEIGHT);
            DailyTaskPanel.setVisible(dailyshown);
            calanderScroll.setBounds(0, 30, WIDTH, HEIGHT);
            CalanderView.setVisible(calandershown);
            updatePanels();
        });
        // add the pannels
        this.add(NavigationPanel);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel1.setText("jLabel1");

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
