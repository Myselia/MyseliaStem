package cms.display;

import java.awt.Color;

public interface GraphicsConstants {
	String TITLE = "CMS v0.5 alpha";

	int ADDRESS_COLUMNS = 16;
	int ADDRESS_GAP = 8;
	
	/*
	 * Color Scheme:
	 * http://paletton.com/#uid=c002o0-3t0kPionlSPvQoBLUocLyG4I
	 */
	
	Color AVA = new Color(0xCCCCCC); // GREY
	
	Color ERR = new Color(0xC20000); // RED
	Color PRE = new Color(0xFF8100); // ORANGE +1
	Color ABS = new Color(0x02517B); // BLUE
	Color RUN = new Color(0x9BF100); // GREEN +1
	
	Color BACK = new Color(0x012A41); // BLUE -1
	Color BEVEL = new Color(0x011018); // BLUE -2
	
	Color SELECTED = new Color(0x4EABDE); // BLUE +2
	Color UNSELECTED = BACK;
	
}
