package cms.view.panel;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cms.model.DataFactory;
import cms.view.GraphicsConstants;

public class GraphingPanel extends JPanel implements MouseListener,
GraphicsConstants{
	private static final long serialVersionUID = 1L;
	
	private int barcount;
	//private static int displaytype;

	public GraphingPanel(){
		super();
		enableInputMethods(true);
		setFocusable(true);
		addMouseListener(this);
		setVisible(true);

		this.barcount = DataFactory.core.length;
		
		this.setBorder(BorderFactory.createEmptyBorder(ADDRESS_GAP, ADDRESS_GAP, ADDRESS_GAP, ADDRESS_GAP));
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//background
		g.setColor(BACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		int offset = getWidth()/barcount;
		int extra = (getWidth() - (((int)(getWidth()/barcount)) * barcount)) / 2;
		
		
		//values
		for(int i = 0; i < barcount; i++){
			g.setColor(ABS);
			
			//x position, y position, width, height
			g.fillRect(offset*i  + extra, getHeight() - DataFactory.core[i].getTemperature()*5 , offset - 2, 5);
			
			
		}
		
		//average
		g.setColor(ABS);
		g.fillRect(0, 125, getWidth(), 3);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	
}
