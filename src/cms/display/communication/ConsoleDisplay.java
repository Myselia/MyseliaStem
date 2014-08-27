package cms.display.communication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import cms.Main;
import cms.control.CommandSystem;
import cms.display.GraphicsConstants;
import cms.display.tools.TextAreaOutputStream;

@SuppressWarnings("serial")
public class ConsoleDisplay extends ScrollingDisplayParent implements KeyListener,
		GraphicsConstants {

	private JTextField field;

	public ConsoleDisplay() {
		super();
		areaSetup();
		fieldSetup();
		this.add(field, BorderLayout.SOUTH);
		setVisible(true);
	}

	private void areaSetup() {
		TextAreaOutputStream taos = new TextAreaOutputStream(textpane, 0, 4000);
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
	 * Loops until focus is given to field. Average of 9 loops so far. (as of merge of ISS7)
	 */
	public void setFocus(){
		while(!field.hasFocus()){
			field.grabFocus();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
