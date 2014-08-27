package cms.display.graphing;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cms.display.GraphicsConstants;
import cms.display.bars.GraphBar;
import cms.display.buttons.GraphingMenuButton;

public class GraphingMenu extends JPanel implements GraphicsConstants{
	private static final long serialVersionUID = 1L;
	private static GraphingMenuButton[] button;
	private static GraphingMenuButton lastButtonClicked;
	
	public GraphingMenu(){
		this.setBackground(BACK);
		this.setPreferredSize(new Dimension(300, 200));
		this.setBorder(BorderFactory.createEmptyBorder(ADDRESS_GAP, ADDRESS_GAP, ADDRESS_GAP, ADDRESS_GAP));
		
		GridLayout menu = new GridLayout(4, 1);
		menu.setHgap(ADDRESS_GAP);
		menu.setVgap(ADDRESS_GAP);
		this.setLayout(menu);
		
		button = new GraphingMenuButton[menu.getRows() * menu.getColumns()];
		for (int i = 0; i < button.length; i++) {
			button[i] = new GraphingMenuButton(2*i+50);
			this.add("Button", button[i]);
		}
		
		button[0].setDisplayType(DisplayType.TEMPERATURE);
		button[1].setDisplayType(DisplayType.CPU);
		button[2].setDisplayType(DisplayType.RAM);
		button[3].setDisplayType(DisplayType.PARTICLES);
		
	}
	
	public static void unselectLast(GraphingMenuButton nextLast) {
		if(lastButtonClicked != null) {
			lastButtonClicked.select(false);
		}
		lastButtonClicked = nextLast;
		GraphBar.setDisplayType(lastButtonClicked.dt);
		
	}
	public static GraphingMenuButton nodeButton(int ID){
		return button[ID];
	}

}
