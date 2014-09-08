package cms.display;

import java.awt.Color;
import java.awt.Font;

public interface GraphicsConstants {
	String TITLE = "CMS v0.5 alpha";
	String OS = SystemFont.OS;

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
	Color HOVER = new Color(0x333344); //DARK GREY
	Color SELECTED = new Color(0x4EABDE); // BLUE +2
	Color UNSELECTED = BACK;
	
	//Fonts
	Font H1_FONT = SystemFont.H1_FONT;
	Font H2_FONT = SystemFont.H2_FONT;
	Font NAV_FONT = SystemFont.NAV_FONT;
	Font BODY_FONT = SystemFont.BODY_FONT;
	Font SMALL_FONT = SystemFont.SMALL_FONT;
	
	float ALPHA_1 = SystemFont.ALPHA_1;
	float ALPHA_2 = SystemFont.ALPHA_2;
	float ALPHA_3 = SystemFont.ALPHA_3;
}
