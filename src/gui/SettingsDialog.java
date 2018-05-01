package gui;

import bll.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SettingsDialog extends JDialog implements ActionListener {
    //TODO: Pauli fertig machen lassen
    private static final long serialVersionUID = 4755471415460680864L;
    private Settings settings;

    private JLabel lblAliasName;
    private JTextField tfAliasName;
    private JCheckBox ckOnlyToDo;
    private JButton btnOk;


    SettingsDialog(Frame owner, String title, boolean modal, Settings settings) {
        super(owner, title, modal);
        this.settings = settings;
        this.initializeControls();
    }

    private void initializeControls() {
        this.setLayout(new BorderLayout(10, 10));

        this.lblAliasName = new JLabel("Full Name: ");
        this.lblAliasName.setSize(new Dimension(150, 30));
        this.tfAliasName = new JTextField(this.settings.getAliasName());
        this.tfAliasName.setSize(new Dimension(150, 30));
        this.ckOnlyToDo = new JCheckBox("Show only tasks to do");
        this.ckOnlyToDo.setSize(new Dimension(300, 30));
        this.btnOk = new JButton("Apply");
        this.btnOk.setSize(new Dimension(100, 40));

        this.add(this.lblAliasName, BorderLayout.LINE_START);
        this.add(this.tfAliasName, BorderLayout.LINE_END);
        //this.add(this.ckOnlyToDo);
        this.add(this.btnOk, BorderLayout.PAGE_END);

        this.setMinimumSize(new Dimension(300, 100));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(this.btnOk)) {
            this.settings.setAliasName(this.tfAliasName.getText());
            this.settings.setOnlyTodo(this.ckOnlyToDo.isSelected());
            System.out.println(this.ckOnlyToDo.isEnabled());
            System.out.println(this.ckOnlyToDo.isSelected());
        }
    }
}
