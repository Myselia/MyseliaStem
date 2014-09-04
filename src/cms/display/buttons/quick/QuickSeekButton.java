package cms.display.buttons.quick;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import cms.Main;
import cms.display.GraphicsConstants;
import cms.display.buttons.IconBuilder;

/**
 * The <code>QuickSeekButton</code> class is a JComponent button integrating the seek() call.
 * @author Eduard Parachivescu
 * @version 1
 * -tag @refactor Philippe Hebert
 */
public class QuickSeekButton extends JComponent implements MouseListener, GraphicsConstants {
	private static final long serialVersionUID = 1L;
	private static QuickSeekButton singleton;
	private static final int type = 100;
	private static boolean select;
	private Color foreground;
	public static int blink = 0;
	static{
		singleton = new QuickSeekButton();
	}
	
	/**
	 * QuickSeekButton default constructor
	 */
	private QuickSeekButton(){
		super();
		this.enableInputMethods(true);
		this.setFocusable(true);
		this.addMouseListener(this);
		this.setVisible(true);
	}
	
	/**
	 * Accessor of the singleton
	 * @return Returns the singleton
	 */
	public static QuickSeekButton getSeeker(){
		return singleton;
	}
	
	/**
	 * Manages if QuickSeekButton is selected or not.
	 * @param select Boolean. True if selected, false otherwise.
	 */
	public static void setSelect(boolean select){
		singleton.select = select;
		singleton.repaint();
		if(!select){
			QuickSeekButton.blink = 0;
		}
	}
	
	/**
	 * Manages if QuickSeekButton is selected or not. Toggles the boolean select.
	 */
	public static void setSelect(){
		setSelect(!singleton.select);
	}
	
	/**
	 * Calls the delegate paint method for the UI.
	 * Paint the QuickSeekButton according to if selected or not. 
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//Background
		if(select){
			blink = (++blink)%6;
			foreground = RUN;
		} else {
			foreground = ABS;
		}
		g.setColor(BACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		IconBuilder.icon(g, foreground, BACK, getWidth(), getHeight(), type, singleton, null);
	}

	/**
	 * MouseClicked Listener.
	 * On click, boots up the seeking signal if not selected. Ends it otherwise.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		setSelect(!select);
		if(select){
			Main.startBCastThread(Main.getBcastRunnable(), Main.getbCastCommunicator());
		} else {
			Main.getbCastCommunicator().interrupt();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

}
