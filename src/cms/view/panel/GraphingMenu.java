package cms.view.panel;

import java.awt.Dimension;

import javax.swing.JPanel;

import cms.view.GraphicsConstants;

public class GraphingMenu extends JPanel implements GraphicsConstants{
	private static final long serialVersionUID = 1L;

	public GraphingMenu(){
		this.setBackground(BACK);
		this.setPreferredSize(new Dimension(300, 200));
	}

}
