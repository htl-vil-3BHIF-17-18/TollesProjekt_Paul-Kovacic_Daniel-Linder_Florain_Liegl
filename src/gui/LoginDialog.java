package gui;

import dal.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = 6158225161645311129L;
    private JLabel lUsername = null;
    private JTextField tfUsername = null;
    private JLabel lPassword = null;
    private JPasswordField pfPasswordField = null;
    private JButton btnLogin = null;
    private JButton btnCancel = null;
    private boolean loggedIn = false;
    private MainFrame parent;

    public LoginDialog(MainFrame owner, String title, boolean modal) {
        super(owner, title, modal);
        this.parent = owner;
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setMinimumSize(new Dimension(300, 130));
        this.setResizable(false);
        this.initializeControls();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initializeControls() {
        GridLayout grid = new GridLayout(3, 2);
        this.setLayout(grid);

        this.lUsername = new JLabel("Username: ");
        this.tfUsername = new JTextField();

        this.lPassword = new JLabel("Password: ");
        this.pfPasswordField = new JPasswordField();

        this.btnLogin = new JButton("Login");
        this.btnCancel = new JButton("Cancel");

        this.btnLogin.addActionListener(this);
        this.btnCancel.addActionListener(this);

        this.add(this.lUsername);
        this.add(this.tfUsername);
        this.add(this.lPassword);
        this.add(this.pfPasswordField);
        this.add(this.btnLogin);
        this.add(this.btnCancel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.btnLogin)) {
            this.setVisible(false);
            //TODO: implement Progress Bar
            new LoadingDialog(this, "Connecting to database...", true);
            if (this.logIn()) {
                this.loggedIn = true;
                this.dispose();
            } else {
                this.setVisible(true);
            }
        } else if (e.getSource().equals(this.btnCancel)) {
            this.lUsername.setText("");
            this.setVisible(false);
            this.dispose();
        }

    }

    private String getUsername() {
        return tfUsername.getText();
    }

    private String getPassword() {
        return String.valueOf(pfPasswordField.getPassword());
    }

    private boolean logIn() {
        boolean successful;
        if (new DatabaseConnection(this.getUsername(), this.getPassword()).checkConnection()) {
            this.parent.setDbConnection(new DatabaseConnection(this.getUsername(), this.getPassword()));
            this.parent.setLoggedInUser(this.getUsername());
            this.parent.setVisible(true);
            successful = true;
        } else {
            JOptionPane.showMessageDialog(null, "Username or password incorrect!", "Warning", JOptionPane.INFORMATION_MESSAGE);
            successful = false;
        }

        return successful;
    }
}
