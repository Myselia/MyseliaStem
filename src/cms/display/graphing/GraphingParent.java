package cms.display.graphing;

import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cms.databank.OverLord;
import cms.display.GraphicsConstants;

public class GraphingParent extends JPanel implements GraphicsConstants{
	private static final long serialVersionUID = 1L;
	protected static int barcount;
	protected DisplayType displaytype;
	public static Container thisContainer;
	
	public GraphingParent(DisplayType displaytype){
		super();
		thisContainer = this;
		enableInputMethods(true);
		setFocusable(true);
		setVisible(true);
		this.barcount = OverLord.nodeCore.size();
		this.displaytype = displaytype;
		this.setBorder(BorderFactory.createEmptyBorder(ADDRESS_GAP, ADDRESS_GAP, ADDRESS_GAP, ADDRESS_GAP));	
	}
	
	public int getBarCount(){
		return this.barcount;
	}
	
	public DisplayType getDisplayType(){
		return this.displaytype;
	}
	
	public void setDisplayType(DisplayType dt){
		displaytype = dt;
	}
	
	public static void updateBarCount() {
		barcount = OverLord.nodeCore.size();
		thisContainer.revalidate();
	}
	
	
}
