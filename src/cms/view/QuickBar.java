package cms.view;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class QuickBar extends JPanel implements GraphicsConstants {

	private int rows = 5;
	
	public QuickBar(){
		this.setBackground(BACK);
		this.setBorder(BorderFactory.createEmptyBorder(ADDRESS_GAP, 0, ADDRESS_GAP, 0));
		GridLayout layout = new GridLayout(rows, ADDRESS_COLUMNS);
		layout.setHgap(ADDRESS_GAP);
		layout.setVgap(ADDRESS_GAP);
		this.setLayout(layout);
	}
}
