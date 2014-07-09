package cms.view.element;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import cms.view.GraphicsConstants;
import cms.view.panel.LogDisplay;

public class TextAreaFormatting implements GraphicsConstants{

	private SimpleAttributeSet gooduserinput = new SimpleAttributeSet();
	private SimpleAttributeSet baduserinput = new SimpleAttributeSet();
	private SimpleAttributeSet programoutput = new SimpleAttributeSet();
	private SimpleAttributeSet programerror = new SimpleAttributeSet();
	private SimpleAttributeSet log = new SimpleAttributeSet();

	public TextAreaFormatting() {
		define();
	}

	public void define() {
		StyleConstants.setFontFamily(gooduserinput, "Verdana");
		StyleConstants.setForeground(gooduserinput, RUN);
		StyleConstants.setFontSize(gooduserinput, 11);

		StyleConstants.setFontFamily(baduserinput, "Verdana");
		StyleConstants.setForeground(baduserinput, ERR);
		StyleConstants.setFontSize(baduserinput, 11);
		
		StyleConstants.setFontFamily(programerror, "Verdana");
		StyleConstants.setForeground(programerror, ERR);
		StyleConstants.setFontSize(programerror, 11);
		
		StyleConstants.setFontFamily(programoutput, "Verdana");
		StyleConstants.setForeground(programoutput, ABS);
		StyleConstants.setFontSize(programoutput, 11);
		
		StyleConstants.setFontFamily(log, "Verdana");
		StyleConstants.setFontSize(log, 9);
		StyleConstants.setForeground(log, ABS);
	}

	public void append(JTextPane textpane, int highlightType, String str) {
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

	}
}
