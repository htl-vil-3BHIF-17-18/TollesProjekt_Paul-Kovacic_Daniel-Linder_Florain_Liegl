package gui;

import java.util.Date;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2412407473703651652L;
	private String[] columnNames = { "DONE", "Category", "Subject", "Description", "From", "Until" };

//	private Object[][] data= {{null,null,null,null,null,null}};
	
	

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
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


	
	

}
