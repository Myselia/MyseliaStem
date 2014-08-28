package cms.display.info;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cms.databank.OverLord;
import cms.display.GraphicsConstants;
import cms.display.buttons.NodeButton;

public class AddressPanel extends JPanel implements GraphicsConstants {
	private static final long serialVersionUID = 1L;
	private static AddressPanel singleton;
	private static Dimension size;
	
	private NodeButton[] button;
	private NodeButton lastButtonClicked;
	private int rows;
	
	static{
		singleton = new AddressPanel();
	}
	
	private AddressPanel() {
		this.setBackground(BACK);
		this.setBorder(BorderFactory.createEmptyBorder(ADDRESS_GAP, 0, ADDRESS_GAP, 0));
		this.rows = (int) Math.ceil((double) OverLord.nodeCore.size()/ ADDRESS_COLUMNS);

		this.setPreferredSize(new Dimension(800, rows * 100));
		this.setMinimumSize(new Dimension(800, 100));
		
		GridLayout coreLayout = new GridLayout(0, ADDRESS_COLUMNS);
		coreLayout.setHgap(ADDRESS_GAP);
		coreLayout.setVgap(ADDRESS_GAP);
		this.setLayout(coreLayout);
		
		this.button = new NodeButton[OverLord.nodeCore.size()];
		for (int i = 0; i < OverLord.nodeCore.size(); i++) {
			this.button[i] = new NodeButton(size, OverLord.nodeCore.get(i));
			this.add("Button", button[i]);
		}
	}
	
	public static AddressPanel getPanel(){
		return singleton;
	}

	public static void unselectLast(NodeButton nextLast) {
		if(singleton.lastButtonClicked != null) {
			singleton.lastButtonClicked.select(false);
		}
		singleton.lastButtonClicked = nextLast;
	}
	
	public static NodeButton nodeButton(int ID){
		return singleton.button[ID];
	}
	
	public static void updateButtonList() {
		singleton.removeAll();
		singleton.rows = (int) Math.ceil((double) OverLord.nodeCore.size()/ ADDRESS_COLUMNS);
		singleton.button = new NodeButton[OverLord.nodeCore.size()];
		for (int i = 0; i < singleton.button.length; i++) {
			singleton.button[i] = new NodeButton(size, OverLord.nodeCore.get(i));
			singleton.add("Button", singleton.button[i]);
		}

		singleton.setPreferredSize(new Dimension(800, singleton.rows * 100));
		singleton.setMinimumSize(new Dimension(800, 100));
		singleton.setSize(singleton.getPreferredSize());
		singleton.repaint();
		singleton.revalidate();
		singleton.repaint();
	}
	
	public int getRows(){
		return rows;
	}

}
