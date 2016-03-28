package main.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import main.block.DraggableRect;

public class Run {
	// generates .java file
	public static void genJava(DraggableRect r) {
		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(Save.getPath() + Save.getFile() + ".java"), "utf-8"))) {
			writer.write(genCode(r, 0));
		} catch (IOException ex) {
			// report
		}
	}

	public static String genCode(DraggableRect r, int tabs) {
		String temp = "";
		if (r != null) {
			if (r.hasChildren()) {
				switch (r.getType()) {
				case 0:
					temp += getTabs(tabs);
					temp += genCode(Controller.getRectByID(r.childrenIDs.get(0)), tabs);
					return temp;
				case 1:
					// TODO update assignment rect
					temp += getTabs(tabs);
					temp += r.f1 + " = " + r.f2 + ";\n";
					temp += genCode(Controller.getRectByID(r.childrenIDs.get(0)), tabs);
					return temp;
				case 2:
					temp += r.f1 + " " + r.f3 + " " + r.f2;
					return temp;
				case 3:
					temp += getTabs(tabs);
					temp += "if(" + r.f1 + "){\n";
					temp += genCode(Controller.getRectByID(r.childrenIDs.get(0)), tabs + 1) + "\n";
					temp += getTabs(tabs);
					temp += "} else {\n";
					temp += genCode(Controller.getRectByID(r.childrenIDs.get(1)), tabs + 1) + "\n";
					temp += getTabs(tabs);
					temp += "}";
					return temp;
				case 4:
					temp += getTabs(tabs);
					temp += "while(" + r.f1 + "){\n";
					temp += genCode(Controller.getRectByID(r.childrenIDs.get(0)), tabs + 1) + "\n";
					temp += getTabs(tabs);
					temp += "}\n";
					temp += genCode(Controller.getRectByID(r.childrenIDs.get(1)), tabs);
					return temp;
				case 5:
					temp += "public static void main(String[] args){\n";
					temp += genCode(Controller.getRectByID(r.childrenIDs.get(0)), tabs + 1) + "\n";
					temp += getTabs(tabs);
					temp += "}";
					return temp;
				case 6:
					temp += getTabs(tabs);
					temp += "switch(" + r.f1 + "){\n";
					for (int i = 0; i < r.childrenIDs.size(); i++) {
						if (Controller.getRectByID(r.childrenIDs.get(i)) != null) {
							temp += getTabs(tabs + 1);
							temp += "case " + i + ":\n";
							temp += genCode(Controller.getRectByID(r.childrenIDs.get(i)), tabs + 2) + "\n";
						} else {
							temp += getTabs(tabs);
							return temp + "}";
						}
					}
					break;
				case 7:
					temp += getTabs(tabs);
					temp += r.f1 + "{\n";
					temp += genCode(Controller.getRectByID(r.childrenIDs.get(0)), tabs + 1) + "\n";
					temp += "}";
					return temp;
				case 8:
					temp += getTabs(tabs);
					temp += r.f1 + "\n";
					temp += genCode(Controller.getRectByID(r.childrenIDs.get(0)), tabs);
					return temp;
				default:
					return Integer.toString(r.getType());
				}
			} else {
				switch (r.getType()) {
				case 0:
					temp += getTabs(tabs);
					return temp;
				case 1:
					temp += getTabs(tabs);
					temp += r.f1 + " = " + r.f2 + ";";
					return temp;
				case 2:
					temp += getTabs(tabs);
					temp += r.f1 + " " + r.f3 + " " + r.f2;
					return temp;
				case 3:
					temp += getTabs(tabs);
					temp += "if(" + r.f1 + "){}";
					return temp;
				case 4:
					temp += getTabs(tabs);
					temp += "while(" + r.f1 + "){}";
					return temp;
				case 5:
					temp += getTabs(tabs);
					temp += "public static void main(String[] args){}";
					return temp;
				case 6:
					temp += getTabs(tabs);
					temp += "switch(" + r.f1 + "){}";
					return temp;
				case 7:
					temp += getTabs(tabs);
					temp += r.f1 + "{}";
					return temp;
				case 8:
					temp += getTabs(tabs);
					temp += r.f1;
					return temp;
				default:
					return Integer.toString(r.getType());
				}
			}
		}
		return "";
	}

	private static String getTabs(int x) {
		String tabs = "";
		for (int i = 0; i < x; i++) {
			tabs += ("\t");
		}
		return tabs;
	}
}
