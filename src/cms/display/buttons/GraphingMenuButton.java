package cms.display.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import cms.display.GraphicsConstants;
import cms.display.graphing.DisplayType;
import cms.display.graphing.GraphingMenu;

public class GraphingMenuButton extends JComponent implements MouseListener, GraphicsConstants{
	private static final long serialVersionUID = 1L;
	private int type;
	public DisplayType dt;
	private boolean select;
	
	private Color background;
	
	public GraphingMenuButton(int type){
		super();
		enableInputMethods(true);
		setFocusable(true);
		
		this.type = type;
		
		addMouseListener(this);
		setVisible(true);
	}
	
	public void select(boolean select){
		this.select = select;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//Background
		if(select){
			background = SELECTED;
		} else {
			background = UNSELECTED;
		}
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		ButtonIconFactory.icon(g, ABS, background, getWidth(), getHeight(), type, null);
	}
	
	public void setDisplayType(DisplayType dt){
		this.dt = dt;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		GraphingMenu.unselectLast(this);
		select(true);
		if(select){
			System.out.println("g>Display: " + dt);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	
}
