package cms.view.button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cms.model.data.BeanNode;

public class IconFactory {
	static Graphics graphics;
	static Color foregrnd, backgrnd;
	static int x_pos, y_pos;
	static BeanNode node_bean;

	public IconFactory() {
	}

	public static void icon(Graphics g, Color foreground, Color background, int x, int y, int type, BeanNode node) {
		graphics = g;
		foregrnd = foreground;
		backgrnd = background;
		node_bean = node;
		x_pos = x / 2;
		y_pos = y / 2;

		Font f = new Font("Dialog", Font.BOLD, 15);
		graphics.setFont(f);

		switch (type) {
		case 0:
			nodeIcon();
			break;
		case 1:
			cmsIcon();
			break;
		case 2:
			amsIcon();
			break;
		case 3:
			dbIcon();
			break;
		case 50:
		case 51:
			tempIcon();
			break;
		case 52:
		case 53:
			cpuIcon();
			break;
		case 54:
		case 55:
			ramIcon();
			break;
		case 56:
		case 57:
			particlesIcon();
			break;
		case 100:
			quickSeek();
			break;
		default:
			nodeIcon();
			break;
		}

	}

	private static void quickSeek() {
		int circ = 8;
		int scale = 3;
		for(int i = circ*2; i > 1; i--){
			if(i % 2 == 0){
				graphics.setColor(foregrnd);
			} else {
				graphics.setColor(backgrnd);
			}
			graphics.fillOval(x_pos - i*scale, y_pos - i*scale, i*scale*2, i*scale*2);
		}
	}

	private static void tempIcon() {
		graphics.setColor(foregrnd);
		graphics.drawString("temperature", x_pos - 42, y_pos + 7);
	}

	private static void cpuIcon() {
		graphics.setColor(foregrnd);
		graphics.drawString("CPU", x_pos - 15, y_pos + 7);
	}

	private static void ramIcon() {
		graphics.setColor(foregrnd);
		graphics.drawString("RAM", x_pos - 15, y_pos + 7);
	}

	private static void particlesIcon() {
		graphics.setColor(foregrnd);
		graphics.drawString("particles", x_pos - 30, y_pos + 7);
	}

	private static void nodeIcon() {
		graphics.setColor(foregrnd);
		graphics.fillOval(x_pos - 15, y_pos - 15, 30, 30);
		graphics.setColor(backgrnd);
		graphics.fillOval(x_pos - 13, y_pos - 13, 26, 26);
		graphics.setColor(foregrnd);
		graphics.fillOval(x_pos - 8, y_pos - 8, 16, 16);
		graphics.fillRect(x_pos - 3, y_pos - 35, 6, 70);

		String textone = node_bean.getIp();
		Pattern pattern = Pattern.compile("\\.\\d{1,3}$");
		Matcher matcher = pattern.matcher(textone);
		if (matcher.find()) {
			textone = textone.substring(matcher.start(), matcher.end());
		} else {
			textone = "...";
		}
		graphics.drawString(textone, x_pos + 5, y_pos - 23);

		String texttwo = "id:" + node_bean.getId();
		graphics.drawString(texttwo, x_pos + 5, y_pos + 34);
	}

	private static void cmsIcon() {
		graphics.setColor(foregrnd);
		graphics.fillOval(x_pos - 21, y_pos - 30, 20, 20);
		graphics.fillOval(x_pos - 4, y_pos - 25, 35, 35);
		graphics.fillOval(x_pos - 23, y_pos, 27, 27);
	}

	private static void amsIcon() {
		graphics.setColor(foregrnd);
		graphics.fillRect(x_pos - 20, y_pos - 28, 40, 5);
		graphics.fillRect(x_pos - 20, y_pos - 16, 40, 5);
		graphics.fillRect(x_pos - 20, y_pos - 4, 40, 5);
		graphics.fillRect(x_pos - 20, y_pos + 8, 40, 5);
		graphics.fillRect(x_pos - 20, y_pos + 20, 40, 5);
	}

	private static void dbIcon() {
		graphics.setColor(foregrnd);
		graphics.fillRect(x_pos - 20, y_pos - 20, 40, 38);
		graphics.setColor(backgrnd);
		graphics.fillOval(x_pos - 21, y_pos - 26, 40, 12);
		graphics.setColor(foregrnd);
		graphics.fillOval(x_pos - 20, y_pos + 12, 38, 12);
		graphics.fillOval(x_pos - 19, y_pos - 26, 36, 10);
	}

}
