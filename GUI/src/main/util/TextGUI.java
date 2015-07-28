package main.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.*;
import javax.swing.*;



public class TextGUI extends JFrame{

    public TextGUI(){

    super("Set Text From TXT File To JTextPane");

    JPanel p = new JPanel();
    JTextPane tp = new JTextPane();

    // call the Function TextFromFile
    TextFromFile(tp);

    Font font = new Font(" ", Font.PLAIN, 12);
    tp.setFont(font);

    tp.setForeground(Color.BLACK);
    tp.setBackground(Color.WHITE);

    JScrollPane jp = new JScrollPane(tp);

    p.setLayout(new BorderLayout());
    p.add(jp,BorderLayout.CENTER);

    setContentPane(p);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setSize(800,300);
    setVisible(true);
    }
    
    /*
    create a function to get the text from a text file 
    and set it into a JTextPane
    */ 
    public static void TextFromFile(JTextPane codeLabel) {
    	try{
        	//the file path
        	String path = "test.java";
            File file = new File(path);
            FileReader reader = new FileReader(file);
            do{
            	codeLabel.read(reader,null);
            }while(reader.read() != -1);
            reader.close();
        } catch(Exception ex){
        	ex.printStackTrace();
        }
     }
   /* 	
    public static void main(String[] args){
    	new TextGUI();
    }
    */
}

