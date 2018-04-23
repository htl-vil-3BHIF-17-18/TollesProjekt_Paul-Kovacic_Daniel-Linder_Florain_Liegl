package app;

import java.util.ArrayList;
import java.util.Date;

import bll.Categories;
import bll.Subjects;
import bll.Task;
import gui.MainFrame;

public class Start {

	public static void main(String[] args) {
		MainFrame mf=new MainFrame("Taskplaner");
		
		//testcode(kot)
//		Task t=new Task(false,Categories.Homework,Subjects.Chemistry,"lernen",new Date(),new Date());
//		ArrayList<Task> tl=new ArrayList<>();
//		tl.add(t);
//		mf.getTaskTable().insertValuesIntoTable(tl);
	}

}
