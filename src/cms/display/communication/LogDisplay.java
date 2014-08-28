package cms.display.communication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.PrintStream;

import cms.display.GraphicsConstants;
import cms.display.tools.TextAreaOutputStream;

/**
 * The <code>LogDisplay</code> class is a log-like JPanel inheriting from ScrollingDisplayParent.
 * Static block initializes the ConsoleDisplay singleton
 * @author Philippe Hebert
 * @author Eduard Parachivescu
 * @version 1
 * -tag @refactor Philippe Hebert
 */
public class LogDisplay extends ScrollingDisplayParent implements GraphicsConstants{
	private static final long serialVersionUID = 1L;
	private static LogDisplay logger;
	private PrintStream log;
	
	static{
		logger = new LogDisplay();
	}
	
	private LogDisplay() {
		super();
		TextAreaOutputStream taos = new TextAreaOutputStream(textpane, 1, 4000);
        this.log = new PrintStream(taos);
        
		this.textpane.setForeground(Color.WHITE);
		this.textpane.setFont(new Font("Verdana", Font.PLAIN, 9));
		this.textpane.setPreferredSize(new Dimension(600, 300));
		this.textpane.setMinimumSize(new Dimension(600, 300));
		this.textpane.setEditable(false);

		this.setVisible(true);
	}
	
	public static LogDisplay getLogger(){
		return logger;
	}
	
	public static void display(String str){
		logger.log.println(str);
	}
	
}
