package cms.view.panel;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cms.view.GraphicsConstants;
import cms.view.button.QuickSeekButton;

@SuppressWarnings("serial")
public class QuickBar extends JPanel implements GraphicsConstants {
	
	public QuickSeekButton seekbutton = new QuickSeekButton();
	
	public QuickBar(){
		this.setBackground(GraphicsConstants.BACK);
		this.setPreferredSize(new Dimension(800,24));
		
		GridLayout layout = new GridLayout(0, 12);
		layout.setHgap(2);
		layout.setVgap(2);
		this.setLayout(layout);
		
		
		this.add("Button", seekbutton);
		
	}
	
	public void updateTime(){}
}
