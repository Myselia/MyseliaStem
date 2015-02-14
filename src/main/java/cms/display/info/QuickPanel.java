package cms.display.info;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import cms.display.GraphicsConstants;
import cms.display.buttons.quick.DBConnectButton;
import cms.display.buttons.quick.EmptyButton;
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
		
		GridLayout layout = new GridLayout(0, 11);
		layout.setHgap(2);
		layout.setVgap(2);
		this.setLayout(layout);
		
		EmptyButton[] empties = new EmptyButton[9];
		for(int i = 0; i < empties.length; i++){
			empties[i] = new EmptyButton();
		}
		
		this.add("Button", empties[0]);
		this.add("Button", empties[1]);
		this.add("Button", empties[2]);
		this.add("Button", empties[3]);
		this.add("Button", empties[4]);
		this.add("Button", TimeDisplayPanel.getTimer());
		this.add("Button", empties[5]);
		this.add("Button", empties[6]);
		this.add("Button", empties[7]);
		this.add("Button", empties[8]);
		this.add("Button", QuickSeekButton.getSeeker());
		
		
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
