package cms.display.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import cms.databank.structures.Node;
import cms.databank.structures.NodeState;
import cms.display.GraphicsConstants;
import cms.display.info.AddressPanel;

/**
 * The <code>NodeButton</code> class is a JComponent displaying a Node.
 * @author Eduard Parachivescu
 * @author Philippe Hebert
 * @version 1
 * -tag @refactor Philippe Hebert
 */
public class NodeButton extends JComponent implements MouseListener, GraphicsConstants {
	private static final long serialVersionUID = 1L;
	private Node core;
	private boolean select = false; 
	private boolean info = false;
	public int blink = 0;
	private Color background, foreground;
	
	/**
	 * NodeButton default constructor.
	 * @param size Dimension of the NodeButton
	 * @param core Node associated with the NodeButton
	 */
	public NodeButton(Dimension size, Node core) {
		super();
		this.enableInputMethods(true);
		this.setFocusable(true);
		this.core = core;
		this.addMouseListener(this);
		this.setPreferredSize(size);
		this.setVisible(true);
	}

	/**
	 * Manages if NodeButton is selected or not.
	 * @param select Boolean. True if selected, false otherwise.
	 */
	public void select(boolean select) {
		this.core.setSelected(select);
		this.select = select;
		this.repaint();
	}
	
	public void setBlink(boolean blinkbool){
		if(blinkbool){
			this.blink = 2;
		}
	}
	
	/**
	 * Accessor of the Node associated with object NodeButton
	 * @return Node associated with object NodeButton.
	 */
	public Node getCore(){
		return this.core;
	}
	
	/**
	 * Calls the delegate paint method for the UI.
	 * Paint the NodeButton according to if selected or not.
	 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//Background
		if(select)
			background = SELECTED;
		else
			background = UNSELECTED;
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//Icon
		NodeState state = core.getState();
		switch (state) {
		case ABSENT:
			foreground = ABS;
			break;
		case PRESENT:
			foreground = PRE;
			break;
		case AVAILABLE:
			foreground = AVA;
			break;
		case RUNNING:
			foreground = RUN;
			break;
		case ERROR:
			foreground = ERR;
			break;
		default:
			foreground = AVA;
			break;
		}
		int subtype = core.getType();
		if(info){ 
			subtype += 1;
		}
		IconBuilder.icon(g, foreground, background, getWidth(), getHeight(), subtype, this, core);
		if(blink > 0){
			blink--;
		}
	}
	
	public void infoToggle(){
		this.info = !this.info;
	}

	/**
	 * MouseClicked Listener.
	 * On click, selects this node as the currently selected one.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)){
			infoToggle();
		} else {
			AddressPanel.unselectLast(this);
			select(true);
			if(select){
				System.out.println("g>" + "Call: " + core.getId());
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
