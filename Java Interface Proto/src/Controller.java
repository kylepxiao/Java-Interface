import java.awt.*; 		 // import java.awt package
import java.awt.event.*; // import java.awt.event package
import java.util.ArrayList; // import ArrayList
import javax.swing.event.MouseInputAdapter; // import javax.swing.event.MouseTinputAdapter

class Controller extends MouseInputAdapter { 	// class DragController that extends MouseInputAdapter
		
	private ArrayList<DraggableRect> rects;
	
    // declare and initialize boolean dragging
    boolean dragging = false;
    
    //default controller constructor
    public Controller(){
    	rects = new ArrayList<DraggableRect>();
    }
    
    //getter function for rects
    public ArrayList<DraggableRect> getRects(){
    	return rects;
    }
    
    //passes position of DraggableRect into DragController
    public void addRect(DraggableRect r){
    	rects.add(r);
    }
    
    //clears everything in DragController rects ArrayList
    public void clearRects(){
    	rects.clear();
    }
    
    //default function to delete last element in rects
    public void deleteRect(){
    	rects.remove(rects.size()-1);
    }
    
    //overload function to specify which element to delete in rects
    public void deleteRect(int index){
    	rects.remove(index);
    }
    
    //displays DraggableRect objects onto Graphics2D object g
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


