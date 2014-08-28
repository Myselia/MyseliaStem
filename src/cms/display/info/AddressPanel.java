package cms.display.info;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cms.databank.OverLord;
import cms.display.GraphicsConstants;
import cms.display.buttons.NodeButton;

/**
 * 
 * @author user
 * 
 */
public class AddressPanel extends JPanel implements GraphicsConstants {
	private static final long serialVersionUID = 1L;
	
	private static AddressPanel addresspanel;
	
	private int rows;
	private NodeButton[] button;
	private NodeButton lastButtonClicked;
	private Dimension size;
	
	
	static{
		addresspanel = new AddressPanel();
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
		return addresspanel;
	}

	public void unselectLast(NodeButton nextLast) {
		if(this.lastButtonClicked != null) {
			this.lastButtonClicked.select(false);
		}
		this.lastButtonClicked = nextLast;
	}
	
	public NodeButton nodeButton(int ID){
		return this.button[ID];
	}
	
	public void updateButtonList() {
		this.removeAll();
		this.rows = (int) Math.ceil((double) OverLord.nodeCore.size()/ ADDRESS_COLUMNS);
		this.button = new NodeButton[OverLord.nodeCore.size()];
		for (int i = 0; i < button.length; i++) {
			this.button[i] = new NodeButton(size, OverLord.nodeCore.get(i));
			this.add("Button", button[i]);
		}

		this.setPreferredSize(new Dimension(800, addresspanel.rows * 100));
		this.setMinimumSize(new Dimension(800, 100));
		this.setSize(this.getPreferredSize());
		this.revalidate();
		this.repaint();
	}
	
	public int getRows(){
		return this.rows;
	}

}
