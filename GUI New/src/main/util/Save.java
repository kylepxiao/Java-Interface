package main.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import main.GUI_Instance;
import main.block.DraggableRect;

public class Save {
	
	public void save(GUI_Instance f, String path, String name){
		try{
			// initiates savefile and ObjectOutputStream
			FileOutputStream saveFile;
			if(!name.isEmpty()){
				if(name.endsWith(".sav")){
					saveFile = new FileOutputStream(path + "\\" + name);
				}else{
					saveFile = new FileOutputStream(path + "\\" + name + ".sav");
				}
			}else{
				saveFile = new FileOutputStream(path + "\\save.sav");
			}
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
	
	public void load(GUI_Instance f, String path){
		try{
			// initiates/loads savefile and ObjectInputStream
			FileInputStream saveFile = new FileInputStream(path);
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
		}catch(StreamCorruptedException | InvalidClassException inv){
			System.err.println("The save file is either corrupted or incompatible with this version");
			inv.printStackTrace();
		}catch(Exception exc){
			exc.printStackTrace();
		}
	}
}
