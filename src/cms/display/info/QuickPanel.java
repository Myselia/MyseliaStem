package cms.display.info;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import cms.display.GraphicsConstants;
import cms.display.buttons.quick.QuickSeekButton;

@SuppressWarnings("serial")
public class QuickPanel extends JPanel implements GraphicsConstants {
	
	public QuickSeekButton seekbutton = new QuickSeekButton();
	
	public QuickPanel(){
		this.setBackground(GraphicsConstants.BACK);
		this.setPreferredSize(new Dimension(800,24));
		
		GridLayout layout = new GridLayout(0, 12);
		layout.setHgap(2);
		layout.setVgap(2);
		this.setLayout(layout);
		
		
		this.add("Button", seekbutton);
		
	}
	
	public void updateTime(){}
	
	/*public static setSeekSelect(){
		seekbutton.
	}*/
}
