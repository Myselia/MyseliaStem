package cms.view.panel;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cms.model.DataFactory;
import cms.view.GraphicsConstants;
import cms.view.element.GraphBar;

public class GraphingPanel extends JPanel implements GraphicsConstants{
	private static final long serialVersionUID = 1L;
	
	private static GraphBar[] graphbar;
	private static int barcount;

	public GraphingPanel(){
		this.setBackground(BACK);
		this.barcount = DataFactory.core.length;
		//LogSystem.log(true, false, ""+this.);
		graphbar = new GraphBar[barcount];
		
		this.setBorder(BorderFactory.createEmptyBorder(ADDRESS_GAP, ADDRESS_GAP, ADDRESS_GAP, ADDRESS_GAP));
		
		GridLayout graphLayout = new GridLayout(1, barcount);
		this.setLayout(graphLayout);
		
		for(int i = 0; i < barcount; i++){
			graphbar[i] = new GraphBar(DataFactory.core[i]);
			this.add("Bar", graphbar[i]);
		}
	}
	
	public static void regraph(){
		for(int i = 0; i < barcount; i++){
			graphbar[i].repaint();
		}
	}
	
}
