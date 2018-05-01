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

public class FilterDialog extends JDialog implements ActionListener {

	private JLabel lbCategory = null;
	private JComboBox JCategory = null;
	private JLabel lbSubject = null;
	private JComboBox JSubject = null;
	private JLabel lbDateFrom = null;
	private JFormattedTextField tfFrom = null;
	private JLabel lbto = null;
	private JFormattedTextField tfto = null;
	private JButton btnOk = null;
	private JButton btnCancel = null;
	private Task task = null;
	private JDatePickerImpl datePicker;
	private JDatePickerImpl datePickerTo;
	private UtilDateModel modelFrom;
	private UtilDateModel modelTo;
	private JDatePanelImpl datePanelFrom;
	private JDatePanelImpl datePanelTo;
	private JPanel panel = null;

	public FilterDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		this.initializeControls();
		this.pack();
		this.setVisible(true);
	}

	private void initializeControls() {
		GridLayout grid = new GridLayout(6, 2);
		this.setLayout(grid);
		Categories[] Categoryvalues = Categories.values();
		this.JCategory = new JComboBox(Categoryvalues);
		this.lbCategory = new JLabel("Category:");

		Subjects[] Subjectvalues = Subjects.values();
		this.JSubject = new JComboBox(Subjectvalues);
		this.lbSubject = new JLabel("Subject:");

		this.lbDateFrom = new JLabel("From:");
		SimpleDateFormat time = new SimpleDateFormat("dd.mm.yyyy");
		this.tfFrom = new JFormattedTextField(time);

		this.lbto = new JLabel("To:");
		this.tfto = new JFormattedTextField(time);

		this.btnOk = new JButton("Filter");
		this.btnOk.addActionListener(this);

		this.btnCancel = new JButton("Cancel");
		this.btnCancel.addActionListener(this);

		this.modelFrom = new UtilDateModel();
		this.modelFrom.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanelFrom = new JDatePanelImpl(modelFrom, p);
		datePicker = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter());

		this.modelTo = new UtilDateModel();
		this.modelFrom.setSelected(true);
		datePanelTo = new JDatePanelImpl(modelTo, p);
		datePickerTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());

		this.add(this.lbCategory);
		this.add(this.JCategory);
		this.add(this.lbSubject);
		this.add(this.JSubject);
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
		SimpleDateFormat time = new SimpleDateFormat("dd.MM.yyyy");

		try {
			Date d1 = time.parse(this.datePicker.getJFormattedTextField().getText());
			Date d2 = time.parse(this.datePickerTo.getJFormattedTextField().getText());
			if (!this.datePicker.getJFormattedTextField().getText().trim().isEmpty()
					&& !this.datePickerTo.getJFormattedTextField().getText().trim().isEmpty()
					&& d2.compareTo(d1) >= 0) {

				isValid = true;
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
