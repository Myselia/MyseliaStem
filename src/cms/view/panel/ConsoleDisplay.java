package cms.view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;

import cms.Main;
import cms.controller.CommandSystem;
import cms.view.GraphicsConstants;
import cms.view.element.TextAreaOutputStream;

@SuppressWarnings("serial")
public class ConsoleDisplay extends JPanel implements KeyListener,
		GraphicsConstants {

	private JTextPane textpane;
	private JTextField field;

	public ConsoleDisplay() {
		this.setBackground(BACK);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 0));

		areaSetup();
		JScrollPane sp = new JScrollPane(textpane);
		sp.setBorder(BorderFactory.createEmptyBorder());
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(sp, BorderLayout.CENTER);

		fieldSetup();
		this.add(field, BorderLayout.SOUTH);
		
		try{
			CommandSystem.setClasses("cms.controller.command");
		}catch(Exception e){
			System.out.println("b>" + "&>" + "cms.controller.CommandSystem.setClasses(String) called in");
			System.out.println("b>" + "&>" + "cms.view.panel.ConsoleDisplay.ConsoleDisplay() threw an exception.");
			System.out.println("b>" + "&>" + "Force kill the app and investigate.");
			e.printStackTrace();
		}
		
		field.grabFocus();
		setVisible(true);
	}

	private void areaSetup() {
		textpane = new JTextPane();
		TextAreaOutputStream taos = new TextAreaOutputStream(textpane, 0, 400);
		PrintStream ps = new PrintStream(taos);

		System.setOut(ps);
		if (Main.REROUTE_ERR)
			System.setErr(ps);
		textpane.setBackground(BACK);
		textpane.setFont(new Font("Verdana", Font.PLAIN, 11));
		textpane.setEditable(false);
	}

	private void fieldSetup() {
		field = new JTextField();
		field.setBackground(Color.BLACK);
		field.setForeground(Color.LIGHT_GRAY);
		field.setFont(new Font("Verdana", Font.LAYOUT_LEFT_TO_RIGHT, 12));
		field.addKeyListener(this);
		field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			CommandSystem.index_reset();
			String make = field.getText();
			if (!make.equals("")) {
				CommandSystem.command(make);
				field.setText("");
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			CommandSystem.index_increase();
			field.setText(CommandSystem.get_command());

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			CommandSystem.index_decrease();
			field.setText(CommandSystem.get_command());
		}
	}

	/**
	 * Loops until focus is given to field. Average of 9 loops so far.
	 */
	public void setFocus(){
		//int loop = 0;
		while(!field.hasFocus()){
			field.grabFocus();
			//loop++;
		}
		//Debug
		//System.out.println("Focus is set, finally: " + field.hasFocus() + ". Loops: " + loop);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
