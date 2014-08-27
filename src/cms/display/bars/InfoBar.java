package cms.display.bars;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import cms.display.GraphicsConstants;
import cms.display.info.AddressPanel;
import cms.display.info.QuickPanel;

public class InfoBar extends JPanel implements GraphicsConstants {

	private static final long serialVersionUID = 1L;

	public static QuickPanel quickbar;
	public static AddressPanel addressbar;
	
	public InfoBar() {

		this.setBackground(BACK);
		this.setPreferredSize(new Dimension(800, 130));
		this.setMinimumSize(new Dimension(800, 130));
		this.setLayout(new BorderLayout());
		
		quickbar = new QuickPanel();
		this.add(quickbar, BorderLayout.NORTH);
		addressbar = new AddressPanel();
		//this.setPreferredSize(new Dimension(800, 24 + addressbar.getRows() * 100));
		this.add(addressbar, BorderLayout.SOUTH);

		Thread refresh = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(200);
						addressbar.repaint();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		});

		refresh.start();
	}
	
	/*public static void setDisplayType(DisplayType dt){
		displaytype = dt;
		graphingpanel.setDisplayType(displaytype);
	}
	
	public static DisplayType getDisplayType(){
		return displaytype;
	}
*/
	
}
