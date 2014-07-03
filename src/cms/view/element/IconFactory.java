package cms.view.element;

import java.awt.Color;
import java.awt.Graphics;

public class IconFactory {
	
	private static final int[] x_offset = {15, 13, 8, 3};
	private static final int[] y_offset = {15, 13, 8, 35};
	static Graphics graphics;
	static Color foregrnd, backgrnd;
	static int x_pos, y_pos;
	
	public IconFactory(){}
	
	public static void icon(Graphics g, Color foreground, Color background, int x, int y, int type){
		graphics = g;
		foregrnd = foreground;
		backgrnd = background;
		x_pos = x/2;
		y_pos = y/2;
		
		switch(type){
		case 0: nodeIcon(); break;
		case 1: /*CMS*/ ; break;
		case 2: /*AMS*/ ; break;
		case 3: /*DBS*/ ; break;	
		default: nodeIcon(); break;
		}
		
	}
	
	private static void nodeIcon(){
		int i = 0;
		graphics.setColor(foregrnd);
		graphics.fillOval(x_pos-x_offset[i], y_pos-y_offset[i], x_offset[i]*2,  y_offset[i]*2);
		i++;
		graphics.setColor(backgrnd);
		graphics.fillOval(x_pos-x_offset[i], y_pos-y_offset[i], x_offset[i]*2,  y_offset[i]*2);
		i++;
		graphics.setColor(foregrnd);
		graphics.fillOval(x_pos-x_offset[i], y_pos-y_offset[i], x_offset[i]*2,  y_offset[i]*2);
		i++;
		graphics.fillRect(x_pos-x_offset[i], y_pos-y_offset[i], x_offset[i]*2,  y_offset[i]*2);
	}
	
	
	

}
