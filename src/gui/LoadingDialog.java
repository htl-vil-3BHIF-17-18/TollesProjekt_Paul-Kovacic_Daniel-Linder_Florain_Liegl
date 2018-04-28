package gui;

import javax.swing.*;
import java.awt.*;

class LoadingDialog extends JDialog {

    LoadingDialog(Dialog owner, String title) {
        super(owner, title);
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
    }
}
