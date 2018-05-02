package gui;

import bll.DateLabelFormatter;
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

class FilterDialog extends JDialog implements ActionListener {

    private JButton btnOk = null;
	private JButton btnCancel = null;
	private JDatePickerImpl datePicker;
	private JDatePickerImpl datePickerTo;
    private Date from = null;
	private Date until = null;

	FilterDialog(Frame owner, String title) {
		super(owner, title, true);
		this.initializeControls();
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void initializeControls() {
		GridLayout grid = new GridLayout(3, 2);
		this.setLayout(grid);

        JLabel lbDateFrom = new JLabel("From:");

		JLabel lbto = new JLabel("To:");

		this.btnOk = new JButton("Filter");
		this.btnOk.addActionListener(this);

		this.btnCancel = new JButton("Cancel");
		this.btnCancel.addActionListener(this);

        UtilDateModel modelFrom = new UtilDateModel();
		modelFrom.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
        JDatePanelImpl datePanelFrom = new JDatePanelImpl(modelFrom, p);
		datePicker = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter());

        UtilDateModel modelTo = new UtilDateModel();
		modelFrom.setSelected(true);
        JDatePanelImpl datePanelTo = new JDatePanelImpl(modelTo, p);
		datePickerTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());

		this.add(lbDateFrom);
		this.add(this.datePicker);
		this.add(lbto);
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

				this.from = time.parse(this.datePicker.getJFormattedTextField().getText());
				this.until = time.parse(this.datePickerTo.getJFormattedTextField().getText());
				isValid = true;
			}

		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}

		return isValid;

	}

	Date getFrom() {
		return from;
	}

	Date getUntil() {
		return until;
	}

}
