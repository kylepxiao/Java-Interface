package main;

import javax.swing.JFrame;

import main.util.Controller;

public abstract class GUI_Instance extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Controller controller = new Controller();
}
