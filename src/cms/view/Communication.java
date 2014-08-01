package cms.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import cms.view.panel.ConsoleDisplay;
import cms.view.panel.LogDisplay;

public class Communication extends JPanel implements GraphicsConstants {
	private static final long serialVersionUID = 1L;
	
	private ConsoleDisplay console = new ConsoleDisplay();
	private LogDisplay log = new LogDisplay();

	public Communication() {
		this.setBackground(BACK);
		this.setPreferredSize(new Dimension(100, 346));
		this.setLayout(new BorderLayout());
		
		this.add(console, BorderLayout.CENTER);
		this.add(log, BorderLayout.EAST);
	}
	
	protected void setFocusOnTextField(){
		console.setFocus();
	}

}
