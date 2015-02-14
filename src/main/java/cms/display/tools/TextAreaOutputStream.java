package cms.display.tools;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import cms.monitoring.LogSystem;

/**
 * The <code>TextAreaOutputStream</code> class is the CMS OutputStream to its JTextPanes
 * @author Laurence Dol
 * @see {@link http://stackoverflow.com/questions/342990/create-java-console-inside-the-panel}
 * @author Eduard Parachivescu
 * @version 1
 * -tag @refactor Philippe Hebert
 */
public class TextAreaOutputStream extends OutputStream {

	private byte[] oneByte; // array for write(int val);
	private Appender appender; // most recent action

	/**
	 * The <code>Appender</code> class is an inner class handling buffering of inputs
	 * @author Laurence Doi
	 * @author Eduard Parachivescu
	 * -tag @refactor Philippe Hebert
	 */
	private static class Appender implements Runnable {
		
		/** * JTextPane to append to*/
		private final JTextPane textpane;
		/** * Maximum number of lines in JTextPane */
		private final int maxLines;
		/** * Length of lines in the JTextPane*/
		private final LinkedList<Integer> lengths;
		/** * Strings enqueued to be appended*/
		private volatile List<String> input_str;
		/** * HighLightType: Determines if output goes to Console or Logger */
		private int highlightType;
		/** * Length of current line */
		private int current_length;
		/** * Boolean true if there is nothing to append... at all*/
		private boolean isClear;
		/** * Boolean true if Strings are waiting to be appended to the input_str*/
		private boolean hasQueue;
		
		private final String EOL1 = "\n";
		private final String EOL2 = System.getProperty("line.separator",EOL1);

		/**
		 * Appender Inner Class default constructor
		 * @param textpane JTextPane to be output to.
		 * @param type HighlightType
		 * @param maxlin Defines maximum number of lines of the JTextPane.
		 */
		public Appender(JTextPane textpane, int type, int maxlin) {
			this.highlightType = type;
			this.textpane = textpane;
			this.maxLines = maxlin;
			this.lengths = new LinkedList<Integer>();
			this.input_str = new ArrayList<String>();
			this.current_length = 0;
			this.isClear = false;
			this.hasQueue = true;
		}

		/**
		 * Appends val to the input_str ArrayList<String>
		 * @param val String to be appended to ArrayList
		 */
		public synchronized void append(String val) {
			this.input_str.add(val);
			if(this.hasQueue){
				this.hasQueue = false;
				EventQueue.invokeLater(this);
			}
		}

		/**
		 * Nukes anything left in the memory to be appended to JTextPane
		 */
		public synchronized void clear() {
			this.isClear = true;
			this.current_length = 0;
			this.lengths.clear();
			this.input_str.clear();
			if(this.hasQueue){
				this.hasQueue = false;
				EventQueue.invokeLater(this);
			}
		}

		/**
		 * Verifies if input strings are valid, then append them to the JTextPane
		 * Must be the lone method to interact with adding content to the JtextPane (according to original author)
		 */
		public synchronized void run() {
			if(this.isClear){
				this.textpane.setText("");
			}
			for(String str : this.input_str){
				this.current_length += str.length();
				if(str.endsWith(this.EOL1) || str.endsWith(this.EOL2)){
					if (lengths.size() >= maxLines) {
						LogSystem.log(true, false, "Console Output Exceeding Limit");
						this.clear();
					}
					this.lengths.addLast(current_length);
					this.current_length = 0;
				}
				//FLAG
				TextAreaFormatting.append(textpane, highlightType, str);
			}
			this.input_str.clear();
			this.isClear = false;
			this.hasQueue = true;
		}
	}
	
	/**
	 * TextAreaOutputStream default constructor
	 * Calls the second constructor with args textpane, 0 and 400
	 * @param textpane
	 * @see TextAreaOutputStream(JTextPane textpane, int type, int maxlin)
	 */
	public TextAreaOutputStream(JTextPane textpane) {
		this(textpane, 0, 400);
	}
	/**
	 * TextAreaOutputStream more detailed constructor
	 * @param textpane JTextPane to append to
	 * @param type HighlightType
	 * @param maxlin Maximum number of lines to be appended
	 */
	public TextAreaOutputStream(JTextPane textpane, int type, int maxlin) {
		if (maxlin < 1) {
			throw new IllegalArgumentException(
					"TextAreaOutputStream maximum lines must be positive (value="
							+ maxlin + ")");
		}
		oneByte = new byte[1];
		appender = new Appender(textpane, type, maxlin);
	}

	/**
	 * Accessor method to appender.clear()
	 */
	public synchronized void clear() {
		if (appender != null) {
			appender.clear();
		}
	}

	/**
	 * Destructor of Appender instance variable
	 */
	public synchronized void close() {
		appender = null;
	}

	public synchronized void flush() {}

	/**
	 * Writes an int to the JTextPane
	 * @see write(byte[] array, int str, int len)
	 */
	public synchronized void write(int val) {
		oneByte[0] = (byte) val;
		write(oneByte, 0, 1);
	}

	/**
	 * Writes a byte array to the JTextPane
	 * @see write(byte[] array, int str, int len)
	 */
	public synchronized void write(byte[] array) {
		write(array, 0, array.length);
	}

	/**
	 * Writes a byte array with specified offset and length to JTextPane
	 * @see bytesToString(byte[] array, int str, int len)
	 */
	public synchronized void write(byte[] array, int str, int len) {
		if (appender != null) {
			appender.append(bytesToString(array, str, len));
		}
	}

	/**
	 * Returns a String from a byte array
	 * @param array Byte array of data
	 * @param str Offset
	 * @param len Length of byte array
	 * @return String built from byte array
	 */
	private static String bytesToString(byte[] array, int str, int len) {
		try {
			return new String(array, str, len, "UTF-8");
		} catch (UnsupportedEncodingException thr) {
			return new String(array, str, len);
		}
	}

}