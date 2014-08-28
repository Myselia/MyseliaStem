package cms.display.info;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cms.databank.OverLord;
import cms.display.GraphicsConstants;
import cms.display.buttons.NodeButton;

/**
 * The <code>AddressPanel</code> class is a JPanel comprised of the buttons for each node.
 * Static block initializes the singleton.
 * @author Eduard Parachivescu
 * @version 1
 * -tag @refactor Philippe Hebert
 */
public class AddressPanel extends JPanel implements GraphicsConstants {
	private static final long serialVersionUID = 1L;
	private static AddressPanel singleton;
	private static Dimension size;
	
	private NodeButton[] button;
	private NodeButton lastButtonClicked;
	private int rows;
	
	static{
		singleton = new AddressPanel();
	}
	
	/**
	 * AddressPanel default constructor
	 * Creates the AddressPanel singleton with all of its NodeButtons
	 */
	private AddressPanel() {
		this.setBackground(BACK);
		this.setBorder(BorderFactory.createEmptyBorder(ADDRESS_GAP, 0, ADDRESS_GAP, 0));
		this.rows = (int) Math.ceil((double) OverLord.nodeCore.size()/ ADDRESS_COLUMNS);

		this.setPreferredSize(new Dimension(800, rows * 100));
		this.setMinimumSize(new Dimension(800, 100));
		
		GridLayout coreLayout = new GridLayout(0, ADDRESS_COLUMNS);
		coreLayout.setHgap(ADDRESS_GAP);
		coreLayout.setVgap(ADDRESS_GAP);
		this.setLayout(coreLayout);
		
		this.button = new NodeButton[OverLord.nodeCore.size()];
		for (int i = 0; i < OverLord.nodeCore.size(); i++) {
			this.button[i] = new NodeButton(size, OverLord.nodeCore.get(i));
			this.add("Button", button[i]);
		}
	}
	
	/**
	 * Accessor to the singleton
	 * @return Returns the singleton
	 */
	public static AddressPanel getPanel(){
		return singleton;
	}

	/**
	 * Change NodeButton current selection
	 * @param nextLast Next button to be selected
	 */
	public static void unselectLast(NodeButton nextLast) {
		if(singleton.lastButtonClicked != null) {
			singleton.lastButtonClicked.select(false);
		}
		singleton.lastButtonClicked = nextLast;
	}
	
	/**
	 * Accessor of a specific NodeButton of id ID
	 * @param ID The ID of the Node accessed
	 * @return Returns the Node of id ID
	 */
	public static NodeButton nodeButton(int ID){
		return singleton.button[ID];
	}
	
	/**
	 * Updates the NodeButtons in the AddressPanel.
	 * Removes all of them, then resizes to accept the new buttons.
	 */
	public static void updateButtonList() {
		singleton.removeAll();
		singleton.rows = (int) Math.ceil((double) OverLord.nodeCore.size()/ ADDRESS_COLUMNS);
		singleton.button = new NodeButton[OverLord.nodeCore.size()];
		for (int i = 0; i < singleton.button.length; i++) {
			singleton.button[i] = new NodeButton(size, OverLord.nodeCore.get(i));
			singleton.add("Button", singleton.button[i]);
		}

		singleton.setPreferredSize(new Dimension(800, singleton.rows * 100));
		singleton.setMinimumSize(new Dimension(800, 100));
		singleton.setSize(singleton.getPreferredSize());
		singleton.repaint();
		singleton.revalidate();
		singleton.repaint();
	}
	
}
