package main.block;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Function extends DraggableRect{

	private static final long serialVersionUID = 1L;

	//information about children
	private static final int numChildren = 2;
	
	//Strings used to write to java file
	public static final String codeEncap1 = "public static void main(String[] args){";
	public static final String codeEncap2 = "}";
	
	//default dimensions of Start object
	private static final int mainWidth = 75;
	private static final int mainHeight = 80;
	private JTextArea name = new JTextArea(1,5);
	private JTextArea description = new JTextArea(2,5);
	JScrollPane scrollPane = new JScrollPane(name);
	JScrollPane scrollPane2 = new JScrollPane(description);
	
	//default constructor
	public Function(){
		super(0, 0, mainWidth, mainHeight);
		setNumChildren(numChildren);
		type =6;
		
		f1 = name.getText();
	}
	
	//override constructor to specify position 
	public Function(int x, int y){
		super(x, y, mainWidth, mainHeight);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		name.setBounds(2, mainHeight/2, mainWidth-30, mainHeight/2 - 15);
		name.setText("Name");
		name.setWrapStyleWord(true);
		name.setVisible(true);
		description.setBounds(2, mainHeight/2, mainWidth-30, mainHeight/2 - 15);
		description.setText("Body");
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		description.setVisible(true);
		this.add(scrollPane);
		this.add(scrollPane2);
		setNumChildren(numChildren);
		type = 6;
		
		f1 = name.getText();
	}
	
	//override constructor to specify color
	public Function(Color c){
		super(0, 0, mainWidth, mainHeight, c);
		setNumChildren(numChildren);
		type = 6;
		
		f1 = name.getText();
	}
	
	//override constructor to specify position and color
	public Function(int x, int y, Color c){
		super(x, y, mainWidth, mainHeight, c);
		setNumChildren(numChildren);
		type = 6;
		
		f1 = name.getText();
	}
	
	@Override
	public int getWidth(){
		return position.width;
	}
	
	@Override
	public void draw(Graphics2D g, boolean fullscreen){
		super.draw(g, fullscreen);
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
		f2 = description.getText();
	}
}