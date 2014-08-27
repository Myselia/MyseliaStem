package cms.display.communication;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import cms.display.GraphicsConstants;

public class ScrollingDisplayParent extends JPanel implements GraphicsConstants, MouseListener{
	
	private static final long serialVersionUID = 1L;
	protected JTextPane textpane;
	protected JScrollPane sp;
	protected JScrollBar sb;
	
	public ScrollingDisplayParent(){
		this.setBackground(BACK);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
		
		textpane = new JTextPane();
		JScrollPane sp = new JScrollPane(textpane);
		sp.setBorder(BorderFactory.createEmptyBorder());
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sb = sp.getVerticalScrollBar();
		sb.setBackground(BACK);
		sb.setLayout(null);
		sb.setPreferredSize(null);
		sp.setBackground(BACK);
		sp.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		sp.getViewport().setBorder(null);
		sp.setViewportBorder(null);
		sp.setBorder(null);
		
		this.add(sp, BorderLayout.CENTER);
	}
	
	public JTextPane getJText(){
		return this.textpane;
	}
	
	public JScrollPane getJScroll(){
		return this.sp;
	}
	
	public JScrollBar getJBar(){
		return this.sb;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
