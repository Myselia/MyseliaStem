package cms.display;

import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import cms.display.bars.CommunicationBar;
import cms.display.bars.GraphBar;
import cms.display.bars.InfoBar;

/**
 * The <code>ProgramWindow</code> class is the application main JFrame.
 * Static block initializes environment and singleton of window.
 * @author Eduard Parachivescu
 * @author Philippe Hebert
 * @version 1
 * -tag @refactor Philippe Hebert
 */
public class ProgramWindow extends JFrame implements MouseMotionListener, GraphicsConstants {
	private static final long serialVersionUID = 1L;
	
	private static GraphicsEnvironment env;
	private static GraphicsDevice[] devices;
	@SuppressWarnings("unused")
	private static JFrame frame;
	private static int width; 

	private GraphicsDevice device;
	private boolean isFullScreen = false;
	private JPanel[] panels;
	
	static{
		env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		devices = env.getScreenDevices();
		frame = new ProgramWindow(devices[0]);
		width = Toolkit.getDefaultToolkit().getScreenSize().width;
	}
	
	/**
	 * Creates a InfoBar with specified device.
	 * @param device The GraphicsDevice
	 * @see java.awt.GraphicsDevice
	 */
	private ProgramWindow(GraphicsDevice device) {
		super(device.getDefaultConfiguration());
		this.device = device;
		this.setTitle(TITLE);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.initContent();
		this.addMouseMotionListener(this);
	}

	/**
	 * Forces class initialization
	 * @see #static
	 */
	public final static void initEnvironment() {}

	/**
	 * Initializes the content of the panels
	 * @see cms.display.bars.CommunicationBar
	 * @see cms.display.bars.InfoBar
	 * @see cms.display.bars.GraphBar
	 */
	private void initContent() {
		this.isFullScreen = device.isFullScreenSupported();
		this.setUndecorated(isFullScreen);
		this.setResizable(!isFullScreen);
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){}
		/*Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle winSize = env.getMaximumWindowBounds();
		Dimension fullscreen = new Dimension(winSize.width, winSize.height);
		this.setUndecorated(true);
		this.setSize(fullscreen.width, fullscreen.height);
		this.validate();
		this.setVisible(true);*/	
		
		if (isFullScreen) {
			device.setFullScreenWindow(this);
			this.validate();
		} else {
			this.pack();
		}

		JLayeredPane layeredPane = this.getLayeredPane();
		layeredPane.setLayout(new BorderLayout());

		this.panels = new JPanel[3];
		this.panels[0] = new CommunicationBar();
		layeredPane.add(panels[0], BorderLayout.SOUTH, JLayeredPane.DEFAULT_LAYER);
		this.panels[1] = InfoBar.getInfoBar();
		layeredPane.add(panels[1], BorderLayout.NORTH, JLayeredPane.DEFAULT_LAYER);
		this.panels[2] = GraphBar.getGraphBar();
		layeredPane.add(panels[2], BorderLayout.CENTER, JLayeredPane.DEFAULT_LAYER);
		((CommunicationBar)this.panels[0]).setFocusOnTextField();
		this.setVisible(true);
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point pt = e.getPoint();
		//TODO
		if(pt.x == width-1){
			System.err.println("Time: " + System.currentTimeMillis() + ", x = right side");
		}else if(pt.y == 0){
			System.err.println("Time: " + System.currentTimeMillis() + ", y = 0");
		}
	}

}
