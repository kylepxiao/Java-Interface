package main.util;
import java.awt.*; 		 // import java.awt package
import java.awt.event.*; // import java.awt.event package
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList; // import ArrayList

import main.block.Switch;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.MouseInputAdapter; // import javax.swing.event.MouseTinputAdapter

import main.GUI;
import main.block.DraggableRect;

public class Controller extends MouseInputAdapter { 	// class DragController that extends MouseInputAdapter
		
	//class variables
	private static ArrayList<DraggableRect> rects;
	private static ArrayList<Integer> ClickNum = new ArrayList<Integer>();
	private static File file = new File("test.java");
	public static boolean newMouseStroke = true;
	public static boolean fullscreen = false;
	int a = 10;
	int b = 100;
	
	
    // declare and initialize boolean dragging
    boolean dragging = false;
    
    // declare and initialize a JPopupMenu
    JPopupMenu menu = new JPopupMenu("Popup");
    
    // declare and initialize a JMenuItem
    JMenuItem deleteRect = new JMenuItem("DeleteRect");
    JMenuItem addCase = new JMenuItem("AddCase");
    
    
    int clicks = 0;
    
    // default controller constructor
    public Controller(){
    	rects = new ArrayList<DraggableRect>();
		// if file doesnt exists, then create it
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    
    // getter function for rects
    public ArrayList<DraggableRect> getRects(){
    	return rects;
    }
    
    // passes position of DraggableRect into DragController
    public void addRect(DraggableRect r){
    	r.update();
    	rects.add(r);
    }
    
    // clears everything in DragController rects ArrayList
    public void clearRects(){
    	rects.clear();
    }
    
    // default function to delete last element in rects
/*    public void deleteRect(){
    	if(rects.size() > 0){
    		rects.get(rects.size()-1).getParent().remove(rects.get(rects.size()-1));
    		rects.remove(rects.size()-1);
    	}
    }*/
    
    // overload function to specify which element to delete in rects
    public void deleteRect(int index){
    	if(rects.size() > index && index >= 0){
    		if(rects.get(index).getType() == 6){
    			((Switch) rects.get(index)).Contents.clear();
        		((Switch) rects.get(index)).clicks = 0;
    		}
    		for(int i=0; i<rects.get(index).childrenIDs.size(); i++){
    			if(rects.get(index).childrenIDs.get(i) != 0){
    				getRectByID(rects.get(index).childrenIDs.get(i)).parentID = 0;
    			}
    		}
    		if(rects.get(index).parentID != 0){
    			getRectByID(rects.get(index).parentID).deleteChild(rects.get(index).id);
    		}
    		GUI.rectToBeRemoved = index;
    	}
    	
    }
    
    // displays DraggableRect objects onto Graphics2D object g
    public void showRects(Graphics2D g){
    	for(DraggableRect r : rects){
    		r.draw(g, fullscreen);
/*    		if(!GUI.rightMenuClick){
    			r.setVisible(true);
    			r.draw(g);
    		}else{
    			r.setBorder(null);
    			r.update();
    			System.out.println("ASDF");
    		}*/
    	}
    } 
    
    public void showBorders(Graphics2D g, int dx, int dy){
    	for(DraggableRect r : rects){
    		r.drawBorder(g, dx, dy);
    	}
    }
    
/*    public static boolean LineIntersectsRect(Point p1, Point p2, Rectangle r)
    {
        return LineIntersectsLine(p1, p2, new Point(r.x, r.y), new Point(r.x + r.width, r.y)) ||
               LineIntersectsLine(p1, p2, new Point(r.x + r.width, r.y), new Point(r.x + r.width, r.y + r.height)) ||
               LineIntersectsLine(p1, p2, new Point(r.x + r.width, r.y + r.height), new Point(r.x, r.y + r.height)) ||
               LineIntersectsLine(p1, p2, new Point(r.x, r.y + r.height), new Point(r.x, r.y)) ||
               (r.contains(p1) && r.contains(p2));
    }

    private static boolean LineIntersectsLine(Point l1p1, Point l1p2, Point l2p1, Point l2p2)
    {
        float q = (l1p1.y - l2p1.y) * (l2p2.x - l2p1.x) - (l1p1.x - l2p1.x) * (l2p2.y - l2p1.y);
        float d = (l1p2.x - l1p1.x) * (l2p2.y - l2p1.y) - (l1p2.y - l1p1.y) * (l2p2.x - l2p1.x);

        if( d == 0 )
        {
            return false;
        }

        float r = q / d;

        q = (l1p1.y - l2p1.y) * (l1p2.x - l1p1.x) - (l1p1.x - l2p1.x) * (l1p2.y - l1p1.y);
        float s = q / d;

        if( r < 0 || r > 1 || s < 0 || s > 1 )
        {
            return false;
        }

        return true;
    }*/
    
//    public void drawNonOverlappingLine(int x1, int y1, int x2, int y2, Graphics2D g){
//    }
    
    // returns rectangle by specified id
    public static DraggableRect getRectByID(int id){
    	for(DraggableRect r : rects){
    		if(r.id == id){
    			return r;
    		}
    	}
    	return null;
    }
    
    public boolean findIfClicked(int mx, int my, ArrayList<DraggableRect> adr){
    	boolean clickInRect = false;
    	int ArrayListSize = adr.size();
    	
    	for(int i = 0; i < ArrayListSize; i++){
    		int dRX = adr.get(i).position.x;
    		int dRY = adr.get(i).position.y;
    		int dRW = adr.get(i).position.width;
    		int dRH = adr.get(i).position.height;
    		if((dRX <= mx && mx <= dRX + dRW) && (dRY <= my && my <= dRY + dRH)){
    			clickInRect = true;
    		}
    	}
    	return clickInRect;
    }
    
    /**
     * @param mx	the x coordinate where the mouse clicked
     * @param my	the y coordinate where the mouse clicked
     * @param adr	an arrayList of DraggableRect objects, from which to find the DraggableRect containing the mouse position 
     * @return		the DraggableRect containing the mouse position
     * 
     * @preconditions	1) mouse position (mx, my) is known
     * 					2) an arrayList of DraggableRect objects is given
     * @postconditions	return the DraggableRect containing the mouse position
     */
    public int getClickedRect(int mx, int my, ArrayList<DraggableRect> adr){
    	int ArrayListSize = adr.size();
    	//DraggableRect theClicked = new DraggableRect();
    	int theClicked = 0;
    	for(int i = 0; i < ArrayListSize; i++){
    		int dRX = adr.get(i).position.x;
    		int dRY = adr.get(i).position.y;
    		int dRW = adr.get(i).position.width;
    		int dRH = adr.get(i).position.height;
    		if((dRX <= mx && mx <= dRX + dRW) && (dRY <= my && my <= dRY + dRH)){
    			theClicked = i;
    			//theClicked = adr.get(i);
    			//System.out.println(theClicked);
    			return theClicked;
    		}
    	}
    	return -1;
    	
    }
    
    public int getClickedType(int mx, int my){
    	int blockType = 0;
    	int index = getClickedRect(mx, my, rects);
    	if(index >= 0 && index < rects.size()){
    		blockType = rects.get(index).getType();
    	}
    	//System.out.println("type" + blockType);
    	return blockType;	
    	
    }
    
    
    
    
    // return array of rectangles by specified type
    public static ArrayList<DraggableRect> getRectsByType(int type){
    	ArrayList<DraggableRect> list = new ArrayList<DraggableRect>();
    	for(DraggableRect r : rects){
    		if(r.getType() == type){
    			list.add(r);
    		}
    	}
    	return list;
    }
    
    
    //recirsuve function to generate code string
    private String getFileMessage(DraggableRect r){
    	if (r != null) {
			if (r.hasChildren()) {
				switch (r.getType()) {
				case 0:
					return getFileMessage(getRectByID(r.childrenIDs.get(0)));
				case 1:
					// TODO update assignment rect
					return r.f1 + " = " + r.f2 + ";\n" + getFileMessage(getRectByID(r.childrenIDs.get(0)));
				case 2:
					return r.f1 + " " + r.f3 + " " + r.f2;
				case 3:
					return "if(" + /*
									 * getFileMessage(getRectByID(r.childrenIDs.
									 * get(0))) +
									 */"){\n" + getFileMessage(getRectByID(r.childrenIDs.get(0))) + "\n} else {\n"
							+ getFileMessage(getRectByID(r.childrenIDs.get(1))) + "\n}";
				case 4:
					return "while(" + r.f1 + "){\n" + getFileMessage(getRectByID(r.childrenIDs.get(0))) + "\n}\n"
							+ getFileMessage(getRectByID(r.childrenIDs.get(1)));
				case 5:
					return "public static void main(String[] args){\n"
							+ getFileMessage(getRectByID(r.childrenIDs.get(0))) + "}";
				case 6:
					String message = "switch(" + r.f1 + ":\n";
					for (int i = 0; i < r.childrenIDs.size(); i++) {
						if (getRectByID(r.childrenIDs.get(i)) != null) {
							message += "case " + i + ":\n";
							message += getFileMessage(getRectByID(r.childrenIDs.get(i))) + "\n";
						} else {
							return message + "}";
						}
					}
					break;
				case 7:
					return r.f1 + "{\n" + getFileMessage(getRectByID(r.childrenIDs.get(0))) + "\n}";
				case 8:
					return r.f1 + getFileMessage(getRectByID(r.childrenIDs.get(0)));
				default:
					return Integer.toString(r.getType());
				}
			} else {
				switch (r.getType()) {
				case 0:
					return "";
				case 1:
					return "[TYPE] " + r.f1 + " = " + r.f2;
				case 2:
					return r.f1 + " " + r.f3 + " " + r.f2;
				case 3:
					return "if(" + r.f1 + "){}";
				case 4:
					return "while(" + r.f1 + "){}";
				case 5:
					return "public static void main(String[] args){}";
				case 6:
					return "switch(" + r.f1 + "){}";
				case 7:
					return r.f1 + "{}";
				case 8:
					return r.f1;
				default:
					return Integer.toString(r.getType());
				}
			}
		}
		return "";
    }
    
  //changes the position of a DraggableRect and all its children
    public static void setTreeLocation(DraggableRect r, int x, int y){
    	if(r != null){
	    	//if the rect has children, then set the position of the children
	    	if(r.hasChildren()){
	    		//keep a record of the original position of the parent rect
	    		int oldX = r.getPosition().x;
	    		int oldY = r.getPosition().y;
	    		//set the position of the parent rect
	    		r.setPosition(x, y);
	    		r.update();
	    		//iterate through each of the first-generation child rects
	    		for(int child : r.childrenIDs){
	    			//if the child isn't empty
	    			if(child != 0){
	    				//find the child with the specified id
	    				DraggableRect childRect = getRectByID(child);
	    				//get the new coordinates
	    				int newX = childRect.getPosition().x + (x - oldX);
	    				int newY = childRect.getPosition().y + (y - oldY);
	    				//continue with the child's child
	    				setTreeLocation(childRect, newX, newY);
	    			}
	    		}
	    	//if there are no children, then just set the position of the rect
	    	}else{
	    		r.setPosition(x, y);
	    		r.update();
	    	}
    	}
    }
    
    //finds the member with the greatest width starting with the parent of initID
    public static int getMaxTreeX(int initID){
    	if(initID != 0){
	    	DraggableRect r = getRectByID(initID);
	    	int x = r.getPosition().x + r.getResizeWidth();
	    	if(r.hasChildren()){
	    		for(int child : r.childrenIDs){
	    			if(child != 0){
		    			int newX = getMaxTreeX(child);
		    			if(newX > x){
		    				x = newX;
		    			}
	    			}
	    		}
	    	}
	    	return x;
    	}
    	return 0;
    }
    
    //finds the member with the greatest width starting with the parent of initID
	public static int getMaxTreeY(int initID){
		if(initID != 0){
	    	DraggableRect r = getRectByID(initID);
	    	int y = r.getPosition().y + r.getResizeHeight();
	    	if(r.hasChildren()){
	    		for(int child : r.childrenIDs){
	    			if(child != 0){
		    			int newY = getMaxTreeY(child);
		    			if(newY > y){
		    				y = newY;
		    			}
	    			}
	    		}
	    	}
	    	return y;
		}
		return 0;
	}
	
	//writes to a file starting from specified rect
    public void writeToFile(DraggableRect r){
    	try {
    		 
			String content = getFileMessage(r);
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    // create method mousePressed with prameter MouseEvent e
    public void mousePressed(MouseEvent e) {
    	// Points where the mouse is pressed
        Point p = e.getPoint();
        
        GUI.rightMenuClick = false;
        
        // create rectangles to keep a record of the rectangles created at the beginning of class DraggableRect.
        // they are used to find the top left point of the rectangles.
        for(DraggableRect r : rects){
        	// used to create the movement of the rectangles 
            if(r.getPosition().contains(p)) {
            	//deletes associations with other blocks
            	if(r.parentID != 0){
            		if(getRectByID(r.parentID).hasChildren()){
	            		getRectByID(r.parentID).deleteChild(r.id);
	            		getRectByID(r.parentID).update();
            		}
            	}
            	r.parentID = 0;
            	//used to set up movement
                r.offset.x = p.x - r.getPosition().x;
                r.offset.y = p.y - r.getPosition().y;
                r.dragging = true;
            }
            r.update();
        }
        
        if(e.isPopupTrigger()){
    		menu.show(e.getComponent(), e.getX(), e.getY());
    	}
        
    }
    
    
    
    // create method mouseReleased with parmeter MouseEvent e
    public void mouseReleased(MouseEvent e) {
    	//declaring variables
    	clicks++;
    	ClickNum.add(clicks);
    	Point p = new Point();
    	boolean hasOverlap;
    	boolean isRelated;
    	newMouseStroke = true;
    	
    	do{
    		hasOverlap = false;
    		isRelated = false;
	    	// makes it so that the rectangle that you are dragging and was previously dragging equal tempRect
	    	for(DraggableRect r : rects){
	    		
	    		//All objects have been placed down, so hovering is not possible
	    		r.setHoveringFalse();
	    		
	    		if(r.dragging == true) {
	    			
			    	// creates a for statement that does the snap for overlapped rectangles
			        for (DraggableRect tempRecti : rects) {
			        	
			        	// create an if statement the sees if tempRect intersects with tempRecti and if tempRect doesn't equal tempRecti
			        	p = tempRecti.getNewSnap(r);
			        	if (p != null){
			        		
			        		//sets location of rectangle being dragged to below the rectangle it overlaps
			        		setTreeLocation(r, p.x, p.y);
			        		
			        		//checks to see if rectangles are already related
			        		if(r.parentID == tempRecti.id){
			        			isRelated = true;
			        		}
			        		
			        		//sets parent and child id based on encapsulation
			        		r.parentID = tempRecti.id;
			        		tempRecti.setChild(r);
			        		r.update();
			        		
			        		//sets code to check again for any more overlap conflicts
			        		hasOverlap = true;
			        		//breaks out of statement to avoid unnecessary repetition
			        		break;
			        		
			        	}
			        }
	        	}
	        }
    	}while(hasOverlap && !isRelated);
    	
        // if the mouse is released dragging stops
    	for(DraggableRect r : rects){
    		r.dragging = false;
    	}
    	
    	// if the clicks is 1 then do the action
    	//if(clicks == ClickNum.size()){
    	final int mx = e.getX();
    	final int my = e.getY();
    	//System.out.println(rects.size() + " mx " + mx + " my" + my + " rects " + rects.get(0).getX() + " rects " + rects.get(0).getY() + "          " + rects.get(0).getWidth() + "      " + rects.get(0).getHeight());
    	//System.out.println(getClickedRect(mx, my, rects));
    	//ClickNum.clear();
    	deleteRect.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if(findIfClicked(mx, my, rects) && newMouseStroke){
    				deleteRect(getClickedRect(mx, my, rects));
    				newMouseStroke = false;
    				//System.out.println("Click number is " + clicks);
    			}
    		}
    	});
    	
    	
    	addCase.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if(newMouseStroke){
    				
    				DraggableRect r = rects.get(getClickedRect(mx, my, rects));
    				if(r.getType() == 6){
    					Switch s = (Switch) r;
	    				s.clicks++;
	    				s.Contents.add(new Rectangle(s.content.x, s.content.y, 75, 50));   		
	    				s.ContentVisible.add(true);
	    				newMouseStroke = false;
    				}
				}
    		}
    	});
    	if(getClickedType(mx,my) == 6){
    		menu.add(addCase);
    		//System.out.println("Type 2 " + getClickedType(mx,my) );
    	}else {
    		menu.remove(addCase);
    	}
        	menu.add(deleteRect);
    	if(findIfClicked(mx, my, rects)){
    		if(e.isPopupTrigger()){
    			menu.show(e.getComponent(), e.getX(), e.getY());
    		}
    	}
    }
    
    // creates method mouseDragged with parameter MouseEvent e 
    public void mouseDragged(MouseEvent e) {
    	// sets up the dragging
    	for(DraggableRect r : rects){
    		if(r.dragging){
    			if(r.getType() == 2){
    				if(r.internalRect){
    					r.dragging = false;
    					r.internalRect = false;
    				}
    			}
    			int x = e.getX() - r.offset.x;
                int y = e.getY() - r.offset.y;
                setTreeLocation(r, x, y);
                for(DraggableRect ri : rects){
                	ri.checkHoverOver(r);
                }
                r.update();
    		}
    	}
    }
   

    
}