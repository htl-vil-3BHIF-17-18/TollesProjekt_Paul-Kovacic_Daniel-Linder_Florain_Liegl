package gui;

import bll.Categories;
import bll.Subjects;
import bll.Task;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Frame;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.*;



public class TaskDialog extends JDialog  implements ActionListener{

    private JLabel lbCategory = null;
    private JComboBox JCategory = null;
    private JLabel lbSubject = null;
    private JComboBox JSubject = null;
    private JLabel lbDescription = null;
    private JTextField tfDescription = null;
    private JLabel lbDateFrom = null;
    private JFormattedTextField  tfFrom= null;
    private JLabel lbto = null;
    private JFormattedTextField  tfto= null;
    private JButton btnOk = null;
    private JButton btnCancel = null;
    private Task task= null;
    JDatePickerImpl datePicker;
    JDatePickerImpl datePickerTo;

    JDatePanelImpl datePanelFrom;
    JDatePanelImpl datePanelTo;
    private JPanel panel = null;



    public TaskDialog(Frame owner, String title, boolean modal)
    {
        super(owner, title, modal);
        this.initializeControls();
        this.pack();
        this.setVisible(true);
    }

    private void initializeControls() {
        GridLayout grid = new GridLayout(6, 2);
        this.setLayout(grid);
        Categories[] Categoriesvalues=Categories.values();
        this.JCategory=new JComboBox(Categoriesvalues);
        this.lbCategory=new JLabel("Category:");

        Subjects[] Subjectvalues=Subjects.values();
        this.JSubject=new JComboBox(Subjectvalues);
        this.lbSubject=new JLabel("Subject:");

        this.lbDescription=new JLabel("Description:");
        this.tfDescription=new JTextField();

        this.lbDateFrom=new JLabel("From:");
        SimpleDateFormat time=new SimpleDateFormat("dd.mm.yyyy");
        this.tfFrom=new JFormattedTextField(time);

        this.lbto=new JLabel("To:");
        this.tfto=new JFormattedTextField(time);

        this.btnOk = new JButton("OK");
        this.btnOk.addActionListener(this);

        this.btnCancel = new JButton("Cancel");
        this.btnCancel.addActionListener(this);


        UtilDateModel modelFrom= new UtilDateModel();
        modelFrom.setValue(java.util.Calendar.getInstance().getTime());
        modelFrom.setSelected(true);
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePanelFrom = new JDatePanelImpl(modelFrom,p);
        datePicker = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter());

        UtilDateModel modelTo= new UtilDateModel();
        modelTo.setValue(java.util.Calendar.getInstance().getTime());
        modelFrom.setSelected(true);
        datePanelTo = new JDatePanelImpl(modelTo,p);
        datePickerTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());

        this.add(this.lbCategory);
        this.add(this.JCategory);
        this.add(this.lbSubject);
        this.add(this.JSubject);
        this.add(this.lbDescription);
        this.add(this.tfDescription);
        this.add(this.lbDateFrom);
        this.add(this.datePicker);
        this.add(this.lbto);
        this.add(this.datePickerTo);
        this.add(this.btnOk);
        this.add(this.btnCancel);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource().equals(this.btnOk)) {

            if (this.writeValuesToMenu()) {
                this.setVisible(false);
                this.dispose();
            }

        } else if (e.getSource().equals(this.btnCancel)) {
            this.setVisible(false);
            this.dispose();
        }

    }

    private boolean writeValuesToMenu() {
        boolean isValid = false;

        try {
            if (!this.tfDescription.getText().trim().isEmpty() && !this.datePicker.getJFormattedTextField().getText().trim().isEmpty() && !this.datePickerTo.getJFormattedTextField().getText().trim().isEmpty()) {
               SimpleDateFormat time=new SimpleDateFormat("dd.MM.yyyy");


               isValid = true;

               this.task= new Task(false,Categories.values()[this.JCategory.getSelectedIndex()],Subjects.values()[this.JSubject.getSelectedIndex()],this.tfDescription.getText(), time.parse(this.datePicker.getJFormattedTextField().getText()),time.parse(this.datePickerTo.getJFormattedTextField().getText()));
               System.out.println(this.task.toString());
            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        return isValid;

    }

	public Task getTask() {
		return task;
	}
    
    

}



 class DateLabelFormatter extends JFormattedTextField.AbstractFormatter
{

    private String datePattern = "dd.MM.yyyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException
    {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException
    {
        if (value != null)
        {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

}
