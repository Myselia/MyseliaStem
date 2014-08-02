package cms.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import cms.view.panel.GraphingMenu;
import cms.view.panel.GraphingPanel;

public class Graph extends JPanel implements GraphicsConstants {
	private static final long serialVersionUID = 1L;

	private static GraphingPanel graphingpanel;
	private static GraphingMenu graphingmenu;
	private static DisplayType displaytype = DisplayType.TEMPERATURE;

	public Graph() {

		this.setBackground(BACK);
		this.setPreferredSize(new Dimension(100, 390));
		this.setLayout(new BorderLayout());
		graphingpanel = new GraphingPanel(displaytype);
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
