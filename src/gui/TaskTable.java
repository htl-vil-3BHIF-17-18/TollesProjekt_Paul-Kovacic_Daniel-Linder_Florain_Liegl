package gui;

import bll.Task;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class TaskTable extends JPanel implements TableModelListener, RowSorterListener {
	private static final long serialVersionUID = -3013523607123464946L;
	private static final int BOOLEAN_COLUMN = 0;
	private final MainFrame mf;
    private final int width;
	private final int height;
	private JTable jTable;
	private final String separator = File.separator;
	private final ImageIcon ii = new ImageIcon("images" + separator + "check_24.png");
	private List<Task> taskList;
	private List<Task> removedList;
	private List<Task> goodList;

    TaskTable(Dimension d, MainFrame mf) {
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
		this.taskList = new ArrayList<>();
		this.removedList = new ArrayList<>();
		this.goodList = new ArrayList<>();
		this.jTable = new JTable(new MyTableModel());
		this.jTable.getSelectionModel().addListSelectionListener(this.mf);
		this.jTable.getModel().addTableModelListener(this);
		this.setHeaderWidth();
		this.jTable.getTableHeader().setReorderingAllowed(false);
		this.jTable.setAutoCreateRowSorter(true);
		this.jTable.setDefaultRenderer(Object.class, new MyTableCellRenderer());
		this.jTable.getRowSorter().addRowSorterListener(this);

		// image in table header(s)
		Border headerBorder = UIManager.getBorder("TableHeader.cellBorder");
		JLabel blueLabel = new JLabel("", ii, JLabel.CENTER);
		blueLabel.setBorder(headerBorder);
		TableCellRenderer renderer = new JComponentTableCellRenderer();
		TableColumnModel columnModel = this.jTable.getColumnModel();
		TableColumn column0 = columnModel.getColumn(0);
		column0.setHeaderRenderer(renderer);
		column0.setHeaderValue(blueLabel);

        JScrollPane scrollpane = new JScrollPane(this.jTable);

		scrollpane.setMinimumSize(new Dimension(this.width, this.height));
		scrollpane.setPreferredSize(new Dimension(this.width, this.height));

		this.add(scrollpane);
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

	void insertValueIntoTable(Task t) {
		DefaultTableModel model = (DefaultTableModel) this.jTable.getModel();
		this.jTable.getRowSorter().removeRowSorterListener(this);

        model.addRow(new Object[]{t.isDone(), t.getCategory(), t.getSubject(), t.getDescription(), t.getFrom(),
                t.getUntil()});
        this.updateColor(model.getRowCount() - 1, t);
        taskList.add(t);

		this.updateAllColors();
		this.jTable.getRowSorter().addRowSorterListener(this);
	}

	void insertValuesIntoTable(List<Task> l) {
        this.jTable.getRowSorter().removeRowSorterListener(this);
		this.taskList = new ArrayList<>();
		DefaultTableModel model = (DefaultTableModel) this.jTable.getModel();
		model.setRowCount(0);

		for (Task t : l) {
            model.addRow(new Object[] { t.isDone(), t.getCategory(), t.getSubject(), t.getDescription(), t.getFrom(),
                    t.getUntil() });
            this.taskList.add(t);
		}
		this.updateAllColors();
		this.jTable.getRowSorter().addRowSorterListener(this);
	}

	Task getTask() {
		return taskList.get(getSelected());
	}

	void insertTask(Task t) {

		int i = this.jTable.getSelectedRow();
		this.jTable.setValueAt(t.isDone(), i, 0);
		this.jTable.setValueAt(t.getCategory(), i, 1);
		this.jTable.setValueAt(t.getSubject(), i, 2);
		this.jTable.setValueAt(t.getDescription(), i, 3);
		this.jTable.setValueAt(t.getFrom(), i, 4);
		this.jTable.setValueAt(t.getUntil(), i, 5);
		this.taskList.set(this.getSelected(), t);

		this.updateAllColors();

	}

	void filter(Date from, Date until) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if (this.removedList.size() != 0) {
			// this.taskList = new ArrayList<Task>(this.goodList);
			this.taskList.removeAll(this.removedList);
			this.taskList.addAll(this.removedList);
			this.removedList = new ArrayList<>();
			this.goodList = new ArrayList<>();
			// Collections.sort(this.taskList);
		}
		for (Task t : this.taskList) {
			int daysFrom = Integer.parseInt(sdf.format(t.getUntil())) - Integer.parseInt(sdf.format(from));
			int daysUntil = Integer.parseInt(sdf.format(t.getUntil())) - Integer.parseInt(sdf.format(until));
			if (daysFrom < 0 || daysUntil > 0) {
				this.removedList.add(t);
			} else {
				this.goodList.add(t);
			}
		}
		this.taskList = new ArrayList<>(this.goodList);
		if (this.removedList.size() == 0) {
			this.goodList = new ArrayList<>();
		}
		this.insertValuesIntoTable(this.taskList);
	}
	
	void resetFilter() {
		this.taskList.removeAll(this.removedList);
		this.taskList.addAll(this.removedList);
		this.removedList = new ArrayList<>();
		this.goodList = new ArrayList<>();
		this.insertValuesIntoTable(this.taskList);
	}

	private void updateColor(int i, Task t) {

		MyTableModel mtm = (MyTableModel) this.jTable.getModel();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int days = Integer.parseInt(sdf.format(t.getUntil())) - Integer.parseInt(sdf.format(d));
		if (sdf.format(d).equals(sdf.format(t.getUntil()))) {
			mtm.setRowColour(i, Color.ORANGE);
		} else if (days <= 2 && days > 0) {
			mtm.setRowColour(i, Color.YELLOW);
		} else if (days < 0) {
			mtm.setRowColour(i, Color.RED);
		} else {
			mtm.setRowColour(i, Color.GREEN);
		}
	}

	private void updateAllColors() {
		int i;
		for (int j = 0; j < this.taskList.size(); j++) {
			i = this.jTable.getRowSorter().convertRowIndexToModel(j);
			// System.out.println(this.jTable.getRowCount());
			this.updateColor(j, this.taskList.get(i));
		}
	}

	int getSelected() {
		if (this.jTable.getSelectedRow() >= 0)
			return this.jTable.getRowSorter().convertRowIndexToModel(this.jTable.getSelectedRow());
		else
			return 0;
	}

	void deleteTask() {
		if (getSelected() >= 0) {
			taskList.remove(getSelected());
			DefaultTableModel model = (DefaultTableModel) this.jTable.getModel();
			model.removeRow(getSelected());
			updateAllColors();
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int column = e.getColumn();

		if (column == BOOLEAN_COLUMN) {
            TableModel model = (TableModel) e.getSource();
            Boolean checked = (Boolean) model.getValueAt(row, column);
			Task old = this.taskList.get(row);
			if (checked) {
				this.taskList.get(row).setDone(true);
				mf.updateCheck(old, this.taskList.get(row));
				if (mf.getUserSettings().isOnlyTodo()) {
					this.taskList.remove(row);
				}
				this.insertValuesIntoTable(this.taskList);

			} else {
				this.taskList.get(row).setDone(false);
				mf.updateCheck(old, this.taskList.get(row));
			}
		}
	}

	@Override
	public void sorterChanged(RowSorterEvent e) {
		if (this.jTable.getRowCount() > 0)
			this.updateAllColors();
	}
}
