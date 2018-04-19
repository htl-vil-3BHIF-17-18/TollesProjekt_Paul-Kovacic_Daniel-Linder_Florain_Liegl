package gui;

import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8026416994513756565L;
	private JMenu file;
	private JMenuItem settings;
	
	private JMenu edit;
	
	private JMenu connection;
	private JMenuItem login;
	private JMenuItem logout;
	
	private JMenu window;
	private JMenuItem preferences;
	
	public MainFrame(String identifier) throws HeadlessException {
		super(identifier);
		this.setMinimumSize(new Dimension(1920, 1080));
		this.setPreferredSize(new Dimension(1920, 1080));
		this.setResizable(true);
		this.setLocationRelativeTo(this);
		this.initializeControls();
	}
	private void initializeControls() {
		// TODO Auto-generated method stub
		
	}

}
