package cms.display.graphing;

import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cms.databank.OverLord;
import cms.display.GraphicsConstants;

/**
* The <code>GraphingParent</code> class is a Graphing JPanel used as superclass of Histogram and Levels.
* @author Philippe Hebert
* @author Eduard Parachivescu
* @version 1
* -tag @refactor Philippe Hebert
*/
public abstract class GraphingParent extends JPanel implements GraphicsConstants{
	private static final long serialVersionUID = 1L;
	protected static int barcount;
	protected static Container container;
	protected DisplayType displaytype;
	
	/**
	 * GraphingParent default constructor
	 * Creates a superclass Graph with DisplayType displaytype.
	 * @param displaytype
	 */
	protected GraphingParent(DisplayType displaytype){
		super();
		barcount = OverLord.nodeCore.size();
		container = this;
		this.enableInputMethods(true);
		this.setFocusable(true);
		this.setVisible(true);
		this.displaytype = displaytype;
		this.setBorder(BorderFactory.createEmptyBorder(ADDRESS_GAP, ADDRESS_GAP, ADDRESS_GAP, ADDRESS_GAP));	
	}
	
	/**
	 * Accessor of static int barcount
	 * @return Returns barcount
	 */
	public int getBarCount(){
		return GraphingParent.barcount;
	}
	
	/**
	 * Accessor of instance's DisplayType
	 * @return
	 */
	public DisplayType getDisplayType(){
		return this.displaytype;
	}
	
	/**
	 * Mutator of instance's DisplayType
	 * @param displaytype New DisplayType
	 */
	public void setDisplayType(DisplayType displaytype){
		this.displaytype = displaytype;
	}
	
	/**
	 * Mutator of static int barcount.
	 * Revalidates the container later on.
	 */
	public static void updateBarCount() {
		barcount = OverLord.nodeCore.size();
		container.revalidate();
	}
	
	
}
