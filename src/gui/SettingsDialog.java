package gui;

import bll.Settings;

import javax.swing.*;
import java.awt.*;

public class SettingsDialog extends JDialog {
    private static final long serialVersionUID = 4755471415460680864L;
    private Settings settings;

    public SettingsDialog(Frame owner, String title, boolean modal, Settings settings) {
        super(owner, title, modal);
        this.settings = settings;
    }
}
