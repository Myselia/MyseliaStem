package cms.view.element;

import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cms.model.DataStore;
import cms.view.DisplayType;
import cms.view.GraphicsConstants;

public class GraphingParent extends JPanel implements GraphicsConstants{
	private static final long serialVersionUID = 1L;
	protected int barcount;
	protected DisplayType displaytype;
	
	public GraphingParent(DisplayType displaytype){
		super();
		enableInputMethods(true);
		setFocusable(true);
		setVisible(true);
		this.barcount = DataStore.core.length;
		this.displaytype = displaytype;
		this.setBorder(BorderFactory.createEmptyBorder(ADDRESS_GAP, ADDRESS_GAP, ADDRESS_GAP, ADDRESS_GAP));	
	}
	
	public int getBarCount(){
		return this.barcount;
	}
	
	public DisplayType getDisplayType(){
		return this.displaytype;
	}
	
	
}
