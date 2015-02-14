package cms.display.communication;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import cms.display.GraphicsConstants;

/**
 * The <code>ScrollingDisplayParent</code> class is a JPanel with a JTextPane.
 * Sets look and feel to match the CMS L&F.
 * @author Philippe Hebert
 * @version 1
 * -tag @refactor Philippe Hebert
 */
public abstract class ScrollingDisplayParent extends JPanel implements GraphicsConstants{
	
	private static final long serialVersionUID = 1L;
	protected JTextPane textpane;
	protected JScrollPane sp;
	protected JScrollBar sb;
	
	/**
	 * ScrollingDisplayParent default constructor
	 * Sets the look & feel of the JScrollPane/JTextPane.
	 */
	protected ScrollingDisplayParent(){
		this.setBackground(BACK);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
		
		this.textpane = new JTextPane();
		this.textpane.setBackground(BACK);
		this.sp = new JScrollPane(textpane);
		this.sp.setBorder(BorderFactory.createEmptyBorder());
		this.sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.sb = sp.getVerticalScrollBar();
		this.sb.setBackground(BACK);
		this.sb.setLayout(null);
		this.sb.setPreferredSize(null);
		this.sp.setBackground(BACK);
		this.sp.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		this.sp.getViewport().setBorder(null);
		this.sp.setViewportBorder(null);
		this.sp.setBorder(null);
		
		this.add(sp, BorderLayout.CENTER);
	}
	
	/**
	 * Accessor of textpane
	 * @return Returns instance variable textpane
	 */
	protected JTextPane getJText(){
		return this.textpane;
	}
	
	/**
	 * Accessor of scrollpane
	 * @return Returns instance variable sp
	 */
	protected JScrollPane getJScroll(){
		return this.sp;
	}
	
	/**
	 * Accessor of scrollbar
	 * @return Returns instance variable sb
	 */
	protected JScrollBar getJBar(){
		return this.sb;
	}
	
}
