package cms.view.panel;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

//import cms.controller.LogSystem;
import cms.model.DataStore;
import cms.view.DisplayType;
import cms.view.GraphicsConstants;

public class GraphingPanel extends JPanel implements MouseListener,
GraphicsConstants{
	private static final long serialVersionUID = 1L;
	
	private int barcount;
	private DisplayType displaytype;

	public GraphingPanel(DisplayType displaytype){
		super();
		enableInputMethods(true);
		setFocusable(true);
		addMouseListener(this);
		setVisible(true);

		this.barcount = DataStore.coreA.size();
		this.displaytype = displaytype;
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
		double sum = 0.0;
		double max = 0.0;
		
		for(int i = 0; i < barcount; i++){
			if(displaytype == DisplayType.TEMPERATURE){
				max = 100.0;
				value[i] = DataStore.coreA.get(i).getTemperature();
			} else if(displaytype == DisplayType.CPU){
				max = 100.0;
				value[i] = DataStore.coreA.get(i).getCpu();
			} else if(displaytype == DisplayType.RAM){
				max = 512.0;
				value[i] = DataStore.coreA.get(i).getRam();
			} else if(displaytype == DisplayType.PARTICLES){
				max = 0.0;
				value[i] = DataStore.coreA.get(i).getParticles();
			}
			
			average += (double)(value[i]/barcount);
			sum += value[i];
		}
		
		//LogSystem.log(true, false, "Average:" + average + " || Sum: "+ sum);
		//LogSystem.log(true, false, "Display Type:" + displaytype);
		
		if(displaytype == DisplayType.PARTICLES){
			max = sum;
		}
	
		//scale calculator
		double scale = getHeight()/max;
		
		//average drawing
		g.setColor(AVA);
		g.fillRect(0, getHeight() - (int)(average*scale), getWidth(), 1);
		String bob = Double.toString(average) + "extrainfo";
		if(bob.length() > 4){
			bob = bob.substring(0,4);
		}
		g.drawString(bob, 4, getHeight() - ((int)(average*scale) + 2)); //text	
		
		//design values
		int offset = getWidth()/(barcount+1);
		int extra = (getWidth() - (((int)(getWidth()/(barcount+1))) * (barcount+1))) / 2;
		
		//indicator drawing
		for(int i = 0; i < barcount; i++){
			g.setColor(ABS);
			double x_pos = offset*(i+1) + extra;
			double y_pos = getHeight() - value[i]*scale;
			
			g.fillRect((int)x_pos , (int)y_pos , (int)(offset - 2), (int)5); //x position, y position, width, height
			g.drawString(Double.toString(value[i]), (int)x_pos + 4, (int)y_pos - 2); //text	
		}
	}
	
	public void setDisplayType(DisplayType dt){
		displaytype = dt;
	}
	
	public DisplayType getDisplayType(){
		return displaytype;
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
