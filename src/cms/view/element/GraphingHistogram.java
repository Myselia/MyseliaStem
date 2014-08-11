package cms.view.element;

import java.awt.Font;
import java.awt.Graphics;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cms.model.DataStore;
import cms.model.data.DisplayMemoryStorage;
import cms.view.DisplayType;

public class GraphingHistogram extends GraphingParent {
	private static final long serialVersionUID = 1L;

	public GraphingHistogram(DisplayType displaytype) {
		super(displaytype);
	}
	
	/*private class Vector{
		private final double[] x_values;
		private final double[] y_values;
		
		public Vector(int x1, int y1, int x2, int y2){
			x_values = new double[2];
			y_values = new double[2];
			x_values[0] = x1;
			x_values[1] = x2;
			y_values[0] = y1;
			y_values[1] = y2;		
		}
		
		public double magnetude(){
			return Math.sqrt((x_values[1]- x_values[0])*(x_values[1]- x_values[0])
						+ (y_values[1] - y_values[0])*(y_values[1] - y_values[0]));
		}
		
		public double height(){
			return (y_values[1] - y_values[0]);
		}
		
		public double width(){
			return (x_values[1]- x_values[0]);
		}
		
		public double direction(){	
			return Math.atan(this.height()/this.width());
		}
	}*/
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	
		//background
		g.setColor(BACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//font values
		Font f = new Font("Dialog", Font.BOLD, 12);
		g.setFont(f);
	
		//read-through values and average
		double[] values;
		
		/**TODO Receive which scale chosen from a slider on the GUI, ranges from 0 to 3 (1s, 10s, 5min, 2h30)*/
		int scale = 0;
		int size = DisplayMemoryStorage.getSize();
		double max = 0.0;
		int coreid = (DataStore.getSelectedCore() == -1) ? 0 : DataStore.getSelectedCore();
		
		if(displaytype == DisplayType.TEMPERATURE){
			max = 100.0;
			values = DataStore.coreA.get(coreid).memstore[1].getmem()[scale];
		} else if(displaytype == DisplayType.CPU){
			max = 100.0;
			values = DataStore.coreA.get(coreid).memstore[2].getmem()[scale];
		} else if(displaytype == DisplayType.RAM){
			max = 512.0;
			values = DataStore.coreA.get(coreid).memstore[3].getmem()[scale];
		} else if(displaytype == DisplayType.PARTICLES){
			max = 0.0;
			values = DataStore.coreA.get(coreid).memstore[4].getmem()[scale];
		} else{
			values = new double[size];
		}
		
		//Calculating average and sum
		double average = 0.0;
		double sum = 0.0;
		for(int i = 0 ; i < values.length ; i++){
			average += values[i];
			sum += values[i];
		}
		average /= size;
		if(displaytype == DisplayType.PARTICLES)
			max = sum;

		//displayscale
		double displayscale = getHeight()/max;
		
		//average drawing
		g.setColor(AVA);
		g.fillRect(0, getHeight() - (int)(average*displayscale), getWidth(), 1);
		String avg = Double.toString(average);
		Pattern p = Pattern.compile("^([1-9]\\d*|0)(\\.\\d)?$");
		Matcher m = p.matcher(avg);
		avg = avg.substring(m.regionStart(), m.regionEnd());
		g.drawString(avg, 4, getHeight() - ((int)(average*displayscale) + 2)); //text	
		
		//design values
		int offset = (getWidth()-100)/(size-1);
		
		//indicator drawing
		int[] y_values = new int[2];
		for(int i = 0, j = 50; i < size; i++){
			g.setColor(ABS);
			g.drawLine(j, 0, j, getHeight());
			g.setColor(PRE);
			y_values[0] = (int)(displayscale*values[i]);
			y_values[1] = (int)(displayscale * values[i+1]);
			g.drawLine( j, y_values[0], j + offset*(i), y_values[1]);
			g.drawString(Double.toString(values[i]), (int) + 4, (int)getHeight() - (int)(values[i]*displayscale) - 2);
		}
	}
}
