package cms.display.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;

import cms.databank.structures.Node;
import cms.display.GraphicsConstants;
import cms.display.buttons.quick.QuickSeekButton;

/**
 * The <code>IconBuilder</code> class is a factory class building icons for the various buttons of the GUI.
 * @author Eduard Parachivescu
 * @author Philippe Hebert
 * @version 1
 * -tag @refactor Philippe Hebert
 */
public final class IconBuilder implements GraphicsConstants{
	
	
	private IconBuilder() {}
	
	/**
	 * The <code>BuilderClass</code> class is a datastructure storing the icon-building related info.
	 * @author Philippe Hebert
	 * @version 1
	 * -tag @refactor Philippe Hebert
	 * TODO Change Node node for an interface implemented commonly by Node, DB, AMS & Relay
	 */
	private final static class BuilderClass{
		private Graphics graphics;
		private Color foreground, background;
		private int x_pos, y_pos;
		private Node node_bean;
		private JComponent component;
		
		public BuilderClass(Graphics g, Color foreground, Color background, int x, int y, int type, JComponent component, Node node_bean){
			this.graphics = g;
			this.foreground = foreground;
			this.background = background;
			this.x_pos = x / 2;
			this.y_pos = y / 2;
			this.node_bean = node_bean;
			this.component = component;
		}
	}

	/**
	 * Icon type parser. Sends the IconBuild-ing task to the appropriate method.
	 * @param g The Graphics of the icon to build.
	 * @param foreground Foreground color of the icon to build.
	 * @param background Background color of the icon to build.
	 * @param x X position of the icon to build within its container.
	 * @param y Y position of the icon to build within its container.
	 * @param type Specifier of the type of icon to build.
	 * @param node Node used in nodeIcon() method. Otherwise null (and not used).
	 * TODO Change Node node for an interface implemented commonly by Node, DB, AMS & Relay
	 */
	public static void icon(Graphics g, Color foreground, Color background, int x, int y, int type, JComponent component, Node node_bean) {
		BuilderClass obj = new BuilderClass(g, foreground, background, x, y, type, component, node_bean);
		obj.graphics.setFont(NAV_FONT);
		Graphics2D g2d = (Graphics2D)obj.graphics;
		g2d.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

		switch (type) {
		case 0:
			nodeIcon(obj);
			break;
		case 1:
			nodeIconInfo(obj);
			break;
		case 10:
			cmsIcon(obj);
			break;
		case 11:
			amsIcon(obj);
			break;
		case 12:
			dbIcon(obj);
			break;
		case 50:
		case 51:
			tempIcon(obj);
			break;
		case 52:
		case 53:
			cpuIcon(obj);
			break;
		case 54:
		case 55:
			ramIcon(obj);
			break;
		case 56:
		case 57:
			particlesIcon(obj);
			break;
		case 100:
			quickSeek(obj);
			break;
		case 150:
			quickDB(obj);
			break;
		case 200:
			quickTime(obj);
			break;
		default:
			nodeIcon(obj);
			break;
		}

	}
	
	private static void quickDB(BuilderClass obj) {
		int circ = 7;
		int scale = 3;
		for(int i = circ*2; i > 4; i--){
			if(i % 2 == 0){
				obj.graphics.setColor(obj.foreground);
			} else {
				obj.graphics.setColor(obj.background);
			}
			obj.graphics.fillOval(obj.x_pos - i*scale, obj.y_pos - i*scale, i*scale*2, i*scale*2);
		}
		obj.graphics.setColor(obj.foreground);
		obj.graphics.fillRect(obj.x_pos - 7, obj.y_pos - 7, 16, 14);
		obj.graphics.setColor(obj.background);
		obj.graphics.fillOval(obj.x_pos - 10, obj.y_pos - 10, 20, 8);
		obj.graphics.setColor(obj.foreground);
		obj.graphics.fillOval(obj.x_pos - 7, obj.y_pos - 10, 14, 6);
		obj.graphics.fillOval(obj.x_pos - 7, obj.y_pos + 3, 14, 6);
	}

	/**
	 * Creates a QuickSeekButton icon
	 * @param obj
	 */
	private static void quickSeek(BuilderClass obj) {
		int circ = 7;
		int scale = 3;
		int blink = QuickSeekButton.blink * 2 + 6;
		for(int i = circ*2; i > 4; i--){
			if(i % 2 == 0){
				if(i < blink){
					obj.graphics.setColor(obj.foreground);
				} else {
					obj.graphics.setColor(GraphicsConstants.AVA);
				}
			} else {
				obj.graphics.setColor(obj.background);
			}
			obj.graphics.fillOval(obj.x_pos - i*scale, obj.y_pos - i*scale, i*scale*2, i*scale*2);
		}
		
		obj.graphics.setColor(obj.foreground);
		obj.graphics.fillOval(obj.x_pos - 2*scale, obj.y_pos - 2*scale, 2*scale*2, 2*scale*2);
		obj.graphics.fillRect(obj.x_pos - 1, obj.y_pos - 15, 4, 30);
	}
	
	private static void quickTime(BuilderClass obj) {
		
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date today = Calendar.getInstance().getTime(); 
		String time = df.format(today);
		float[] for_rgb = obj.foreground.getRGBColorComponents(null);
		obj.graphics.setColor(new Color(for_rgb[0], for_rgb[1], for_rgb[2], ALPHA_1));
		obj.graphics.drawString(time, obj.x_pos - 28, obj.y_pos + 6);
	}

	/**
	 * Creates a Temperature icon
	 * @param obj
	 */
	private static void tempIcon(BuilderClass obj) {
		obj.graphics.setColor(obj.foreground);
		obj.graphics.drawString("Temperature", 30, obj.y_pos + 7);
	}

	/**
	 * Creates a CPU icon
	 * @param obj
	 */
	private static void cpuIcon(BuilderClass obj) {
		obj.graphics.setColor(obj.foreground);
		obj.graphics.drawString("CPU", 30, obj.y_pos + 7);
	}

	/**
	 * Creates a RAM icon
	 * @param obj
	 */
	private static void ramIcon(BuilderClass obj) {
		obj.graphics.setColor(obj.foreground);
		obj.graphics.drawString("RAM", 30, obj.y_pos + 7);
	}

	/**
	 * Creates a Particles icon
	 * @param obj
	 */
	private static void particlesIcon(BuilderClass obj) {
		obj.graphics.setColor(obj.foreground);
		obj.graphics.drawString("Atoms", 30, obj.y_pos + 7);
	}

	/**
	 * Creates a Node icon
	 * @param obj
	 */
	private static void nodeIcon(BuilderClass obj) {
		obj.graphics.setColor(obj.foreground);
		obj.graphics.setFont(BODY_FONT);
		obj.graphics.fillOval(obj.x_pos - 15, obj.y_pos - 15, 30, 30);
		NodeButton bob = (NodeButton)obj.component;
		if(bob.blink == 0){
			obj.graphics.setColor(obj.background);
		} else {
			obj.graphics.setColor(GraphicsConstants.RUN);
		}
		obj.graphics.fillOval(obj.x_pos - 13, obj.y_pos - 13, 26, 26);
		obj.graphics.setColor(obj.foreground);
		obj.graphics.fillOval(obj.x_pos - 8, obj.y_pos - 8, 16, 16);
		obj.graphics.fillRect(obj.x_pos - 3, obj.y_pos - 35, 6, 70);

		String textone = obj.node_bean.getIp();
		Pattern pattern = Pattern.compile("\\.\\d{1,3}$");
		Matcher matcher = pattern.matcher(textone);
		if (matcher.find()) {
			textone = textone.substring(matcher.start(), matcher.end());
		} else {
			textone = "...";
		}
		obj.graphics.drawString(textone, obj.x_pos + 5, obj.y_pos - 23);

		String texttwo = "id:" + obj.node_bean.getId();
		obj.graphics.drawString(texttwo, obj.x_pos + 5, obj.y_pos + 34);
	}
	
	private static void nodeIconInfo(BuilderClass obj){
		obj.graphics.setColor(obj.foreground);
		
		String texttwo = "id:" + obj.node_bean.getId();
		obj.graphics.drawString(texttwo, obj.x_pos - 30, obj.y_pos - 23);
		
		String ipstring = obj.node_bean.getIp();
		Pattern pattern = Pattern.compile("\\.\\d{1,3}$");
		Matcher matcher = pattern.matcher(ipstring);
		if (matcher.find()) {
			ipstring = ipstring.substring(matcher.start(), matcher.end());
		} else {
			ipstring = "...";
		}
		obj.graphics.drawString(ipstring, obj.x_pos + 5, obj.y_pos - 23);
		
		obj.graphics.setFont(SMALL_FONT);
		
		String tempstring = obj.node_bean.getTemperature() + "°";
		obj.graphics.drawString(tempstring, obj.x_pos - 30, obj.y_pos - 9);
		
		String cpustring = obj.node_bean.getCpu() + "%";
		obj.graphics.drawString(cpustring, obj.x_pos - 30, obj.y_pos + 2);
		
		String ramstring = obj.node_bean.getRam() + "mb";
		obj.graphics.drawString(ramstring, obj.x_pos - 30, obj.y_pos + 13);
		
		String partstring = obj.node_bean.getAtoms() + "::";
		obj.graphics.drawString(partstring, obj.x_pos - 30, obj.y_pos + 24);
		
		String netstring = obj.node_bean.getRam() + "kb/s";
		obj.graphics.drawString(netstring, obj.x_pos - 30, obj.y_pos + 35);
	}

	/**
	 * Creates a CMS icon
	 * @param obj
	 */
	private static void cmsIcon(BuilderClass obj) {
		obj.graphics.setColor(obj.foreground);
		obj.graphics.fillOval(obj.x_pos - 21, obj.y_pos - 30, 20, 20);
		obj.graphics.fillOval(obj.x_pos - 4, obj.y_pos - 25, 35, 35);
		obj.graphics.fillOval(obj.x_pos - 23, obj.y_pos, 27, 27);
	}

	/**
	 * Creates an AMS icon
	 * @param obj
	 */
	private static void amsIcon(BuilderClass obj) {
		obj.graphics.setColor(obj.foreground);
		obj.graphics.fillRect(obj.x_pos - 20, obj.y_pos - 28, 40, 5);
		obj.graphics.fillRect(obj.x_pos - 20, obj.y_pos - 16, 40, 5);
		obj.graphics.fillRect(obj.x_pos - 20, obj.y_pos - 4, 40, 5);
		obj.graphics.fillRect(obj.x_pos - 20, obj.y_pos + 8, 40, 5);
		obj.graphics.fillRect(obj.x_pos - 20, obj.y_pos + 20, 40, 5);
	}

	/**
	 * Creates a DB icon
	 * @param obj
	 */
	private static void dbIcon(BuilderClass obj) {
		obj.graphics.setColor(obj.foreground);
		obj.graphics.fillRect(obj.x_pos - 20, obj.y_pos - 20, 40, 38);
		obj.graphics.setColor(obj.background);
		obj.graphics.fillOval(obj.x_pos - 21, obj.y_pos - 26, 40, 12);
		obj.graphics.setColor(obj.foreground);
		obj.graphics.fillOval(obj.x_pos - 20, obj.y_pos + 12, 38, 12);
		obj.graphics.fillOval(obj.x_pos - 19, obj.y_pos - 26, 36, 10);
	}

}
