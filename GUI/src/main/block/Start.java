package main.block;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Start extends DraggableRect{

	private static final long serialVersionUID = 1L;

	//information about children
	private static final int numChildren = 2;
	
	//Strings used to write to java file
	public static final String codeEncap1 = "public static void main(String[] args){";
	public static final String codeEncap2 = "}";
	
	//default dimensions of Start object
	private static final int mainWidth = 75;
	private static final int mainHeight = 50;
	
	//default constructor
	public Start(){
		super(0, 0, mainWidth, mainHeight);
		setNumChildren(numChildren);
	}
	
	//override constructor to specify position
	public Start(int x, int y){
		super(x, y, mainWidth, mainHeight);
		setNumChildren(numChildren);
	}
	
	//override constructor to specify color
	public Start(Color c){
		super(0, 0, mainWidth, mainHeight, c);
		setNumChildren(numChildren);
	}
	
	//override constructor to specify position and color
	public Start(int x, int y, Color c){
		super(x, y, mainWidth, mainHeight, c);
		setNumChildren(numChildren);
	}
	
	@Override
	public int getWidth(){
		return position.width;
	}
	
	@Override
	public void draw(Graphics2D g){
		super.draw(g);
		g.setFont(new Font(Font.SANS_SERIF, 3, 18));
		g.drawString("Start", position.x +15, 5 + position.y + (position.height)/2);
		if(childrenIDs.get(0) != 0){
			int midX = position.x + (position.width/2);
			int bottomY = position.y + position.height;
			int[] xPoints = {midX - triangleSize, midX + triangleSize, midX};
			int[] yPoints = {bottomY + displacement - triangleSize, bottomY + displacement - triangleSize, bottomY + displacement};
			g.drawLine(midX, bottomY, midX, bottomY + displacement);
			g.fillPolygon(xPoints, yPoints, 3);
		}
	}

	@Override
	public int getType(){
		return 1;
	}
}