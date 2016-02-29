package main.block;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.util.Controller;

public class Loop extends DraggableRect {

	private static final long serialVersionUID = 1L;
	
	private Rectangle content;
	private Rectangle branch;
	private boolean contentVisible = true;
	private boolean branchVisible = true;
	private static final int numChildren = 3;
	private JTextArea condition = new JTextArea();
	
	private static final int mainWidth = 130;
	private static final int mainHeight = 76;
	private static final int contentWidth = 75;
	private static final int contentHeight = 75;
	private static final int mainWidth2= 75;
	private static final int mainHeight2 = 75;
	private static final int branchDisplacementX = 150;
	private static final int branchDisplacementY = 130;
	private static final int arrowGap = 5;
	
	private static final int subWidth = 20;
	private static final int subHeight = 25;
	private static final int subDisplacementX = 32;
	
	private JTextField t1 = new JTextField();
	private JTextField t2 = new JTextField();
	
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
		type = 4;
	}
	
	public Loop(int x, int y){
		super(x, y, mainWidth, mainHeight);
		this.setBorder(null);
		this.setLayout(null);
		setNumChildren(numChildren);
		content = new Rectangle(0, 0, contentWidth, contentHeight);
		branch = new Rectangle(0, 0, mainWidth2, mainHeight2);
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
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		condition.setOpaque(false);
		condition.setText("<Condition>");
		add(condition, BorderLayout.CENTER);
		update();
		type = 4;
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
		type = 4;
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
		type = 4;
	}
	
	//returns point to the rightmost bottom portion of the while content area
	private Point getTreePoint(DraggableRect r){
		int childID = r.childrenIDs.get(r.childrenIDs.size()-1);
		if(childID != 0){
			return getTreePoint(Controller.getRectByID(childID));
		}
		return r.getRightPoint();
	}
	
	private void updateBoxes(){
		//sets branch location with respect to main position
		content.setLocation(position.x+mainWidth/2-contentWidth/2, position.y + branchDisplacementY);
		branch.setLocation(position.x + branchDisplacementX, position.y + branchDisplacementY);
		if(childrenIDs.size() > 0 && childrenIDs.get(0) != 0){
			//sets content to encapsulate child
			contentVisible = false;
			int childX = Controller.getMaxTreeX(childrenIDs.get(0));
			if(childX > branch.x){
				branch.x = childX + 20;
			}
		}else{
			//sets content to visible
			contentVisible = true;
		}
		//checks if there is something in the second branch
		if(childrenIDs.size() > 1 && childrenIDs.get(1) != 0){
			//sets branch to encapsulate child
			branchVisible = false;
			Controller.setTreeLocation(Controller.getRectByID(childrenIDs.get(1)), branch.x, branch.y);
		}else{
			//sets branch visible
			branchVisible = true;
		}
		//expands branch if condition is in place
		if(childrenIDs.size() > 2 && childrenIDs.get(2) != 0){
			if(branch.x < position.x + branchDisplacementX + 30){
				branch.x = position.x + branchDisplacementX + 30;
			}
			if(childrenIDs.get(1) != 0){
				Controller.setTreeLocation(Controller.getRectByID(childrenIDs.get(1)), branch.x, branch.y);
			}
		}
	}
	
	@Override
	public void update(){
//		this.setBounds(getOffset(position));
		super.update();
		updateBoxes();
		if(internalRect){
			this.setVisible(false);
		}else{
			this.setVisible(true);
		}
	}
	
	//overrides getWidth to account for changes in branch sizes
	@Override
	public int getResizeWidth(){
		return branch.x - content.x + branch.width;
	}
	
	//overrides getHeight to account for changes in branch sizes
	@Override
	public int getResizeHeight(){
		return branchDisplacementY;
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
		if(!childrenIDs.contains(rect.id)){
			//if intersect main rect
			if (position.intersects(rect.getPosition()) && id != rect.id){
				if(rect.getType() == 4){
					return new Point(position.x-35, position.y+3);
				}
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
	    	}
		}else{
			if(childrenIDs.get(0) == rect.id){
				return new Point(content.x, content.y);
			}else if(childrenIDs.get(1) == rect.id){
				return new Point(branch.x, branch.y);
			}
		}
		return null;
	}
	
	//overrides setChild function to set child under branch
	@Override
	public void setChild(DraggableRect rect){
		if(!position.equals(rect.getPosition())){
			if(content.intersects(rect.getPosition()) && childrenIDs.get(0) == 0){
				childrenIDs.set(0, rect.id);
			}else if(branch.intersects(rect.getPosition()) && childrenIDs.get(1) == 0){
				childrenIDs.set(1, rect.id);
			}else if(position.intersects(rect.getPosition()) && childrenIDs.get(2) == 0 && rect.getType() == 4){
				childrenIDs.set(2, rect.id);
				internalRect = true;
			}
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
	
	//sets right point to right branch
	@Override
	public Point getRightPoint(){
		return new Point(branch.x + branch.width, (int) (branch.y + (0.25 * branch.height)));
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
	public void drawArrows(Graphics2D g){
		Point p = new Point();
		Boolean hasContent = this.childrenIDs.get(0) != 0;
		if(hasContent){
			p = getTreePoint(Controller.getRectByID(this.childrenIDs.get(0)));
		}
		//defines points
		int mainMidX = position.x + (position.width/2);
		int mainMidY = position.y + position.height/2-9;
		int mainBottom = position.y + position.height-9;
		int branchTop = position.y + branchDisplacementY;
		int mainRight = position.x + position.width;
		int branchMidX = branch.x + (branch.width/2);
		int mainToBranchMidY = (branchTop + mainBottom)/2;
		int mainbranchMidX = (mainRight + branchMidX)/2;
		int branchMidY;
		int contentRightX;
		int contentToBranchMidX;
		int mainTop = position.y -9;
		int[] xPoints = {position.x, mainMidX, mainRight, mainMidX};
		int[] yPoints = {mainMidY, mainTop, mainMidY, mainBottom};
		if(hasContent){
			branchMidY = p.y;
			contentRightX = p.x;
			contentToBranchMidX = branch.x - 10;
		}else{
			branchMidY = (int) (branch.y + (0.25 * branch.height));
			contentRightX = content.x + content.width;
			contentToBranchMidX = (contentRightX + branch.x)/2;
		}
		int[] xPoints1 = {mainMidX - triangleSize, mainMidX + triangleSize, mainMidX};
		int[] yPoints1 = {branchTop - triangleSize, branchTop - triangleSize, branchTop};
		int[] xPoints2 = {branchMidX - triangleSize, branchMidX + triangleSize, branchMidX};
		int[] yPoints2 = {branchTop - triangleSize, branchTop - triangleSize, branchTop};
		int[] xPoints3 = {mainRight-30, mainRight + triangleSize-30, mainRight + triangleSize-30};
		int[] yPoints3 = {mainMidY+40 , mainMidY - triangleSize+40, mainMidY + triangleSize+40};
		//draws lines for arrows
		g.drawLine(mainMidX, mainBottom+9, mainMidX, mainToBranchMidY - 10);
		g.drawLine(mainMidX, mainToBranchMidY + 10, mainMidX, branchTop);
		g.drawLine(mainRight, mainMidY +9, branchMidX, mainMidY+9);
		g.drawLine(branchMidX, mainMidY+9, branchMidX, branchTop);
		g.drawLine(contentRightX, branchMidY, contentToBranchMidX, branchMidY);
		g.drawLine(contentToBranchMidX, branchMidY, contentToBranchMidX, mainMidY+40);
		g.drawLine(contentToBranchMidX, mainMidY+40, mainRight-30, mainMidY+40);
		//draws triangles for arrows
		g.fillPolygon(xPoints1, yPoints1, 3);
		g.fillPolygon(xPoints2, yPoints2, 3);
		g.fillPolygon(xPoints3, yPoints3, 3);
		//draws labels for arrows
		g.setFont(new Font(Font.SANS_SERIF, 3, 18));
		g.drawString("False", mainbranchMidX - 23, mainMidY - 5);
		g.drawString("True", mainMidX - 20, mainToBranchMidY + 5);
		

		g.drawPolygon(getOffsetPointsX(xPoints), getOffsetPointsY(yPoints), 4);
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
}