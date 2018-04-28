package gui;

import javax.swing.*;
import java.awt.*;

public class LoadingDialog extends JDialog {
    private JLabel loading = null;
    private final JProgressBar jpb = new JProgressBar();


    public LoadingDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        initializeControls();
    }

    private void initializeControls() {
        this.setLayout(new GridLayout(1, 1));
    }
}
