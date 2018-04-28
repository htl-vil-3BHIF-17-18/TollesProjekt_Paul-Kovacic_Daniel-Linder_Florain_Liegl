package gui;

import javax.swing.*;
import java.awt.*;

public class LoadingDialog extends JDialog {
    private JLabel loading = null;
    private JProgressBar jpb = null;


    public LoadingDialog(Dialog owner, String title, boolean modal) {
        super(owner, title, modal);
        initializeControls();
    }

    private void initializeControls() {
        this.setLayout(new GridLayout(1, 1));
        this.jpb = new JProgressBar();
        this.add(jpb);
        this.jpb.setIndeterminate(true);
        this.jpb.setSize(new Dimension(250, 20));
        this.setMinimumSize(new Dimension(300, 60));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
