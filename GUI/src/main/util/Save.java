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

import main.GUI;
import main.block.DraggableRect;

public class Save {

	static String projectPath = "";
	static String fileName = "";

	private static JFileChooser newFileChooser;
	private static JFileChooser loadFileChooser;

	public static void newProject() {

		// sets default attributes for new file chooser
		newFileChooser = new JFileChooser();
		newFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		newFileChooser.setCurrentDirectory(new java.io.File("."));
		newFileChooser.setDialogTitle("New");
		newFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		String inputPath = "";

		int newStatus = newFileChooser.showSaveDialog(null);
		if (newStatus == JFileChooser.APPROVE_OPTION) {
			if (!newFileChooser.getSelectedFile().getName().isEmpty()) {
				inputPath = newFileChooser.getCurrentDirectory()
						.getAbsolutePath()
						+ "\\"
						+ newFileChooser.getSelectedFile().getName();
			} else {
				inputPath = newFileChooser.getCurrentDirectory()
						.getAbsolutePath();
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

	public static void save() {
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
			Controller c = GUI.controller;
			ArrayList<DraggableRect> l = c.getRects();
			save.writeObject(l);
			System.out.println("saved");

			// closes ObjectOutputStream
			save.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public static void load() {
		try {
			// sets default attributes for load file chooser
			loadFileChooser = new JFileChooser();
			loadFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
			loadFileChooser.setCurrentDirectory(new java.io.File("."));
			loadFileChooser.setDialogTitle("Open");
			loadFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			
			String inputPath = "";
			
			int loadStatus = loadFileChooser.showOpenDialog(null);

			if (loadStatus == JFileChooser.APPROVE_OPTION) {
				
				String path = loadFileChooser.getSelectedFile().getAbsolutePath();
				
				// initiates/loads savefile and ObjectInputStream
				FileInputStream saveFile = new FileInputStream(path);
				ObjectInputStream save = new ObjectInputStream(saveFile);
				
				inputPath = loadFileChooser.getCurrentDirectory()
				.getAbsolutePath();
				
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
				
				// initiates temporary controller c
				@SuppressWarnings("unchecked")
				ArrayList<DraggableRect> l = (ArrayList<DraggableRect>) save
						.readObject();
				// loads each rectangle from rects ArrayList
				GUI.controller.clearRects();
				for (DraggableRect r : l) {
					GUI.controller.addRect(r);
				}
				System.out.println("loaded");

				// closes ObjectInputStream
				save.close();
			}
		} catch (StreamCorruptedException | InvalidClassException inv) {
			System.err
					.println("The save file is either corrupted or incompatible with this version");
			inv.printStackTrace();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public static void setPath(String path) {
		projectPath = path;
	}

	public static void setFile(String file) {
		fileName = file;
	}

	public static String getPath() {
		return projectPath;
	}

	public static String getFile() {
		return fileName;
	}
}