package cms.view.button;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import cms.Main;
import cms.view.GraphicsConstants;

public class QuickSeekButton extends JComponent implements MouseListener, GraphicsConstants {
	private static final long serialVersionUID = 1L;
	int type;
	boolean select;
	Color foreground;
	
	public QuickSeekButton(){
		super();
		enableInputMethods(true);
		setFocusable(true);
		
		type = 100;
		//System.out.println("BOB");
		
		addMouseListener(this);
		setVisible(true);
	}
	
	public void setSelect(boolean select){
		this.select = select;
		repaint();
	}
	public void setSelect(){
		setSelect(!select);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//Background
		if(select){
			foreground = RUN;
		} else {
			foreground = ABS;
		}
		g.setColor(BACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		IconFactory.icon(g, foreground, BACK, getWidth(), getHeight(), type, null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		setSelect(!select);
		
		if(select){
			Main.startBCastThread(Main.getBcastRunnable(), Main.getbCastCommunicator());
			System.out.println("Seek mode activated");
		} else {
			Main.getbCastCommunicator().interrupt();
			System.out.println("Seek mode deactivated");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

}
