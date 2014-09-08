package cms.display.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import cms.display.GraphicsConstants;
import cms.display.graphing.DisplayType;
import cms.display.graphing.GraphingMenu;

/**
 * The <code>GraphingMenuButton</code> class is a JComponent button associated with the Graphing* GUI.
 * @author Eduard Parachivescu
 * @version 1
 * -tag @refactor Philippe Hebert
 * @see cms.display.graphing.*
 */
public final class GraphingMenuButton extends JComponent implements MouseListener, GraphicsConstants{
	private static final long serialVersionUID = 1L;
	private int type;
	private DisplayType displaytype;
	private boolean select = false;
	private boolean hover = false;
	private Color background;
	
	/**
	 * GraphingMenuButton default constructor
	 * @param type Something not used.
	 */
	public GraphingMenuButton(int type){
		super();
		this.enableInputMethods(true);
		this.setFocusable(true);
		this.type = type;
		this.addMouseListener(this);
		this.setVisible(true);
	}
	
	/**
	 * Manages if button is selected or not
	 * @param select True if selected, false otherwise
	 */
	public void select(boolean select){
		this.select = select;
		this.repaint();
	}
	
	/**
	 * Calls the delegate paint method for the UI.
	 * Paint the NodeButton according to if selected or not.
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//Background
		if(select){
			background = SELECTED;
		}else if(hover){
			background = HOVER;
		}else{
			background = UNSELECTED;
		}
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), 50);
		
		IconBuilder.icon(g, ABS, background, getWidth(), 50, type, null, null);
	}
	
	/**
	 * Accessor of the GraphingMenuButton's displaytype
	 * @return Return the instance's displaytype
	 */
	public DisplayType getDisplayType(){
		return this.displaytype;
	}
	
	/**
	 * Mutator of the GraphingMenuButton's displaytype
	 * @param displaytype
	 */
	public void setDisplayType(DisplayType displaytype){
		this.displaytype = displaytype;
	}

	/**
	 * MouseClicked Listener
	 * Paint the GraphingMenuButton according to if selected or not.
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		GraphingMenu.getGraphMenu().unselectLast(this);
		select(true);
		if(select){
			System.out.println("g>Display: " + displaytype);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if(!select){
			this.hover = true;
			this.repaint();
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.hover = false;
		if(!select)
			this.repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	
}
