package cms.view;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ProgramWindow extends JFrame implements GraphicsConstants {
	private static final long serialVersionUID = 1L;

	private static GraphicsEnvironment env;
	private static GraphicsDevice[] devices;
	private static JFrame frame;

	private GraphicsDevice device;
	private boolean isFullScreen = false;

	public ProgramWindow(GraphicsDevice device) {
		super(device.getDefaultConfiguration());
		this.device = device;
		setTitle(TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void init() {
		env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		devices = env.getScreenDevices();
		frame = new ProgramWindow(devices[0]);
		((ProgramWindow) frame).begin();
	}

	private void begin() {
		this.isFullScreen = device.isFullScreenSupported();
		setUndecorated(isFullScreen);
		setResizable(!isFullScreen);
		if (isFullScreen) {
			device.setFullScreenWindow(this);
			validate();
		} else {
			pack();
		}

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());

		JPanel[] panels = new JPanel[3];
		
		panels[0] = new Communication();
		contentPane.add(panels[0], BorderLayout.SOUTH);
		panels[1] = new AddressBar();
		contentPane.add(panels[1], BorderLayout.NORTH);
		panels[2] = new Graph();
		contentPane.add(panels[2], BorderLayout.CENTER);
		

		setVisible(true);
	}

}
