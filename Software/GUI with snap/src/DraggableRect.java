import java.awt.*; 		 // import java.awt package
import javax.swing.*;	 // import javax.swing package

public class DraggableRect extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// class variables declarations
	public Rectangle position;
	private Color c;
	private Color b;
	public boolean dragging;
	public Point offset = new Point();
	
	// default constructor which initializes values
	public DraggableRect(){
		position = new Rectangle();
		c = Color.BLACK;
	}
			
	// override constructor which sets position values for rectangle
	public DraggableRect(int x, int y, int w, int h){
		position = new Rectangle(x, y, w, h);
		c = Color.BLACK;
	}
	
	// override constructor to set color as well
	public DraggableRect(int x, int y, int w, int h, Color color){
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
	
	
	// draw function for DraggableRect onto Graphics2D g
	public void draw(Graphics2D g){
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);		// call setRenderingHint method
		g.setPaint(c);
		g.draw(position);
	}
}
