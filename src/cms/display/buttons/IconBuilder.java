package cms.display.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cms.databank.structures.Node;

/**
 * The <code>IconBuilder</code> class is a factory class building icons for the various buttons of the GUI.
 * @author Eduard Parachivescu
 * @author Philippe Hebert
 * @version 1
 * -tag @refactor Philippe Hebert
 */
public final class IconBuilder {

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
		
		public BuilderClass(Graphics g, Color foreground, Color background, int x, int y, int type, Node node){
			this.graphics = g;
			this.foreground = foreground;
			this.background = background;
			this.x_pos = x / 2;
			this.y_pos = y / 2;
			this.node_bean = node;
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
	public static void icon(Graphics g, Color foreground, Color background, int x, int y, int type, Node node) {
		BuilderClass obj = new BuilderClass(g, foreground, background, x, y, type, node);
		obj.graphics.setFont(new Font("Dialog", Font.BOLD, 15));

		switch (type) {
		case 0:
			nodeIcon(obj);
			break;
		case 1:
			cmsIcon(obj);
			break;
		case 2:
			amsIcon(obj);
			break;
		case 3:
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
		default:
			nodeIcon(obj);
			break;
		}

	}

	/**
	 * Creates a QuickSeekButton icon
	 * @param obj
	 */
	private static void quickSeek(BuilderClass obj) {
		int circ = 8;
		int scale = 3;
		for(int i = circ*2; i > 1; i--){
			if(i % 2 == 0){
				obj.graphics.setColor(obj.foreground);
			} else {
				obj.graphics.setColor(obj.background);
			}
			obj.graphics.fillOval(obj.x_pos - i*scale, obj.y_pos - i*scale, i*scale*2, i*scale*2);
		}
	}

	/**
	 * Creates a Temperature icon
	 * @param obj
	 */
	private static void tempIcon(BuilderClass obj) {
		obj.graphics.setColor(obj.foreground);
		obj.graphics.drawString("temperature", obj.x_pos - 42, obj.y_pos + 7);
	}

	/**
	 * Creates a CPU icon
	 * @param obj
	 */
	private static void cpuIcon(BuilderClass obj) {
		obj.graphics.setColor(obj.foreground);
		obj.graphics.drawString("CPU", obj.x_pos - 15, obj.y_pos + 7);
	}

	/**
	 * Creates a RAM icon
	 * @param obj
	 */
	private static void ramIcon(BuilderClass obj) {
		obj.graphics.setColor(obj.foreground);
		obj.graphics.drawString("RAM", obj.x_pos - 15, obj.y_pos + 7);
	}

	/**
	 * Creates a Particles icon
	 * @param obj
	 */
	private static void particlesIcon(BuilderClass obj) {
		obj.graphics.setColor(obj.foreground);
		obj.graphics.drawString("particles", obj.x_pos - 30, obj.y_pos + 7);
	}

	/**
	 * Creates a Node icon
	 * @param obj
	 */
	private static void nodeIcon(BuilderClass obj) {
		obj.graphics.setColor(obj.foreground);
		obj.graphics.fillOval(obj.x_pos - 15, obj.y_pos - 15, 30, 30);
		obj.graphics.setColor(obj.background);
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
