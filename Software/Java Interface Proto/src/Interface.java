import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import javax.swing.JToolBar;


public class Interface {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface window = new Interface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Interface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setVisible(false);
		frame.getContentPane().setBackground(Color.WHITE);
		
		JPanel p_main = new JPanel();
		p_main.setVisible(false);
		p_main.setRequestFocusEnabled(false);
		p_main.setOpaque(false);
		p_main.setFocusable(false);
		p_main.setBackground(Color.WHITE);
		frame.getContentPane().add(p_main, BorderLayout.CENTER);
		GridBagLayout gbl_p_main = new GridBagLayout();
		gbl_p_main.columnWidths = new int[]{0, 0, 0, 0};
		gbl_p_main.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_p_main.columnWeights = new double[]{1.0, 2.0, 1.0, Double.MIN_VALUE};
		gbl_p_main.rowWeights = new double[]{1.0, 2.0, 2.0, 2.0, 2.0, 4.0, 1.0, Double.MIN_VALUE};
		p_main.setLayout(gbl_p_main);
		
		JPanel p_palette = new JPanel();
		p_palette.setForeground(Color.DARK_GRAY);
		p_palette.setBackground(Color.DARK_GRAY);
		GridBagConstraints gbc_p_palette = new GridBagConstraints();
		gbc_p_palette.anchor = GridBagConstraints.WEST;
		gbc_p_palette.gridheight = 7;
		gbc_p_palette.insets = new Insets(0, 0, 5, 5);
		gbc_p_palette.fill = GridBagConstraints.VERTICAL;
		gbc_p_palette.gridx = 0;
		gbc_p_palette.gridy = 0;
		p_main.add(p_palette, gbc_p_palette);
		GridBagLayout gbl_p_palette = new GridBagLayout();
		gbl_p_palette.columnWidths = new int[]{0};
		gbl_p_palette.rowHeights = new int[]{0};
		gbl_p_palette.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_p_palette.rowWeights = new double[]{Double.MIN_VALUE};
		p_palette.setLayout(gbl_p_palette);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.anchor = GridBagConstraints.NORTHWEST;
		gbc_toolBar.gridwidth = 2;
		gbc_toolBar.insets = new Insets(0, 0, 5, 0);
		gbc_toolBar.gridx = 1;
		gbc_toolBar.gridy = 0;
		p_main.add(toolBar, gbc_toolBar);
		
		JPanel p_workspace = new JPanel();
		GridBagConstraints gbc_p_workspace = new GridBagConstraints();
		gbc_p_workspace.anchor = GridBagConstraints.WEST;
		gbc_p_workspace.gridheight = 2;
		gbc_p_workspace.insets = new Insets(0, 0, 5, 5);
		gbc_p_workspace.fill = GridBagConstraints.VERTICAL;
		gbc_p_workspace.gridx = 1;
		gbc_p_workspace.gridy = 1;
		p_main.add(p_workspace, gbc_p_workspace);
		GridBagLayout gbl_p_workspace = new GridBagLayout();
		gbl_p_workspace.columnWidths = new int[]{0};
		gbl_p_workspace.rowHeights = new int[]{0};
		gbl_p_workspace.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_p_workspace.rowWeights = new double[]{Double.MIN_VALUE};
		p_workspace.setLayout(gbl_p_workspace);
		
		JPanel p_browser = new JPanel();
		GridBagConstraints gbc_p_browser = new GridBagConstraints();
		gbc_p_browser.anchor = GridBagConstraints.WEST;
		gbc_p_browser.gridheight = 4;
		gbc_p_browser.insets = new Insets(0, 0, 5, 0);
		gbc_p_browser.fill = GridBagConstraints.VERTICAL;
		gbc_p_browser.gridx = 2;
		gbc_p_browser.gridy = 1;
		p_main.add(p_browser, gbc_p_browser);
		GridBagLayout gbl_p_browser = new GridBagLayout();
		gbl_p_browser.columnWidths = new int[]{0};
		gbl_p_browser.rowHeights = new int[]{0};
		gbl_p_browser.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_p_browser.rowWeights = new double[]{Double.MIN_VALUE};
		p_browser.setLayout(gbl_p_browser);
		
		JPanel p_output = new JPanel();
		GridBagConstraints gbc_p_output = new GridBagConstraints();
		gbc_p_output.anchor = GridBagConstraints.WEST;
		gbc_p_output.gridheight = 2;
		gbc_p_output.insets = new Insets(0, 0, 5, 5);
		gbc_p_output.fill = GridBagConstraints.VERTICAL;
		gbc_p_output.gridx = 1;
		gbc_p_output.gridy = 3;
		p_main.add(p_output, gbc_p_output);
		GridBagLayout gbl_p_output = new GridBagLayout();
		gbl_p_output.columnWidths = new int[]{0};
		gbl_p_output.rowHeights = new int[]{0};
		gbl_p_output.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_p_output.rowWeights = new double[]{Double.MIN_VALUE};
		p_output.setLayout(gbl_p_output);
		
		JPanel p_console = new JPanel();
		GridBagConstraints gbc_p_console = new GridBagConstraints();
		gbc_p_console.anchor = GridBagConstraints.WEST;
		gbc_p_console.gridwidth = 2;
		gbc_p_console.insets = new Insets(0, 0, 5, 0);
		gbc_p_console.fill = GridBagConstraints.VERTICAL;
		gbc_p_console.gridx = 1;
		gbc_p_console.gridy = 5;
		p_main.add(p_console, gbc_p_console);
		GridBagLayout gbl_p_console = new GridBagLayout();
		gbl_p_console.columnWidths = new int[]{0};
		gbl_p_console.rowHeights = new int[]{0};
		gbl_p_console.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_p_console.rowWeights = new double[]{Double.MIN_VALUE};
		p_console.setLayout(gbl_p_console);
		
		JPanel p_information = new JPanel();
		GridBagConstraints gbc_p_information = new GridBagConstraints();
		gbc_p_information.gridwidth = 2;
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
		frame.setBounds(100, 100, 800, 457);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
