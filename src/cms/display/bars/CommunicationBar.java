package cms.display.bars;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import cms.display.GraphicsConstants;
import cms.display.communication.ConsoleDisplay;
import cms.display.communication.LogDisplay;

public class CommunicationBar extends JPanel implements GraphicsConstants {
	private static final long serialVersionUID = 1L;
	
	private ConsoleDisplay console = new ConsoleDisplay();
	private LogDisplay log = new LogDisplay();

	public CommunicationBar() {
		this.setBackground(BACK);
		this.setPreferredSize(new Dimension(100, 346));
		this.setLayout(new BorderLayout());
		
		this.add(console, BorderLayout.CENTER);
		this.add(log, BorderLayout.EAST);
	}
	
	public void setFocusOnTextField(){
		console.setFocus();
	}

}
