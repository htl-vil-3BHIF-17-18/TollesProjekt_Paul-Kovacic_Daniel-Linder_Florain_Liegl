package gui;

import dal.DatabaseConnection;

import javax.swing.*;
import java.awt.*;

class LoadingDialog extends JDialog {
    private boolean con;
    private final InBackground ib;

    LoadingDialog(DatabaseConnection db, LoginDialog owner) {
        super(owner, "Connecting to database...", true);
        this.ib = new InBackground(db, this);
        this.ib.execute();

        initializeControls();
    }

    LoadingDialog(DatabaseConnection db, MainFrame owner) {
        super(owner, "Connecting to database...", true);
        this.ib = new InBackground(db, this);
        this.ib.execute();

        initializeControls();
    }

    private void initializeControls() {
        this.setLayout(new GridLayout(1, 1));
        final JProgressBar jpb = new JProgressBar();
        this.add(jpb);
        jpb.setIndeterminate(true);
        this.setMinimumSize(new Dimension(300, 60));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
        this.setCon(this.ib.isCon());
    }

    boolean isCon() {
        return con;
    }

    private void setCon(boolean con) {
        this.con = con;
    }
}
