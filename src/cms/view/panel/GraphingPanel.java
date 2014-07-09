package cms.view.panel;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import cms.controller.LogSystem;
import cms.model.DataFactory;
import cms.view.GraphicsConstants;
import cms.view.element.GraphBar;

public class GraphingPanel extends JPanel implements GraphicsConstants{
	
	private static GraphBar[] graphbar;
	private Dimension size;
	private int barcount;

	public GraphingPanel(){
		this.setBackground(ABS);
		this.barcount = DataFactory.core.length;
		//LogSystem.log(true, false, ""+this.);
		graphbar = new GraphBar[barcount];
		
		GridLayout coreLayout = new GridLayout(1, barcount);
		this.setLayout(coreLayout);
		
		for(int i = 0; i < barcount; i++){
			graphbar[i] = new GraphBar(this.getWidth()/barcount, DataFactory.core[i]);
			this.add("Bar", graphbar[i]);
			LogSystem.log(true, false, ""+ this.getSize());
			//LogSystem.log(true, false, "Created bar no." +i);
		}
	}
	
}
