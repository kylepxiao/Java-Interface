package main.block;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.util.Controller;

public class Conditional extends DraggableRect{

	private static final long serialVersionUID = 1L;
	
	//Stores information about position of branches and children
	private Rectangle branch1;
	private Rectangle branch2;
	private boolean branch1Visible = true;
	private boolean branch2Visible = true;
	private static final int numChildren = 2;
	private JTextArea condition = new JTextArea();
	
	//Strings used to write to java file
	public static final String codeEncap1 = "if(";
	public static final String codeEncap2 = "){";
	public static final String codeEncap3 = "}";
	
	//default dimensions of conditional object
	private static final int mainWidth = 130;
	private static final int mainHeight = 76;
	private static final int branchWidth = 75;
	private static final int branchHeight = 50;
	private static final int branchDisplacementX = 130;
	private static final int branchDisplacementY = 130;
	
	private static final int subWidth = 20;
	private static final int subHeight = 25;
	private static final int subDisplacementX = 32;
	
	private JTextField t1 = new JTextField();
	private JTextField t2 = new JTextField();
	
	//default constructor
	public Conditional(){
		super(0, 0, mainWidth, mainHeight);
		setNumChildren(numChildren);
		branch1 = new Rectangle(0, 0, branchWidth, branchHeight);
		branch2 = new Rectangle(0, 0, branchWidth, branchHeight);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		condition.setOpaque(false);
		condition.setText("<Condition>");
		add(condition, BorderLayout.CENTER);
		update();
		type = 3;
	}
	
	//override constructor to specify position
	public Conditional(int x, int y){
		super(x, y, mainWidth, mainHeight);
		this.setBorder(null);
		this.setLayout(null);
		setNumChildren(numChildren);
		branch1 = new Rectangle(0, 0, branchWidth, branchHeight);
		branch2 = new Rectangle(0, 0, branchWidth, branchHeight);
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
		type = 3;
	}
	
	//override constructor to specify color
	public Conditional(Color c){
		super(0, 0, mainWidth, mainHeight, c);
		setNumChildren(numChildren);
		branch1 = new Rectangle(0, 0, branchWidth, branchHeight);
		branch2 = new Rectangle(branchWidth, branchHeight);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		condition.setOpaque(false);
		condition.setText("<Condition>");
		add(condition, BorderLayout.CENTER);
		update();
		type = 3;
	}
	
	//override constructor to specify position and color
	public Conditional(int x, int y, Color c){
		super(x, y, mainWidth, mainHeight, c);
		setNumChildren(numChildren);
		branch1 = new Rectangle(0, 0, branchWidth, branchHeight);
		branch2 = new Rectangle(0, 0, branchWidth, branchHeight);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		condition.setOpaque(false);
		condition.setText("<Condition>");
		add(condition, BorderLayout.CENTER);
		update();
		type = 3;
	}
	
	//calculates position of branches and updates dimensions based on children
	private void updateBranches(){
		//sets branch location with respect to main position
		branch1.setLocation(position.x+mainWidth/2-branchWidth/2, position.y + branchDisplacementY);
		branch2.setLocation(position.x + branchDisplacementX, position.y + branchDisplacementY);
		if(childrenIDs.size() > 0 && childrenIDs.get(0) != 0){
			//sets branch1 to encapsulate child
			branch1Visible = false;
			int childWidth = main.util.Controller.getRectByID(childrenIDs.get(0)).getResizeWidth();
			if(childWidth > branch2.x - position.x){
				branch2.x += childWidth - branchDisplacementX + 5;
			}
		}else{
			//sets branch1 to visible
			branch1Visible = true;
		}
		//checks if there is something in the second branch
		if(childrenIDs.size() > 1 && childrenIDs.get(1) != 0){
			//sets branch2 to encapsulate child
			branch2Visible = false;
			Controller.setTreeLocation(Controller.getRectByID(childrenIDs.get(1)), branch2.x, branch2.y);
		}else{
			//sets branch2 visible
			branch2Visible = true;
		}
		//expands branch if condition is in place
		if(childrenIDs.size() > 2 && childrenIDs.get(2) != 0){
			if(branch2.x < position.x + branchDisplacementX + 30){
				branch2.x = position.x + branchDisplacementX + 30;
			}
			if(childrenIDs.get(1) != 0){
				Controller.setTreeLocation(Controller.getRectByID(childrenIDs.get(1)), branch2.x, branch2.y);
			}
		}
	}
	
	//overrides update to account for branches
	@Override
	public void update(){
//		this.setBounds(getOffset(position));
		super.update();
		updateBranches();
	}
	
	//overrides getWidth to account for changes in branch sizes
	@Override
	public int getResizeWidth(){
		return branch2.x - branch1.x + branch2.width +45;
	}
	
	//overrides getHeight to account for changes in branch sizes
	@Override
	public int getResizeHeight(){
		return branchDisplacementY;
	}
	
	//override resetFamily to account for different number of children
	@Override
	public void resetFamily(){
		parentID = 0;
		setNumChildren(numChildren);
	}
	
	//Overrides getNewSnap to account for branches
	@Override
	public Point getNewSnap(DraggableRect rect){
		//updates branches to adjust for child height
		updateBranches();
		if(!childrenIDs.contains(rect.id)){
			//if intersect main rect
			if (position.intersects(rect.getPosition()) && id != rect.id){
				if(rect.getType() == 4){
					return new Point(position.x-35, position.y+3);
				}
				//sets location to far right to avoid collisions
				return new Point(position.x + branchDisplacementX + branch2.width + displacement, rect.getPosition().y);
			//if intersecting first branch
			}else if (branch1.intersects(rect.getPosition()) && !branch1.equals(rect) && childrenIDs.get(0) == 0){
	    		//sets location of rectangle being dragged to below the rectangle it overlaps
				return new Point(branch1.x, branch1.y);
	    	//if intersecting second branch
	    	}else if(branch2.intersects(rect.getPosition()) && !branch2.equals(rect) && childrenIDs.get(1) == 0){
	    		//sets location of rectangle being dragged to below the rectangle it overlaps
	    		return new Point(branch2.x, branch2.y);
	    	}
		}else{
			if(childrenIDs.get(0) == rect.id){
				return new Point(branch1.x, branch1.y);
			}else if(childrenIDs.get(1) == rect.id){
				return new Point(branch2.x, branch2.y);
			}
		}
		return null;
	}
	
	//overrides setChild function to set child under branch
	@Override
	public void setChild(DraggableRect rect){
		if(!position.equals(rect.getPosition()) && !childrenIDs.contains(rect.id)){
			if(branch1.intersects(rect.getPosition()) && childrenIDs.get(0) == 0){
				childrenIDs.set(0, rect.id);
			}else if(branch2.intersects(rect.getPosition()) && childrenIDs.get(1) == 0){
				childrenIDs.set(1, rect.id);
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
		return new Point(branch2.x + branch2.width, (int) (branch2.y + (0.25 * branch2.height)));
	}
	
	//overrides deleteChild to retract the branches for missing child
/*	@Override
	public void deleteChild(int id){
		int deletedChild = -1;
		for(int i=0; i<childrenIDs.size(); i++){
			if(childrenIDs.get(i) == id){
				childrenIDs.set(i, 0);
				deletedChild = i;
				break;
			}
		}
		if(deletedChild == 0){
			updateBranches();
		}
	}*/
	
	//overrides checkHoverOver to account for branches
	@Override
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
			//checks hovering over branch1
			if(branch1.intersects(r.getPosition()) && id != r.id){
				if(!objectsHoveringAbove.get(1)){
					objectsHoveringAbove.set(1, true);
				}
			}else{
				if(objectsHoveringAbove.get(1)){
					objectsHoveringAbove.set(1, false);
				}
			}
			//checks hovering over branch2
			if(branch2.intersects(r.getPosition()) && id != r.id){
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
	@Override
	public void drawArrows(Graphics2D g){
		//defines points
		int mainMidX = position.x + position.width/2;
		int mainMidY = position.y + position.height/2-9;
		int mainRight = position.x + position.width;
		//int mainMidX = position.x + (position.width/2);
		//int mainMidY = (int) (position.y + (0.75 * position.height));
		int mainBottom = position.y + position.height-9;
		int branchTop = position.y + branchDisplacementY;
		//int mainRight = position.x + position.width;
		int branch2MidX = branch2.x + (branch2.width/2);
		int mainToBranchMidY = (branchTop + mainBottom)/2;
		int mainBranch2MidX = (mainRight + branch2MidX)/2;
		int[] xPoints1 = {mainMidX - triangleSize, mainMidX + triangleSize, mainMidX};
		int[] yPoints1 = {branchTop - triangleSize, branchTop - triangleSize, branchTop};
		int[] xPoints2 = {branch2MidX - triangleSize, branch2MidX + triangleSize, branch2MidX};
		int[] yPoints2 = {branchTop - triangleSize, branchTop - triangleSize, branchTop};
		
		
	
		
		int mainTop = position.y -9;
		
		//int mainBottom = position.y + position.height - 9;
		
		int[] xPoints = {position.x, mainMidX, mainRight, mainMidX};
		int[] yPoints = {mainMidY, mainTop, mainMidY, mainBottom};

		g.drawPolygon(getOffsetPointsX(xPoints), getOffsetPointsY(yPoints), 4);
		//draws lines for arrows
		g.drawLine(mainMidX, mainBottom+9, mainMidX, mainToBranchMidY - 10);
		g.drawLine(mainMidX, mainToBranchMidY + 10, mainMidX, branchTop);
		g.drawLine(mainRight, mainMidY+9, branch2MidX, mainMidY+9);
		g.drawLine(branch2MidX, mainMidY+9, branch2MidX, branchTop);
		//draws triangles for arrows
		g.fillPolygon(xPoints1, yPoints1, 3);
		g.fillPolygon(xPoints2, yPoints2, 3);
		//draws labels for arrows
		g.setFont(new Font(Font.SANS_SERIF, 3, 18));
		g.drawString("False", mainBranch2MidX - 23, mainMidY - 4);
		g.drawString("True", mainMidX - 20, mainToBranchMidY + 5);
	}
	
	//overrides draw function to include branches
	@Override
	public void draw(Graphics2D g){
		super.draw(g);
		update();
		//fills in shadows for hovering
		if(branch1Visible && objectsHoveringAbove.get(1)){
			g.setPaint(shadow);
			g.fill(branch1);
		}
		if(branch2Visible && objectsHoveringAbove.get(2)){
			g.setPaint(shadow);
			g.fill(branch2);
		}
		//draws branches if there is not a child occupying its space
		g.setPaint(c);
		if(branch1Visible){
			g.draw(branch1);
		}
		if(branch2Visible){
			g.draw(branch2);
		}
		//draws decorations
		drawArrows(g);
	}
}