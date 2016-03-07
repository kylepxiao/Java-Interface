package main.block;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JTextArea;

public class Assignment extends DraggableRect{
	private static final long serialVersionUID = 1L;
	
	private Rectangle sub1;
	private Rectangle sub2;
	private static final boolean sub1Visible = true;
	private static final boolean sub2Visible = true;
	private static final int numChildren = 2;
	private JTextArea variable = new JTextArea();
	private JTextArea value = new JTextArea();
	
	private static final int mainWidth = 130;
	private static final int mainHeight = 76;
	private static final int subWidth = 30;
	private static final int subHeight = 26;
	private static final int subDisplacementX = 20;
	private static final int subDisplacementY = 25;
	private static final int subsDisplacmentX = 60;
	private static final int equalsDisplacementX = 35;
	private static final int equalsDisplacementY = 10;
	private static final int equalsLength = 20;
	private static final int triangleSize = 5;
	
	public Assignment(){
		super(0, 0, mainWidth, mainHeight);
		this.setLayout(null);
		setNumChildren(numChildren);
		sub1 = new Rectangle(0, 0, subWidth, subHeight);
		sub2 = new Rectangle(0, 0, subWidth, subHeight);
		//points for text boxes
		int t1X = (mainWidth/2) - (subWidth/2) - subDisplacementX-8;
		int t2X = (mainWidth/2) - (subWidth/2) + subDisplacementX+12;
		int midY = (mainHeight/2) - (subHeight/2);
		//left text box attributes
		variable.setBounds(t1X, midY, subWidth, subHeight);
		variable.setText("A");
		variable.setVisible(true);
		//right text box attributes
		value.setBounds(t2X, midY, subWidth, subHeight);
		value.setText("B");
		value.setVisible(true);
		//adding text boxes
		this.add(variable);
		this.add(value);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		update();
		type = 1;
		this.setOpaque(false);
	}
	public Assignment(int x, int y){
		super(x, y, mainWidth, mainHeight);
		this.setLayout(null);
		setNumChildren(numChildren);
		sub1 = new Rectangle(0, 0, subWidth, subHeight);
		sub2 = new Rectangle(0, 0, subWidth, subHeight);
		//points for text boxes
		int t1X = (mainWidth/2) - (subWidth/2) - subDisplacementX-8;
		int t2X = (mainWidth/2) - (subWidth/2) + subDisplacementX+12;
		int midY = (mainHeight/2) - (subHeight/2);
		//left text box attributes
		variable.setBounds(t1X, midY, subWidth, subHeight);
		variable.setText("A");
		variable.setVisible(true);
		//right text box attributes
		value.setBounds(t2X, midY, subWidth, subHeight);
		value.setText("B");
		value.setVisible(true);
		//adding text boxes
		this.add(variable);
		this.add(value);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		update();
		type = 1;
		this.setOpaque(false);
	}
	public Assignment(Color c){
		super(0, 0, mainWidth, mainHeight, c);
		this.setLayout(null);
		setNumChildren(numChildren);
		sub1 = new Rectangle(0, 0, subWidth, subHeight);
		sub2 = new Rectangle(0, 0, subWidth, subHeight);
		//points for text boxes
		int t1X = (mainWidth/2) - (subWidth/2) - subDisplacementX-8;
		int t2X = (mainWidth/2) - (subWidth/2) + subDisplacementX+12;
		int midY = (mainHeight/2) - (subHeight/2);
		//left text box attributes
		variable.setBounds(t1X, midY, subWidth, subHeight);
		variable.setText("A");
		variable.setVisible(true);
		//right text box attributes
		value.setBounds(t2X, midY, subWidth, subHeight);
		value.setText("B");
		value.setVisible(true);
		//adding text boxes
		this.add(variable);
		this.add(value);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		update();
		type = 1;
		this.setOpaque(false);
	}
	public Assignment(int x, int y, Color c){
		super(x, y, mainWidth, mainHeight, c);
		this.setLayout(null);
		setNumChildren(numChildren);
		sub1 = new Rectangle(0, 0, subWidth, subHeight);
		sub2 = new Rectangle(0, 0, subWidth, subHeight);
		//points for text boxes
		int t1X = (mainWidth/2) - (subWidth/2) - subDisplacementX-8;
		int t2X = (mainWidth/2) - (subWidth/2) + subDisplacementX+12;
		int midY = (mainHeight/2) - (subHeight/2);
		//left text box attributes
		variable.setBounds(t1X, midY, subWidth, subHeight);
		variable.setText("A");
		variable.setVisible(true);
		//right text box attributes
		value.setBounds(t2X, midY, subWidth, subHeight);
		value.setText("B");
		value.setVisible(true);
		//adding text boxes
		this.add(variable);
		this.add(value);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		update();
		type = 1;
		this.setOpaque(false);
	}
	private void updateSubs(){
		sub1.setLocation(position.x + subDisplacementX, position.y + subDisplacementY);
		sub2.setLocation(sub1.x + subsDisplacmentX, sub1.y);	
	}
	@Override
	public void update(){
		this.setBounds(getOffset(position));
		updateSubs();
		
		f1 = variable.getText();
		f2 = value.getText();
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
	private void drawLines(Graphics2D g){
		int equalsLeft = sub1.x + equalsDisplacementX;
		int equalsRight = equalsLeft + equalsLength;
		
		g.drawLine(equalsLeft, sub1.y + equalsDisplacementY, equalsRight, sub1.y + equalsDisplacementY);
		g.drawLine(equalsLeft, sub1.y + equalsDisplacementY + 5, equalsRight, sub1.y + equalsDisplacementY + 5);
		
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
	public void draw(Graphics2D g, boolean fullscreen){
		super.draw(g, fullscreen);
		update();
		//fills in shadows for hovering
		if(sub1Visible && objectsHoveringAbove.get(1)){
			g.setPaint(shadow);
			g.fill(sub1);
		}
		if(sub2Visible && objectsHoveringAbove.get(2)){
			g.setPaint(shadow);
			g.fill(sub2);
		}
		//draws branches if there is not a child occupying its space
		g.setPaint(c);
		if(sub1Visible){
			g.draw(sub1);
		}
		if(sub2Visible){
			g.draw(sub2);
		}
		//draws decorations
		drawLines(g);
	}

}