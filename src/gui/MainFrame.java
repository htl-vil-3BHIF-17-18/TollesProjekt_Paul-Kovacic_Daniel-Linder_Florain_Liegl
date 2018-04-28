package gui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bll.Task;
import dal.DatabaseConnection;
import dal.SerializationHelper;

public class MainFrame extends JFrame implements ActionListener, ListSelectionListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8026416994513756565L;
	private TaskTable taskTable;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem changeUser;
	private JMenuItem exit;

	private JMenu task;
	private JMenuItem newTask;
	private JMenuItem edit;
	private JMenuItem delete;

	private JMenu settings;

	private JMenu help;
	private JMenuItem github;

	private List<Task> taskList;
	private DatabaseConnection dbConnection;

	private String localFilepath;

	public MainFrame(String identifier) throws HeadlessException {
		super(identifier);
		this.taskList = new ArrayList<>();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(600, 400));
		this.setPreferredSize(new Dimension(1080, 720));
		this.setResizable(true);
		this.taskTable = new TaskTable(Toolkit.getDefaultToolkit().getScreenSize(), this);
		this.initializeControls();
		this.pack();
		this.setLocationRelativeTo(null);
		new LoginDialog(this,"Please enter your credentials to access your tasks", true);
		this.taskList=this.dbConnection.getAllTasks();
		this.taskTable.insertValuesIntoTable(this.taskList);
	}

	private void initializeControls() {
		this.setLayout(new GridLayout(0, 1));
		// create MenuBar
		this.menuBar = new JMenuBar();

		this.file = new JMenu("File");
		this.changeUser = new JMenuItem("Change User");
		this.exit = new JMenuItem("Exit");

		this.task = new JMenu("Task");
		this.newTask = new JMenuItem("New");
		this.edit = new JMenuItem("Edit");
		this.delete = new JMenuItem("Delete");

		this.settings = new JMenu("Settings");

		this.help = new JMenu("Help");
		this.github = new JMenuItem("Report a Bug");

		// add to ActionListener
		this.exit.addActionListener(this);
		this.changeUser.addActionListener(this);
		this.newTask.addActionListener(this);
		this.edit.addActionListener(this);
		this.delete.addActionListener(this);
		this.settings.addActionListener(this);
		this.github.addActionListener(this);

		//
		this.edit.setEnabled(false);
		this.delete.setEnabled(false);

		// add to Frame

		this.setJMenuBar(this.menuBar);

		this.menuBar.add(this.file);
		this.file.add(this.changeUser);
		this.file.add(new JSeparator());
		this.file.add(this.exit);

		this.menuBar.add(this.task);
		this.task.add(this.newTask);
		this.task.add(this.edit);
		this.task.add(this.delete);

		this.menuBar.add(this.settings);

		this.menuBar.add(this.help);
		this.help.add(this.github);

		// add TaskTable
		this.add(this.taskTable);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.newTask)) {
			TaskDialog td = new TaskDialog(this, "New Task", true);
			this.taskTable.insertValueIntoTable(td.getTask());
			this.dbConnection.addEntry(td.getTask());
		} else if (e.getSource().equals(this.exit)) {
            System.exit(NORMAL);
		} else if (e.getSource().equals(this.edit)) {
			Task t=this.taskTable.getTask();
			TaskDialog td = new TaskDialog(this, "New Task", true, this.taskTable.getTask());
			this.taskTable.insertTask(td.getTask());
			this.dbConnection.updateEntry(t, td.getTask());
		} else if (e.getSource().equals(this.delete)) {
			this.dbConnection.removeEntry(this.taskTable.getTask());
			this.taskTable.deleteTask();		
		} else if (e.getSource().equals(this.settings)) {
            new SettingsDialog(this, "Settings", true);
		} else if (e.getSource().equals(this.github)) {
			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.browse(new URI(
							"https://github.com/htl-vil-3BHIF-17-18/TollesProjekt_Paul-Kovacic_Daniel-Linder_Florain_Liegl/issues"));
				} catch (IOException | URISyntaxException ex) {
					ex.printStackTrace();
				}
			} else {
				Runtime runtime = Runtime.getRuntime();
				try {
					runtime.exec("xdg-open "
							+ "https://github.com/htl-vil-3BHIF-17-18/TollesProjekt_Paul-Kovacic_Daniel-Linder_Florain_Liegl/issues");
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		} else if (e.getSource().equals(this.changeUser)) {
			new LoginDialog(this, "Please enter your credentials to access your tasks", true);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (this.taskTable.getSelected() >= 0) {
			this.edit.setEnabled(true);
			this.delete.setEnabled(true);
		} else {
			this.edit.setEnabled(false);
			this.delete.setEnabled(false);
		}

	}

    public void setDbConnection(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void setLocalFilepath(String localFilepath) {
	    this.localFilepath = localFilepath;
    }
}
