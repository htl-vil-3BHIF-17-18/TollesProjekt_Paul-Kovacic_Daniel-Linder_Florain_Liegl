package gui;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import bll.Task;

public class TaskTable extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3013523607123464946L;
	private MainFrame mf;
	private JScrollPane scrollpane = null;
	private int width;
	private int height;
	private JTable jTable;
	private String separator=File.separator;
	private ImageIcon ii =new ImageIcon("images"+separator+"check_24.png");
	
	
	
	public TaskTable(int width, int height, MainFrame mf) {
		super();
		this.width=width;
		this.height=height;
		this.setSize(new Dimension(width, height));
		this.mf = mf;
		this.initializeControls();
	}

	private void initializeControls() {
		// TODO Auto-generated method stub
		this.jTable=new JTable(new TableModel());
		this.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.jTable.getColumnModel().getColumn(0).setWidth(50);
		this.jTable.getTableHeader().setReorderingAllowed(false);
//		this.jTable.getColumnModel().
//		System.out.println(ii);
//		ii.setImageObserver(this.jTable.getTableHeader());

		
		this.scrollpane = new JScrollPane(this.jTable);
		
		
		this.scrollpane.setMinimumSize(new Dimension(this.width, this.height));
		this.scrollpane.setPreferredSize(new Dimension(this.width, this.height));
		
		this.add(this.scrollpane);
		
		this.setVisible(true);
		
	}
	

	
	public void insertValuesIntoTable(ArrayList<Task> tasks) {
		int i=0;
		for(Task t : tasks) {
			this.jTable.setValueAt(t.isDone(), i, 0);
			this.jTable.setValueAt(t.getCategorie(), i, 1);
			this.jTable.setValueAt(t.getSubject(), i, 2);
			this.jTable.setValueAt(t.getDescription(), i, 3);
			this.jTable.setValueAt(t.getFrom(), i, 4);
			this.jTable.setValueAt(t.getUntil(), i, 5);
			i++;
		}
	}

	
}
