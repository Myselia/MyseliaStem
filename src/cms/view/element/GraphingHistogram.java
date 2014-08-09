package cms.view.element;

import java.awt.Font;
import java.awt.Graphics;

import cms.model.DataStore;
import cms.view.DisplayType;

public class GraphingHistogram extends GraphingParent {
	private static final long serialVersionUID = 1L;

	public GraphingHistogram(DisplayType displaytype) {
		super(displaytype);
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
	}
}
