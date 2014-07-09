package cms.view;

import java.awt.Color;

public interface GraphicsConstants {
	String TITLE = "CMS v0.1 alpha";

	int ADDRESS_COLUMNS = 16;
	int ADDRESS_GAP = 8;
	
	/*
	 * Color Scheme:
	 * http://paletton.com/#uid=73u1Z0kJGsmQLb8N4khx6HTpoNk
	 */
	
	Color ABS = new Color(0x999999); // GREY
	Color PRE = new Color(0xE27C00); // ORANGE
	Color AVA = new Color(0x045C90); // BLUE
	Color RUN = new Color(0x4EC700); // GREEN
	Color ERR = new Color(0xCE0034); // RED
	
	Color BACK = new Color(0x012438); // DARK BLUE
	
	Color SELECTED = new Color(0x369BD7); //LIGHT BLUE
	Color UNSELECTED = BACK;
	
}
