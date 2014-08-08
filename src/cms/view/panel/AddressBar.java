package cms.view.panel;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cms.model.DataStore;
import cms.view.GraphicsConstants;
import cms.view.element.NodeButton;

public class AddressBar extends JPanel implements GraphicsConstants {
	private static final long serialVersionUID = 1L;

	private static NodeButton[] button;
	private static NodeButton lastButtonClicked;
	private Dimension size;

	public AddressBar() {
		this.setBackground(BACK);
		this.setBorder(BorderFactory.createEmptyBorder(ADDRESS_GAP, 0, ADDRESS_GAP, 0));
		int rows = (int) Math.ceil((double) DataStore.core.length
				/ ADDRESS_COLUMNS);

		this.setPreferredSize(new Dimension(800, rows * 100));
		GridLayout coreLayout = new GridLayout(rows, ADDRESS_COLUMNS);
		coreLayout.setHgap(ADDRESS_GAP);
		coreLayout.setVgap(ADDRESS_GAP);

		this.setLayout(coreLayout);
		
		button = new NodeButton[DataStore.core.length];
		for (int i = 0; i < DataStore.core.length; i++) {
			button[i] = new NodeButton(size, DataStore.core[i]);
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

}
