package cms.display.buttons.quick;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import cms.display.GraphicsConstants;

public class EmptyButton  extends JComponent implements GraphicsConstants {
	private static final long serialVersionUID = 1L;
	private static final int type = 200;
	private Color foreground;

	
	public EmptyButton(){
		super();
		this.setFocusable(true);
		this.setVisible(true);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		g.setColor(BACK);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

}
