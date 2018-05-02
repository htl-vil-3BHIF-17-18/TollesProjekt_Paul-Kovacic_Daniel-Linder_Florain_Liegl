package gui;

import dal.DatabaseConnection;

import javax.swing.*;

class InBackground extends SwingWorker<Void, Void> {


    private final DatabaseConnection db;
    private boolean con;
    private final LoadingDialog ld;

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

    boolean isCon() {
        return con;
    }
}
