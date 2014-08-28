package cms.display;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
public class ProgramWindow extends JFrame implements GraphicsConstants {
	private static final long serialVersionUID = 1L;
	
	private static GraphicsEnvironment env;
	private static GraphicsDevice[] devices;
	@SuppressWarnings("unused")
	private static JFrame frame;

	private GraphicsDevice device;
	private boolean isFullScreen = false;
	private JPanel[] panels;
	
	static{
		env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		devices = env.getScreenDevices();
		frame = new ProgramWindow(devices[0]);
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
		if (isFullScreen) {
			device.setFullScreenWindow(this);
			this.validate();
		} else {
			this.pack();
		}

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());

		this.panels = new JPanel[3];
		this.panels[0] = new CommunicationBar();
		contentPane.add(panels[0], BorderLayout.SOUTH);
		this.panels[1] = InfoBar.getInfoBar();
		contentPane.add(panels[1], BorderLayout.NORTH);
		this.panels[2] = GraphBar.getGraphBar();
		contentPane.add(panels[2], BorderLayout.CENTER);
		((CommunicationBar)this.panels[0]).setFocusOnTextField();
		this.setVisible(true);
	}

}
