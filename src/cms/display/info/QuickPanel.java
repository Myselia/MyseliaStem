package cms.display.info;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import cms.display.GraphicsConstants;
import cms.display.buttons.quick.DBConnectButton;
import cms.display.buttons.quick.QuickSeekButton;
import cms.display.buttons.quick.TimeDisplayPanel;

/**
 * The <code>QuickPanel</code> class is a JPanel comprised of quick action buttons.
 * Static block initializes the singleton.
 * @author Eduard Parachivescu
 * @version 1
 * -tag @refactor Philippe Hebert
 */
public class QuickPanel extends JPanel implements GraphicsConstants {
	private static final long serialVersionUID = 1L;
	
	private static QuickPanel singleton;
	
	static{
		singleton = new QuickPanel();
	}
	
	/**
	 * QuickPanel default constructor.
	 * Creates a QuickPanel comprised of its buttons.
	 */
	private QuickPanel(){
		this.setBackground(GraphicsConstants.BACK);
		this.setPreferredSize(new Dimension(800,24));
		
		GridLayout layout = new GridLayout(0, 12);
		layout.setHgap(2);
		layout.setVgap(2);
		this.setLayout(layout);
		
		this.add("Button", TimeDisplayPanel.getTimer());
		this.add("Button", QuickSeekButton.getSeeker());
		this.add("Button", DBConnectButton.getConnector());
		
	}
	
	/**
	 * Accessor of the singleton.
	 * @return Returns the QuickPanel singleton.
	 */
	public static QuickPanel getPanel(){
		return singleton;
	}
	
	/**
	 * Calls the setSelect() method of the seekbutton.
	 * @see #seekbutton
	 */
	public static void setSeekSelect(){
		QuickSeekButton.setSelect();
	}
	
	public static void setDBConnSelect(){
		DBConnectButton.setSelect();
	}
}
