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
    private JPasswordField pfPasswordField = null;
    private JButton btnLogin = null;
    private JButton btnCancel = null;
    private MainFrame parent;

    LoginDialog(MainFrame owner, String title, boolean modal) {
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

        JLabel lPassword = new JLabel("Password: ");
        this.pfPasswordField = new JPasswordField();

        this.btnLogin = new JButton("Login");
        this.btnCancel = new JButton("Cancel");

        this.btnLogin.addActionListener(this);
        this.btnCancel.addActionListener(this);

        this.add(this.lUsername);
        this.add(this.tfUsername);
        this.add(lPassword);
        this.add(this.pfPasswordField);
        this.add(this.btnLogin);
        this.add(this.btnCancel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.btnLogin)) {
            this.setVisible(false);

            if (this.logIn()) {
                this.dispose();
            } else {
                this.setVisible(true);
            }

        } else if (e.getSource().equals(this.btnCancel)) {
            this.lUsername.setText("");
            this.setVisible(false);
            this.dispose();
            System.exit(0);
        }

    }

    private boolean logIn() {
        boolean successful = false;
        DatabaseConnection db = new DatabaseConnection(this.getUsername(), this.getPassword());

        LoadingDialog loadingDialog = new LoadingDialog(db, this, "Connecting to database...", true);
        try {
            if (loadingDialog.isCon()) {
                this.parent.setDbConnection(db);
                this.parent.setVisible(true);
                successful = true;
            } else {
                JOptionPane.showMessageDialog(null, "Username or password incorrect!", "Warning", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return successful;
    }

    private String getUsername() {
        return tfUsername.getText();
    }

    private String getPassword() {
        return String.valueOf(pfPasswordField.getPassword());
    }
}
