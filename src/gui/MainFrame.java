package gui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bll.Task;
import javafx.scene.control.Separator;

public class MainFrame extends JFrame implements ActionListener, ListSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8026416994513756565L;
	private TaskTable taskTable;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem newTable;
	private JMenuItem open;
	private JMenuItem saveAs;
	private JMenuItem save;
	private JMenuItem settings;

	private JMenu edit;

	private JMenu connection;
	private JMenuItem login;

	private JMenu window;
	private JMenuItem preferences;

	private JMenu help;
	private JMenuItem github;
	
	private List<Task> tl=null;

	public MainFrame(String identifier) throws HeadlessException {
		super(identifier);
		this.tl=new ArrayList<>();
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
		// create MenuBa
		this.menuBar = new JMenuBar();

		this.file = new JMenu("File");
		this.newTable = new JMenuItem("New");
		this.open = new JMenuItem("Open");
		this.saveAs = new JMenuItem("Save As...");
		this.save = new JMenuItem("Save");
		this.settings = new JMenuItem("Settings");

		this.edit = new JMenu("Edit");

		this.connection = new JMenu("Connection");
		this.login = new JMenuItem("Login");

		this.window = new JMenu("Window");
		this.preferences = new JMenuItem("Preferences");

		this.help = new JMenu("Help");
		this.github = new JMenuItem("Github");

		// add to ActionListener
		this.newTable.addActionListener(this);
		this.open.addActionListener(this);
		this.saveAs.addActionListener(this);
		this.save.addActionListener(this);
		this.settings.addActionListener(this);
		this.login.addActionListener(this);
		this.preferences.addActionListener(this);
		this.github.addActionListener(this);

		// add to Frame
		this.setJMenuBar(this.menuBar);

		this.menuBar.add(this.file);
		this.file.add(this.newTable);
		this.file.add(this.open);
		this.file.add(new JSeparator());
		this.file.add(this.saveAs);
		this.file.add(this.save);
		this.file.add(new JSeparator());
		this.file.add(this.settings);

		this.menuBar.add(this.edit);

		this.menuBar.add(this.connection);
		this.connection.add(this.login);

		this.menuBar.add(this.window);
		this.window.add(this.preferences);

		this.menuBar.add(this.help);
		this.help.add(this.github);

		// add TaskTable
		this.add(this.taskTable);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(this.newTable)) {
//			this.taskTable = new TaskTable(Toolkit.getDefaultToolkit().getScreenSize(), this);
		} else if (e.getSource().equals(this.open)) {

		} else if (e.getSource().equals(this.saveAs)) {

		} else if (e.getSource().equals(this.save)) {

		} else if (e.getSource().equals(this.settings)) {

		} else if (e.getSource().equals(this.login)) {
			LoginDialog dialog = new LoginDialog(this, "Login", true);
			this.tl=dialog.getTl();
			this.taskTable.insertValuesIntoTable(this.tl);
		} else if (e.getSource().equals(this.preferences)) {

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
		return settings;
	}

	public JMenu getEdit() {
		return edit;
	}

	public JMenu getConnection() {
		return connection;
	}

	public JMenuItem getLogin() {
		return login;
	}

	public JMenu getWindow() {
		return window;
	}

	public JMenuItem getPreferences() {
		return preferences;
	}

	public JMenu getHelp() {
		return help;
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
