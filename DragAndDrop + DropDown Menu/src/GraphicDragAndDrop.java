import java.awt.*; 		 // import java.awt package
import java.awt.event.*; // import java.awt.event package
import javax.swing.*;	 // import javax.swing package
import javax.swing.event.MouseInputAdapter; // import javax.swing.event.MouseTinputAdapter
import java.util.*;

public class GraphicDragAndDrop extends JPanel { // public class named GraphicDragAndDrop that also extends JPanel	

		Rectangle rectBlue = new Rectangle(5,5,75,75);		// makes a rectangle named and  
        Rectangle rectRed = new Rectangle(5,105,75,75);      // makes a rectangle named or
        Rectangle rectGreen = new Rectangle(5,205,75,75);	// makes a rectangle named xnor
        Rectangle rectYellow = new Rectangle(5,305,75,75);	// makes a rectangle named nand
        Rectangle rectBlack = new Rectangle(5,405,75,75);		// makes a rectangle named xor
        Rectangle rectPink = new Rectangle(5,505,75,75);// makes a rectangle named inverter
        
        ArrayList<Rectangle> allRectangles = new ArrayList<Rectangle>();

        protected void paintComponent(Graphics g) {				 // makes a protected method named paintComponent
        	super.paintComponent(g);		// call paintComponent method of JPanel
	        Graphics2D g2 = (Graphics2D)g;  // create a Graphics2D object g2
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // call setRenderingHint method
	                            RenderingHints.VALUE_ANTIALIAS_ON);	
	        g2.setPaint(Color.blue); 		// call setPaint and sets color blue
	        g2.draw(rectBlue);		 		// call draw to draw and

	        Graphics2D g3 = (Graphics2D)g;
	        g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                            RenderingHints.VALUE_ANTIALIAS_ON);
	        g3.setPaint(Color.red);
	        g3.draw(rectRed);

	        Graphics2D g4 = (Graphics2D)g;
	        g4.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                            RenderingHints.VALUE_ANTIALIAS_ON);
	        g4.setPaint(Color.green);
	        g4.draw(rectGreen);

	        Graphics2D g5 = (Graphics2D)g;
	        g5.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                            RenderingHints.VALUE_ANTIALIAS_ON);
	        g5.setPaint(Color.yellow);
	        g5.draw(rectYellow);

	        Graphics2D g6 = (Graphics2D)g;
	        g6.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                            RenderingHints.VALUE_ANTIALIAS_ON);
	        g6.setPaint(Color.black);
	        g6.draw(rectBlack);


	        Graphics2D g7 = (Graphics2D)g;
	        g7.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                            RenderingHints.VALUE_ANTIALIAS_ON);
	        g7.setPaint(Color.pink);
	        g7.draw(rectPink);
	    }

	    public void setRect(int x, int y) { // public void method named setRect with parmenters  int x and y
	    	rectBlue.setLocation(x, y); 	// sets rectBlue's location to x, y
	    	repaint(); 						// repaints rectBlue  
	    }
	    
	    public void setRect2(int x2, int y2) {
	    	rectRed.setLocation(x2, y2);
	    	repaint();    
		}
	    
	    public void setRect3(int x3, int y3) {
	    	rectGreen.setLocation(x3, y3);
			repaint();    
		}
	    
	    public void setRect4(int x4, int y4) {
	    	rectYellow.setLocation(x4, y4);
	    	repaint();    
		}
	    
	    public void setRect5(int x5, int y5) {
	    	rectBlack.setLocation(x5, y5);
	    	repaint();    
		}
	    
	    public void setRect6(int x6, int y6) {
	    	rectPink.setLocation(x6, y6);
	    	repaint();          
		}

	    public static void main(String[] args) { // main method
	        GraphicDragAndDrop test = new GraphicDragAndDrop(); // declare new GraphicDragAndDrop object
	        new GraphicDragController(test); 	// declare new constructor method with parameters of test
	        JFrame f = new JFrame();		 	// declare new JFrame f
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// call setDefaultCloseOperation
	        f.add(test);			// call add to add test to JFrame
	        f.setSize(700,700);		// call setSize to set the size of JFrame to 700 by 700 
	        f.setLocation(0,0); 	// call setLocation to show where the frame will appear
	        f.setVisible(true);		// call setVisible to make JFrame visible  
	    } 
}

	
class GraphicDragController extends MouseInputAdapter { 	// class GraphicDragController that extends MouseInputAdapter
		
	GraphicDragAndDrop gdad = new GraphicDragAndDrop();		// create object gdad with type GraphicDragAndDrop
	GraphicDragAndDrop component = new GraphicDragAndDrop();// create object component with type GraphicDragAndDrop
	Rectangle newRectangle = new Rectangle();				// create object newRectangle with type Rectangle
	ArrayList<Rectangle> allRectangles = new ArrayList<Rectangle>(); // create object allRectangles with type ArrayList
	
	// create object offsets,2,3,4,5,6 with type Point
	Point offset = new Point();
    Point offset2 = new Point();
    Point offset3 = new Point();
    Point offset4 = new Point();
    Point offset5 = new Point();
    Point offset6 = new Point();
	
    // declare and initialize boolean draggings 2,3,4,5,6 and needSnap
    boolean dragging = false;
    boolean dragging2 = false;
    boolean dragging3 = false;
    boolean dragging4 = false;
    boolean dragging5 = false;
    boolean dragging6 = false;
   
	// constructer method GraphicDragController
    public GraphicDragController(GraphicDragAndDrop gdad) {
        component = gdad;
        component.addMouseListener(this);
        component.addMouseMotionListener(this);
    } 
    
    // create method mousePressed with prameter MouseEvent e
    public void mousePressed(MouseEvent e) {
    	// Points where the mouse is pressed
        Point q = e.getPoint();
        Point w = e.getPoint();
        Point d = e.getPoint();
        Point c = e.getPoint();
        Point t = e.getPoint();
        Point y = e.getPoint();
        
        // create rectangles to keep a record of the rectangles created at the beginning of class GraphicDragAndDrop.
        // they are used to find the top left point of the rectangles.
        Rectangle b = component.rectBlue;
        Rectangle r = component.rectRed;
        Rectangle g = component.rectGreen;
        Rectangle o = component.rectYellow;
        Rectangle l = component.rectBlack;
        Rectangle p = component.rectPink;

        // used to create the movement of the rectangles 
        if(b.contains(q)) {
            offset.x = q.x - b.x;
            offset.y = q.y - b.y;
            dragging = true;    
        }
        if(r.contains(w)) {
            offset2.x = w.x - r.x;
            offset2.y = w.y - r.y;
            dragging2 = true;
        }
        if(g.contains(d)) {
            offset3.x = d.x - g.x;
            offset3.y = d.y - g.y;
            dragging3 = true;
        }
        if(o.contains(c)) {
            offset4.x = c.x - o.x;
            offset4.y = c.y - o.y;
            dragging4 = true;
        }
        if(l.contains(t)) {
            offset5.x = t.x - l.x;
            offset5.y = t.y - l.y;
            dragging5 = true;
        }
        if(p.contains(y)) {
            offset6.x = y.x - p.x;
            offset6.y = y.y - p.y;
            dragging6 = true;
        }
    }
    
    // create method mouseReleased with parmeter MouseEvent e
    public void mouseReleased(MouseEvent e) {

    	// create object tempRect with class Rectangle
    	Rectangle tempRect = new Rectangle();
    	
    	// makes it so that the rectangle that you are dragging and was previously dragging equal tempRect
    	if(dragging == true) {
    		tempRect = component.rectBlue;
    	}
    	if(dragging2 == true) {
    		tempRect = component.rectRed;
    	}
    	if(dragging3 == true) {
    		tempRect = component.rectGreen;
    	}
    	if(dragging4 == true) {
    		tempRect = component.rectYellow;
    	}
    	if(dragging5 == true) {
    		tempRect = component.rectBlack;
    	}
    	if(dragging6 == true) {
    		tempRect = component.rectPink;
    	}
    	
    	// clear ArrayList allRectangles
    	allRectangles.clear();
    	// adds all of the rectangles to ArrayList allRectangles
    	allRectangles.add(component.rectBlue);
    	allRectangles.add(component.rectRed);                       
    	allRectangles.add(component.rectGreen);
    	allRectangles.add(component.rectYellow);
    	allRectangles.add(component.rectBlack);
    	allRectangles.add(component.rectPink);
       
    	// create object overlapRect with type Rectangle
    	Rectangle overlapRect = new Rectangle();
    	
    	// declare a variable that is the size of ArrayList allRectangles 
    	int sizeOfAllRectangles = allRectangles.size();	   
    	
    	// creates a for statement that does the snap for overlapped rectangles
        for (int i = 0; i < sizeOfAllRectangles; i++) {
        	// create object tempRecti that is the ith rectangle in allRectangles 
        	Rectangle tempRecti = allRectangles.get(i);
        	
        	// create an if statement the sees if tempRect intersects with tempRecti and if tempRect doesn't equal tempRecti
        	if (tempRect.intersects(tempRecti) && !tempRect.equals(tempRecti)){
        		
        		// makes overlapRect the intersection of tempRect and tempRecti
        		overlapRect = tempRect.intersection(tempRecti);
        		
        		// declares two variables that are the height and width of overlapRect
        		int h = overlapRect.height;
        		int w = overlapRect.width;
        		
        		// sees if tempRect will snap to the left or right of tempRecti
        		if(tempRect.x < tempRecti.x){
        			newRectangle = new Rectangle(tempRect.x - w - 5, tempRect.y, 75, 75);
        		}
        		else
        			newRectangle = new Rectangle(tempRect.x + w + 5, tempRect.y, 75, 75);
        	
        		// makes it so that tempRect is the original rectangle that was before the snap
    	    	if(dragging == true) {
    	    		component.setRect(newRectangle.x, newRectangle.y);
    	    	}
    	    	if(dragging2 == true) {
    	    		component.setRect2(newRectangle.x, newRectangle.y);
    	    	}
    	    	if(dragging3 == true) {
    	    		component.setRect3(newRectangle.x, newRectangle.y);
    	    	}
    	    	if(dragging4 == true) {
    	    		component.setRect4(newRectangle.x, newRectangle.y);
    	    	}
    	    	if(dragging5 == true) {
    	    		component.setRect5(newRectangle.x, newRectangle.y);
    	    	}
    	    	if(dragging6 == true) {
    	    		component.setRect6(newRectangle.x, newRectangle.y);
    	    	}
         		// break out of the code
        		break;
        	}	
        }

        // if the mouse is released dragging stops
        dragging = false;
        dragging2 = false;
        dragging3 = false;
        dragging4 = false;
        dragging5 = false;
        dragging6 = false;
    }
    
    // creates method mouseDragged with parameter MouseEvent e 
    public void mouseDragged(MouseEvent e) {
    	// sets up the dragging
        if(dragging) {
            int x = e.getX() - offset.x;
            int y = e.getY() - offset.y;
            component.setRect(x, y); 
        }
        if(dragging2) {
            int x2 = e.getX() - offset2.x;
            int y2 = e.getY() - offset2.y;
            component.setRect2(x2, y2);
        }
        if(dragging3) {
            int x3 = e.getX() - offset3.x;
            int y3 = e.getY() - offset3.y;
            component.setRect3(x3, y3);
        }
        if(dragging4) {
            int x4 = e.getX() - offset4.x;
            int y4 = e.getY() - offset4.y;
            component.setRect4(x4, y4);
        }
        if(dragging5) {
            int x5 = e.getX() - offset5.x;
            int y5 = e.getY() - offset5.y;
            component.setRect5(x5, y5);
        }
        if(dragging6) {
            int x6 = e.getX() - offset6.x;
            int y6 = e.getY() - offset6.y;
            component.setRect6(x6, y6);
        }
    }
}


