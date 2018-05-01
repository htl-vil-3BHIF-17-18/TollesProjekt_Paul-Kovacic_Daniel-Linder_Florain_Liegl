package gui;

import bll.Settings;

import javax.swing.*;
import java.awt.*;

class SettingsDialog extends JDialog {
    private static final long serialVersionUID = 4755471415460680864L;
    private Settings settings;

    private JLabel lblAliasName;
    private JTextField tfAliasName;
    private JCheckBox ckOnlyToDo;

    SettingsDialog(Frame owner, String title, boolean modal, Settings settings) {
        super(owner, title, modal);
        this.settings = settings;
        this.initializeControls();
    }

    private void initializeControls() {
        this.setLayout(new BorderLayout());

        this.lblAliasName = new JLabel("Full Name: ");
        this.tfAliasName = new JTextField(this.settings.getAliasName());
        this.ckOnlyToDo = new JCheckBox("Show only tasks to do");

        this.add(this.lblAliasName);
        this.add(this.tfAliasName);
        this.add(this.ckOnlyToDo);

        this.setMinimumSize(new Dimension(300, 60));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }


}
