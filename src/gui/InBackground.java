package gui;

import dal.DatabaseConnection;

import javax.swing.*;

public class InBackground extends SwingWorker<Void, Void> {


    private DatabaseConnection db;
    private boolean con;
    private LoadingDialog ld;

    InBackground(DatabaseConnection db, LoadingDialog ld) {
        super();
        this.db = db;
        this.ld = ld;
    }

    @Override
    protected Void doInBackground() {
        this.con = db.checkConnection();

        return null;
    }

    @Override
    protected void done() {
        ld.setVisible(false);
        ld.dispose();

        super.done();
    }

    public boolean isCon() {
        return con;
    }


}
