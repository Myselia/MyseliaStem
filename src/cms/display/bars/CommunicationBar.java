package cms.display.bars;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import cms.display.GraphicsConstants;
import cms.display.communication.ConsoleDisplay;
import cms.display.communication.LogDisplay;

/**
 * The <code>CommunicationBar</code> class is a JPanel with the Communication displays.
 * @author Eduard Parachivescu
 * @version 1
 * @see cms.display.communication.*
 * -tag @refactor Philippe Hebert
 */
public class CommunicationBar extends JPanel implements GraphicsConstants {
	private static final long serialVersionUID = 1L;
	
	private ConsoleDisplay console = ConsoleDisplay.getConsole();
	private LogDisplay log = LogDisplay.getLogger();

	/**
	 * Default CommunicationBar 
	 * Creates a JPanel comprised of a ConsoleDisplay and a LogDisplay
	 * @see cms.display.communication.ConsoleDisplay
	 * @see cms.display.communication.LogDisplay
	 */
	public CommunicationBar() {
		this.setBackground(BACK);
		this.setPreferredSize(new Dimension(100, 346));
		this.setLayout(new BorderLayout());
		
		this.add(console, BorderLayout.CENTER);
		this.add(log, BorderLayout.EAST);
	}
	
	/**
	 * Calls the method to set the focus on the console's JTextField
	 */
	public void setFocusOnTextField(){
		console.setFocus();
	}
	
	/**
	 * Forwards the intercepted event to the console's keylistener.
	 * @param e
	 */
	public void keyListener(KeyEvent e){
		console.keyPressed(e);
	}

}
