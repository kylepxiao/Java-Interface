package main.block;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Script extends DraggableRect {
	private static final long serialVersionUID = 1L;

	//information about children
	private static final int numChildren = 2;
	
	//Strings used to write to java file
	public static final String codeEncap1 = "public static void main(String[] args){";
	public static final String codeEncap2 = "}";
	
	//default dimensions of Start object
	private static final int mainWidth = 75;
	private static final int mainHeight = 75;
	private JTextArea name = new JTextArea(4,4);
	JScrollPane scrollPane = new JScrollPane(name);
	
	//default constructor
	public Script(){
		super(0, 0, mainWidth, mainHeight);
		setNumChildren(numChildren);
		type = 8;
	}
	
	//override constructor to specify position 
	public Script(int x, int y){
		super(x, y, mainWidth, mainHeight);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		name.setBounds(2, mainHeight/2, mainWidth-5, mainHeight/2);
		name.setText("Text");
		name.setWrapStyleWord(true);
		name.setVisible(true);
		name.setLineWrap(true);
		this.add(scrollPane);
		setNumChildren(numChildren);
		type = 8;
	}
	
	//override constructor to specify color
	public Script(Color c){
		super(0, 0, mainWidth, mainHeight, c);
		setNumChildren(numChildren);
		type = 8;
	}
	
	//override constructor to specify position and color
	public Script(int x, int y, Color c){
		super(x, y, mainWidth, mainHeight, c);
		setNumChildren(numChildren);
		type = 8;
	}
	
	@Override
	public int getWidth(){
		return position.width;
	}
	
	@Override
	public void draw(Graphics2D g){
		super.draw(g);
		g.setFont(new Font(Font.SANS_SERIF, 3, 18));
		if(childrenIDs.get(0) != 0){
			int midX = position.x + (position.width/2);
			int bottomY = position.y + position.height;
			int[] xPoints = {midX - triangleSize, midX + triangleSize, midX};
			int[] yPoints = {bottomY + displacement - triangleSize, bottomY + displacement - triangleSize, bottomY + displacement};
			g.drawLine(midX, bottomY, midX, bottomY + displacement);
			g.fillPolygon(xPoints, yPoints, 3);
		}
		f1 = name.getText();
	}
}
