package cms.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import cms.view.element.GraphingLevels;
import cms.view.panel.GraphingMenu;

public class Graph extends JPanel implements GraphicsConstants {
	private static final long serialVersionUID = 1L;

	private static GraphingLevels graphingpanel;
	private static GraphingMenu graphingmenu;
	private static DisplayType displaytype = DisplayType.TEMPERATURE;

	public Graph() {

		this.setBackground(BACK);
		this.setPreferredSize(new Dimension(100, 390));
		this.setLayout(new BorderLayout());
		graphingpanel = new GraphingLevels(displaytype);
		this.add(graphingpanel, BorderLayout.CENTER);
		graphingmenu = new GraphingMenu();
		this.add(graphingmenu, BorderLayout.EAST);

		Thread refresh = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(200);
						graphingpanel.repaint();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		});

		refresh.start();
	}
	
	public static void setDisplayType(DisplayType dt){
		displaytype = dt;
		graphingpanel.setDisplayType(displaytype);
	}
	
	public static DisplayType getDisplayType(){
		return displaytype;
	}

}
