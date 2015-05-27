import java.awt.*; 		 // import java.awt package
import javax.swing.*;	 // import javax.swing package

public class DraggableRect extends JPanel {

	private static final long serialVersionUID = 1L;
	private Rectangle position;
	private DragController controls;
	private Color c;
	
	//default constructor which initializes values
	public DraggableRect(){
		position = new Rectangle();
		controls = new DragController(this);
		c = Color.BLACK;
	}
	
	//override constructor which sets position values for rectangle
	public DraggableRect(int x, int y, int w, int h){
		position = new Rectangle(x, y, w, h);
		controls = new DragController(this);
		c = Color.BLACK;
	}
	
	//override constructor to set color as well
	public DraggableRect(int x, int y, int w, int h, Color color){
		position = new Rectangle(x, y, w, h);
		controls = new DragController(this);
		c = color;
	}
	
	//getter function for position
	public Rectangle getPosition(){
		return position;
	}
	
	//mutator function to set position
	public void setLocation(int x, int y){
		position.setLocation(x, y);
	}
	
	//function to set width and height for position
	public void setPosition(int x, int y, int w, int h){
		position = new Rectangle(x, y, w, h);
	}
	
	//mutator function to set position based on another Rectange
	public void setPosition(Rectangle r){
		position = r;
	}
	
	//changes position by a set amount
	public void move(int x, int y){
		position.x += x;
		position.y += y;
	}
	
	//mutator function for color
	public void setColor(Color color){
		c = color;
	}
	
	//getter function for color
	public Color getColor(){
		return c;
	}
	
	//updates values for mouse input
	private void update(){
		setPosition(controls.getNewComponent());
	}
	
	protected void paintComponent(Graphics graphics){ // makes a protected method named paintComponent
		update();
		super.paintComponent(graphics); // call paintComponent method of JPanel
		Graphics2D g = (Graphics2D) graphics; // create a Graphics2D object g2
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // call setRenderingHint method
                RenderingHints.VALUE_ANTIALIAS_ON);
		g.setPaint(c);
		g.draw(position);
	}
}
