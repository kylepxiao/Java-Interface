package main.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import main.GUI_Instance;
import main.block.DraggableRect;

public class Save {

	String projectPath = "";
	String fileName = "";

	private JFileChooser newFileChooser;

	public Save() {
		newFileChooser = new JFileChooser();
		newFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		newFileChooser.setCurrentDirectory(new java.io.File("."));
		newFileChooser.setDialogTitle("New");
		newFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	}

	public void newProject(GUI_Instance f) {
		String inputPath = "";

		int newStatus = newFileChooser.showSaveDialog(null);
		if (newStatus == JFileChooser.APPROVE_OPTION) {
			if(!newFileChooser.getSelectedFile().getName().isEmpty()){
				inputPath = newFileChooser.getCurrentDirectory().getAbsolutePath()
					+ "\\" + newFileChooser.getSelectedFile().getName();
			}
			else{
				inputPath = newFileChooser.getCurrentDirectory().getAbsolutePath();
			}
		}

		new File(inputPath).mkdirs();

		projectPath = inputPath;
		System.out.println(projectPath);

		String tempPath = new StringBuffer(inputPath).reverse().toString();
		String tempNewFile = "";
		for (Character c : tempPath.toCharArray()) {
			tempNewFile += c;
			if (c == '\\') {
				fileName = new StringBuffer(tempNewFile).reverse().toString();
				break;
			}
		}
	}

	public void save(GUI_Instance f) {
		try {
			// initiates savefile and ObjectOutputStream
			FileOutputStream saveFile;
			if (!fileName.isEmpty()) {
				if (fileName.endsWith(".sav")) {
					saveFile = new FileOutputStream(projectPath + "\\"
							+ fileName);
				} else {
					saveFile = new FileOutputStream(projectPath + "\\"
							+ fileName + ".sav");
				}
			} else {
				saveFile = new FileOutputStream(projectPath + "\\save.sav");
			}
			ObjectOutputStream save = new ObjectOutputStream(saveFile);

			// saves rects ArrayList in controller object
			Controller c = f.controller;
			ArrayList<DraggableRect> l = c.getRects();
			save.writeObject(l);
			System.out.println("saved");

			// closes ObjectOutputStream
			save.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public void load(GUI_Instance f, String path) {
		try {
			// initiates/loads savefile and ObjectInputStream
			FileInputStream saveFile = new FileInputStream(path);
			ObjectInputStream save = new ObjectInputStream(saveFile);

			// initiates temporary controller c
			@SuppressWarnings("unchecked")
			ArrayList<DraggableRect> l = (ArrayList<DraggableRect>) save
					.readObject();
			// loads each rectangle from rects ArrayList
			f.controller.clearRects();
			for (DraggableRect r : l) {
				f.controller.addRect(r);
			}
			System.out.println("loaded");

			// closes ObjectInputStream
			save.close();
		} catch (StreamCorruptedException | InvalidClassException inv) {
			System.err
					.println("The save file is either corrupted or incompatible with this version");
			inv.printStackTrace();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public void setPath(String path) {
		projectPath = path;
	}

	public void setFile(String file) {
		fileName = file;
	}
}
