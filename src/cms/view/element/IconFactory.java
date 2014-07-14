package cms.view.element;

import java.awt.Color;
import java.awt.Graphics;

public class IconFactory {
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
		case 0: nodeIcon();			break;
		case 1: cmsIcon();			break;
		case 2: amsIcon();			break;
		case 3: dbIcon();			break;
		case 50: 
		case 51: tempIcon();		break;
		case 52: 
		case 53: particlesIcon();	break;
		case 54: 
		case 55: pingIcon();		break;
		case 56: 
		case 57: mathIcon();		break;
		default: nodeIcon();		break;
		}
		
	}
	
	private static void mathIcon() {nodeIcon();}

	private static void pingIcon() {nodeIcon();}

	private static void particlesIcon() {nodeIcon();}

	private static void tempIcon() {nodeIcon();}

	private static void nodeIcon(){
		graphics.setColor(foregrnd);
		graphics.fillOval(x_pos-15, y_pos-15, 30,  30);
		graphics.setColor(backgrnd);
		graphics.fillOval(x_pos-13, y_pos-13, 26,  26);
		graphics.setColor(foregrnd);
		graphics.fillOval(x_pos-8, y_pos-8, 16,  16);
		graphics.fillRect(x_pos-3, y_pos-35, 6,  70);
	}
	
	private static void cmsIcon(){
		graphics.setColor(foregrnd);
		graphics.fillOval(x_pos - 21, y_pos - 30, 20,  20);
		graphics.fillOval(x_pos - 4, y_pos - 25, 35, 35);
		graphics.fillOval(x_pos - 23, y_pos, 27, 27);
	}
	
	private static void amsIcon(){
		graphics.setColor(foregrnd);
		graphics.fillRect(x_pos - 20, y_pos - 28, 40, 5);
		graphics.fillRect(x_pos - 20, y_pos - 16, 40, 5);
		graphics.fillRect(x_pos - 20, y_pos - 4, 40, 5);
		graphics.fillRect(x_pos - 20, y_pos + 8, 40, 5);
		graphics.fillRect(x_pos - 20, y_pos + 20, 40, 5);
	}
	
	private static void dbIcon(){
		graphics.setColor(foregrnd);
		graphics.fillRect(x_pos - 20, y_pos - 20, 40, 38);
		graphics.setColor(backgrnd);
		graphics.fillOval(x_pos - 21, y_pos - 26, 40, 12);
		graphics.setColor(foregrnd);
		graphics.fillOval(x_pos - 20, y_pos + 12, 38, 12);
		graphics.fillOval(x_pos - 19, y_pos - 26, 36, 10);
	}
	
	
	

}
