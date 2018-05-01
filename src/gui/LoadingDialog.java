package gui;

import javax.swing.*;

import dal.DatabaseConnection;

import java.awt.*;

class LoadingDialog extends JDialog {
    
	
	private DatabaseConnection db;
	private boolean con;
	private InBackground ib;
	
    LoadingDialog(DatabaseConnection db,LoginDialog owner, String title,boolean modal) {
        super(owner, title,modal);
        this.db=db;
        this.ib=new InBackground(db,this);
        this.ib.execute();
        
        initializeControls();
    }
    
    LoadingDialog(DatabaseConnection db,MainFrame owner, String title,boolean modal) {
        super(owner, title,modal);
        this.db=db;
        this.ib=new InBackground(db,this);
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

	public boolean isCon() {
		return con;
	}

	public void setCon(boolean con) {
		this.con = con;
	}
}
