package cms.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QuickBar extends JPanel implements GraphicsConstants {

	private int col = 5;
	
	public QuickBar(){
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(800,20));
		this.setBorder(BorderFactory.createEmptyBorder(ADDRESS_GAP, 0, ADDRESS_GAP, 0));
		GridLayout layout = new GridLayout(col, ADDRESS_COLUMNS);
		this.add(new JLabel("Test"));
		layout.setHgap(ADDRESS_GAP);
		layout.setVgap(ADDRESS_GAP);
		this.setLayout(layout);
	}
	
	public void updateTime(){}
}
