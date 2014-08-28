package cms.display.bars;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import cms.display.GraphicsConstants;
import cms.display.info.AddressPanel;
import cms.display.info.QuickPanel;

/**
 * The <code>InfoBar</code> class is a JPanel comprised of the QuickPanel and AddressPanel.
 * Static block initializes the singleton.
 * @author Eduard Parachivescu
 * @version 1
 * -tag @refactor Philippe Hebert
 * @see cms.display.info.*
 */
public class InfoBar extends JPanel implements GraphicsConstants, Runnable {

	private static final long serialVersionUID = 1L;
	private static InfoBar infobar;
	
	private QuickPanel quickpanel;
	private AddressPanel addresspanel;
	private Thread refresh;
	
	static{
		infobar = new InfoBar();
	}
	
	/**
	 * InfoBar default constructor.
	 * Creates an InfoBar comprised of a quickpanel and an addresspanel.
	 * @see cms.display.info.*
	 */
	private InfoBar() {	
		this.setBackground(BACK);
		this.setPreferredSize(new Dimension(800, 130));
		this.setMinimumSize(new Dimension(800, 130));
		this.setLayout(new BorderLayout());
		
		this.quickpanel = new QuickPanel();
		this.add(quickpanel, BorderLayout.NORTH);
		this.addresspanel = AddressPanel.getPanel();
		this.add(addresspanel, BorderLayout.SOUTH);
		this.refresh = new Thread(this);
		refresh.start();
	}

	/**
	 * Runnable behavior.
	 * Repaints the graph every 0.2 seconds.
	 */
	@Override
	public void run() {
		while(true){
			try{
				Thread.sleep(200);
				addresspanel.repaint();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Accessor of infobar singleton.
	 * Runs after the static block, so infobar is always initialized.
	 * @return Returns the InfoBar
	 */
	public static InfoBar getInfoBar(){
		return InfoBar.infobar;
	}
	
	/**
	 * Accessor of infobar's quickpanel.
	 * @return Returns the QuickPanel of the InfoBar
	 */
	public QuickPanel getQuickPanel(){
		return this.quickpanel;
	}
	
	/**
	 * Accessor of infobar's addresspanel.
	 * @return Returns the QuickPanel of the InfoBar
	 */
	public AddressPanel getAddressPanel(){
		return this.addresspanel;
	}
	
}