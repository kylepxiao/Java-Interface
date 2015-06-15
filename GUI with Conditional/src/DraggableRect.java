import java.awt.*; 		 // import java.awt package
import java.util.ArrayList; // import java.util.ArrayList package
import javax.swing.*;	 // import javax.swing package

public class DraggableRect extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// class variables declarations
	protected Rectangle position;
	protected static int numInstancesCreated = 0;
	protected static final int displacement = 5;
	private Color c;
	private static final int numChildren = 1;
	public boolean dragging;
	public Point offset = new Point();
	public int id;
	public int parentID = 0;
	public ArrayList<Integer> childrenIDs;
	
	// default constructor which initializes values
	public DraggableRect(){
		setNumChildren(numChildren);
		numInstancesCreated++;
		id = numInstancesCreated;
		position = new Rectangle();
		c = Color.BLACK;
	}
			
	// override constructor which sets position values for rectangle
	public DraggableRect(int x, int y, int w, int h){
		setNumChildren(numChildren);
		numInstancesCreated++;
		id = numInstancesCreated;
		position = new Rectangle(x, y, w, h);
		c = Color.BLACK;
	}
	
	// override constructor to set color as well
	public DraggableRect(int x, int y, int w, int h, Color color){
		setNumChildren(numChildren);
		numInstancesCreated++;
		id = numInstancesCreated;
		position = new Rectangle(x, y, w, h);
		c = color;
	}
	
	// getter function for position
	public Rectangle getPosition(){
		return position;
	}
	
	// mutator function to set position
	public void setLocation(int x, int y){
		position.setLocation(x, y);
	}
	
	// function to set width and height for position
	public void setPosition(int x, int y, int w, int h){
		position = new Rectangle(x, y, w, h);
	}
	
	// mutator function to set position based on another Rectangle
	public void setPosition(Rectangle r){
		position = r;
	}
	
	// changes position by a set amount
	public void move(int x, int y){
		position.x += x;
		position.y += y;
	}
	
	// mutator function for color
	public void setColor(Color color){
		c = color;
	}
	
	// getter function for color
	public Color getColor(){
		return c;
	}
	
	//function that updates DraggableRect for child classes
	public void update(){}
	
	//resets childrenIDs and sets the number of children
	protected void setNumChildren(int n){
		childrenIDs = new ArrayList<Integer>(n);
		for(int i=0; i<n; i++){
			childrenIDs.add(0);
		}
	}
	
	//sets parentID to 0 and clears childrenIDs
	public void resetFamily(){
		parentID = 0;
		setNumChildren(numChildren);
	}
	
	//deletes child by id
	public void deleteChild(int id){
		for(int i=0; i<childrenIDs.size(); i++){
			if(childrenIDs.get(i) == id){
				childrenIDs.set(i, 0);
				break;
			}
		}
	}
	
	public void setChild(DraggableRect r){
		if(!childrenIDs.contains(r.id)){
			childrenIDs.set(0, r.id);
		}
	}
	
	//function to return new position to snap to during overlap
	public Point getNewSnap(Rectangle rect){
		//if rectangles intersect and are not the same
		if (position.intersects(rect) && !position.equals(rect)){
    		//sets location of rectangle being dragged to below the rectangle it overlaps
    		return new Point(position.x, position.y + position.height + displacement);
    	}return null;
	}
	
	// draw function for DraggableRect onto Graphics2D g
	public void draw(Graphics2D g){
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);		// call setRenderingHint method
		g.setPaint(c);
		g.draw(position);
	}
}
