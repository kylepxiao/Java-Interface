import java.awt.*;

public class Conditional extends DraggableRect{

	private static final long serialVersionUID = 1L;
	
	//Stores information about position of branches and children
	private Rectangle branch1;
	private Rectangle branch2;
	private static final int numChildren = 2;
	
	//Strings used to write to java file
	public static final String codeEncap1 = "if(";
	public static final String codeEncap2 = "){";
	public static final String codeEncap3 = "}else{";
	public static final String codeEncap4 = "}";
	
	//default dimensions of conditional object
	private static final int mainWidth = 75;
	private static final int mainHeight = 75;
	private static final int branchWidth = 75;
	private static final int branchHeight = 50;
	private static final int branchDisplacementX = 100;
	private static final int branchDisplacementY = 100;
	private static final int triangleSize = 5;
	
	//default constructor
	public Conditional(){
		super(0, 0, mainWidth, mainHeight);
		setNumChildren(numChildren);
		branch1 = new Rectangle(0, 0, branchWidth, branchHeight);
		branch2 = new Rectangle(0, 0, branchWidth, branchHeight);
		updateBranches();
	}
	
	//override constructor to specify position
	public Conditional(int x, int y){
		super(x, y, mainWidth, mainHeight);
		setNumChildren(numChildren);
		branch1 = new Rectangle(0, 0, branchWidth, branchHeight);
		branch2 = new Rectangle(0, 0, branchWidth, branchHeight);
		updateBranches();
	}
	
	//override constructor to specify color
	public Conditional(Color c){
		super(0, 0, mainWidth, mainHeight, c);
		setNumChildren(numChildren);
		branch1 = new Rectangle(0, 0, branchWidth, branchHeight);
		branch2 = new Rectangle(branchWidth, branchHeight);
		updateBranches();
	}
	
	//override constructor to specify position and color
	public Conditional(int x, int y, Color c){
		super(x, y, mainWidth, mainHeight, c);
		setNumChildren(numChildren);
		branch1 = new Rectangle(0, 0, branchWidth, branchHeight);
		branch2 = new Rectangle(0, 0, branchWidth, branchHeight);
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
			int h = Controller.getRectByID(childrenIDs.get(1)).getPosition().height;
			branch2.height = h + (2*displacement);
		}else{
			//sets branch2 height to default
			branch2.height = branchHeight;
		}
		if(childrenIDs.size() > 0 && childrenIDs.get(0) != 0){
			//sets branch1 to encapsulate child
			int h = Controller.getRectByID(childrenIDs.get(0)).getPosition().height;
			branch1.height = h + (2*displacement);
		}else{
			//sets branch1 height to default
			branch1.height = branchHeight;
		}
	}
	
	//overrides update to account for branches
	@Override
	public void update(){
		updateBranches();
	}
	
	//override resetFamily to account for different number of children
	@Override
	public void resetFamily(){
		parentID = 0;
		setNumChildren(numChildren);
	}
	
	//Overrides getNewSnap to account for branches
	@Override
	public Point getNewSnap(Rectangle rect){
		//updates branches to adjust for child height
		updateBranches();
		//if intersect main rect
		if (position.intersects(rect) && !position.equals(rect)){
			//sets location to far right to avoid collisions
			return new Point(position.x + branchDisplacementX + branch2.width + displacement, rect.y);
		//if intersecting first branch
		}else if (branch1.intersects(rect) && !branch1.equals(rect)){
    		//sets location of rectangle being dragged to below the rectangle it overlaps
    		return new Point(branch1.x, branch1.y + displacement);
    	//if intersecting second branch
    	}else if(branch2.intersects(rect) && !branch2.equals(rect)){
    		//sets location of rectangle being dragged to below the rectangle it overlaps
    		return new Point(branch2.x, branch2.y + displacement);
    	}return null;
	}
	
	//overrides setChild function to set child under branch
	@Override
	public void setChild(DraggableRect rect){
		if(!position.equals(rect.getPosition())){
			if(branch1.intersects(rect.getPosition())){
				childrenIDs.set(0, rect.id);
			}else if(branch2.intersects(rect.getPosition())){
				childrenIDs.set(1, rect.id);
			}
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
		int[] xPoints1 = {mainMidX - triangleSize, mainMidX + triangleSize, mainMidX};
		int[] yPoints1 = {branchTop - triangleSize, branchTop - triangleSize, branchTop};
		int[] xPoints2 = {branch2MidX - triangleSize, branch2MidX + triangleSize, branch2MidX};
		int[] yPoints2 = {branchTop - triangleSize, branchTop - triangleSize, branchTop};
		//draws lines for arrows
		g.drawLine(mainMidX, mainBottom, mainMidX, branchTop);
		g.drawLine(mainRight, mainMidY, branch2MidX, mainMidY);
		g.drawLine(branch2MidX, mainMidY, branch2MidX, branchTop);
		//draws triangles for arrows
		g.fillPolygon(xPoints1, yPoints1, 3);
		g.fillPolygon(xPoints2, yPoints2, 3);
		
	}
	
	//overrides draw function to include branches
	@Override
	public void draw(Graphics2D g){
		super.draw(g);
		g.draw(branch1);
		g.draw(branch2);
		drawArrows(g);
	}
	
}
