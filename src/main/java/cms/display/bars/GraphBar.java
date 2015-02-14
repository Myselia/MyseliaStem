package cms.display.bars;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import cms.display.GraphicsConstants;
import cms.display.graphing.DisplayType;
import cms.display.graphing.GraphingHistogram;
import cms.display.graphing.GraphingLevels;
import cms.display.graphing.GraphingMenu;
import cms.display.graphing.GraphingParent;

/**
 * The <code>GraphBar</code> class is a JPanel with the Graph displays.
 * Includes method to switch between different Graphing* displays.
 * Static block initializes the Graphing components
 * @author Eduard Parachivescu
 * @author Philippe Hebert
 * @version 1
 * -tag @refactor Philippe Hebert
 * @see cms.display.graphing.*
 */
public class GraphBar extends JPanel implements GraphicsConstants, Runnable {
	private static final long serialVersionUID = 1L;

	private static GraphBar singleton;
	private static GraphingLevels graphinglevels;
	private static GraphingHistogram graphinghistogram;
	private static GraphingMenu graphingmenu;
	private volatile static GraphingParent graph;
	private static DisplayType displaytype = DisplayType.TEMPERATURE;
	private static boolean isHistogram = false;
	
	private Thread refresh;

	static{
		graphinglevels = new GraphingLevels(displaytype);
		graphinghistogram = new GraphingHistogram(displaytype);
		graph = graphinglevels;
		graphingmenu = GraphingMenu.getGraphMenu();
		singleton = new GraphBar();
	}
	
	/**
	 * Default GraphBar constructor.
	 * Creates a GraphBar comprised of a graph and a graph-related menu.
	 * @param device The GraphicsDevice
	 * @see cms.display.graphing.*
	 */
	private GraphBar() {
		this.setBackground(BACK);
		this.setPreferredSize(new Dimension(100, 390));
		this.setLayout(new BorderLayout());
		this.add(graph, BorderLayout.CENTER);
		this.add(graphingmenu, BorderLayout.EAST);
		this.refresh = new Thread(this);
		this.refresh.start();
	}
	
	/**
	 * Accessor of the Singleton.
	 * Creates a singleton if not already created.
	 * @return Returns the GraphBar element created.
	 */
	public static GraphBar getGraphBar(){
		return singleton;
	}
	
	/**
	 * Sets the DisplayType displayed by the graphs.
	 * @param dt The new DisplayType
	 * @see cms.display.graphing.DisplayType
	 */
	public static void setDisplayType(DisplayType dt){
		displaytype = dt;
		graphinglevels.setDisplayType(displaytype);
		graphinghistogram.setDisplayType(displaytype);
	}
	
	/**
	 * Accessor of the current DisplayType
	 * @return Returns the current DisplayType
	 */
	public static DisplayType getDisplayType(){
		return displaytype;
	}
	
	/**
	 * Verifies if GraphBar is currently displaying the Histogram
	 * @return True if Histogram, false otherwise.
	 */
	public static boolean isHistogram(){
		return GraphBar.isHistogram;
	}
	
	/**
	 * Toggles the GraphBar graph display from current setting to the other setting
	 * @return Returns isHistogram()
	 * @see #isHistogram()
	 */
	public static boolean toggle(){
		GraphBar.isHistogram = !GraphBar.isHistogram;
		if(isHistogram){
			singleton.remove(graph);
			graph = graphinghistogram;
			singleton.add(graph);
			singleton.revalidate();
		}else{
			singleton.remove(graph);
			graph = graphinglevels;
			singleton.add(graph);
			singleton.revalidate();
		}
		return GraphBar.isHistogram;
	}

	/**
	 * Runnable behavior.
	 * Repaints the graph every 0.2 seconds.
	 */
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(200);
				graph.repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}