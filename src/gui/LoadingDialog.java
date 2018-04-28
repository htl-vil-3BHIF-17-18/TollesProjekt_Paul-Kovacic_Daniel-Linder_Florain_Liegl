package gui;

import javax.swing.*;
import java.awt.*;

class LoadingDialog extends JDialog {


    LoadingDialog(Dialog owner, String title, boolean modal) {
        super(owner, title, modal);
        initializeControls();
    }

    private void initializeControls() {
        this.setLayout(new GridLayout(1, 1));
        JProgressBar jpb = new JProgressBar();
        this.add(jpb);
        jpb.setIndeterminate(true);
        jpb.setSize(new Dimension(250, 20));
        this.setMinimumSize(new Dimension(300, 60));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
