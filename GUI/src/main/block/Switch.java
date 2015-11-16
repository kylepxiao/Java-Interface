package main.block;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextArea;

import main.util.Controller;

public class Switch extends DraggableRect{
	
	private static final long serialVersionUID = 1L;

	public Rectangle content;
	
	private boolean contentVisible = true;
	
	
	private static final int mainWidth = 75;
	public static final int mainHeight = 75;
	
	
	private static final int contentWidth = 75;
	private static final int contentHeight = 50;
	
	public static final int contentDisplacementX = 100;
	public static final int contentDisplacementY = 100;
	
	public int clicks = 0;
	public ArrayList<Rectangle> casesAndContents = new ArrayList<Rectangle>(); 
	
	private JTextArea expression = new JTextArea();
	private static final int numChildren = 0;
	
	
	public Switch(){
		super(0, 0, mainWidth, mainHeight);
		setNumChildren(numChildren);
		content = new Rectangle(0, 0, contentWidth, contentHeight);
		objectsHoveringAbove.add(false);
		expression.setOpaque(false);
		expression.setText("<Expression>");
		add(expression, BorderLayout.CENTER);
		type = 6;
		update();
	}
	
	public Switch(int x, int y){
		super(x, y, mainWidth, mainHeight);
		setNumChildren(numChildren);
		content = new Rectangle(position.x + contentDisplacementX, position.y + contentDisplacementX, contentWidth, contentHeight);
		casesAndContents.add(content);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		expression.setOpaque(false);
		expression.setText("<Expression>");
		add(expression, BorderLayout.CENTER);
		type = 6;
		//update();
	}
	
	public Switch(Color c){
		super(0, 0, mainWidth, mainHeight, c);
		setNumChildren(numChildren);
		content = new Rectangle(0, 0, contentWidth, contentHeight);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		expression.setOpaque(false);
		expression.setText("<Expression>");
		add(expression, BorderLayout.CENTER);
		type = 6;
		update();
	}
	
	
	
	public Switch(int x, int y, Color c){
		super(x, y, mainWidth, mainHeight, c);
		setNumChildren(numChildren);
		content = new Rectangle(0, 0, contentWidth, contentHeight);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		expression.setOpaque(false);
		expression.setText("<Expression>");
		add(expression, BorderLayout.CENTER);
		type = 6;
		update();
	}
	//calculates position of branches and updates dimensions based on children
	private void updateBranches(){
		//sets branch location with respect to main position
		// make a for loop here that has the the arraylist in it and then specify each consectutive case to the case before it
		//cases = new Rectangle(cases.x, cases.y + caseDisplacementY, 100, 100);
		content.setLocation(position.x + contentDisplacementX, position.y + contentDisplacementY);
		for(int i = 1; i < casesAndContents.size(); i++){
			casesAndContents.get(i).setLocation(casesAndContents.get(i - 1).x, casesAndContents.get(i - 1).y + contentDisplacementY);;
			//casesAndContents.set(i, cases);
		}
/*
		if(childrenIDs.size() > 0 && childrenIDs.get(0) != 0){
			//sets branch1 to encapsulate child
			caseVisible = false;
			int childWidth = main.util.Controller.getRectByID(childrenIDs.get(0)).getResizeWidth();
			if(childWidth > content.x - position.x){
				content.x += childWidth - contentDisplacementX + 5;
			}
		}else{
			//sets branch1 to visible
			caseVisible = true;
		}
		//checks if there is something in the second branch
		if(childrenIDs.size() > 1 && childrenIDs.get(1) != 0){
			//sets branch2 to encapsulate child
			contentVisible = false;
			Controller.setTreeLocation(Controller.getRectByID(childrenIDs.get(1)), content.x, content.y);
		}else{
			//sets branch2 visible
			contentVisible = true;
		}
		//expands branch if condition is in place
		if(childrenIDs.size() > 2 && childrenIDs.get(2) != 0){
			if(content.x < position.x + contentDisplacementX + 30){
				content.x = position.x + contentDisplacementX + 30;
			}
			if(childrenIDs.get(1) != 0){
				Controller.setTreeLocation(Controller.getRectByID(childrenIDs.get(1)), content.x, content.y);
			}
		}
*/			
	}
		
	//overrides update to account for branches
	@Override
	public void update(){
		//this.setBounds(getOffset(position));
		super.update();
		updateBranches();
		if(internalRect){
			this.setVisible(false);
		}else{
			this.setVisible(true);
		}
	}
	
	//override resetFamily to account for different number of children
	@Override
	public void resetFamily(){
		parentID = 0;
		setNumChildren(numChildren);
	}
/*	
	//Overrides getNewSnap to account for branches
	@Override
	public Point getNewSnap(DraggableRect rect){
		//updates branches to adjust for child height
		updateBranches();
		//if intersect main rect
		if (position.intersects(rect.getPosition()) && id != rect.id){
			//sets location to far right to avoid collisions
			if(rect.getType() == 0){
				return new Point(position.x-35, position.y+3);
			}
			return new Point(position.x + contentDisplacementX + content.width + displacement, rect.getPosition().y);
			//if intersecting first branch
		}else if (cases.intersects(rect.getPosition()) && !cases.equals(rect) && childrenIDs.get(0) == 0){
			//sets location of rectangle being dragged to below the rectangle it overlaps
			return new Point(cases.x, cases.y);
			//if intersecting second branch
		}else if(content.intersects(rect.getPosition()) && !content.equals(rect) && childrenIDs.get(1) == 0){
			//sets location of rectangle being dragged to below the rectangle it overlaps
			return new Point(content.x, content.y);
		}return null;
	}
	
	//overrides setChild function to set child under branch
	@Override
	public void setChild(DraggableRect rect){
		if(!position.equals(rect.getPosition())){
			if(cases.intersects(rect.getPosition()) && childrenIDs.get(0) == 0){
				childrenIDs.set(0, rect.id);
			}else if(content.intersects(rect.getPosition()) && childrenIDs.get(1) == 0){
				childrenIDs.set(1, rect.id);
			}else if(position.intersects(rect.getPosition()) && childrenIDs.get(2) == 0 && rect.getType() == 4){
				childrenIDs.set(2, rect.id);
				internalRect = true;
			}
		}
	}
*/		
	@Override
	public void checkHoverOver(DraggableRect r){
		try{
			//checks hovering over main body
			if(position.intersects(r.getPosition()) && id != r.id){
				if(!objectsHoveringAbove.get(0)){
					objectsHoveringAbove.set(0, true);
				}
			}else	{
				if(	objectsHoveringAbove.get(0)){
					objectsHoveringAbove.set(0, false);
				}
			}	
			//checks hovering over branch1
			if(content.intersects(r.getPosition()) && id != r.id){
				if(!objectsHoveringAbove.get(1)){
					objectsHoveringAbove.set(1, true);
				}
			}else{
				if(objectsHoveringAbove.get(1)){
					objectsHoveringAbove.set(1, false);
				}
			}
			
				
			//catches indexOutOfBounds exception
		}catch(Exception ex){
			ex.printStackTrace();		
		}
	}
	
	private void drawArrows(Graphics2D g){
		int mainMidX = position.x + (position.width/2);
		int mainBottom = position.y + position.height;
		int caseLeft = position.x + contentDisplacementX;
		int caseMidY = position.y + clicks*contentDisplacementY + 100 + (content.height/2);
		int mainBranch2MidX = content.x - (content.width/2);
		//draws lines for arrows
		g.drawLine(mainMidX, mainBottom, mainMidX, caseMidY);
		
		for(int i = 0; i < casesAndContents.size(); i++){
			int contentMidY2 = position.y + i*contentDisplacementY + 100 + (content.height/2);
			int[] xPoints = {caseLeft - triangleSize, caseLeft - triangleSize, caseLeft};
			int[] yPoints = {contentMidY2 - triangleSize, contentMidY2 + triangleSize, contentMidY2};
			g.drawLine(mainMidX, contentMidY2, caseLeft, contentMidY2);
			//draws triangles for arrows
			g.fillPolygon(xPoints, yPoints, 3);
			//draws labels for arrows
			g.setFont(new Font(Font.SANS_SERIF, 3, 18));
			g.drawString("Case " + (i + 1), mainBranch2MidX - 23, contentMidY2 - 5);
		}
		
	}
	
	@Override
	public void draw(Graphics2D g){
		super.draw(g);
		
		
		//System.out.println("cases " + casesAndContents.size());
		for(int i = 0; i < casesAndContents.size(); i++){
			
			if(contentVisible && objectsHoveringAbove.get(1)){
				g.setPaint(shadow);
				g.fill(content);
			}
			
			if(contentVisible){
				g.draw(casesAndContents.get(i));
			}
				drawArrows(g);
				
				
				
		}
		update();
		//System.out.println("cases " + casesAndContents.size());
	}
	/*
	@Override
	public int getType(){
		return type;
	}
	*/
	
}
