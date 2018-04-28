package gui;

import javax.swing.SwingWorker;

import dal.DatabaseConnection;

public class InBackground extends SwingWorker<Void, Void>{

	
	private DatabaseConnection db;
	private boolean con;
	private LoadingDialog ld;
	
	public InBackground(DatabaseConnection db,LoadingDialog ld) {
		super();
		this.db=db;
		this.ld=ld;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
			this.con=db.checkConnection();
	
		return null;
	}

	@Override
	protected void done() {
		// TODO Auto-generated method stub
		ld.setVisible(false);
		ld.dispose();
		
		super.done();
	}

	public boolean isCon() {
		return con;
	}

	public void setCon(boolean con) {
		this.con = con;
	}
	
	

}
