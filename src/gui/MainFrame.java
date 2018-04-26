package gui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bll.Task;
import dal.DatabaseConnection;
import dal.SerializationHelper;

public class MainFrame extends JFrame implements ActionListener, ListSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8026416994513756565L;
	private TaskTable taskTable;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenu loadFrom;
	private JMenuItem fromDatabase;
	private JMenuItem fromFile;
	private JMenuItem saveAs;
	private JMenuItem sync;
	private JMenuItem exit;

	private JMenu task;
	private JMenuItem newTask;
	private JMenuItem edit;
	private JMenuItem delete;

	private JMenu settings;

	private JMenu help;
	private JMenuItem github;

	private List<Task> tl ;
	private DatabaseConnection db;

	public MainFrame(String identifier) throws HeadlessException {
		super(identifier);
		this.tl = new ArrayList<>();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(600, 400));
		this.setPreferredSize(new Dimension(1080, 720));
		this.setResizable(true);
		this.taskTable = new TaskTable(Toolkit.getDefaultToolkit().getScreenSize(), this);
		this.initializeControls();
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void initializeControls() {
		// TODO Auto-generated method stub
		this.setLayout(new GridLayout(0, 1));
		// create MenuBar
		this.menuBar = new JMenuBar();

		this.file = new JMenu("File");
		this.loadFrom = new JMenu("Load From");
		this.saveAs = new JMenuItem("Save As...");
		this.fromDatabase = new JMenuItem("Database");
		this.fromFile = new JMenuItem("File");
		this.sync = new JMenuItem("Sync");
		this.exit = new JMenuItem("Exit");

		this.task = new JMenu("Task");
		this.newTask = new JMenuItem("New");
		this.edit = new JMenuItem("Edit");
		this.delete = new JMenuItem("Delete");

		this.settings = new JMenu("Settings");

		this.help = new JMenu("Help");
		this.github = new JMenuItem("Report a Bug");

		// add to ActionListener
		this.loadFrom.addActionListener(this);
		this.saveAs.addActionListener(this);
		this.exit.addActionListener(this);
		this.sync.addActionListener(this);
		this.newTask.addActionListener(this);
		this.edit.addActionListener(this);
		this.delete.addActionListener(this);
		this.settings.addActionListener(this);
		this.github.addActionListener(this);
		this.fromDatabase.addActionListener(this);
		this.fromFile.addActionListener(this);

		//
		this.edit.setEnabled(false);
		this.delete.setEnabled(false);

		// add to Frame

		this.setJMenuBar(this.menuBar);

		this.menuBar.add(this.file);
		this.file.add(this.loadFrom);
		this.loadFrom.add(this.fromDatabase);
		this.loadFrom.add(this.fromFile);
		this.file.add(new JSeparator());
		this.file.add(this.saveAs);
		this.file.add(this.sync);
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
		// TODO Auto-generated method stub
		if (e.getSource().equals(this.fromDatabase)) {
			this.openConnection();
			this.getTaskFromDb();
			this.taskTable.insertValuesIntoTable(this.tl);

		} else if (e.getSource().equals(this.fromFile)) {

			try {
				System.out.println(SerializationHelper.readSerializableTask("Tasks.bat"));
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

		} else if (e.getSource().equals(this.newTask)) {
			TaskDialog td = new TaskDialog(this, "New Task", true);
			this.taskTable.insertValueIntoTable(td.getTask());
		} else if (e.getSource().equals(this.saveAs)) {

			try {
				SerializationHelper.writeSerializedTask(this.tl,"Tasks.bat");
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} else if (e.getSource().equals(this.exit)) {

		} else if (e.getSource().equals(this.sync)) {
			this.openConnection();
			for (Task t : this.taskTable.getAllTasks()) {
				this.db.addEntry(t);
			}

		} else if (e.getSource().equals(this.edit)) {
			TaskDialog td = new TaskDialog(this, "New Task", true, this.taskTable.getTask());
			this.taskTable.insertTask(td.getTask());

		} else if (e.getSource().equals(this.delete)) {
			

		} else if (e.getSource().equals(this.settings)) {

		} else if (e.getSource().equals(this.github)) {

			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.browse(new URI(
							"https://github.com/htl-vil-3BHIF-17-18/TollesProjekt_Paul-Kovacic_Daniel-Linder_Florain_Liegl/issues"));
				} catch (IOException | URISyntaxException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			} else {
				Runtime runtime = Runtime.getRuntime();
				try {
					runtime.exec("xdg-open "
							+ "https://github.com/htl-vil-3BHIF-17-18/TollesProjekt_Paul-Kovacic_Daniel-Linder_Florain_Liegl/issues");
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if (!(this.edit.isEnabled() && this.delete.isEnabled())) {
			this.edit.setEnabled(true);
			this.delete.setEnabled(true);
		}
	}

	private void openConnection() {
		// TODO Auto-generated method stub
		if (this.db == null) {
			LoginDialog dialog = new LoginDialog(this, "Login", true);
			if (dialog.isLogedIn())
				this.db = new DatabaseConnection(dialog.getUsername(), dialog.getPassword());
			if (!db.checkConnection()) {
				JOptionPane.showMessageDialog(null, "Wrong credentials!!", "Warning", JOptionPane.INFORMATION_MESSAGE);
				this.db = null;
			}

		}
	}

	private void getTaskFromDb() {
		if (this.db != null) {
			this.tl = db.getAllTasks();
		}
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TaskTable getTaskTable() {
		return taskTable;
	}

	public JMenu getFile() {
		return file;
	}

	public JMenuItem getSettings() {
		return sync;
	}

	public JMenu getConnection() {
		return task;
	}

	public JMenuItem getLogin() {
		return newTask;
	}

	public JMenu getWindow() {
		return settings;
	}

	public JMenu getHelp() {
		return help;
	}

}
