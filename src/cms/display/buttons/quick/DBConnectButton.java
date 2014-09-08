package cms.display.buttons.quick;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import cms.Main;
import cms.control.methods.CMSCommand;
import cms.databank.OverLord;
import cms.display.GraphicsConstants;
import cms.display.buttons.IconBuilder;
import cms.display.info.QuickPanel;

/**
 * The <code>QuickSeekButton</code> class is a JComponent button integrating the seek() call.
 * @author Eduard Parachivescu
 * @version 1
 * -tag @refactor Philippe Hebert
 */
public class DBConnectButton extends JComponent implements MouseListener, GraphicsConstants {
	private static final long serialVersionUID = 1L;
	private static DBConnectButton singleton;
	private static final int type = 150;
	private boolean select;
	private Color foreground;
	
	static{
		singleton = new DBConnectButton();
	}
	
	/**
	 * DBConnect default constructor
	 */
	private DBConnectButton(){
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
	public static DBConnectButton getConnector(){
		return singleton;
	}
	
	/**
	 * Manages if QuickSeekButton is selected or not.
	 * @param select Boolean. True if selected, false otherwise.
	 */
	public static void setSelect(boolean select){
		singleton.select = select;
		singleton.repaint();
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
			foreground = RUN;
		} else {
			foreground = ABS;
		}
		g.setColor(BEVEL);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		IconBuilder.icon(g, foreground, BEVEL, getWidth(), getHeight(), type, null, null);
	}

	/**
	 * MouseClicked Listener.
	 * On click, boots up the seeking signal if not selected. Ends it otherwise.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		setSelect(!select);
		if(select){
			for (int i = 0; i < OverLord.dbCore.size(); i++) {
				OverLord.dbCore.get(i).startConnection();
			}
		} else {
			for (int i = 0; i < OverLord.dbCore.size(); i++) {
				OverLord.dbCore.get(i).closeConnection();
			}
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
