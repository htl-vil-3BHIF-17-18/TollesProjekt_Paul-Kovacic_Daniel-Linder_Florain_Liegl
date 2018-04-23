package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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
//		System.out.println("test");
		
		
		this.scrollpane = new JScrollPane(this.jTable);
		
		
		this.scrollpane.setMinimumSize(new Dimension(this.width, this.height));
		this.scrollpane.setPreferredSize(new Dimension(this.width, this.height));
		
		this.add(this.scrollpane);
		
		this.setVisible(true);
		
	}

	
}
