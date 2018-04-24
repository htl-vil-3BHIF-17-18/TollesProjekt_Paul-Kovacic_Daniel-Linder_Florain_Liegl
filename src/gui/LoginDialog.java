package gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import bll.Task;
import dal.DatabaseConnection;

public class LoginDialog extends JDialog implements ActionListener{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6158225161645311129L;
	private JLabel lUsername = null;
	private JTextField tfUsername = null;
	private JLabel lPassword = null;
	private JPasswordField pfPasswordField = null;
	private JButton btnLogin = null;
	private JButton btnCancel = null;
	private List<Task> tl=null;

	public LoginDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		this.tl=new ArrayList<Task>();
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(300,130));
		this.setResizable(false);
		this.initializeControls();
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void initializeControls() {
		// TODO Auto-generated method stub
		GridLayout grid = new GridLayout(3,2);
		this.setLayout(grid);
		
		this.lUsername=new JLabel("Username: ");
		this.tfUsername=new JTextField();
		
		this.lPassword=new JLabel("Password: ");
		this.pfPasswordField=new JPasswordField();
		
		this.btnLogin=new JButton("Login");
		this.btnCancel=new JButton("Cancel");
		
		this.btnLogin.addActionListener(this);
		this.btnCancel.addActionListener(this);
		
		this.add(this.lUsername);
		this.add(this.tfUsername);
		this.add(this.lPassword);
		this.add(this.pfPasswordField);
		this.add(this.btnLogin);
		this.add(this.btnCancel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(this.btnLogin))
		{
			
//			DatabaseConnection db =new DatabaseConnection(this.tfUsername.getText(),this.pfPasswordField.getPassword().toString());
				this.setVisible(false);
				this.dispose();		
		}
		else if(e.getSource().equals(this.btnCancel)){
			this.setVisible(false);
			this.dispose();
		}
		
	}

}
