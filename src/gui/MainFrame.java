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
		this.setMinimumSize(new Dimension(600, 400));
		this.setPreferredSize(new Dimension(1080, 720));
		this.setResizable(true);
		this.taskTable = new TaskTable(Toolkit.getDefaultToolkit().getScreenSize(), this);
		this.initializeControls();
		this.pack();
		this.setLocationRelativeTo(null);
        if (this.logIn()) {
            this.chooseMethod();
            this.setVisible(true);
        }
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
		} else if (e.getSource().equals(this.exit)) {
            //TODO
		} else if (e.getSource().equals(this.edit)) {
			TaskDialog td = new TaskDialog(this, "New Task", true, this.taskTable.getTask());
			this.taskTable.insertTask(td.getTask());

		} else if (e.getSource().equals(this.delete)) {
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
			this.openConnection();
			this.chooseMethod();
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

	private boolean openConnection() {
		if (this.dbConnection == null) {
			LoginDialog dialog = new LoginDialog(this, "Login", true);
			if (dialog.isLoggedIn()) {
				this.dbConnection = new DatabaseConnection(dialog.getUsername(), dialog.getPassword());
				this.localFilepath = "tasks_" + dialog.getUsername() + ".txt";

				if (!dbConnection.checkConnection()) {
					dialog.setVisible(false);
					dialog.dispose();
					JOptionPane.showMessageDialog(null, "Wrong credentials!", "Warning",
							JOptionPane.INFORMATION_MESSAGE);
					this.dbConnection = null;
					this.openConnection();
				} else {
					dialog.setVisible(false);
					dialog.dispose();
				}
				return true;
			}
			return false;
		}
		return true;
	}

	private boolean logIn() {
	    boolean successful;
	    LoginDialog loginDialog = new LoginDialog(this, "Please Log In To Access Your Tasks", true);
	    if(loginDialog.isLoggedIn()) {
	        System.out.println(loginDialog.getUsername());
            if(new DatabaseConnection(loginDialog.getUsername(), loginDialog.getPassword()).checkConnection()) {
                this.dbConnection = new DatabaseConnection(loginDialog.getUsername(), loginDialog.getPassword());
                this.localFilepath = "tasks_" + loginDialog.getUsername() + ".txt";
                successful = true;
            } else {
                JOptionPane.showMessageDialog(null, "Username or password incorrect!", "Warning", JOptionPane.INFORMATION_MESSAGE);
                successful = false;
            }
        } else {
            successful = false;
        }
        return successful;
    }

	private void chooseMethod() {
		Date dbDate = this.dbConnection.getTimestampDB();
		Date localDate = SerializationHelper.getTimestampFile(this.localFilepath);
		System.out.println(dbDate);
        System.out.println(localDate);
		if (dbDate.getTime() >= localDate.getTime()) {
            this.taskList = dbConnection.getAllTasks();
            try {
                SerializationHelper.writeSerializedTask(this.taskList, this.localFilepath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                this.taskList = SerializationHelper.readSerializableTask(this.localFilepath);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            this.taskList.forEach(t -> this.dbConnection.addEntry(t));
		}
        this.taskTable.insertValuesIntoTable(this.taskList);
	}
}
