package gui;

import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTableModel extends DefaultTableModel {
    private static final long serialVersionUID = -2412407473703651652L;
    private String[] columnNames = {"DONE", "Category", "Subject", "Description", "From", "Until"};
    private SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
    private Date d = new Date();

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
    public void setValueAt(Object aValue, int row, int column) {
        // TODO Auto-generated method stub
        if (aValue instanceof Date) {
            aValue = f.format(aValue);
        }
        super.setValueAt(aValue, row, column);
    }

    @Override
    public void addRow(Object[] rowData) {
        // TODO Auto-generated method stub
        rowData[4] = f.format(rowData[4]);
        rowData[5] = f.format(rowData[5]);
        super.addRow(rowData);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return (col == 0);
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }
}
