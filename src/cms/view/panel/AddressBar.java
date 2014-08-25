package cms.view.panel;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cms.model.DataStore;
import cms.view.GraphicsConstants;
import cms.view.button.NodeButton;

public class AddressBar extends JPanel implements GraphicsConstants {
	private static final long serialVersionUID = 1L;

	private static NodeButton[] button;
	private static NodeButton lastButtonClicked;
	private static Dimension size;
	private static Container thisContainer;
	private static int rows;
	
	public AddressBar() {
		thisContainer = this;
		this.setBackground(BACK);
		this.setBorder(BorderFactory.createEmptyBorder(ADDRESS_GAP, 0, ADDRESS_GAP, 0));
		rows = (int) Math.ceil((double) DataStore.coreA.size()/ ADDRESS_COLUMNS);

		this.setPreferredSize(new Dimension(800, rows * 100));
		GridLayout coreLayout = new GridLayout(0, ADDRESS_COLUMNS);
		coreLayout.setHgap(ADDRESS_GAP);
		coreLayout.setVgap(ADDRESS_GAP);

		this.setLayout(coreLayout);
		
		button = new NodeButton[DataStore.coreA.size()];
		for (int i = 0; i < DataStore.coreA.size(); i++) {
			button[i] = new NodeButton(size, DataStore.coreA.get(i));
			this.add("Button", button[i]);
		}
	}

	public static void unselectLast(NodeButton nextLast) {
		if(lastButtonClicked != null) {
			lastButtonClicked.select(false);
		}
		lastButtonClicked = nextLast;
	}
	
	public static NodeButton nodeButton(int ID){
		return button[ID];
	}
	
	public static void updateButtonList() {
		thisContainer.removeAll();
		rows = (int) Math.ceil((double) DataStore.coreA.size()/ ADDRESS_COLUMNS);
		button = new NodeButton[DataStore.coreA.size()];
		for (int i = 0; i < button.length; i++) {
			button[i] = new NodeButton(size, DataStore.coreA.get(i));
			thisContainer.add("Button", button[i]);
		}
		thisContainer.revalidate();
	}
	
	public int getRows(){
		return rows;
	}

}
