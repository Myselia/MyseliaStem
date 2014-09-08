package cms.display.buttons;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import cms.display.GraphicsConstants;
import cms.display.graphing.DisplayType;

public class RadioButton extends JComponent implements MouseListener, GraphicsConstants {

	private static final long serialVersionUID = 1L;
	private DisplayType displaytype;
	private boolean select;
	
	public RadioButton(DisplayType type){
		super();
		this.enableInputMethods(true);
		this.setFocusable(true);
		this.displaytype = type;
		this.addMouseListener(this);
		this.setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(ABS);
		g.fillRect(getHeight(), 0, getWidth(), getHeight());
		//Background
		if(select){
			
		}
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
