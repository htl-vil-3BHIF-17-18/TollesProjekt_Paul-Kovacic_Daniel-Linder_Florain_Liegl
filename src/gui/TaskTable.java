package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import bll.Categories;
import bll.Task;

public class TaskTable extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3013523607123464946L;
	private MainFrame mf;
	private JScrollPane scrollpane = null;
	private int width;
	private int height;
	private JTable jTable;
	private String separator = File.separator;
	private ImageIcon ii = new ImageIcon("images" + separator + "check_24.png");
	private List<Task> tl;

	public TaskTable(Dimension d, MainFrame mf) {
		super();
		this.mf = mf;
		this.width = d.width;
		this.height = d.height;
		this.setSize(new Dimension(width, height));
		this.setMinimumSize(this.mf.getMinimumSize());
		this.initializeControls();
	}

	private void initializeControls() {
		// TODO Auto-generated method stub
		this.setLayout(new GridLayout(0, 1));
		this.tl=new ArrayList<Task>();
		this.jTable = new JTable(new MyTableModel());
		this.jTable.getSelectionModel().addListSelectionListener(this.mf);
		this.setHeaderWidth();
		this.jTable.getTableHeader().setReorderingAllowed(false);

		// image in table header(s)
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
		// set visible
		this.setVisible(true);

	}

	private void setHeaderWidth() {
		this.jTable.getColumnModel().getColumn(0).setMinWidth(30);
		this.jTable.getColumnModel().getColumn(0).setMaxWidth(30);
		this.jTable.getColumnModel().getColumn(1).setMinWidth(100);
		this.jTable.getColumnModel().getColumn(2).setMinWidth(100);
		this.jTable.getColumnModel().getColumn(3).setMinWidth(100);
		this.jTable.getColumnModel().getColumn(4).setMinWidth(100);
		this.jTable.getColumnModel().getColumn(5).setMinWidth(100);
	}

	public void insertValueIntoTable(Task t) {
		DefaultTableModel model = (DefaultTableModel) this.jTable.getModel();
		model.addRow(new Object[] { t.isDone(), t.getCategorie(), t.getSubject(), t.getDescription(), t.getFrom(),
				t.getUntil() });
		tl.add(t);
	}

	public void insertValuesIntoTable(List<Task> tl) {
		int i = 0;
		this.tl=new ArrayList<>();
		DefaultTableModel model = (DefaultTableModel) this.jTable.getModel();
		for (Task t : tl) {
			model.addRow(new Object[] { t.isDone(), t.getCategorie(), t.getSubject(), t.getDescription(), t.getFrom(),
					t.getUntil() });
			i++;
			tl.add(t);
		}
	}

	 public List<Task> getAllTasks(){
		 return tl;
	 }

	public Task getTask() {
		return tl.get(getSelected());
	}
	
	public void insertTask(Task t) {
		int i=getSelected();
		this.jTable.setValueAt(t.isDone(), i, 0);
		this.jTable.setValueAt(t.getCategorie(), i, 1);
		this.jTable.setValueAt(t.getSubject(), i, 2);
		this.jTable.setValueAt(t.getDescription(), i, 3);
		this.jTable.setValueAt(t.getFrom(), i, 4);
		this.jTable.setValueAt(t.getUntil(), i, 5);
		tl.set(i, t);
	}
	
	public int getSelected() {
		return this.jTable.getSelectedRow();
	}
	
	
	public void deleteTask(int row) {
		tl.remove(row);
		//doTo: delete from table
	}

}
