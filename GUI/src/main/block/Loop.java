package main.block;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JTextArea;

public class Loop extends DraggableRect {

	private static final long serialVersionUID = 1L;
	
	private Rectangle content;
	private Rectangle branch;
	private boolean contentVisible = true;
	private boolean branchVisible = true;
	private static final int numChildren = 2;
	private JTextArea condition = new JTextArea();
	
	private static final int mainWidth = 75;
	private static final int mainHeight = 75;
	private static final int contentWidth = 75;
	private static final int contentHeight = 75;
	private static final int mainWidth2= 75;
	private static final int mainHeight2 = 75;
	private static final int branchDisplacementX = 125;
	private static final int branchDisplacementY = 130;
	private static final int arrowGap = 5;
	
	public Loop(){
		super(0, 0, mainWidth, mainHeight);
		setNumChildren(numChildren);
		content = new Rectangle(0, 0, contentWidth, contentHeight);
		branch = new Rectangle(0, 0, mainWidth2, mainHeight2);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		condition.setOpaque(false);
		condition.setText("<Condition>");
		add(condition, BorderLayout.CENTER);
		update();
	}
	
	public Loop(int x, int y){
		super(x, y, mainWidth, mainHeight);
		setNumChildren(numChildren);
		content = new Rectangle(0, 0, contentWidth, contentHeight);
		branch = new Rectangle(0, 0, mainWidth2, mainHeight2);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		condition.setOpaque(false);
		condition.setText("<Condition>");
		add(condition, BorderLayout.CENTER);
		update();
	}
	
	public Loop(Color c){
		super(0, 0, mainWidth, mainHeight, c);
		setNumChildren(numChildren);
		content = new Rectangle(0, 0, contentWidth, contentHeight);
		branch = new Rectangle(0, 0, mainWidth2, mainHeight2);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		condition.setOpaque(false);
		condition.setText("<Condition>");
		add(condition, BorderLayout.CENTER);
		update();
	}
	
	public Loop(int x, int y, Color c){
		super(x, y, mainWidth, mainHeight, c);
		setNumChildren(numChildren);
		content = new Rectangle(0, 0, contentWidth, contentHeight);
		branch = new Rectangle(0, 0, mainWidth2, mainHeight2);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		condition.setOpaque(false);
		condition.setText("<Condition>");
		add(condition, BorderLayout.CENTER);
		update();
	}
	
	private void updateBoxes(){
		//sets branch location with respect to main position
		content.setLocation(position.x, position.y + branchDisplacementY);
		branch.setLocation(position.x + branchDisplacementX, position.y + branchDisplacementY);
		//checks if there is something in the second branch
		if(childrenIDs.size() > 1 && childrenIDs.get(1) != 0){
			//sets branch to encapsulate child
			branchVisible = false;
		}else{
			//sets branch visible
			branchVisible = true;
		}
		if(childrenIDs.size() > 0 && childrenIDs.get(0) != 0){
			//sets content to encapsulate child
			contentVisible = false;
			int childWidth = main.util.Controller.getMaxTreeWidth(childrenIDs.get(0));
			if(childWidth > branch.x - position.x){
				branch.x += childWidth - branchDisplacementX + 5;
			}
		}else{
			//sets content to visible
			contentVisible = true;
		}
	}
	
	@Override
	public void update(){
		this.setBounds(getOffset(position));
		updateBoxes();
	}
	
	//overrides getWidth to account for changes in branch sizes
	@Override
	public int getResizeWidth(){
		return branch.x - content.x + branch.width;
	}
	
	public void resetFamily(){
		parentID = 0;
		setNumChildren(numChildren);
	}
	
	//Overrides getNewSnap to account for branches
	@Override
	public Point getNewSnap(DraggableRect rect){
		//updates branches to adjust for child height
		updateBoxes();
		//if intersect main rect
		if (position.intersects(rect.getPosition()) && id != rect.id){
			//sets location to far right to avoid collisions
			return new Point(position.x + branchDisplacementX + branch.width + displacement, rect.getPosition().y);
		//if intersecting first branch
		}else if (content.intersects(rect.getPosition()) && !content.equals(rect) && childrenIDs.get(0) == 0){
    		//sets location of rectangle being dragged to below the rectangle it overlaps
    		return new Point(content.x, content.y);
    	//if intersecting second branch
    	}else if(branch.intersects(rect.getPosition()) && !branch.equals(rect) && childrenIDs.get(1) == 0){
    		//sets location of rectangle being dragged to below the rectangle it overlaps
    		return new Point(branch.x, branch.y);
    	}return null;
	}
	
	//overrides setChild function to set child under branch
	@Override
	public void setChild(DraggableRect rect){
		if(!position.equals(rect.getPosition())){
			if(content.intersects(rect.getPosition()) && childrenIDs.get(0) == 0){
				childrenIDs.set(0, rect.id);
			}else if(branch.intersects(rect.getPosition()) && childrenIDs.get(1) == 0){
				childrenIDs.set(1, rect.id);
			}
		}
	}
	
	@Override
	public void deleteChild(int id){
		super.deleteChild(id);
	}
	
	@Override
	public void checkHoverOver(DraggableRect r){
		try{
			//checks hovering over main
			if(position.intersects(r.getPosition()) && id != r.id){
				if(!objectsHoveringAbove.get(0)){
					objectsHoveringAbove.set(0, true);
				}
			}else{
				if(objectsHoveringAbove.get(0)){
					objectsHoveringAbove.set(0, false);
				}
			}
			//checks hovering over content
			if(content.intersects(r.getPosition()) && id != r.id){
				if(!objectsHoveringAbove.get(1)){
					objectsHoveringAbove.set(1, true);
				}
			}else{
				if(objectsHoveringAbove.get(1)){
					objectsHoveringAbove.set(1, false);
				}
			}
			//checks hovering over branch
			if(branch.intersects(r.getPosition()) && id != r.id){
				if(!objectsHoveringAbove.get(2)){
					objectsHoveringAbove.set(2, true);
				}
			}else{
				if(objectsHoveringAbove.get(2)){
					objectsHoveringAbove.set(2, false);
				}
			}
			//catches indexOutOfBounds exception
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	//draws arrows from main rectangle to branches
	private void drawArrows(Graphics2D g){
		//defines points
		int mainMidX = position.x + (position.width/2);
		int mainMidY = (int) (position.y + (0.75 * position.height));
		int mainBottom = position.y + position.height;
		int branchTop = position.y + branchDisplacementY;
		int mainRight = position.x + position.width;
		int branchMidX = branch.x + (branch.width/2);
		int branchMidY = (int) (branch.y + (0.25 * branch.height));
		int mainToBranchMidY = (branchTop + mainBottom)/2;
		int mainbranchMidX = (mainRight + branchMidX)/2;
		int contentRightX = content.x + content.height;
		int contentToBranchMidX = (contentRightX + branch.x)/2;
		int[] xPoints1 = {mainMidX - triangleSize, mainMidX + triangleSize, mainMidX};
		int[] yPoints1 = {branchTop - triangleSize, branchTop - triangleSize, branchTop};
		int[] xPoints2 = {branchMidX - triangleSize, branchMidX + triangleSize, branchMidX};
		int[] yPoints2 = {branchTop - triangleSize, branchTop - triangleSize, branchTop};
		int[] xPoints3 = {mainRight, mainRight + triangleSize, mainRight + triangleSize};
		int[] yPoints3 = {mainMidY + arrowGap, mainMidY - triangleSize + arrowGap, mainMidY + triangleSize + arrowGap};
		//draws lines for arrows
		g.drawLine(mainMidX, mainBottom, mainMidX, mainToBranchMidY - 10);
		g.drawLine(mainMidX, mainToBranchMidY + 10, mainMidX, branchTop);
		g.drawLine(mainRight, mainMidY - arrowGap, branchMidX, mainMidY - arrowGap);
		g.drawLine(branchMidX, mainMidY - arrowGap, branchMidX, branchTop);
		g.drawLine(contentRightX, branchMidY, contentToBranchMidX, branchMidY);
		g.drawLine(contentToBranchMidX, branchMidY, contentToBranchMidX, mainMidY + arrowGap);
		g.drawLine(contentToBranchMidX, mainMidY + arrowGap, mainRight, mainMidY + arrowGap);
		//draws triangles for arrows
		g.fillPolygon(xPoints1, yPoints1, 3);
		g.fillPolygon(xPoints2, yPoints2, 3);
		g.fillPolygon(xPoints3, yPoints3, 3);
		//draws labels for arrows
		g.setFont(new Font(Font.SANS_SERIF, 3, 18));
		g.drawString("False", mainbranchMidX - 23, mainMidY - arrowGap - 5);
		g.drawString("True", mainMidX - 20, mainToBranchMidY + 5);
	}
	
	public void draw(Graphics2D g){
		super.draw(g);
		update();
		if(contentVisible && objectsHoveringAbove.get(1)){
			g.setPaint(shadow);
			g.fill(content);
		}
		if(branchVisible && objectsHoveringAbove.get(2)){
			g.setPaint(shadow);
			g.fill(branch);
		}
		g.setPaint(c);
		if(contentVisible){
			g.draw(content);
		}
		if(branchVisible){
			g.draw(branch);
		}
		//draws decorations
		drawArrows(g);
	}
	
	@Override
	public int getType(){
		return 3;
	}
}
