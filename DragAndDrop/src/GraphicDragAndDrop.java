	import java.awt.*; 		 // import java.awt package
	import java.awt.event.*; // import java.awt.event package
	import javax.swing.*;	 // import javax.swing package
	import javax.swing.event.MouseInputAdapter; // import javax.swing.event.MouseTinputAdapter

	public class GraphicDragAndDrop extends JPanel { // public class named GraphicDragAndDrop that also extends JPanel

			Rectangle rectBlue = new Rectangle(5,5,75,75);		// makes a rectangle named and  
	        Rectangle rectRed = new Rectangle(5,105,75,75);      // makes a rectangle named or
	        Rectangle rectGreen = new Rectangle(5,205,75,75);	// makes a rectangle named xnor
	        Rectangle rectYellow = new Rectangle(5,305,75,75);	// makes a rectangle named nand
	        Rectangle rectBlack = new Rectangle(5,405,75,75);		// makes a rectangle named xor
	        Rectangle rectPink = new Rectangle(5,505,75,75);// makes a rectangle named inverter

	    protected void paintComponent(Graphics g) {					// makes a protected method named paintComponent
	        super.paintComponent(g);		// call paintComponent method of JPanel
	        Graphics2D g2 = (Graphics2D)g;  // create a Graphics2D object g2
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // call setRenderingHint method
	                            RenderingHints.VALUE_ANTIALIAS_ON);	
	        g2.setPaint(Color.blue); // call setPaint and sets color blue
	        g2.draw(rectBlue);		 	 // call draw to draw and

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
	             repaint(); 				// repaints rectBlue  
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
	        new GraphicDragController(test); // declare new constructor method with parameters of test
	        JFrame f = new JFrame();		 // declare new JFrame f
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// call setDefaultCloseOperation
	        f.add(test);			// call add to add test to JFrame
	        f.setSize(700,700);		// call setSize to set the size of JFrame to 700 by 700 
	        f.setLocation(100,100); // call setLocation to show where the frame will appear
	        f.setVisible(true);		// call setVisible to make JFrame visible
	    }
	}

class GraphicDragController extends MouseInputAdapter { // class GraphicDragController that extends MouseInputAdapter
	    GraphicDragAndDrop component; 
	    Point offset = new Point();
	    Point offset2 = new Point();
	    Point offset3 = new Point();
	    Point offset4 = new Point();
	    Point offset5 = new Point();
	    Point offset6 = new Point();
	    boolean dragging = false;
	    boolean dragging2 = false;
	    boolean dragging3 = false;
	    boolean dragging4 = false;
	    boolean dragging5 = false;
	    boolean dragging6 = false;

	    public GraphicDragController(GraphicDragAndDrop gdad) {
	        component = gdad;
	        component.addMouseListener(this);
	        component.addMouseMotionListener(this);
	    }
	    
	    
	    

	    public void mousePressed(MouseEvent e) {
	        Point q = e.getPoint();
	        Point w = e.getPoint();
	        Point d = e.getPoint();
	        Point c = e.getPoint();
	        Point t = e.getPoint();
	        Point y = e.getPoint();
	        
	        Rectangle b = component.rectBlue;
	        Rectangle r = component.rectRed;
	        Rectangle g = component.rectGreen;
	        Rectangle o = component.rectYellow;
	        Rectangle l = component.rectBlack;
	        Rectangle p = component.rectPink;
	        
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
	    
	    public void mouseReleased(MouseEvent e) {
	        dragging = false;
	        dragging2 = false;
	        dragging3 = false;
	        dragging4 = false;
	        dragging5 = false;
	        dragging6 = false;
	    }

	    
	    public void mouseDragged(MouseEvent e) {
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


