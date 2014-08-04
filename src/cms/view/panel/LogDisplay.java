package cms.view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import cms.view.GraphicsConstants;
import cms.view.element.TextAreaOutputStream;

public class LogDisplay extends JPanel implements GraphicsConstants{
	private static final long serialVersionUID = 1L;
	
	private JTextPane textpane;
	private static PrintStream log;
	
	public LogDisplay() {
		this.setBackground(BACK);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
		
		textpane = new JTextPane();
		TextAreaOutputStream taos = new TextAreaOutputStream(textpane, 1, 4000);
        log = new PrintStream(taos);
		textpane.setBackground(BACK);
		textpane.setForeground(Color.WHITE);
		textpane.setFont(new Font("Verdana", Font.PLAIN, 9));
		textpane.setPreferredSize(new Dimension(600, 300));
		textpane.setMinimumSize(new Dimension(600, 300));
		textpane.setEditable(false);
		
		JScrollPane sp = new JScrollPane(textpane);
		sp.setBorder(BorderFactory.createEmptyBorder());
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(sp, BorderLayout.CENTER);

		setVisible(true);
	}
	
	public static void display(String str){
		log.println(str);
	}
	
}
