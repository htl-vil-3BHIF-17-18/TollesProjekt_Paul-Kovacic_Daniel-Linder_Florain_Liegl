package gui;

import bll.Categories;
import bll.Subjects;
import bll.Task;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Frame;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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


        this.add(this.lbCategory);
        this.add(this.JCategory);
        this.add(this.lbSubject);
        this.add(this.JSubject);
        this.add(this.lbDescription);
        this.add(this.tfDescription);
        this.add(this.lbDateFrom);

        this.add(this.lbto);

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
            if (!this.tfDescription.getText().trim().isEmpty() && !this.tfFrom.getText().trim().isEmpty()
                    && !this.tfto.getText().trim().isEmpty()) {
               SimpleDateFormat time=new SimpleDateFormat("dd-mm-yyyy");


               isValid = true;

               this.task= new Task(false,Categories.values()[this.JCategory.getSelectedIndex()],Subjects.values()[this.JSubject.getSelectedIndex()],this.tfDescription.getText(), time.parse(this.tfFrom.getText()),time.parse(this.tfFrom.getText()));
               System.out.println(this.task.toString());
            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        return isValid;

    }

}

