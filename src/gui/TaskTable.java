package gui;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

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
//		this.setMinimumSize(minimumSize);
		this.mf = mf;
		this.initializeControls();
	}

	private void initializeControls() {
		// TODO Auto-generated method stub
		this.jTable=new JTable(new TableModel());
		this.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.setHeaderWidth();
		this.jTable.getTableHeader().setReorderingAllowed(false);

		//image in table header(s)
	    Border headerBorder = UIManager.getBorder("TableHeader.cellBorder");
	    JLabel blueLabel = new JLabel("", ii, JLabel.CENTER);
	    blueLabel.setBorder(headerBorder);
	    TableCellRenderer renderer = new JComponentTableCellRenderer();
	    TableColumnModel columnModel = this.jTable.getColumnModel();
	    TableColumn column0 = columnModel.getColumn(0);
	    column0.setHeaderRenderer(renderer);
	    column0.setHeaderValue(blueLabel);
	    

		
		this.scrollpane = new JScrollPane(this.jTable);
		
		
		this.scrollpane.setMinimumSize(new Dimension(this.width, this.height));
		this.scrollpane.setPreferredSize(new Dimension(this.width, this.height));
		
		this.add(this.scrollpane);
		
		this.setVisible(true);
		
	}
	
	private void setHeaderWidth() {
		this.jTable.getColumnModel().getColumn(0).setMinWidth(30);
		this.jTable.getColumnModel().getColumn(0).setMaxWidth(30);
		this.jTable.getColumnModel().getColumn(1).setMinWidth(75);;
		this.jTable.getColumnModel().getColumn(1).setMaxWidth(30);
		this.jTable.getColumnModel().getColumn(2).setMinWidth(30);
		this.jTable.getColumnModel().getColumn(2).setMaxWidth(30);
		this.jTable.getColumnModel().getColumn(3).setMinWidth(30);
		this.jTable.getColumnModel().getColumn(3).setMaxWidth(30);
		this.jTable.getColumnModel().getColumn(4).setMinWidth(30);
		this.jTable.getColumnModel().getColumn(4).setMaxWidth(30);
		this.jTable.getColumnModel().getColumn(5).setMinWidth(30);
		this.jTable.getColumnModel().getColumn(5).setMaxWidth(30);
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
