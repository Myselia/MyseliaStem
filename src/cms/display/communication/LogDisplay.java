package cms.display.communication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.PrintStream;

import cms.display.GraphicsConstants;
import cms.display.tools.TextAreaOutputStream;

public class LogDisplay extends ScrollingDisplayParent implements GraphicsConstants{
	private static final long serialVersionUID = 1L;
	private static PrintStream log;
	
	public LogDisplay() {
		super();

		TextAreaOutputStream taos = new TextAreaOutputStream(textpane, 1, 4000);
        log = new PrintStream(taos);
		textpane.setBackground(BACK);
		textpane.setForeground(Color.WHITE);
		textpane.setFont(new Font("Verdana", Font.PLAIN, 9));
		textpane.setPreferredSize(new Dimension(600, 300));
		textpane.setMinimumSize(new Dimension(600, 300));
		textpane.setEditable(false);

		setVisible(true);
	}
	
	public static void display(String str){
		log.println(str);
	}
	
}
