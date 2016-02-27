package main.block;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextArea;

import main.util.Controller;

public class Switch extends DraggableRect{
	
	private static final long serialVersionUID = 1L;

	public Rectangle content;
	
	private static final int mainWidth = 75;
	public static final int mainHeight = 75;
	
	private static final int contentWidth = 75;
	private static final int contentHeight = 50;
	
	public static final int contentDisplacementX = 100;
	public static final int contentDisplacementY = 100;
	
	public int clicks = 0;
	public ArrayList<Rectangle> Contents = new ArrayList<Rectangle>();
	public ArrayList<Boolean> ContentVisible = new ArrayList<Boolean>();
	
	private JTextArea expression = new JTextArea();
	private static final int numChildren = 50;
	
	int justSnapped = -1;
	
	
	public Switch(){
		super(0, 0, mainWidth, mainHeight);
		setNumChildren(numChildren);
		content = new Rectangle(0, 0, contentWidth, contentHeight);
		objectsHoveringAbove.add(false);
		expression.setOpaque(false);
		expression.setText("<Expression>");
		add(expression, BorderLayout.CENTER);
		type = 6;
		update();
	}
	
	public Switch(int x, int y){
		super(x, y, mainWidth, mainHeight);
		setNumChildren(numChildren);
		content = new Rectangle(position.x + contentDisplacementX, position.y + contentDisplacementX, contentWidth, contentHeight);
		Contents.add(content);
		ContentVisible.add(true);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		expression.setOpaque(false);
		expression.setText("<Expression>");
		add(expression, BorderLayout.CENTER);
		type = 6;
		update();
	}
	
	public Switch(Color c){
		super(0, 0, mainWidth, mainHeight, c);
		setNumChildren(numChildren);
		content = new Rectangle(0, 0, contentWidth, contentHeight);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		expression.setOpaque(false);
		expression.setText("<Expression>");
		add(expression, BorderLayout.CENTER);
		type = 6;
		update();
	}
	
	
	
	public Switch(int x, int y, Color c){
		super(x, y, mainWidth, mainHeight, c);
		setNumChildren(numChildren);
		content = new Rectangle(0, 0, contentWidth, contentHeight);
		objectsHoveringAbove.add(false);
		objectsHoveringAbove.add(false);
		expression.setOpaque(false);
		expression.setText("<Expression>");
		add(expression, BorderLayout.CENTER);
		type = 6;
		update();
	}
	//calculates position of branches and updates dimensions based on children
	private void updateBranches(){
		content.setLocation(position.x + contentDisplacementX, position.y + contentDisplacementY);
		for(int i = 1; i < Contents.size(); i++){
			if(childrenIDs.get(i - 1) != 0){
				Contents.get(i).setLocation(Controller.getMaxTreeX(childrenIDs.get(i - 1)), Controller.getMaxTreeY(childrenIDs.get(i - 1)) + contentDisplacementY);
				Controller.setTreeLocation(Controller.getRectByID(childrenIDs.get(i)), Contents.get(i).x, Contents.get(i).y);
			}else{
				Contents.get(i).setLocation(Contents.get(i - 1).x, Contents.get(i - 1).y + contentDisplacementY); 
			}
		}

		if(childrenIDs.size() > 0 && justSnapped != -1 && childrenIDs.get(justSnapped) != 0){
			//sets branch1 to encapsulate child
			ContentVisible.set(justSnapped, false);
			
		}else if(justSnapped != -1) {
			//sets branch1 to visible
			ContentVisible.set(justSnapped, true);
		}
		for(int i=0; i<min(childrenIDs.size(), Contents.size()); i++){
			if(childrenIDs.get(i) == 0){
				ContentVisible.set(i, true);
			}
		}
	}
		
	//overrides update to account for branches
	@Override
	public void update(){
		//this.setBounds(getOffset(position));
		super.update();
		updateBranches();
		if(internalRect){
			this.setVisible(false);
		}else{
			this.setVisible(true);
		}
		
		f1 = expression.getText();
	}
	
	//override resetFamily to account for different number of children
	@Override
	public void resetFamily(){
		parentID = 0;
		setNumChildren(numChildren);
	}
	
	//Overrides getNewSnap to account for branches
	@Override
	public Point getNewSnap(DraggableRect rect){
		//updates branches to adjust for child height
		updateBranches();
		//if intersect main rect
		for(int i = 0; i < Contents.size(); i++){
			if(position.intersects(rect.getPosition()) && id != rect.id){
				if(type == 4){
					return new Point(position.x-35, position.y+3);
				}
				//sets location to far right to avoid collisions
				return new Point(position.x + contentDisplacementX + content.width + displacement, rect.getPosition().y);
				
			}else if (Contents.get(i).intersects(rect.getPosition()) && !Contents.get(i).equals(rect) && childrenIDs.get(i) == 0){
				
				//sets location of rectangle being dragged to below the rectangle it overlaps
				justSnapped = i;
				return new Point(Contents.get(i).x, Contents.get(i).y);
				
			}
		}return null;
	}
	
	//overrides setChild function set child under branch
	@Override
	public void setChild(DraggableRect rect){
		if(!position.equals(rect.getPosition())){
			for(int i = 0; i < Contents.size(); i++){
				if(Contents.get(i).intersects(rect.getPosition()) && childrenIDs.get(i) == 0){
					childrenIDs.set(i, rect.id);
				}else if(position.intersects(rect.getPosition()) && childrenIDs.get(1) == 0 && rect.getType() == 6){
					childrenIDs.set(49, rect.id);
					internalRect = true;
				}
			}
		}
		update();
	}	
	@Override
	public void checkHoverOver(DraggableRect r){
		try{
			//checks hovering over main body
			if(position.intersects(r.getPosition()) && id != r.id){
				if(!objectsHoveringAbove.get(0)){
					objectsHoveringAbove.set(0, true);
				}
			}else	{
				if(	objectsHoveringAbove.get(0)){
					objectsHoveringAbove.set(0, false);
				}
			}	
		
			for(int i=0; i<Contents.size(); i++){
				//checks hovering over branch1
				if(Contents.get(i).intersects(r.getPosition()) && id != r.id){
					if(!objectsHoveringAbove.get(i)){
						objectsHoveringAbove.set(i, true);
					}
				}else{
					if(objectsHoveringAbove.get(i)){
						objectsHoveringAbove.set(i, false);
					}
				}
			}
		
			
				
			//catches indexOutOfBounds exception
		}catch(Exception ex){
			ex.printStackTrace();		
		}
	}
	
	public void drawArrows(Graphics2D g){
		int mainMidX = position.x + (position.width/2);
		int mainBottom = position.y + position.height;
		int caseLeft = position.x + contentDisplacementX;
//		int caseMidY = position.y + clicks*contentDisplacementY + 100 + (content.height/2);
		int caseMidY = Contents.get(clicks).y + (content.height/2);//+ 100 + (content.height/2);
		int mainBranch2MidX = content.x - (content.width/2);
		//draws lines for arrows
		g.setColor(Color.BLACK);
		g.drawLine(mainMidX, mainBottom, mainMidX, caseMidY);
		
		for(int i = 0; i < Contents.size(); i++){
			//draws polygons
			caseLeft = Contents.get(i).x;
			int contentMidY2 = Contents.get(i).y + (content.height/2);
			int[] xPoints = {caseLeft - triangleSize, caseLeft - triangleSize, caseLeft};
			int[] yPoints = {contentMidY2 - triangleSize, contentMidY2 + triangleSize, contentMidY2};
			g.drawLine(mainMidX, contentMidY2, caseLeft, contentMidY2);
			//draws triangles for arrows
			g.fillPolygon(xPoints, yPoints, 3);
			//draws labels for arrows
			g.setFont(new Font(Font.SANS_SERIF, 3, 18));
			g.drawString("Case " + (i + 1), mainBranch2MidX - 23, contentMidY2 - 5);
		}
		
	}
	
	@Override
	public void draw(Graphics2D g){
		g.setPaint(c);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);		// call setRenderingHint method
		update();
		
		//System.out.println("cases " + casesAndContents.size());
		for(int i = 0; i < Contents.size(); i++){
			
			if(ContentVisible.get(i) && objectsHoveringAbove.get(i)){
				g.setPaint(shadow);
				g.fill(Contents.get(i));
			}
			
			if(ContentVisible.get(i)){
				g.setColor(Color.BLACK);
				g.draw(Contents.get(i));
			}
			
		}
		drawArrows(g);
	}
	
	private int min(int a, int b){
		if(a > b){
			return b;
		}return a;
	}
	
}