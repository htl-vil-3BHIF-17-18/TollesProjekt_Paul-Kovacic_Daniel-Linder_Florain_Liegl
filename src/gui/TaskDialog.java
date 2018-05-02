package gui;

import bll.Categories;
import bll.DateLabelFormatter;
import bll.Subjects;
import bll.Task;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

class TaskDialog extends JDialog implements ActionListener {

    private JComboBox<Categories> JCategory = null;
    private JComboBox<Subjects> JSubject = null;
    private JTextField tfDescription = null;
    private JButton btnOk = null;
    private JButton btnCancel = null;
    private Task task = null;
    private JDatePickerImpl datePicker;
    private JDatePickerImpl datePickerTo;
    private UtilDateModel modelFrom;
    private UtilDateModel modelTo;

    TaskDialog(Frame owner, String title) {
        super(owner, title, true);
        this.initializeControls();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    TaskDialog(Frame owner, String title, Task task) {
        super(owner, title, true);
        this.task = task;

        this.initializeControls();
        this.fillControls();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initializeControls() {
        GridLayout grid = new GridLayout(6, 2);
        this.setLayout(grid);
        this.setResizable(false);
        
        Categories[] CategoryValues = Categories.values();
        this.JCategory = new JComboBox<>(CategoryValues);
        JLabel lbCategory = new JLabel("Category:");

        Subjects[] Subjectvalues = Subjects.values();
        this.JSubject = new JComboBox<>(Subjectvalues);
        JLabel lbSubject = new JLabel("Subject:");

        JLabel lbDescription = new JLabel("Description:");
        this.tfDescription = new JTextField();

        JLabel lbDateFrom = new JLabel("From:");

        JLabel lbto = new JLabel("To:");

        this.btnOk = new JButton("OK");
        this.btnOk.addActionListener(this);

        this.btnCancel = new JButton("Cancel");
        this.btnCancel.addActionListener(this);

        this.modelFrom = new UtilDateModel();
        this.modelFrom.setValue(java.util.Calendar.getInstance().getTime());
        this.modelFrom.setSelected(true);
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanelFrom = new JDatePanelImpl(modelFrom, p);
        datePicker = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter());

        this.modelTo = new UtilDateModel();
        this.modelTo.setValue(java.util.Calendar.getInstance().getTime());
        this.modelFrom.setSelected(true);
        JDatePanelImpl datePanelTo = new JDatePanelImpl(modelTo, p);
        datePickerTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());

        this.add(lbCategory);
        this.add(this.JCategory);
        this.add(lbSubject);
        this.add(this.JSubject);
        this.add(lbDescription);
        this.add(this.tfDescription);
        this.add(lbDateFrom);
        this.add(this.datePicker);
        this.add(lbto);
        this.add(this.datePickerTo);
        this.add(this.btnOk);
        this.add(this.btnCancel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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

    private void fillControls() {

        this.JCategory.setSelectedIndex(Categories.valueOf(this.task.getCategory().toString()).ordinal());
        this.JSubject.setSelectedIndex(Subjects.valueOf(this.task.getSubject().toString()).ordinal());
        this.tfDescription.setText(task.getDescription());
        this.modelTo.setValue(this.task.getUntil());
        this.modelTo.setSelected(true);
        this.modelFrom.setValue(this.task.getFrom());
        this.modelFrom.setSelected(true);
    }

    private boolean writeValuesToMenu() {
        boolean isValid = false;
        SimpleDateFormat time = new SimpleDateFormat("dd.MM.yyyy");

        try {
            Date d1 = time.parse(this.datePicker.getJFormattedTextField().getText());
            Date d2 = time.parse(this.datePickerTo.getJFormattedTextField().getText());
            if (!this.tfDescription.getText().trim().isEmpty()
                    && !this.datePicker.getJFormattedTextField().getText().trim().isEmpty()
                    && !this.datePickerTo.getJFormattedTextField().getText().trim().isEmpty() && d2.compareTo(d1) >= 0) {

                isValid = true;

                this.task = new Task(false, Categories.values()[this.JCategory.getSelectedIndex()],
                        Subjects.values()[this.JSubject.getSelectedIndex()], this.tfDescription.getText(),
                        time.parse(this.datePicker.getJFormattedTextField().getText()),
                        time.parse(this.datePickerTo.getJFormattedTextField().getText()));
            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        return isValid;

    }

    Task getTask() {
        return task;
    }
}