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

/**
 * The <code>ConsoleDisplay</code> class is a console-like JPanel inheriting from ScrollingDisplayParent.
 * Static block initializes the ConsoleDisplay singleton
 * @author Philippe Hebert
 * @author Eduard Parachivescu
 * @version 1
 * -tag @refactor Philippe Hebert
 */
public class ConsoleDisplay extends ScrollingDisplayParent implements KeyListener, 
																GraphicsConstants {

	private static final long serialVersionUID = 1L;
	private static ConsoleDisplay console;
	private JTextField field;

	static{
		console = new ConsoleDisplay();
	}
	
	/**
	 * Default ConsoleDisplay constructor.
	 * Creates a ConsoleDisplay comprised of a JScrollPane and a JTextField.
	 */
	private ConsoleDisplay() {
		super();
		this.areaSetup();
		this.fieldSetup();
		this.add(field, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	/**
	 * Accessor of the Singleton.
	 * Creates a singleton if not already created.
	 * @return Returns the ConsoleDisplay console
	 */
	public static ConsoleDisplay getConsole(){
		return console;
	}

	/**
	 * Sets up the System streams to the console & sets the specifics of the JTextPane.
	 * @see cms.display.tools.TextAreaOutputStream
	 */
	private void areaSetup() {
		TextAreaOutputStream taos = new TextAreaOutputStream(textpane, 0, 4000);
		PrintStream ps = new PrintStream(taos);
		System.setOut(ps);
		if (Main.REROUTE_ERR)
			System.setErr(ps);
		
		this.textpane.setFont(new Font("Verdana", Font.PLAIN, 11));
		this.textpane.setEditable(false);
	}

	/**
	 * Sets up the JTextField instance variable and its characteristics.
	 */
	private void fieldSetup() {
		this.field = new JTextField();
		this.field.setBackground(BEVEL);
		this.field.setForeground(Color.LIGHT_GRAY);
		this.field.setFont(new Font("Verdana", Font.LAYOUT_LEFT_TO_RIGHT, 12));
		this.field.addKeyListener(this);
		this.field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	/**
	 * Handles the KeyEvents sent to console.field
	 * ENTER: Parses for command.
	 * UP/DOWN: Navigates previous commands.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			CommandSystem.index_reset();
			String make = this.field.getText();
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
	 * Grabs the focus on the field.
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
