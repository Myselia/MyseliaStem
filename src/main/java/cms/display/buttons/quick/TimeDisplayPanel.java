package cms.display.buttons.quick;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import cms.display.buttons.IconBuilder;
import cms.display.GraphicsConstants;

public class TimeDisplayPanel  extends JComponent implements GraphicsConstants {
	private static final long serialVersionUID = 1L;
	private static TimeDisplayPanel singleton;
	private static final int type = 200;
	private Color foreground;
	
	static{
		singleton = new TimeDisplayPanel();
	}
	
	private TimeDisplayPanel(){
		super();
		this.enableInputMethods(true);
		this.setFocusable(true);
		this.setVisible(true);
	}
	
	public static TimeDisplayPanel getTimer(){
		return singleton;
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		foreground = AVA;

		g.setColor(BACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		IconBuilder.icon(g, foreground, BACK, getWidth(), getHeight(), type, null, null);
	}

}
