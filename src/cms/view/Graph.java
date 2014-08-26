package cms.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import cms.view.element.GraphingHistogram;
import cms.view.element.GraphingLevels;
import cms.view.element.GraphingParent;
import cms.view.panel.GraphingMenu;

public class Graph extends JPanel implements GraphicsConstants {
	private static final long serialVersionUID = 1L;

	private static Graph singleton = null;
	private static GraphingLevels graphinglevels;
	private static GraphingHistogram graphinghistogram;
	private static GraphingMenu graphingmenu;
	private volatile static GraphingParent graph;
	private static DisplayType displaytype = DisplayType.TEMPERATURE;
	private static boolean isHistogram = false;

	private Graph() {
		this.setBackground(BACK);
		this.setPreferredSize(new Dimension(100, 390));
		this.setLayout(new BorderLayout());
		
		graphinglevels = new GraphingLevels(displaytype);
		graphinghistogram = new GraphingHistogram(displaytype);
		graph = graphinglevels;
		
		this.add(graph, BorderLayout.CENTER);
		graphingmenu = new GraphingMenu();
		this.add(graphingmenu, BorderLayout.EAST);

		Thread refresh = new Thread(new Runnable() {
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
		});

		refresh.start();
	}
	
	public static Graph setGraph(){
		if(singleton == null)
			singleton = new Graph();
			return singleton;
	}
	
	public static void setDisplayType(DisplayType dt){
		displaytype = dt;
		graphinglevels.setDisplayType(displaytype);
		graphinghistogram.setDisplayType(displaytype);
	}
	
	public static DisplayType getDisplayType(){
		return displaytype;
	}
	
	public static boolean isHistogram(){
		return Graph.isHistogram;
	}
	
	public static boolean toggle(){
		Graph.isHistogram = !Graph.isHistogram;
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
		return Graph.isHistogram;
	}

}