package gui;

import java.util.Date;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2412407473703651652L;
	private String[] columnNames = { "DONE", "Category", "Subject", "Description", "From", "Until" };

	private Object[][] data = {{new Boolean(false),"Homework","Math","Page 42",new Date(),new Date()}};

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		return data[row][col];
	}

}
