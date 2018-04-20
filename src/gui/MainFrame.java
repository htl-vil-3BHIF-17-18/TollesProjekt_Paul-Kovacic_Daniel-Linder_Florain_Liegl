package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8026416994513756565L;
	private TaskTable taskTable;
	private JMenuBar menuBar;
	private JMenu file;
	private JMenuItem settings;

	private JMenu edit;

	private JMenu connection;
	private JMenuItem login;
	private JMenuItem logout;

	private JMenu window;
	private JMenuItem preferences;

	private JMenu help;

	public MainFrame(String identifier) throws HeadlessException {
		super(identifier);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(600, 400));
		this.setPreferredSize(new Dimension(1080, 720));
		this.setResizable(true);
		this.setLocationRelativeTo(this);
		this.taskTable=new TaskTable(1800,1000,this);
		this.initializeControls();
	}

	private void initializeControls() {
		// TODO Auto-generated method stub
		GridLayout gridLayout = new GridLayout(1,1);
		this.setLayout(gridLayout);
		//create MenuBa
		this.menuBar = new JMenuBar();

		this.file = new JMenu("File");
		this.settings = new JMenuItem("Settings");

		this.edit = new JMenu("Edit");

		this.connection = new JMenu("Connection");
		this.login = new JMenuItem("Login");
		this.logout = new JMenuItem("Logout");

		this.window = new JMenu("Window");
		this.preferences = new JMenuItem("Preferences");

		this.help = new JMenu("Help");

		//add to ActionListener
		this.settings.addActionListener(this);
		this.login.addActionListener(this);
		this.logout.addActionListener(this);
		this.preferences.addActionListener(this);

		//add to Frame
		this.setJMenuBar(this.menuBar);

		this.menuBar.add(this.file);
		this.file.add(this.settings);

		this.menuBar.add(this.edit);

		this.menuBar.add(this.connection);
		this.connection.add(this.login);
		this.connection.add(this.logout);

		this.menuBar.add(this.window);
		this.window.add(this.preferences);
		this.menuBar.add(this.help);

		//add TaskTable
		this.add(this.taskTable);
		//set visible
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
