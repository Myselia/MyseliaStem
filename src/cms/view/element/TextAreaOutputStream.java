package cms.view.element;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import cms.controller.LogSystem;

public class TextAreaOutputStream extends OutputStream {

	// *************************************************************************************************
	// INSTANCE MEMBERS
	// *************************************************************************************************

	private byte[] oneByte; // array for write(int val);
	private Appender appender; // most recent action

	public TextAreaOutputStream(JTextPane textpane) {
		this(textpane, 0, 400);
	}

	public TextAreaOutputStream(JTextPane textpane, int type, int maxlin) {
		if (maxlin < 1) {
			throw new IllegalArgumentException(
					"TextAreaOutputStream maximum lines must be positive (value="
							+ maxlin + ")");
		}
		oneByte = new byte[1];
		appender = new Appender(textpane, type, maxlin);
	}

	/** Clear the current console text area. */
	public synchronized void clear() {
		if (appender != null) {
			appender.clear();
		}
	}

	public synchronized void close() {
		appender = null;
	}

	public synchronized void flush() {
	}

	public synchronized void write(int val) {
		oneByte[0] = (byte) val;
		write(oneByte, 0, 1);
	}

	public synchronized void write(byte[] ba) {
		write(ba, 0, ba.length);
	}

	public synchronized void write(byte[] ba, int str, int len) {
		if (appender != null) {
			appender.append(bytesToString(ba, str, len));
		}
	}

	static private String bytesToString(byte[] ba, int str, int len) {
		try {
			return new String(ba, str, len, "UTF-8");
		} catch (UnsupportedEncodingException thr) {
			return new String(ba, str, len);
		} // all JVMs are required to support UTF-8
	}

	// *************************************************************************************************
	// STATIC MEMBERS
	// *************************************************************************************************

	class Appender implements Runnable {
		private final JTextPane txtpane;
		private final int maxLines; // maximum lines allowed in text area
		private final LinkedList<Integer> lengths; // length of lines within
													// text area
		private final List<String> values; // values waiting to be appended
		private int highlighttype;

		private int curLength; // length of current line
		private boolean clear;
		private boolean queue;

		Appender(JTextPane textpane, int type, int maxlin) {
			highlighttype = type;
			txtpane = textpane;
			maxLines = maxlin;
			lengths = new LinkedList<Integer>();
			values = new ArrayList<String>();

			curLength = 0;
			clear = false;
			queue = true;
		}

		synchronized void append(String val) {
			values.add(val);
			if (queue) {
				queue = false;
				EventQueue.invokeLater(this);
			}
		}

		synchronized void clear() {
			clear = true;
			curLength = 0;
			lengths.clear();
			values.clear();
			if (queue) {
				queue = false;
				EventQueue.invokeLater(this);
			}
		}

		// MUST BE THE ONLY METHOD THAT TOUCHES textArea!
		public synchronized void run() {
			if (clear) {
				txtpane.setText("");
			}
			for (String val : values) {
				curLength += val.length();
				if (val.endsWith(EOL1) || val.endsWith(EOL2)) {
					if (lengths.size() >= maxLines) {
						LogSystem.log(true, false, "Console Output Exceeding Limit");
					}
					lengths.addLast(curLength);
					curLength = 0;
				}
				
				// -------------------------------------------------------------------------------------FLAG
				TextAreaFormatting taf = new TextAreaFormatting();
				taf.append(txtpane, highlighttype, val);
				
			}
			values.clear();
			clear = false;
			queue = true;
		}

		private final String EOL1 = "\n";
		private final String EOL2 = System.getProperty("line.separator",
				EOL1);
	}

} /* END PUBLIC CLASS */