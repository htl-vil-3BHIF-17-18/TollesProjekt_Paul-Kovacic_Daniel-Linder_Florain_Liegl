package gui;

import java.util.Date;
import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {

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
	
	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value;
        fireTableCellUpdated(row, col);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
	    if (columnIndex == 0)
	        return Boolean.class;
	    return super.getColumnClass(columnIndex);
	}
	
	
	
	@Override
	public boolean isCellEditable(int row, int col) {
	    return (col == 0); 
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
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
