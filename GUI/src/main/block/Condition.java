package main.block;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Condition extends DraggableRect{

	private static final long serialVersionUID = 1L;
	
	private static final int numChildren = 0; // or maybe 3 im not sure
	
	private static final int mainWidth = 130;
	private static final int mainHeight = 76;
	private static final int subWidth = 20;
	private static final int subHeight = 25;
	private static final int subDisplacementX = 32;
//	private static final int snapDisplacement = 50;
	
	private JTextField t1 = new JTextField();
	private JTextField t2 = new JTextField();
	
	public Condition(){
		super(0, 0, mainWidth, mainHeight);
		this.setBorder(null);
		this.setLayout(null);
		//points for text boxes
		int t1X = (mainWidth/2) - (subWidth/2) - subDisplacementX;
		int t2X = (mainWidth/2) - (subWidth/2) + subDisplacementX;
		int midY = (mainHeight/2) - (subHeight/2);
		//left text box attributes
		t1.setBounds(t1X, midY, subWidth, subHeight);
		t1.setText("A");
		t1.setVisible(true);
		//right text box attributes
		t2.setBounds(t2X, midY, subWidth, subHeight);
		t2.setText("B");
		t2.setVisible(true);
		//adding text boxes
		this.add(t1);
		this.add(t2);
		setNumChildren(numChildren);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		update();
	}
	public Condition(int x, int y){
		super(x, y, mainWidth, mainHeight);
		this.setBorder(null);
		this.setLayout(null);
		//points for text boxes
		int t1X = (mainWidth/2) - (subWidth/2) - subDisplacementX;
		int t2X = (mainWidth/2) - (subWidth/2) + subDisplacementX;
		int midY = (mainHeight/2) - (subHeight/2);
		//left text box attributes
		t1.setBounds(t1X, midY, subWidth, subHeight);
		t1.setText("A");
		t1.setVisible(true);
		//right text box attributes
		t2.setBounds(t2X, midY, subWidth, subHeight);
		t2.setText("B");
		t2.setVisible(true);
		//adding text boxes
		this.add(t1);
		this.add(t2);
		//adds combo box
		this.add(getComboBox());
		setNumChildren(numChildren);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		update();
	}
	public Condition(Color c){
		super(0, 0, mainWidth, mainHeight, c);
		this.setBorder(null);
		this.setLayout(null);
		//points for text boxes
		int t1X = (mainWidth/2) - (subWidth/2) - subDisplacementX;
		int t2X = (mainWidth/2) - (subWidth/2) + subDisplacementX;
		int midY = (mainHeight/2) - (subHeight/2);
		//left text box attributes
		t1.setBounds(t1X, midY, subWidth, subHeight);
		t1.setText("A");
		t1.setVisible(true);
		//right text box attributes
		t2.setBounds(t2X, midY, subWidth, subHeight);
		t2.setText("B");
		t2.setVisible(true);
		//adding text boxes
		this.add(t1);
		this.add(t2);
		setNumChildren(numChildren);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		update();
	}
	public Condition(int x, int y, Color c){
		super(x, y, mainWidth, mainHeight,c);
		this.setBorder(null);
		this.setLayout(null);
		//points for text boxes
		int t1X = (mainWidth/2) - (subWidth/2) - subDisplacementX;
		int t2X = (mainWidth/2) - (subWidth/2) + subDisplacementX;
		int midY = (mainHeight/2) - (subHeight/2);
		//left text box attributes
		t1.setBounds(t1X, midY, subWidth, subHeight);
		t1.setText("A");
		t1.setVisible(true);
		//right text box attributes
		t2.setBounds(t2X, midY, subWidth, subHeight);
		t2.setText("B");
		t2.setVisible(true);
		//adding text boxes
		this.add(t1);
		this.add(t2);
		setNumChildren(numChildren);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		update();
	}
	@Override 
	public void update(){
		if(parentID == 0){
			this.setBounds(getOffset(position));
		}else{
			this.setBounds(getOffset(new Rectangle(position.x, position.y, position.width, position.height)));
		}
	}
	public void checkHoverOver(DraggableRect r){
		try{
			//checks hovering over main body
			if(position.intersects(r.getPosition()) && id != r.id){
				if(!objectsHoveringAbove.get(0)){
					objectsHoveringAbove.set(0, true);
				}
			}else{
				if(objectsHoveringAbove.get(0)){
					objectsHoveringAbove.set(0, false);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private Container getComboBox(){
		String labels[] = { "==", "!=", ">", "<",">=", "<="};
		JFrame frame = new JFrame();
		Container contentpane = frame.getContentPane();

	    JComboBox<String> comboBox1 = new JComboBox<String>(labels);
	    contentpane.add(comboBox1, BorderLayout.CENTER);
	    
	    //sets bounds of contentpane
	    int x = (mainWidth/2) - 20;
	    int y = (mainHeight/2) - 12;
	    contentpane.setBounds(x, y, 40, 25);
	    
	    return contentpane;
	}
	
	//gets the offset for an array of x points
	private int[] getOffsetPointsX(int[] xPoints){
		for(int i=0; i<xPoints.length; i++){
			xPoints[i] += 0;
		}
		return xPoints;
	}
	
	//gets the offset for an array of y points
	private int[] getOffsetPointsY(int[] yPoints){
		for(int i=0; i<yPoints.length; i++){
			yPoints[i] += 9;
		}
		return yPoints;
	}
	
	//draws the diamond
	private void drawLines(Graphics2D g){
		int mainMidX = position.x + position.width/2;
		int mainMidY = position.y + position.height/2 - 9;
		int mainRight = position.x + position.width;
		int mainTop = position.y - 9;
		int mainBottom = position.y + position.height - 9;
		
		int[] xPoints = {position.x, mainMidX, mainRight, mainMidX};
		int[] yPoints = {mainMidY, mainTop, mainMidY, mainBottom};

		g.drawPolygon(getOffsetPointsX(xPoints), getOffsetPointsY(yPoints), 4);
	}
	@Override
	public void draw(Graphics2D g){
		super.draw(g);
		update();
		//draws decorations
		drawLines(g);
	}
	@Override
	public int getType(){
		return 4;
	}
}