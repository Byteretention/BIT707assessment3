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
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 *
 * @author Tarrynt Whitty
 */
public class DailyTasks extends javax.swing.JFrame {

    Boolean windowcreated = false;
    ToDoController control;
    String url = "C://sqlite/db/ToDo.db";

    //window style settings.
    //size
    int WIDTH = 415;
    int HEIGHT = 500;
    //Colors of objects
    String MAINBGCOLOR = "#FFFFFF";
    String BUTTONCOLOR = "#EEEEEE";
    String HEADERCOLOR = "#4527A0";
    JPanel EditPanel;
    JPanel DailyVeiw;
    JPanel CalanderVeiw;
    boolean EditorActive = false;
    int activemenu = 1;

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
        this.setLayout(null);
        //create navigation bar
        JPanel Navi = createNavi();
        DailyVeiw = DailyVeiw();
        DailyVeiw.setBounds(0, 30, WIDTH, HEIGHT);
        CalanderVeiw = CalanderVeiw();
        CalanderVeiw.setBounds(9000, 9000, WIDTH, HEIGHT);
        EditPanel = new JPanel();

        this.add(Navi);
        this.add(DailyVeiw);
        this.add(CalanderVeiw);

        //pack and set size
        this.pack();
        this.setResizable(false);
        this.setSize(WIDTH + 6, HEIGHT + 58);
        this.setLocationRelativeTo(null);
        this.setTitle("Daily Tasks");

    }

    /**
     * Calling this will update all panels on screen
     * call it when you make any changes to the database.
     * ensures everything stays up to date
     */
    private void updatePanels() {
        this.remove(DailyVeiw);
        this.remove(CalanderVeiw);
        if (EditorActive) {
            this.setSize(WIDTH + 6 + 485, HEIGHT + 58);
        } else {
            this.setSize(WIDTH + 6, HEIGHT + 58);
        }
        DailyVeiw = DailyVeiw();
        CalanderVeiw = CalanderVeiw();
        if (activemenu == 1) {
            DailyVeiw.setBounds(0, 30, WIDTH, HEIGHT);
            CalanderVeiw.setBounds(9000, 9000, WIDTH, HEIGHT);
        } else if (activemenu == 2) {
            DailyVeiw.setBounds(9000, 9000, WIDTH, HEIGHT);
            CalanderVeiw.setBounds(0, 30, WIDTH, HEIGHT);
        }

        this.add(DailyVeiw);
        this.add(CalanderVeiw);
        this.validate();
        this.repaint();
    }

    /**
     * Will create a panel that a user can use to select a task to edit. 
     * calling this will return a panel containing a button and a label
     * the button will have the task status and the title for the task
     * the label will have the due date for the task
     * the button can be used to open the editor panel
     * @param item, item we wish to make into a display panel
     * @return, JPanel with selection button and label
     */
    private JPanel TaskPanel(Task item) {
        //create the Panel
        JPanel taskpannel = new JPanel(new GridLayout(1, 2));
        taskpannel.setBackground(Color.decode(MAINBGCOLOR));
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

        JButton butt = new JButton(buttontext);

        butt.setBackground(Color.decode(BUTTONCOLOR));
        butt.setFocusPainted(false);
        butt.setContentAreaFilled(true);
        butt.setHorizontalAlignment(SwingConstants.LEFT);
        butt.setBounds(0, 0, WIDTH - 150, 35);

        //add action to button to allow us to open a edit panel if we select a task
        butt.addActionListener((java.awt.event.ActionEvent evt) -> {
            createEditor(item, true);
            updatePanels();
        });
        //formatter for date strings
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        //create the label to go on the right
        Label dueDate = new Label("Due Date: (" + item.getDueDate().format(formatter) + ")");
        dueDate.setBounds(WIDTH - 150 + 3, 0, WIDTH - 80, 35);

        taskpannel.add(butt);
        taskpannel.add(dueDate);

        return taskpannel;
    }

    /**
     * when calling this. it will get a list of all tasks that a user owns
     * and then check the date for them
     * if the date is today and the task is not complete it will call TaskPanel()
     * and create a list of tasks that need to be completed today
     * @return returns a JPanel with a list of tasks that must be compelted today
     */
    private JPanel DailyVeiw() {
        JPanel host = new JPanel();
        //create DailyVeiw Bar
        JPanel DailyVeiw = new JPanel();

        //set DailyView Bar size and color
        DailyVeiw.setBackground(Color.decode(MAINBGCOLOR));

        //add all the tasks
        int taskcount = 0;
        for (Task item : control.getallTasks(1)) {
            if (LocalDate.now().equals(item.getDueDate())) {
                if (!item.getCompletion()) {
                    DailyVeiw.add(TaskPanel(item));
                    taskcount++;
                }

            }
        }
        //because there can be alot of tasks, set the grid layout now
        DailyVeiw.setLayout(new GridLayout(taskcount, 1));

        //scroll pane time?
        JScrollPane scrollPane = new JScrollPane(DailyVeiw,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);

        host.setLayout(new GridLayout(1, 1));
        host.add(scrollPane);

        return host;
    }

    /**
     * when calling this. it will get a list of all tasks that a user owns
     * and create a list of tasks that the user owns. completed or not
     * @return JPanel containing all tasks the user owns
     */
    public JPanel CalanderVeiw() {
        JPanel host = new JPanel();
        //create DailyVeiw Bar
        JPanel DailyVeiw = new JPanel();

        //set DailyView Bar size and color
        DailyVeiw.setBackground(Color.decode(MAINBGCOLOR));

        //add all the tasks
        int taskcount = 0;
        for (Task item : control.getallTasks(1)) {

            DailyVeiw.add(TaskPanel(item));
            taskcount++;

        }
        //because there can be alot of tasks, set the grid layout now
        DailyVeiw.setLayout(new GridLayout(taskcount, 1));

        //scroll pane time?
        JScrollPane scrollPane = new JScrollPane(DailyVeiw,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);

        host.setLayout(new GridLayout(1, 1));
        host.add(scrollPane);

        return host;
    }

    //create a editor window
    //, if isEdit is true we are editing a item
    //, if isEdit is false we are createing a new item

    /**
     * Call this to create a panel for ether creating a new task or editing a existing one
     * if creating a task will provide different buttons and editable feilds then if editing a task
     * @param item, if we are creating a task. provide null. if you are editing a task provide the task to edit
     * @param isEdit, if false the function will assume you are creating a task, if true it will assume you are editing a task
     */
    public void createEditor(Task item, boolean isEdit) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        //remove the old Panel;
        this.remove(EditPanel);
        //set flag for edit panel active to true
        EditorActive = true;

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
        //if is edit, edit task and and display if complete
        //if is create, display todo
        if (isEdit) {
            JButton Status = new JButton(item.getCompletion() ? "Done" : "ToDo");
            Status.setBounds(5, 5, 60, 35);
            Status.setBackground(Color.decode(BUTTONCOLOR));
            EditPanel.add(Status);
            //set Button to be Toggleable between complete and not complete
            Status.addActionListener((java.awt.event.ActionEvent evt) -> {
                item.toggleCompleteion();
                Status.setText(item.getCompletion() ? "Done" : "ToDo");
            });
        } else {
            Label Status = new Label("ToDo", Label.CENTER);
            Status.setBounds(5, 5, 60, 35);
            Status.setBackground(Color.decode(BUTTONCOLOR));
            EditPanel.add(Status);
        }

        //Create label for Task ID
        //if editing task, show id
        //if creating task display "new task"
        String taskidtext = "";
        if (isEdit) {
            taskidtext = "Task ID: " + item.getID();
        } else {
            taskidtext = "New Task";
        }
        Label TaskID = new Label(taskidtext, Label.CENTER);
        TaskID.setBounds(68, 5, 80, 35);
        TaskID.setBackground(Color.decode(BUTTONCOLOR));
        EditPanel.add(TaskID);

        //Task Title
        //if editing task, show a noneditable title
        //else make a textbox that allows the user to make a new name
        JTextField TaskTitleeditable = new JTextField(); //this should only apper if we in new mode
        if (isEdit) {
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
        } else {

            TaskTitleeditable.setBounds(204, 5, 160, 35);
            EditPanel.add(TaskTitleeditable);
        }

        //owner name would go here but we dont have atable for all the owners/users so we will just display
        //owner id 
        //if in edit mode show id
        //if in create show nothing
        if (isEdit) {
            Label OwnerName = new Label(Integer.toString(item.getOwnerId()), Label.RIGHT);
            OwnerName.setBounds(442, 5, 40, 35);
            OwnerName.setBackground(Color.decode(BUTTONCOLOR));
            EditPanel.add(OwnerName);
        }

        //Task Desc
        //if in edit mode assign existing text to box. other wise do nothing
        JTextArea taskDesc = new JTextArea();
        taskDesc.setBounds(3, 41, 479, 300);
        if (isEdit) {
            taskDesc.setText(item.getDesc());
        }
        EditPanel.add(taskDesc);

        //get date from task
        //if in edit mode, get date from task
        //if in create, assign todays day
        String taskdatetoedit = "";
        if (isEdit) {
            taskdatetoedit = item.getDueDate().format(formatter);
        } else {
            taskdatetoedit = LocalDate.now().format(formatter);
        }

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
        //if in edit mode, create update button
        //if in create mode, create create button
        if (isEdit) {
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

        } else {
            //create button to create a new task and publish it to database
            System.out.println("create button");
            JButton create = new JButton("Create Task");
            create.setBounds(239, 492, 120, 35);
            create.setBackground(Color.decode(BUTTONCOLOR));
            create.addActionListener((java.awt.event.ActionEvent evt) -> {
                //if the task name is null just refuse to do anything and error
                if (TaskTitleeditable.getText().equals("")) {
                    JOptionPane.showMessageDialog(this, "You have not entered a task title\nPlease enter a title");
                } else {
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
                    //for now all owners will be owner id 1 since we dont have a owner class
                    control.createTask(datetoset, 1, TaskTitleeditable.getText(), taskDesc.getText());
                    updatePanels();
                }
            });
            EditPanel.add(create);
        }
        //button to close edit menu if they do not wish to keep editing
        JButton close = new JButton("Canel");
        close.setBounds(362, 492, 120, 35);
        close.setBackground(Color.decode(BUTTONCOLOR));
        //make it so clicking close, closes the edit ui.
        close.addActionListener((java.awt.event.ActionEvent evt) -> {
            EditorActive = false;
            updatePanels();
        });
        EditPanel.add(close);

        //add a delete button if in edit mode
        if (isEdit) {
            JButton Delele = new JButton("Delete");
            Delele.setBounds(116, 492, 120, 35);
            Delele.setBackground(Color.decode(BUTTONCOLOR));
            //make it so clicking close, closes the edit ui.
            Delele.addActionListener((java.awt.event.ActionEvent evt) -> {
                control.removeTask(item.getID());
                EditorActive = false;
                updatePanels();
            });
            EditPanel.add(Delele);
        }

        //add the panel
        this.add(EditPanel);
        //run a update since we changed things
        updatePanels();
    }

    /**
     * Will return a JPanel containing a navigation bar for the UI
     * this bar will allow user to atemp to create a task
     * or switch between daily view and all tasks view.
     * @return JPanel with navigation bar
     */
    public JPanel createNavi() {
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
            activemenu = 1;
            DailyVeiw.setBounds(0, 30, WIDTH, HEIGHT);
            CalanderVeiw.setBounds(9000, 9000, WIDTH, HEIGHT);
        });
        Calenderselector.addActionListener((java.awt.event.ActionEvent evt) -> {
            activemenu = 2;
            DailyVeiw.setBounds(9000, 9000, WIDTH, HEIGHT);
            CalanderVeiw.setBounds(0, 30, WIDTH, HEIGHT);
        });

        //because at this point we have given up trying to match the UI to the design files
        JButton CreateNewTask = new JButton("Create Task");
        NavigationPanel.add(CreateNewTask);

        //call edit task but tell it we in create task mode
        CreateNewTask.addActionListener((java.awt.event.ActionEvent evt) -> {
            createEditor(null, false);
        });

        // add the pannels
        return NavigationPanel;
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
