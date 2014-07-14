package cms.view.element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import cms.model.data.BeanNode;
import cms.view.AddressBar;
import cms.view.GraphicsConstants;

public class NodeButton extends JComponent implements MouseListener, GraphicsConstants {
	private static final long serialVersionUID = 1L;

	private BeanNode core;
	private boolean select = false; 
	
	private Color background, foreground;

	public NodeButton(Dimension size, BeanNode core) {
		super();
		enableInputMethods(true);
		setFocusable(true);
		
		this.core = core;
		
		addMouseListener(this);
		setPreferredSize(size);
		setVisible(true);
	}

	public void select(boolean select) {
		core.setSelected(select);
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
		
		//Icon
		switch(core.getState()){
		case 0: foreground = ABS; break;
		case 10: foreground = PRE; break;
		case 20: foreground = AVA; break;
		case 30: foreground = RUN; break;
		case 40: foreground = ERR; break;
		default: foreground = ABS; break;
		}
		IconFactory.icon(g, foreground, background, getWidth(), getHeight(), core.getType());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		AddressBar.unselectLast(this);
		select(true);
		if(select){
			System.out.println("g>call " + core.getId());
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
