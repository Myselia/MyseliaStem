package cms.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import cms.view.panel.ConsoleDisplay;
import cms.view.panel.GraphingMenu;
import cms.view.panel.GraphingPanel;
import cms.view.panel.LogDisplay;

public class Graph extends JPanel implements GraphicsConstants {
	private static final long serialVersionUID = 1L;
		
	private GraphingPanel graphingpanel = new GraphingPanel();
	private GraphingMenu graphingmenu = new GraphingMenu();

	public Graph() {
		this.setBackground(BACK);
		this.setPreferredSize(new Dimension(100, 390));
		this.setLayout(new BorderLayout());
		
		this.add(graphingpanel, BorderLayout.CENTER);
		this.add(graphingmenu, BorderLayout.EAST);
	}

}
