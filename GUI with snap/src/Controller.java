import java.awt.*; 		 // import java.awt package
import java.awt.event.*; // import java.awt.event package
import java.util.ArrayList; // import ArrayList

import javax.swing.event.MouseInputAdapter; // import javax.swing.event.MouseTinputAdapter

class Controller extends MouseInputAdapter { 	// class DragController that extends MouseInputAdapter
		
	private ArrayList<DraggableRect> rects;
	Rectangle newRectangle = new Rectangle();
	private ArrayList<DraggableRect> allRectangles = new ArrayList<DraggableRect>();
	
    // declare and initialize boolean dragging
    boolean dragging = false;
    
    // default controller constructor
    public Controller(){
    	rects = new ArrayList<DraggableRect>();
    }
    
    // getter function for rects
    public ArrayList<DraggableRect> getRects(){
    	return rects;
    }
    
    // passes position of DraggableRect into DragController
    public void addRect(DraggableRect r){
    	rects.add(r);
    }
    
    // clears everything in DragController rects ArrayList
    public void clearRects(){
    	rects.clear();
    }
    
    // default function to delete last element in rects
    public void deleteRect(){
    	rects.remove(rects.size()-1);
    }
    
    // overload function to specify which element to delete in rects
    public void deleteRect(int index){
    	rects.remove(index);
    }
    
    // displays DraggableRect objects onto Graphics2D object g
    public void showRects(Graphics2D g){
    	for(DraggableRect r : rects){
    		r.draw(g);
    	}
    }
    
    // create method mousePressed with prameter MouseEvent e
    public void mousePressed(MouseEvent e) {
    	// Points where the mouse is pressed
        Point p = e.getPoint();
        
        // create rectangles to keep a record of the rectangles created at the beginning of class DraggableRect.
        // they are used to find the top left point of the rectangles.
        for(DraggableRect r : rects){
        	// used to create the movement of the rectangles 
            if(r.getPosition().contains(p)) {
                r.offset.x = p.x - r.getPosition().x;
                r.offset.y = p.y - r.getPosition().y;
                r.dragging = true;
            }
        }
        
    }

    
    // create method mouseReleased with parmeter MouseEvent e
    public void mouseReleased(MouseEvent e) {
    	
    	
    	// create object tempRect with class Rectangle
    	DraggableRect tempRect = new DraggableRect();
    	
    	// makes it so that the rectangle that you are dragging and was previously dragging equal tempRect
    	for(DraggableRect r : rects){
    		if(r.dragging == true) {
        		tempRect = r;
        	}
    	}
    	
    	
   
    	// clear ArrayList allRectangles
    	allRectangles.clear();
    	// adds all of the rectangles to ArrayList allRectangles
    	allRectangles.addAll(rects);
       
   
    	
    	// create object overlapRect with type Rectangle
    	Rectangle overlapRect = new Rectangle();
    	
    	// declare a variable that is the size of ArrayList allRectangles 
    	int sizeOfAllRectangles = allRectangles.size();
    	
    	// creates a for statement that does the snap for overlapped rectangles
        for (int i = 0; i < sizeOfAllRectangles; i++) {
        	// create object tempRecti that is the ith rectangle in allRectangles 
        	DraggableRect tempRecti = allRectangles.get(i);
        	
        	// create an if statement the sees if tempRect intersects with tempRecti and if tempRect doesn't equal tempRecti
        	if (tempRect.position.intersects(tempRecti.position) && !tempRect.equals(tempRecti)){
        		
        		// makes overlapRect the intersection of tempRect and tempRecti
        		overlapRect = tempRect.position.intersection(tempRecti.position);
        		
        		// declares two variables that are the height and width of overlapRect
        		
        		int h = overlapRect.height;
        		int w = overlapRect.width;
        		int ih = 75 - overlapRect.height;
        		
        		// sees if tempRect will snap to the left or right of tempRecti
        		if (tempRecti.position.y < tempRect.position.y){
        			newRectangle = new Rectangle(tempRecti.position.x, tempRect.position.y + h + 10, 75, 75);
        		}else 
        			newRectangle = new Rectangle(tempRecti.position.x, tempRect.position.y + ih + 85, 75, 75);
        		
        		// makes it so that tempRect is the original rectangle that was before the snap
        		for(DraggableRect r : rects){
        			if(r.dragging == true) {
        	    		r.setLocation(newRectangle.x, newRectangle.y);
        	    	}
        		}
         		// break out of the code
        		break;
        	}
        }
    	    	
    	    	
         		
        
        
        
        // if the mouse is released dragging stops
    	for(DraggableRect r : rects){
    		r.dragging = false;
    	}
    }
    
    // creates method mouseDragged with parameter MouseEvent e 
    public void mouseDragged(MouseEvent e) {
    	// sets up the dragging
    	for(DraggableRect r : rects){
    		if(r.dragging){
    			int x = e.getX() - r.offset.x;
                int y = e.getY() - r.offset.y;
                r.setLocation(x, y);
    		}
    	}
    }
}
