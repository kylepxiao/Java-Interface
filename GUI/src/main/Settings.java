package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main.util.Save;
import net.miginfocom.swing.MigLayout;

public class Settings extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField workspacePath;
	private JTextField jdkPath;
	
	public String workspacePathStr;
	public String jdkPathStr;
	
	public Settings(String ws, String path) {
		
		workspacePathStr = ws;
		jdkPathStr = path;
		
		this.setTitle("Preferences");
		
		getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][][][][][][][]"));
		
		JLabel lblWorkspace = new JLabel("Workspace");
		getContentPane().add(lblWorkspace, "cell 0 0,alignx trailing");
		
		workspacePath = new JTextField(workspacePathStr);
		getContentPane().add(workspacePath, "cell 1 0,growx");
		workspacePath.setColumns(10);
		
		JLabel lblJdkPath = new JLabel("JDK Path");
		getContentPane().add(lblJdkPath, "cell 0 1,alignx trailing");
		
		jdkPath = new JTextField(jdkPathStr);
		getContentPane().add(jdkPath, "cell 1 1,growx");
		jdkPath.setColumns(10);
		
		JButton Apply = new JButton("Apply");
		Apply.setHorizontalAlignment(SwingConstants.RIGHT);
	    Apply.addActionListener(this);
	    Apply.setActionCommand("apply");
		
		JButton Okay = new JButton("Okay");
		Okay.setHorizontalAlignment(SwingConstants.RIGHT);
	    Okay.addActionListener(this);
	    Okay.setActionCommand("okay");
	    
		JButton Cancel = new JButton("Cancel");
		Cancel.setHorizontalAlignment(SwingConstants.RIGHT);
		Cancel.addActionListener(this);
		Cancel.setActionCommand("cancel");		
		
		getContentPane().add(Apply, "flowx,cell 1 8");
		getContentPane().add(Okay, "cell 1 8");
		getContentPane().add(Cancel, "cell 1 8");
	}
	
	public void setWorkspace(String ws){
		workspacePathStr = ws;
		workspacePath.setText(ws);
	}
	
	public void setPath(String path){
		jdkPathStr = path;
		jdkPath.setText(path);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();
		switch(command){
			case "apply":
				workspacePathStr = workspacePath.getText();
				jdkPathStr = jdkPath.getText();
				GUI.setVars(jdkPathStr, workspacePathStr);
				setWorkspace(Save.getPath());
				break;
			case "okay":
				workspacePathStr = workspacePath.getText();
				jdkPathStr = jdkPath.getText();
				GUI.setVars(jdkPathStr, workspacePathStr);
				setWorkspace(Save.getPath());
				this.setVisible(false);
				break;
			case "cancel":
				this.setVisible(false);
				break;
			default:
				break;
		}
	}

}