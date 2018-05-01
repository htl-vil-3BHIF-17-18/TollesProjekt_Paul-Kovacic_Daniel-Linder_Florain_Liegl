package gui;

import bll.Task;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskTable extends JPanel implements TableModelListener {

	/**
	 *
	 */
	private static final long serialVersionUID = -3013523607123464946L;
	private static final int BOOLEAN_COLUMN = 0;
	private MainFrame mf;
	private JScrollPane scrollpane = null;
	private int width;
	private int height;
	private JTable jTable;
	private String separator = File.separator;
	private ImageIcon ii = new ImageIcon("images" + separator + "check_24.png");
	private List<Task> taskList;
	private boolean showDone = false;
	private SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

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
		this.setLayout(new GridLayout(0, 1));
		this.taskList = new ArrayList<Task>();
		this.jTable = new JTable(new MyTableModel());
		this.jTable.getSelectionModel().addListSelectionListener(this.mf);
		this.jTable.getModel().addTableModelListener(this);
		this.setHeaderWidth();
		this.jTable.getTableHeader().setReorderingAllowed(false);
		this.jTable.setAutoCreateRowSorter(true);
		this.jTable.setDefaultRenderer(Object.class, new MyTableCellRenderer());

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
		MyTableModel mtm = (MyTableModel) this.jTable.getModel();
		Date d = new Date();
		String date=f.format(d);
		DefaultTableModel model = (DefaultTableModel) this.jTable.getModel();

		model.addRow(new Object[] { t.isDone(), t.getCategory(), t.getSubject(), t.getDescription(), t.getFrom(),
				t.getUntil() });
		if (t.getUntil().toString().equals(date)) {
			mtm.setRowColour(model.getRowCount() - 1, Color.RED);
		} else {
			mtm.setRowColour(model.getRowCount() - 1, javax.swing.UIManager.getColor("Table.dropCellForeground"));
		}
		taskList.add(t);
	}

	public void insertValuesIntoTable(List<Task> l) {
		int i = 0;
		this.taskList = new ArrayList<>();
		Date d = new Date();
		String date=f.format(d);
		DefaultTableModel model = (DefaultTableModel) this.jTable.getModel();
		MyTableModel mtm = (MyTableModel) this.jTable.getModel();
		model.setRowCount(0);
		for (Task t : l) {

			model.addRow(new Object[] { t.isDone(), t.getCategory(), t.getSubject(), t.getDescription(), t.getFrom(),
					t.getUntil() });

			this.taskList.add(t);
			if (t.getUntil().toString().equals(date)) {
				mtm.setRowColour(i, Color.RED);
			} else {
				mtm.setRowColour(i, javax.swing.UIManager.getColor("Table.dropCellForeground"));
			}
			i++;
		}
	}

	public Task getTask() {
		return taskList.get(getSelected());
	}

	public void insertTask(Task t) {
		int i = getSelected();

		this.jTable.setValueAt(t.isDone(), i, 0);
		this.jTable.setValueAt(t.getCategory(), i, 1);
		this.jTable.setValueAt(t.getSubject(), i, 2);
		this.jTable.setValueAt(t.getDescription(), i, 3);
		this.jTable.setValueAt(t.getFrom(), i, 4);
		this.jTable.setValueAt(t.getUntil(), i, 5);
		this.taskList.set(i, t);



	}

	public int getSelected() {
		return this.jTable.getSelectedRow();
	}

	public void deleteTask() {
		if (getSelected() >= 0) {
			taskList.remove(getSelected());
			DefaultTableModel model = (DefaultTableModel) this.jTable.getModel();
			model.removeRow(getSelected());
		}

		// doTo: delete from table
	}

	public void clearTable() {
		DefaultTableModel dtm = (DefaultTableModel) this.jTable.getModel();
		while (dtm.getRowCount() > 0)
			dtm.removeRow(0);
	}

	public boolean isShowDone() {
		return showDone;
	}

	public void setShowDone(boolean showDone) {
		this.showDone = showDone;
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int column = e.getColumn();
		DefaultTableModel dtm = (DefaultTableModel) this.jTable.getModel();
		if (column == BOOLEAN_COLUMN) {
			TableModel model = (TableModel) e.getSource();
			String columnName = model.getColumnName(column);
			Boolean checked = (Boolean) model.getValueAt(row, column);
			if (checked) {
				this.taskList.get(row).setDone(true);
			} else {
				this.taskList.get(row).setDone(false);
			}
		}
	}
}
