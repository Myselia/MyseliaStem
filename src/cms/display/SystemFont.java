package cms.display;

import java.awt.Font;
import java.io.File;

public  class SystemFont {

	public static  String OS;
	
	public static Font H1_FONT;
	public static Font H2_FONT;
	public static Font H3_FONT;
	public static Font NAV_FONT;
	public static Font BODY_FONT;
	public static Font SMALL_FONT;
	
	public static float ALPHA_1;
	public static float ALPHA_2;
	public static float ALPHA_3;
	
	static{
		OS = System.getProperty("os.name");
		if(OS.contains("Windows 8") || OS.contains("Windows 7")){
			H1_FONT = new Font("Segoe UI Light", Font.PLAIN, 42);
			H2_FONT = new Font("Segoe UI Light", Font.BOLD, 20);
			H3_FONT = new Font("Segoe UI Semibold", Font.PLAIN, 11);
			NAV_FONT = new Font("Segoe UI", Font.PLAIN, 20);
			BODY_FONT = new Font("Segoe UI SemiLight", Font.PLAIN, 11);
			SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 11);
			ALPHA_1 = 1.0f;
			ALPHA_2 = 0.8f;
			ALPHA_3 = 0.4f;
		}else if(OS.contains("Linux")){
			try{
				System.err.println(new File("..\\ubuntu-font-family-0.80\\Ubuntu-B.tff").exists());
				H1_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("..\\ubuntu-font-family-0.80\\Ubuntu-B.tff")).deriveFont((float)18);
				H2_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("..\\ubuntu-font-family-0.80\\Ubuntu-R.tff")).deriveFont((float)14);
				H3_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("..\\ubuntu-font-family-0.80\\Ubuntu-R.tff")).deriveFont((float)11);
				NAV_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("..\\ubuntu-font-family-0.80\\Ubuntu-L.tff")).deriveFont((float)11);
				BODY_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("..\\ubuntu-font-family-0.80\\Ubuntu-R.tff")).deriveFont((float)11);
				SMALL_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("..\\ubuntu-font-family-0.80\\Ubuntu-L.tff")).deriveFont((float)9);
			}catch(Exception e){
				e.printStackTrace();
			}
			ALPHA_1 = 1.0f;
			ALPHA_2 = ALPHA_1;
			ALPHA_3 = ALPHA_1;
		}else{
			//fuck Apple
			H1_FONT = new Font("Dialog", Font.PLAIN, 18);
			H2_FONT = new Font("Dialog", Font.PLAIN, 15);
			H3_FONT = H2_FONT;
			NAV_FONT = H2_FONT;
			BODY_FONT = new Font("Dialog", Font.BOLD, 12);
			SMALL_FONT = new Font("Dialog", Font.PLAIN, 10);
			ALPHA_1 = 1.0f;
			ALPHA_2 = ALPHA_1;
			ALPHA_3 = ALPHA_1;
		}
	}
	
	private SystemFont(){}
	
	
}
