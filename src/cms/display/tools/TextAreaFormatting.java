package cms.display.tools;

import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import cms.display.GraphicsConstants;
import cms.display.communication.LogDisplay;

/**
 * The <code>TextAreaFormatting</code> class is a datastructure for the various output formatting
 * Static block initializes the various SimpleAttributeSets.
 * @author Eduard Parachivescu
 * @author Philippe Hebert
 * @version 1
 * -tag @refactor Philippe Hebert
 * @see TextAreaOutputStream
 */
public final class TextAreaFormatting implements GraphicsConstants{

	private static SimpleAttributeSet gooduserinput = new SimpleAttributeSet();
	private static SimpleAttributeSet baduserinput = new SimpleAttributeSet();
	private static SimpleAttributeSet programoutput = new SimpleAttributeSet();
	private static SimpleAttributeSet programerror = new SimpleAttributeSet();
	private static SimpleAttributeSet log = new SimpleAttributeSet();

	static{
		StyleConstants.setFontFamily(gooduserinput, SMALL_FONT.getFamily());
		StyleConstants.setForeground(gooduserinput, RUN);
		StyleConstants.setFontSize(gooduserinput, 11);

		StyleConstants.setFontFamily(baduserinput, SMALL_FONT.getFamily());
		StyleConstants.setForeground(baduserinput, ERR);
		StyleConstants.setFontSize(baduserinput, 11);
		
		StyleConstants.setFontFamily(programerror, SMALL_FONT.getFamily());
		StyleConstants.setForeground(programerror, ERR);
		StyleConstants.setFontSize(programerror, 11);
		
		StyleConstants.setFontFamily(programoutput, SMALL_FONT.getFamily());
		StyleConstants.setForeground(programoutput, ABS);
		StyleConstants.setFontSize(programoutput, 11);
		
		StyleConstants.setFontFamily(log, SMALL_FONT.getFamily());
		StyleConstants.setFontSize(log, 10);
		StyleConstants.setForeground(log, ABS);
	}
	
	private TextAreaFormatting(){}

	/**
	 * Appends to the specified textpane using the specified highlight type and the input tag.
	 * @param textpane JTextPane to output to.
	 * @param highlightType Distinguishes between Console and Log output
	 * @param str String to be output
	 */
	public static void append(JTextPane textpane, int highlightType, String str) {
		if (highlightType == 0) {
			String type = str.substring(0, 2);
			String command = str.substring(2, str.length());
			try {
				switch (type) {
				case "b>":
					textpane.getDocument().insertString(
							textpane.getDocument().getLength(), command,
							baduserinput);
					break;				
				case "e>":
					textpane.getDocument().insertString(
							textpane.getDocument().getLength(), command,
							programerror);
					break;

				case "g>":
					textpane.getDocument().insertString(
							textpane.getDocument().getLength(), command,
							gooduserinput);
					break;
				case "s>":
					textpane.getDocument().insertString(
							textpane.getDocument().getLength(), command,
							programoutput);
					break;
				default:
					command = str;
					textpane.getDocument().insertString(
							textpane.getDocument().getLength(), command,
							programoutput);
					break;
				}
			} catch (Exception e) {
				LogDisplay.display("Error Adding Text: ||" + str + "||");
			}
		} else if (highlightType == 1) {
			try {
				textpane.getDocument().insertString(
						textpane.getDocument().getLength(), str, log);
			} catch (Exception e) {
			}
		}
		textpane.setCaretPosition(textpane.getDocument().getLength());
	}
}
