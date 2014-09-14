package cms.display.layers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import cms.display.GraphicsConstants;
import cms.display.ProgramWindow;
import cms.display.graphing.GraphingMenu;

public class SideMenu extends JPanel implements GraphicsConstants, MouseListener {

	private static final long serialVersionUID = 1L;
	private static SideMenu singleton;
	private static GraphingMenu graphingmenu;
	private Point location;
	private volatile boolean open = false;
	
	static{
		graphingmenu = GraphingMenu.getGraphMenu();
		singleton = new SideMenu();
	}
	
	private SideMenu(){
		super();
		this.location = new Point(ProgramWindow.getDeviceWidth(), 0);
		this.setBackground(BEVEL);
		this.setPreferredSize(new Dimension((int)(ProgramWindow.getDeviceWidth() * (346/1600.0)), ProgramWindow.getDeviceHeight()));
		this.setLayout(new BorderLayout());
		this.add(graphingmenu, BorderLayout.CENTER);
		this.addMouseListener(this);
		this.setVisible(true);
	}
	
	public static void toggle(boolean open){
		singleton.setOpen(open);
		Thread displayMenu = new Thread(new Runnable(){
			
			@Override
			public void run() {
				if(singleton.getLocation().x == ProgramWindow.getDeviceWidth()){
					System.out.println("Hello from side, " + singleton.getLocation().x);
					while(singleton.getLocation().x >= (ProgramWindow.getDeviceWidth()-200)){
						System.out.println(singleton.getLocation().x + "Parce que:" + graphingmenu.getWidth());
						singleton.setLocation(singleton.getLocation().x - 1, 0);
						singleton.repaint();
						try{
							Thread.sleep(1);
						}catch(InterruptedException e){
							e.printStackTrace();
						}
					}
				}else if(!singleton.open){
					while(singleton.getLocation().x <= ProgramWindow.getDeviceWidth()){
						singleton.setLocation(singleton.getLocation().x + 1, 0);
						try{
							Thread.sleep(1);
						}catch(InterruptedException e){
							e.printStackTrace();
						}
					}
				}
			}
			
		});
		displayMenu.start();
	}
	
	private void setOpen(boolean open){
		this.open = open;
	}
	
	public static SideMenu getSideMenu(){
		return singleton;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		SideMenu.toggle(false);
	}
}
