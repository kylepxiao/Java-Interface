package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ToolTipManager;

import main.block.Condition;
import main.block.Conditional;
import main.block.DraggableRect;
import main.block.Loop;
import main.block.Start;
import main.util.Controller;
import main.util.Save;

public class GUI extends GUI_Instance implements ActionListener{

	private static final long serialVersionUID = 1L;

	/**
	 *Java Interface Prototype
	 *Created 5/15/15 by John Lu, Kyle Xiao, and Yangqi Zheng
	 *Siemens Competition 2015-16
	 */
	
	// contains DraggableRect objects and handles mouse input
	public Controller controller;
	
	// sets up private save handler
	private Save save;
	boolean saved = false;
	
	// sets up buffer strategy for graphics
	public BufferStrategy s;
	
	// sets up menubar and associated variables
	private JMenuBar menuBar = new JMenuBar(); // Window menu bar
	
	//sets up destop area to drag internal frame
	private JDesktopPane desktop;
	
	// sets up content JPanels
	private JPanel bufferPanel = new JPanel();
	private JPanel p_main = new JPanel();
	private JPanel p_palette = new JPanel();
	private JToolBar toolBar = new JToolBar();
	private JPanel p_workspace = new JPanel();
	private JPanel p_browser = new JPanel();
	private JPanel p_output = new JPanel();
	private JPanel p_console = new JPanel();
	private JPanel p_information = new JPanel();
	
	//sets up content JInternalPanes
	private JInternalFrame i_console = new JInternalFrame();
	
	//sets up booleans to track if the JInternalPanels are Docked
	private boolean i_console_docked = false;
	
	//JComponent that holds the string from the java document
	private JTextPane codeLabel = new JTextPane();
	
	//sets up filechoosers needed for saving and loading file
	private JFileChooser saveFileChooser;
	private JFileChooser loadFileChooser;
	
	// declares items in menu
	private JMenuItem mItemSave, mItemLoad, mItemRun, mItemGenCode, mItemGenFrame, mItemDockFrame;
	private JRadioButtonMenuItem rItem, rItem2, rItem3;
	@SuppressWarnings("unused")
	private JCheckBoxMenuItem cItem, cItem2;
	
	//sets default directory for java JDK and is subject to change by user
	private static String path = "C:\\Program Files\\Java\\jdk1.8.0_05\\bin";
	
	// main function
	public static void main(String[] args){
		//sets the default look of new JFrames
		JFrame.setDefaultLookAndFeelDecorated(true);
		//creates new GUI instance
		@SuppressWarnings("unused")
		GUI window = new GUI();
	}
	
	// Default constructor; Sets default attributes of window and sets up handlers
	public GUI(){
		//sets up file choosers
		setFileChoosers();
		// initialises JFrame
		setContent();
		//sets default window attributes
		this.setDefaultAttributes();
		// instantiates controller object
		setController();
		//sets toolbar to go over frame
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
		//sets antialiasing
		((Graphics2D) this.getGraphics()).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
	
	//creates a draggable internal frame
	protected void createInternalFrame(){
		JInternalFrame frame = new JInternalFrame();
	    frame.setVisible(true);
	    frame.setClosable(true);
	    frame.setResizable(true);
	    frame.setFocusable(true);
	    frame.setSize(new Dimension(300, 200));
	    frame.setLocation(100, 100);
	    desktop.add(frame);
	    try {
	        frame.setSelected(true);
	    } catch (java.beans.PropertyVetoException e) {}
	}
	
	//returns an internal JFrame that can be added to the JDesktopFrame
	protected JInternalFrame getNewInternalFrame(){
		JInternalFrame frame = new JInternalFrame();
	    frame.setVisible(true);
	    frame.setClosable(true);
	    frame.setResizable(true);
	    frame.setFocusable(true);
	    frame.setSize(new Dimension(300, 200));
	    frame.setLocation(100, 350);
	    try {
	        frame.setSelected(true);
	    } catch (java.beans.PropertyVetoException e) {}
	    return frame;
	}
	
	//initializes and sets default attributes for file choosers
	private void setFileChoosers(){
		//sets default attributes for save file chooser
		saveFileChooser = new JFileChooser();
		saveFileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		saveFileChooser.setCurrentDirectory(new java.io.File("."));
	    saveFileChooser.setDialogTitle("Save As");
		saveFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		//sets default attributes for load file chooser
		loadFileChooser = new JFileChooser();
		loadFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		loadFileChooser.setCurrentDirectory(new java.io.File("."));
	    loadFileChooser.setDialogTitle("Open");
		loadFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	}
	
	//sets up page layout content and initializes JPanel components
	private void setContent(){
		//sets window content visible
		this.getContentPane().setVisible(true);
		
		//sets up desktop area inside JFrame for JInternalFrame
		desktop = new JDesktopPane();
		i_console = getNewInternalFrame();
		i_console.add(codeLabel);
		desktop.add(i_console);
		this.setContentPane(desktop);
		
		//buffer panel between JFrame and p_main content
		bufferPanel.setVisible(true);
		bufferPanel.setLayout(new GridBagLayout());
		bufferPanel.setOpaque(false);
		this.getContentPane().add(bufferPanel);
		
		//GridBagConstraints for setting p_main into bufferPanel
		GridBagConstraints gbc_p_main = new GridBagConstraints();
		gbc_p_main.anchor =  GridBagConstraints.CENTER;
		gbc_p_main.insets = new Insets(0, 0, 5, 5);
		gbc_p_main.weightx = 1;
		gbc_p_main.weighty = 1;
		gbc_p_main.gridx = 0;
		gbc_p_main.gridy = 0;
		gbc_p_main.fill = GridBagConstraints.BOTH;
		
		//initializes JPanel encapsulating the content (p_main)
		p_main.setVisible(true);
		p_main.setBorder(BorderFactory.createLineBorder(Color.black));
		p_main.setRequestFocusEnabled(false);
		p_main.setOpaque(false);
		p_main.setFocusable(false);
		p_main.setBackground(Color.WHITE);
		bufferPanel.add(p_main, gbc_p_main);
		GridBagLayout gbl_p_main = new GridBagLayout();
		gbl_p_main.columnWidths = new int[]{0, 0, 0, 0};
		gbl_p_main.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_p_main.columnWeights = new double[]{1.0, 2.0, 1.0, Double.MIN_VALUE};
		gbl_p_main.rowWeights = new double[]{1.0, 2.0, 2.0, 2.0, 2.0, 4.0, 1.0, Double.MIN_VALUE};
		p_main.setLayout(gbl_p_main);
		
		//initializes p_palette
		p_palette.setVisible(true);
		p_palette.setBorder(BorderFactory.createLineBorder(Color.black));
		p_palette.setOpaque(false);
		GridBagConstraints gbc_p_palette = new GridBagConstraints();
		gbc_p_palette.anchor = GridBagConstraints.WEST;
		gbc_p_palette.gridheight = 7;
		gbc_p_palette.insets = new Insets(0, 0, 5, 5);
		gbc_p_palette.fill = GridBagConstraints.BOTH;
		gbc_p_palette.gridx = 0;
		gbc_p_palette.gridy = 0;
		p_main.add(p_palette, gbc_p_palette);
		GridBagLayout gbl_p_palette = new GridBagLayout();
		gbl_p_palette.columnWidths = new int[]{0};
		gbl_p_palette.rowHeights = new int[]{0};
		gbl_p_palette.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_p_palette.rowWeights = new double[]{Double.MIN_VALUE};
		p_palette.setLayout(gbl_p_palette);
		
		//initializes toolbar
		toolBar.setVisible(true);
		toolBar.setBorder(BorderFactory.createLineBorder(Color.black));
		toolBar.setFloatable(false);
		toolBar.setOpaque(false);
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.anchor = GridBagConstraints.NORTHWEST;
		gbc_toolBar.gridwidth = 2;
		gbc_toolBar.insets = new Insets(0, 0, 5, 0);
		gbc_toolBar.fill = GridBagConstraints.BOTH;
		gbc_toolBar.gridx = 1;
		gbc_toolBar.gridy = 0;
		p_main.add(toolBar, gbc_toolBar);
		
		//initializes p_workspace
		p_workspace.setVisible(true);
		p_workspace.setBorder(BorderFactory.createLineBorder(Color.black));
		p_workspace.setOpaque(false);
		GridBagConstraints gbc_p_workspace = new GridBagConstraints();
		gbc_p_workspace.anchor = GridBagConstraints.WEST;
		gbc_p_workspace.gridheight = 2;
		gbc_p_workspace.insets = new Insets(0, 0, 5, 5);
		gbc_p_workspace.fill = GridBagConstraints.BOTH;
		gbc_p_workspace.gridx = 1;
		gbc_p_workspace.gridy = 1;
		p_main.add(p_workspace, gbc_p_workspace);
		GridBagLayout gbl_p_workspace = new GridBagLayout();
		gbl_p_workspace.columnWidths = new int[]{0};
		gbl_p_workspace.rowHeights = new int[]{0};
		gbl_p_workspace.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_p_workspace.rowWeights = new double[]{Double.MIN_VALUE};
		p_workspace.setLayout(gbl_p_workspace);
		
		//initializes p_browser
		p_browser.setVisible(true);
		p_browser.setBorder(BorderFactory.createLineBorder(Color.black));
		p_browser.setOpaque(false);
		GridBagConstraints gbc_p_browser = new GridBagConstraints();
		gbc_p_browser.anchor = GridBagConstraints.WEST;
		gbc_p_browser.gridheight = 4;
		gbc_p_browser.insets = new Insets(0, 0, 5, 0);
		gbc_p_browser.fill = GridBagConstraints.BOTH;
		gbc_p_browser.gridx = 2;
		gbc_p_browser.gridy = 1;
		p_main.add(p_browser, gbc_p_browser);
		GridBagLayout gbl_p_browser = new GridBagLayout();
		gbl_p_browser.columnWidths = new int[]{0};
		gbl_p_browser.rowHeights = new int[]{0};
		gbl_p_browser.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_p_browser.rowWeights = new double[]{Double.MIN_VALUE};
		p_browser.setLayout(gbl_p_browser);
		
		//initializes p_output
		p_output.setBorder(BorderFactory.createLineBorder(Color.black));
		p_output.setVisible(true);
		p_output.setOpaque(false);
		GridBagConstraints gbc_p_output = new GridBagConstraints();
		gbc_p_output.anchor = GridBagConstraints.WEST;
		gbc_p_output.gridheight = 2;
		gbc_p_output.insets = new Insets(0, 0, 5, 5);
		gbc_p_output.fill = GridBagConstraints.BOTH;
		gbc_p_output.gridx = 1;
		gbc_p_output.gridy = 3;
		p_main.add(p_output, gbc_p_output);
		GridBagLayout gbl_p_output = new GridBagLayout();
		gbl_p_output.columnWidths = new int[]{0};
		gbl_p_output.rowHeights = new int[]{0};
		gbl_p_output.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_p_output.rowWeights = new double[]{Double.MIN_VALUE};
		p_output.setLayout(gbl_p_output);
		
		//initializes p_console
		p_console.setBorder(BorderFactory.createLineBorder(Color.black));
		p_console.setVisible(true);
		p_console.setOpaque(false);
		GridBagConstraints gbc_p_console = new GridBagConstraints();
		gbc_p_console.anchor = GridBagConstraints.WEST;
		gbc_p_console.gridwidth = 2;
		gbc_p_console.insets = new Insets(0, 0, 5, 0);
		gbc_p_console.fill = GridBagConstraints.BOTH;
		gbc_p_console.gridx = 1;
		gbc_p_console.gridy = 5;
		p_main.add(p_console, gbc_p_console);
/*		GridBagLayout gbl_p_console = new GridBagLayout();
		gbl_p_console.columnWidths = new int[]{0};
		gbl_p_console.rowHeights = new int[]{0};
		gbl_p_console.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_p_console.rowWeights = new double[]{Double.MIN_VALUE};
		p_console.setLayout(gbl_p_console);*/
		p_console.setLayout(new BorderLayout());
		
		//initializes p_information
		p_information.setBorder(BorderFactory.createLineBorder(Color.black));
		p_information.setVisible(true);
		p_information.setOpaque(false);
		GridBagConstraints gbc_p_information = new GridBagConstraints();
		gbc_p_information.gridwidth = 2;
		gbc_p_information.fill = GridBagConstraints.BOTH;
		gbc_p_information.anchor = GridBagConstraints.SOUTHWEST;
		gbc_p_information.gridx = 1;
		gbc_p_information.gridy = 6;
		p_main.add(p_information, gbc_p_information);
		GridBagLayout gbl_p_information = new GridBagLayout();
		gbl_p_information.columnWidths = new int[]{0};
		gbl_p_information.rowHeights = new int[]{0};
		gbl_p_information.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_p_information.rowWeights = new double[]{Double.MIN_VALUE};
		p_information.setLayout(gbl_p_information);
	}
	
	//initiates controller and creates default rectangles
	private void setController(){
		controller = new Controller();
		save = new Save();
		// adds mouse listeners
		this.addMouseListener(controller);
		this.addMouseMotionListener(controller);
		// adds default DraggableRect objects with positions
		controller.addRect(new DraggableRect(525, 120, 75, 75));
		controller.addRect(new DraggableRect(525, 250, 75, 75));
		controller.addRect(new Conditional(50, 80));
		controller.addRect(new Conditional(250, 80));
		controller.addRect(new Start(50, 300));
		controller.addRect(new Loop(620, 300));
		controller.addRect(new Loop(620, 600));
		controller.addRect(new Condition(620, 80));
		//adds all draggableRects to the JFrame
		for(DraggableRect r : controller.getRects()){
			add(r);
		}
	}
	
	// Sets default layout and preferences for window
	public void setDefaultAttributes(){
		//sets window information and basic graphical attributes
		this.setBounds(50, 50, 1000, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setTitle("Siemens Intuitive Interface");
		this.setBackground(Color.GRAY);
		this.setForeground(Color.BLACK);
		this.setLayout(null);
		this.setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		//creates buffer strategy for smooth window graphics
		this.createBufferStrategy(2);
		s = this.getBufferStrategy();

		// adds menu items
		setJMenuBar(menuBar);
	    
	    JMenu fileMenu = new JMenu("DDM Normal");
	    JMenu elementMenu = new JMenu("DDM Radio&Check");

	    mItemLoad = fileMenu.add("Open");
	    mItemLoad.addActionListener(this);
	    mItemLoad.setActionCommand("open");
	    
	    mItemSave = fileMenu.add("Save");
	    mItemSave.addActionListener(this);
	    mItemSave.setActionCommand("save");
	    
	    mItemRun = fileMenu.add("Run");
	    mItemRun.addActionListener(this);
	    mItemRun.setActionCommand("run");
	    
	    mItemGenCode = fileMenu.add("Generate Code");
	    mItemGenCode.addActionListener(this);
	    mItemGenCode.setActionCommand("genCode");
	    
	    mItemGenFrame = fileMenu.add("Show Console");
	    mItemGenFrame.addActionListener(this);
	    mItemGenFrame.setActionCommand("genFrame");
	    
	    mItemDockFrame = fileMenu.add("Dock/Undock");
	    mItemDockFrame.addActionListener(this);
	    mItemDockFrame.setActionCommand("dock");
	    
	    elementMenu.add(rItem = new JRadioButtonMenuItem("rItem", true));
	    elementMenu.add(rItem2 = new JRadioButtonMenuItem("rItem2", false));
	    elementMenu.add(rItem3 = new JRadioButtonMenuItem("rItem3", false));
	    
	    ButtonGroup types = new ButtonGroup();
	    
	    types.add(rItem);
	    types.add(rItem2);
	    types.add(rItem3);
	    
	    elementMenu.addSeparator();
	    
	    elementMenu.add(cItem = new JCheckBoxMenuItem("cItem", false));
	    elementMenu.add(cItem2 = new JCheckBoxMenuItem("cItem2", false));
	    
	    menuBar.add(fileMenu); 
	    menuBar.add(elementMenu);
	}
	
	//draws text from .java file
	private void drawJavaString(JComponent frame){
		BufferedReader br = null;
		try {	
			//opens test.java
			br = new BufferedReader(new FileReader("test.java"));
			//variables to keep track of message
			String message = "<html>";
			String sCurrentLine;
			//loops through each line to read file
			while ((sCurrentLine = br.readLine()) != null) {
				message += sCurrentLine + "<br>";
			}
			message += "</html>";
			//sets JTextPane codeLabel attributes
			codeLabel.setContentType("text/html");
			codeLabel.setText(message);
			codeLabel.setOpaque(false);
			codeLabel.setEditable(true);
			codeLabel.setText(message);
			codeLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
			codeLabel.setForeground(Color.MAGENTA);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null){
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	// ActionHandler method
	public void actionPerformed(ActionEvent e){
		String command = e.getActionCommand();
		switch(command){
			case "open": 
				int loadStatus = loadFileChooser.showOpenDialog(null);
				if(loadStatus == JFileChooser.APPROVE_OPTION){
					save.load(this, loadFileChooser.getSelectedFile().getAbsolutePath());
				}
				break;
			case "save": 
				if(!saved){
					int saveStatusSave = saveFileChooser.showSaveDialog(null);
					if(saveStatusSave == JFileChooser.APPROVE_OPTION){
						save.setPath(saveFileChooser.getCurrentDirectory().getAbsolutePath());
						save.setFile(saveFileChooser.getSelectedFile().getName());
						save.save(this);
						saved = true;
					}
				}
				else{
					save.save(this);
				}
				break;
			case "run":
				runProject();
				break;
			case "genCode":
				ArrayList<DraggableRect> startRects = Controller.getRectsByType(1);
				for(DraggableRect r : startRects){
					controller.writeToFile(r);
				}
				break;
			case "genFrame":
				if(i_console.isClosed()){
					i_console = getNewInternalFrame();
					i_console.add(codeLabel);
					desktop.add(i_console);
				}
				break;
			case "dock":
				i_console_docked = !i_console_docked;
				if(!i_console_docked){
					i_console.setSize(new Dimension(300, 200));
				    i_console.setLocation(100, 350);
				}
				break;
			default:
				break;
		}
	}
	// Function to run handlers
	public void run(){

	}
	
	// Draw function which is called by default
	public void draw(){
		super.repaint();
	}
	
	// Overrides JFrame default paint function
	@Override
	public void paint(Graphics graphics){
		try{
			// calls default paint functions in parent object
			super.paint(s.getDrawGraphics());
			super.paintComponents(s.getDrawGraphics());
			//sets buffer panel to size of window
			bufferPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
			// calls function to draw onto g
			Graphics2D g = (Graphics2D) s.getDrawGraphics();
			drawJavaString(i_console);
			if(i_console_docked){
				i_console.setBounds(p_console.getBounds());
			}
			controller.showRects(g);
			s.show();
			Toolkit.getDefaultToolkit().sync();
			super.repaint();
		}catch(Exception ex){}
	}
	
	//function to compile and run code in project
	private void runProject(){
		//displays text input dialog to get user JDK path
		String s = (String)JOptionPane.showInputDialog(
				new JFrame(),
				"Java JDK Path:",
				"Run Process",
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				path);
		//if the user did not cancel and the string is not empty
		if ((s != null) && (s.length() > 0)) {
			//set the path to input
		    path = s;
		    //compile and run the process
		    try{
			    runProcess("javac test.java");
			    runProcess("java test");
			//catches invalid path exception
		    }catch(IOException io){
		    	JOptionPane.showMessageDialog(new JFrame(), "Invalid Path");
		    	io.printStackTrace();
		    //catches other exceptions
		    }catch(Exception ex){
		    	ex.printStackTrace();
		    }
		}
	}
	
	//prints commands and standard output from them into the console
	private static void printLines(String name, InputStream ins) throws Exception {
	    String line = null;
	    BufferedReader in = new BufferedReader(new InputStreamReader(ins));
	    while ((line = in.readLine()) != null) {
	    	//prints command and the output in quotes
	        System.out.println(name + "\"" + line + "\"");
	    }
	}

	//run argument into command line
	private static void runProcess(String command) throws Exception {
		Process pro;
		pro = Runtime.getRuntime().exec(path+"\\"+command);
		//prints regular output
		printLines(command + "> stdout: ", pro.getInputStream());
		//prints errors
		printLines(command + "> stderr: ", pro.getErrorStream());
		//waits until process is finished and prints return value
		pro.waitFor();
		System.out.println(command + "> exitValue " + pro.exitValue());
	}
}