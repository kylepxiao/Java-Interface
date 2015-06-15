package Buttons;

import javax.swing.*;
import java.awt.*;

public class AddingDropDownMenus extends JFrame {
  private JMenuBar menuBar = new JMenuBar(); // Window menu bar
  
  private JMenuItem mItem, mItem2, mItem3, mItem4, mItem5, mItem6;
  private JRadioButtonMenuItem rItem, rItem2, rItem3;
  private JCheckBoxMenuItem cItem, cItem2;
  
  public AddingDropDownMenus() {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setJMenuBar(menuBar);
    
    JMenu fileMenu = new JMenu("DDM Normal");
    JMenu elementMenu = new JMenu("DDM Radio&Check");
    
    mItem = fileMenu.add("mItem");
    mItem2 = fileMenu.add("mItem2");
    mItem3 = fileMenu.add("mItem3");
    mItem4 = fileMenu.add("mItem4");
    mItem5 = fileMenu.add("mItem5");
    mItem6 = fileMenu.add("mItem6");
    
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
  
  public static void main(String[] a) {
    AddingDropDownMenus window = new AddingDropDownMenus(); 
    window.setSize(300, 300); // Size
    window.setVisible(true);
    window.setBackground(Color.GRAY);
    
  }
}