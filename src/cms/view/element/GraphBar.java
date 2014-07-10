package cms.view.element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import cms.model.data.BeanNode;
import cms.view.GraphicsConstants;

public class GraphBar extends JComponent implements MouseListener,
		GraphicsConstants {
	private static final long serialVersionUID = 1L;

	private BeanNode node;

	private static int displaytype;
	private int value;
	

	public GraphBar(BeanNode node) {
		super();
		
		this.node = node;
		
		enableInputMethods(true);
		setFocusable(true);

		addMouseListener(this);
		setVisible(true);

	}

	public void displaytypesettings(int displaytype) {
		this.displaytype = displaytype;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(BACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(ABS);
		g.fillRect(getWidth()/4, getHeight() - node.getTemperature()*5, getWidth()/2, 5);
		
		g.setColor(ABS);
		g.fillRect(0, 125, getWidth(), 2);

	}
	
	public void setAverage(int avg){
		//average set
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

}
