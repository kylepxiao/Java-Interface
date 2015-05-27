import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.*;

public class GUI extends JFrame{
	
	/**
	 *Java Interface Prototype
	 *Created 5/15/15 by Kyle Xiao
	 *Siemens Competition 2015-16
	 */
	
	private static final long serialVersionUID = 1L;
	
	public BufferStrategy s;
	
	DraggableRect rect1 = new DraggableRect(510, 70, 100, 100);
	
	//Default constructor; Sets default attributes of window
	public GUI(){
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		((Graphics2D) this.getGraphics()).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		this.createBufferStrategy(2);
	}

	//Main function
	public static void main(String[] args){
		GUI window = new GUI();
		window.setBufferStrategy();
		window.setDefaultImage();
	}
	
	public void setBufferStrategy(){
		s = this.getBufferStrategy();
	}
	
	//Sets default layout and preferences for window
	public void setDefaultImage(){
		setTitle("Siemens Intuitive Interface");
		setSize(800, 600);
		
		setBackground(Color.GRAY);
		setForeground(Color.BLACK);
		setFont(new Font("Tahoma", Font.PLAIN, 24));
		
		add(new JLabel("Example Message"));
		
		add(rect1);

/*        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(5, 5));

        JScrollPane textScroller = new JScrollPane();
        JTextArea tarea = new JTextArea(300, 300);
        textScroller.setViewportView(tarea);

        contentPane.add(textScroller);
        setContentPane(contentPane);
        pack();*/
	}
	
	//Function to run handlers
	public void run(){
	}
	
	//Draw function which is called by default
	public void draw(Graphics2D g){
		g.drawString("Code Display", 20, 80);
		g.drawString("Interface", 520, 80);
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(5));
		g.drawLine(0, 100, 2000, 100);
		g.drawLine(500, 100, 500, 1000);
	}
	
	//Overrides JFrame default paint function
	@Override
	public void paint(Graphics graphics){
		try{
			super.paint(s.getDrawGraphics());
			super.paintComponents(s.getDrawGraphics());
			Graphics2D g = (Graphics2D) s.getDrawGraphics();
			draw(g);
			g.dispose();
			s.show();
			Toolkit.getDefaultToolkit().sync();
			repaint();
		}catch(Exception ex){}
	}
}
