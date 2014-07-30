package cms.view.panel;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cms.model.DataFactory;
import cms.view.GraphicsConstants;

public class GraphingPanel extends JPanel implements MouseListener,
GraphicsConstants{
	private static final long serialVersionUID = 1L;
	
	private int barcount;
	//private static int displaytype;

	public GraphingPanel(){
		super();
		enableInputMethods(true);
		setFocusable(true);
		addMouseListener(this);
		setVisible(true);

		this.barcount = DataFactory.core.length;
		
		this.setBorder(BorderFactory.createEmptyBorder(ADDRESS_GAP, ADDRESS_GAP, ADDRESS_GAP, ADDRESS_GAP));
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//background
		g.setColor(BACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//font values
		Font f = new Font("Dialog", Font.BOLD, 12);
		g.setFont(f);

		//read-through values and average
		double[] value = new double[barcount];
		double average = 0.0;
		for(int i = 0; i < barcount; i++){
			value[i] = (int)DataFactory.core[i].getTemperature();
			average += ((double)value[i]/(double)barcount);
		}
		
		//average drawing
		g.setColor(AVA);
		g.fillRect(0, getHeight() - (int)(average*5), getWidth(), 1);
		String bob = Double.toString(average) + "extrainfo";
		if(bob.length() > 4){
			bob = bob.substring(0,4);
		}
		g.drawString(bob, 4, getHeight() - ((int)(average*5) + 2)); //text	
		
		//design values
		int offset = getWidth()/(barcount+1);
		int extra = (getWidth() - (((int)(getWidth()/(barcount+1))) * (barcount+1))) / 2;
		
		//indicator drawing
		for(int i = 0; i < barcount; i++){
			g.setColor(ABS);
			double x_pos = offset*(i+1) + extra;
			double y_pos = getHeight() - value[i]*5;
			
			g.fillRect((int)x_pos , (int)y_pos , (int)(offset - 2), (int)5); //x position, y position, width, height
			g.drawString(Double.toString(value[i]), (int)x_pos + 4, (int)y_pos - 2); //text	
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	
}
