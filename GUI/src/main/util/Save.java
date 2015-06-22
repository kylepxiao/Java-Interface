package main.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import main.GUI_Instance;
import main.block.DraggableRect;

public class Save {
	
	public void save(GUI_Instance f){
		try{
			// initiates savefile and ObjectOutputStream
			FileOutputStream saveFile = new FileOutputStream("save.sav");
			ObjectOutputStream save = new ObjectOutputStream(saveFile);
			
			// saves rects ArrayList in controller object
			Controller c = f.controller;
			ArrayList<DraggableRect> l = c.getRects();
			save.writeObject(l);
			System.out.println("saved");
			
			// closes ObjectOutputStream
			save.close();
		}
		catch(Exception exc){
			exc.printStackTrace();
		}
	}
	
	public void load(GUI_Instance f){
		try{
			// initiates/loads savefile and ObjectInputStream
			FileInputStream saveFile = new FileInputStream("save.sav");
			ObjectInputStream save = new ObjectInputStream(saveFile);
			
			// initiates temporary controller c
			@SuppressWarnings("unchecked")
			ArrayList<DraggableRect> l = (ArrayList<DraggableRect>) save.readObject();
			
			// loads each rectangle from rects ArrayList
			f.controller.clearRects();
			for(DraggableRect r : l){
				f.controller.addRect(r);
			}
			System.out.println("loaded");
			
			// closes ObjectInputStream
			save.close();
		}
		catch(Exception exc){
			exc.printStackTrace();
		}
	}
}
