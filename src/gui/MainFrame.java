package gui;

import bll.Settings;
import bll.Task;
import dal.DatabaseConnection;
import dal.SerializationHelper;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

import static java.awt.BorderLayout.PAGE_END;

public class MainFrame extends JFrame implements ActionListener, ListSelectionListener, Serializable {
	private static final long serialVersionUID = -8026416994513756565L;
	private TaskTable taskTable;
	private JMenuItem changeUser;
	private JMenuItem exit;

	private JMenuItem newTask;
	private JMenuItem edit;
	private JMenuItem delete;

	private JMenuItem settingsItem;
	private JMenuItem filter;

	private JMenuItem github;

	private DatabaseConnection dbConnection;
	private Settings userSettings = null;

	private JLabel statusBar = null;

	public MainFrame(String identifier) throws HeadlessException {
		super(identifier);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				String ObjButtons[] = { "Yes", "No" };

				int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Taskplaner",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
				if (PromptResult == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		this.setMinimumSize(new Dimension(600, 400));
		this.setPreferredSize(new Dimension(1080, 720));
		this.setResizable(true);
		this.taskTable = new TaskTable(Toolkit.getDefaultToolkit().getScreenSize(), this);
		this.initializeControls();
		this.pack();
		this.setLocationRelativeTo(null);
		new LoginDialog(this, "Log in", true);

		while (dbConnection == null) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			this.userSettings = SerializationHelper
					.readSettings("settings/" + this.dbConnection.getUsername() + ".txt");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.setStatusBar("Getting tasks from database...");
		this.taskTable.insertValuesIntoTable(this.userSettings.isOnlyTodo() ? this.dbConnection.getUndoneTasks() : this.dbConnection.getAllTasks());
		// prevent adding tasks until data was loaded from database
		this.newTask.setEnabled(true);
		this.setStatusBar(
				"Logged in as " + (this.userSettings.getAliasName().equals("") ? this.dbConnection.getUsername()
						: this.userSettings.getAliasName()));
	}

	private void initializeControls() {
		this.setLayout(new BorderLayout());
		// create MenuBar
		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		this.changeUser = new JMenuItem("Change User");
		this.exit = new JMenuItem("Exit");

		JMenu task = new JMenu("Task");
		this.newTask = new JMenuItem("New");
		this.newTask.setEnabled(false);
		this.edit = new JMenuItem("Edit");
		this.edit.setEnabled(false);
		this.delete = new JMenuItem("Delete");
		this.delete.setEnabled(false);

		JMenu settings = new JMenu("Settings");
		this.settingsItem = new JMenuItem("Settings");
		this.filter = new JMenuItem("Filter");

		JMenu help = new JMenu("Help");
		this.github = new JMenuItem("Report a Bug");

		this.statusBar = new JLabel();

		// add to ActionListener
		this.exit.addActionListener(this);
		this.changeUser.addActionListener(this);
		this.newTask.addActionListener(this);
		this.edit.addActionListener(this);
		this.delete.addActionListener(this);
		this.settingsItem.addActionListener(this);
		this.filter.addActionListener(this);
		this.github.addActionListener(this);

		// Build menu
		this.setJMenuBar(menuBar);
		menuBar.setEnabled(false);

		menuBar.add(file);
		file.add(this.changeUser);
		file.add(new JSeparator());
		file.add(this.exit);

		menuBar.add(task);
		task.add(this.newTask);
		task.add(this.edit);
		task.add(this.delete);

		menuBar.add(settings);
		settings.add(this.settingsItem);
		settings.add(this.filter);

		menuBar.add(help);
		help.add(this.github);
		Toolkit.getDefaultToolkit().beep();

		// Build frame
		this.add(this.taskTable);
		this.add(this.statusBar, PAGE_END);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.newTask)) {
			TaskDialog td = new TaskDialog(this, "New Task", true);
			if (td.getTask() != null) {
				new LoadingDialog(this.dbConnection, this, "Connecting to database...", true);
				this.dbConnection.addEntry(td.getTask());
				this.taskTable.insertValueIntoTable(td.getTask());
			}

		} else if (e.getSource().equals(this.exit)) {
			System.exit(NORMAL);
		} else if (e.getSource().equals(this.edit)) {
			Task t = this.taskTable.getTask();
			TaskDialog td = new TaskDialog(this, "New Task", true, this.taskTable.getTask());

			if (!t.equals(td.getTask())) {
				new LoadingDialog(this.dbConnection, this, "Connecting to database...", true);
				this.dbConnection.updateEntry(t, td.getTask());
				this.taskTable.insertTask(td.getTask());
			}

		} else if (e.getSource().equals(this.delete)) {
			new LoadingDialog(this.dbConnection, this, "Connecting to database...", true);
			this.dbConnection.removeEntry(this.taskTable.getTask());
			this.taskTable.deleteTask();
		} else if (e.getSource().equals(this.settingsItem)) {
			new SettingsDialog(this, "Settings", true, this.userSettings);
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
			new LoginDialog(this, "Change user", true);
            this.setStatusBar("Getting tasks from database...");
            try {
                this.userSettings = SerializationHelper
                        .readSettings("settings/" + this.dbConnection.getUsername() + ".txt");
            } catch (IOException | ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            this.taskTable.insertValuesIntoTable(this.userSettings.isOnlyTodo() ? this.dbConnection.getUndoneTasks() : this.dbConnection.getAllTasks());
            this.setStatusBar("Logged in as " + (this.userSettings.getAliasName().equals("") ? this.dbConnection.getUsername()
                    : this.userSettings.getAliasName()));
		} else if (e.getSource().equals(this.filter)) {
			FilterDialog fd = new FilterDialog(this, "Filter Tasks", true);
			if (fd.getFrom() != null) {
				this.taskTable.filter(fd.getFrom(), fd.getUntil());
			}
		}
	}
	
	public void updateCheck(Task oldT,Task newT) {
		new LoadingDialog(this.dbConnection, this, "Connecting to database...", true);
		this.dbConnection.updateEntry(oldT,newT);
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

	void setDbConnection(DatabaseConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	private void setStatusBar(String status) {
		this.statusBar.setText(status);
	}

	Settings getUserSettings() {
		return userSettings;
	}
	
	
}
