import java.awt.*; 		 // import java.awt package
import java.awt.event.*; // import java.awt.event package
import javax.swing.event.MouseInputAdapter; // import javax.swing.event.MouseTinputAdapter

class DragController extends MouseInputAdapter { 	// class DragController that extends MouseInputAdapter
		
	private DraggableRect component; // create object component with type DraggableRect
	
	// create object offsets Point
	Point offset = new Point();
	
    // declare and initialize boolean dragging
    boolean dragging = false;
   
	// constructer method DragController
    public DragController(DraggableRect gdad) {
        component = gdad;
        component.addMouseListener(this);
        component.addMouseMotionListener(this);
    } 
    
    //getter function for new position
    public Rectangle getNewComponent(){
    	return component.getPosition();
    }
    
    // create method mousePressed with prameter MouseEvent e
    public void mousePressed(MouseEvent e) {
    	// Points where the mouse is pressed
        Point p = e.getPoint();
        
        // create rectangles to keep a record of the rectangles created at the beginning of class DraggableRect.
        // they are used to find the top left point of the rectangles.
        Rectangle r = component.getPosition();

        // used to create the movement of the rectangles 
        if(r.contains(p)) {
            offset.x = p.x - r.x;
            offset.y = p.y - r.y;
            dragging = true;
        }
    }
    
    // create method mouseReleased with parmeter MouseEvent e
    public void mouseReleased(MouseEvent e) {
        // if the mouse is released dragging stops
        dragging = false;
    }
    
    // creates method mouseDragged with parameter MouseEvent e 
    public void mouseDragged(MouseEvent e) {
    	// sets up the dragging
        if(dragging) {
            int x = e.getX() - offset.x;
            int y = e.getY() - offset.y;
            component.setLocation(x, y); 
        }
    }
}


