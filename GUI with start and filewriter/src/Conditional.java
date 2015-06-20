import java.awt.*;

public class Conditional extends DraggableRect{

	private static final long serialVersionUID = 1L;
	
	//Stores information about position of branches and children
	private Rectangle branch1;
	private Rectangle branch2;
	private boolean branch1Visible = true;
	private boolean branch2Visible = true;
	private static final int numChildren = 2;
	
	//Strings used to write to java file
	public static final String codeEncap1 = "if(";
	public static final String codeEncap2 = "){";
	public static final String codeEncap3 = "}";
	
	//default dimensions of conditional object
	private static final int mainWidth = 75;
	private static final int mainHeight = 75;
	private static final int branchWidth = 75;
	private static final int branchHeight = 50;
	private static final int branchDisplacementX = 100;
	private static final int branchDisplacementY = 130;
	
	//default constructor
	public Conditional(){
		super(0, 0, mainWidth, mainHeight);
		setNumChildren(numChildren);
		branch1 = new Rectangle(0, 0, branchWidth, branchHeight);
		branch2 = new Rectangle(0, 0, branchWidth, branchHeight);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		updateBranches();
	}
	
	//override constructor to specify position
	public Conditional(int x, int y){
		super(x, y, mainWidth, mainHeight);
		setNumChildren(numChildren);
		branch1 = new Rectangle(0, 0, branchWidth, branchHeight);
		branch2 = new Rectangle(0, 0, branchWidth, branchHeight);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		updateBranches();
	}
	
	//override constructor to specify color
	public Conditional(Color c){
		super(0, 0, mainWidth, mainHeight, c);
		setNumChildren(numChildren);
		branch1 = new Rectangle(0, 0, branchWidth, branchHeight);
		branch2 = new Rectangle(branchWidth, branchHeight);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		updateBranches();
	}
	
	//override constructor to specify position and color
	public Conditional(int x, int y, Color c){
		super(x, y, mainWidth, mainHeight, c);
		setNumChildren(numChildren);
		branch1 = new Rectangle(0, 0, branchWidth, branchHeight);
		branch2 = new Rectangle(0, 0, branchWidth, branchHeight);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		updateBranches();
	}
	
	//calculates position of branches and updates dimensions based on children
	private void updateBranches(){
		//sets branch location with respect to main position
		branch1.setLocation(position.x, position.y + branchDisplacementY);
		branch2.setLocation(position.x + branchDisplacementX, position.y + branchDisplacementY);
		//checks if there is something in the second branch
		if(childrenIDs.size() > 1 && childrenIDs.get(1) != 0){
			//sets branch2 to encapsulate child
			branch2Visible = false;
		}else{
			//sets branch2 visible
			branch2Visible = true;
		}
		if(childrenIDs.size() > 0 && childrenIDs.get(0) != 0){
			//sets branch1 to encapsulate child
			branch1Visible = false;
			int childWidth = Controller.getRectByID(childrenIDs.get(0)).getWidth();
			if(childWidth > branch2.x - position.x){
				branch2.x += childWidth - branchDisplacementX + 5;
			}
		}else{
			//sets branch1 to visible
			branch1Visible = true;
		}
	}
	
	//overrides update to account for branches
	@Override
	public void update(){
		updateBranches();
	}
	
	//overrides getWidth to account for changes in branch sizes
	@Override
	public int getWidth(){
		return branch2.x - branch1.x + branch2.width;
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
		//if intersect main rect
		if (position.intersects(rect.getPosition()) && id != rect.id){
			//sets location to far right to avoid collisions
			return new Point(position.x + branchDisplacementX + branch2.width + displacement, rect.getPosition().y);
		//if intersecting first branch
		}else if (branch1.intersects(rect.getPosition()) && !branch1.equals(rect)){
    		//sets location of rectangle being dragged to below the rectangle it overlaps
    		return new Point(branch1.x, branch1.y);
    	//if intersecting second branch
    	}else if(branch2.intersects(rect.getPosition()) && !branch2.equals(rect)){
    		//sets location of rectangle being dragged to below the rectangle it overlaps
    		return new Point(branch2.x, branch2.y);
    	}return null;
	}
	
	//overrides setChild function to set child under branch
	@Override
	public void setChild(DraggableRect rect){
		if(!position.equals(rect.getPosition())){
			if(branch1.intersects(rect.getPosition()) && childrenIDs.get(0) == 0){
				childrenIDs.set(0, rect.id);
			}else if(branch2.intersects(rect.getPosition()) && childrenIDs.get(1) == 0){
				childrenIDs.set(1, rect.id);
			}
		}
	}
	
	//overrides deleteChild to retract the branches for missing child
	@Override
	public void deleteChild(int id){
		super.deleteChild(id);
	}
	
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
	private void drawArrows(Graphics2D g){
		//defines points
		int mainMidX = position.x + (position.width/2);
		int mainMidY = position.y + (position.height/2);
		int mainBottom = position.y + position.height;
		int branchTop = position.y + branchDisplacementY;
		int mainRight = position.x + position.width;
		int branch2MidX = branch2.x + (branch2.width/2);
		int mainToBranchMidY = (branchTop + mainBottom)/2;
		int mainBranch2MidX = (mainRight + branch2MidX)/2;
		int[] xPoints1 = {mainMidX - triangleSize, mainMidX + triangleSize, mainMidX};
		int[] yPoints1 = {branchTop - triangleSize, branchTop - triangleSize, branchTop};
		int[] xPoints2 = {branch2MidX - triangleSize, branch2MidX + triangleSize, branch2MidX};
		int[] yPoints2 = {branchTop - triangleSize, branchTop - triangleSize, branchTop};
		//draws lines for arrows
		g.drawLine(mainMidX, mainBottom, mainMidX, mainToBranchMidY - 10);
		g.drawLine(mainMidX, mainToBranchMidY + 10, mainMidX, branchTop);
		g.drawLine(mainRight, mainMidY, branch2MidX, mainMidY);
		g.drawLine(branch2MidX, mainMidY, branch2MidX, branchTop);
		//draws triangles for arrows
		g.fillPolygon(xPoints1, yPoints1, 3);
		g.fillPolygon(xPoints2, yPoints2, 3);
		//draws labels for arrows
		g.setFont(new Font(Font.SANS_SERIF, 3, 18));
		g.drawString("False", mainBranch2MidX - 20, mainMidY - 5);
		g.drawString("True", mainMidX - 20, mainToBranchMidY + 5);
	}
	
	//overrides draw function to include branches
	@Override
	public void draw(Graphics2D g){
		super.draw(g);
		updateBranches();
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
	
	@Override
	public int getType(){
		return 2;
	}
	
}
