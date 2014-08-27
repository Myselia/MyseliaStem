package cms.display.graphing;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;





import cms.databank.OverLord;
import cms.display.GraphicsConstants;
//import cms.controller.LogSystem;

public class GraphingLevels extends GraphingParent implements MouseListener,
GraphicsConstants{
	private static final long serialVersionUID = 1L;

	public GraphingLevels(DisplayType displaytype){
		super(displaytype);
		addMouseListener(this);
		setVisible(true);

		this.barcount = OverLord.nodeCore.size();
		this.displaytype = displaytype;
		this.setBorder(BorderFactory.createEmptyBorder(ADDRESS_GAP, ADDRESS_GAP, ADDRESS_GAP, ADDRESS_GAP));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//background
		g.setColor(BACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//font valuess
		Font f = new Font("Dialog", Font.BOLD, 12);
		g.setFont(f);

		//read-through valuess and average
		double[] values = new double[barcount];
		double average = 0.0;
		double sum = 0.0;
		double max = 0.0;
		for(int i = 0; i < barcount; i++){
			if(displaytype == DisplayType.TEMPERATURE){
				max = 100.0;
				values[i] = OverLord.nodeCore.get(i).getTemperature();
			} else if(displaytype == DisplayType.CPU){
				max = 100.0;
				values[i] = OverLord.nodeCore.get(i).getCpu();
			} else if(displaytype == DisplayType.RAM){
				max = 512.0;
				values[i] = OverLord.nodeCore.get(i).getRam();
			} else if(displaytype == DisplayType.PARTICLES){
				max = 0.0;
				values[i] = OverLord.nodeCore.get(i).getParticles();
			}
			
			average += (double)(values[i]/barcount);
		}
		
		//LogSystem.log(true, false, "Average:" + average + " || Sum: "+ sum);
		//LogSystem.log(true, false, "Display Type:" + displaytype);
		
		if(displaytype == DisplayType.PARTICLES){
			max = sum;
			for(int i = 0 ; i < values.length ; i++){
				if(values[i] > max){
					max = values[i];
				}
			}
			max *= 2;
		}
	
		//scale calculator
		double scale = getHeight()/max;
		
		//average drawing
		g.setColor(AVA);
		g.fillRect(0, getHeight() - (int)(average*scale), getWidth(), 1);
		String avg = Double.toString(average);
		Pattern pattern = Pattern.compile("^\\d+(\\.\\d)?");
		Matcher matcher = pattern.matcher(avg);
		if (matcher.find()) {
			avg = avg.substring(matcher.start(), matcher.end());
		} else {
			avg = "err";
		}
		
		g.drawString(avg, 4, getHeight() - ((int)(average*scale) + 2)); //text	
		
		//design valuess
		int offset = getWidth()/(barcount+1);
		int extra = (getWidth() - (((int)(getWidth()/(barcount+1))) * (barcount+1))) / 2;
		
		//indicator drawing
		for(int i = 0; i < barcount; i++){
			g.setColor(ABS);
			double x_pos = offset*(i+1) + extra;
			double y_pos = getHeight() - values[i]*scale;
			
			g.fillRect((int)x_pos , (int)y_pos , (int)(offset - 2), (int)5); //x position, y position, width, height
			
			String val = Double.toString(values[i]);
			Matcher valm = pattern.matcher(val);
			if (valm.find()) {
				val = val.substring(valm.start(), valm.end());
			} else {
				val = "err";
			}
			
			g.drawString(val, (int)x_pos + 4, (int)y_pos - 2); //text	
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
