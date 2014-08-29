package cms.display.graphing;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cms.display.GraphicsConstants;
import cms.display.bars.GraphBar;
import cms.display.buttons.GraphingMenuButton;

/**
* The <code>GraphingMenu</code> class is a menu JPanel for the Graphing*.
* @author Eduard Parachivescu
* @version 1
* -tag @refactor Philippe Hebert
*/
public class GraphingMenu extends JPanel implements GraphicsConstants{
	private static final long serialVersionUID = 1L;
	private static GraphingMenu menu;
	
	private GraphingMenuButton[] button;
	private GraphingMenuButton lastButtonClicked;
	
	static{
		menu = new GraphingMenu();
	}
	
	/**
	 * GraphingMenu default constructor
	 * Creates a menu for the Graph
	 */
	private GraphingMenu(){
		this.setBackground(BACK);
		this.setPreferredSize(new Dimension(300, 200));
		this.setBorder(BorderFactory.createEmptyBorder(ADDRESS_GAP, ADDRESS_GAP, ADDRESS_GAP, ADDRESS_GAP));
		
		GridLayout menuLayout = new GridLayout(4, 1);
		menuLayout.setHgap(ADDRESS_GAP);
		menuLayout.setVgap(ADDRESS_GAP);
		this.setLayout(menuLayout);
		
		this.button = new GraphingMenuButton[DisplayType.size() * menuLayout.getColumns()];
		for (int i = 0; i < button.length; i++) {
			button[i] = new GraphingMenuButton(2*i+50);
			this.add("Button", button[i]);
		}
		
		DisplayType[] displaytypes = DisplayType.values();
		for(int i = 0; i < button.length; i++){
			button[i].setDisplayType(displaytypes[i % (displaytypes.length - 1)]);
		}
	}
	
	/**
	 * Accessor of static GraphingMenu menu
	 * @return Returns menu
	 */
	public static GraphingMenu getGraphMenu(){
		return menu;
	}
	
	/**
	 * Selects the target GraphingMenuButton
	 * @param nextLast
	 */
	public void unselectLast(GraphingMenuButton nextLast) {
		if(lastButtonClicked != null)
			lastButtonClicked.select(false);
		lastButtonClicked = nextLast;
		GraphBar.setDisplayType(lastButtonClicked.getDisplayType());
		
	}
	
	/**
	 * Accessor of the GraphingMenuButtons
	 * @param ID The array index to the chosen button. Specified by displaytype.getID().
	 * @return Returns the button specified by ID
	 */
	public GraphingMenuButton getGraphButton(int ID){
		return button[ID];
	}

}
