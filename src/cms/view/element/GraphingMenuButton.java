package cms.view.element;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import cms.view.AddressBar;
import cms.view.GraphicsConstants;
import cms.view.panel.GraphingMenu;

public class GraphingMenuButton extends JComponent implements MouseListener, GraphicsConstants{
	private static final long serialVersionUID = 1L;
	private int type;
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
		g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
		
		IconFactory.icon(g, ABS, background, getWidth(), getHeight(), type);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		GraphingMenu.unselectLast(this);
		select(true);
		if(select){
			System.out.println("g>display " + type);
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
