package gui;

import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JFrame;

public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8026416994513756565L;
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
