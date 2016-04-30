package main.block;
import java.awt.*; 		 // import java.awt package
import java.util.ArrayList; // import java.util.ArrayList package

import javax.swing.*;

import main.util.Controller;	 // import javax.swing package

public class DraggableRect extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// class variables declarations
	public Rectangle position;
	protected static int numInstancesCreated = 0;
	protected static final int displacement = 20;
	protected static final Color shadow = new Color(255, 80, 10, 128);
	protected static final int triangleSize = 5;
	protected Color c;
	private static final int numChildren = 1;
	public ArrayList<Boolean> objectsHoveringAbove = new ArrayList<Boolean>();
	public boolean dragging;
	public Point offset = new Point();
	public int id;
	public int parentID = 0;
	public boolean internalRect = false;
	public ArrayList<Integer> childrenIDs;
	protected int type = 0;
	
	public String f1;
	public String f2;
	public String f3;

	
	// default constructor which initializes values
	public DraggableRect(){
		setNumChildren(numChildren);
		numInstancesCreated++;
		id = numInstancesCreated;
		position = new Rectangle();
		c = Color.BLACK;
		objectsHoveringAbove.add(false);
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setOpaque(false);
	}
			
	// override constructor which sets position values for rectangle
	public DraggableRect(int x, int y, int w, int h){
		setNumChildren(numChildren);
		numInstancesCreated++;
		id = numInstancesCreated;
		position = new Rectangle(x, y, w, h);
		c = Color.BLACK;
		objectsHoveringAbove.add(false);
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setOpaque(false);
	}
	
	// override constructor to set color as well
	public DraggableRect(int x, int y, int w, int h, Color color){
		setNumChildren(numChildren);
		numInstancesCreated++;
		id = numInstancesCreated;
		position = new Rectangle(x, y, w, h);
		c = color;
		objectsHoveringAbove.add(false);
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setOpaque(false);
	}	
	
	// getter function for position
	public Rectangle getPosition(){
		return position;
	}
	
	// mutator function to set position
	public void setPosition(int x, int y){
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
	public void update(){
		this.setBounds(getOffset(position));
	}
	
	//returns width requirement for blocks to avoid overlap conflicts
	public int getResizeWidth(){
		return position.width;
	}
	
	//returns height requirement for blocks to avoid overlap conflicts
	public int getResizeHeight(){
		return position.height;
	}
	
	//resets childrenIDs and sets the number of children
	protected void setNumChildren(int n){
		childrenIDs = new ArrayList<Integer>(n);
		for(int i=0; i<n; i++){
			childrenIDs.add(0);
		}
	}
	
	//gets offset between rect position and JPanel position
	protected Rectangle getOffset(Rectangle r){
		if(!Controller.fullscreen){
			return new Rectangle(r.x - 5, r.y - 50, r.width, r.height);
		}
		return new Rectangle(r.x, r.y - 45, r.width, r.height);
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
	
	//records child under ChildrenIDs
	public void setChild(DraggableRect r){
		if(!childrenIDs.contains(r.id) && r.type != 5){
			if(childrenIDs.size() > 0){
				childrenIDs.set(0, r.id);
			}
		}
	}
	
	//returns child of highest index of childrenIDs
	public int getLastChild(){
		int id;
		for(int i=childrenIDs.size(); i>0; i--){
			id = childrenIDs.get(i-1);
			if(id != 0){
				return id;
			}
		}
		return 0;
	}
	
	//returns rightmost point for loops to refer to
	public Point getRightPoint(){
		return new Point(position.x + position.width, (int) (position.y + (0.25 * position.height)));
	}
	
	//function to return new position to snap to during overlap
	public Point getNewSnap(DraggableRect rect){
		//if rectangles intersect and are not the same
		if (position.intersects(rect.getPosition()) && id != rect.id){
    		//sets location of rectangle being dragged to below the rectangle it overlaps
    		return new Point(position.x, position.y + position.height + displacement);
    		
		}return null;
	}
	
	//displays shadows when child rect is hovering over it
	public void checkHoverOver(DraggableRect r){
		try{
			if(position.intersects(r.getPosition()) && id != r.id){
				if(!objectsHoveringAbove.get(0)){
					objectsHoveringAbove.set(0, true);
				}
			}else{
				if(objectsHoveringAbove.get(0)){
					objectsHoveringAbove.set(0, false);
				}
			}
		//catches indexOutOfBounds exception
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	//sets everything in objectHoveringAbove to false
	public void setHoveringFalse(){
		for(int i=0; i<objectsHoveringAbove.size(); i++){
			objectsHoveringAbove.set(i, false);
		}
	}
	
	// draw function for DraggableRect onto Graphics2D g
	public void draw(Graphics2D g, boolean fullscreen){
		g.setPaint(c);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);		// call setRenderingHint method
		if(objectsHoveringAbove.get(0)){
			g.setPaint(shadow);
			if(!fullscreen){
				g.fill(position);
			}else{
				g.fillRect(position.x - 5, position.y - 4, position.width, position.height);
			}
		}
	}
	
	//redraws the border using the graphics object
	public void drawBorder(Graphics2D g, int dx, int dy){
		g.setPaint(c);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);		// call setRenderingHint method
		g.drawRect(getBounds().x + dx, getBounds().y + dy, getBounds().width - 1, getBounds().height - 1);
	}
	
	// returns integer to identify block type
	public int getType(){
		return type;
	}
	
	//returns true if the draggableRect has children
	public boolean hasChildren(){
		for(int n : childrenIDs){
			if(n != 0){
				return true;
			}
		}return false;
	}
	
	public void drawArrows(Graphics2D g){}
}