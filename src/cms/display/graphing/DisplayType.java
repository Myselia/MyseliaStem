package cms.display.graphing;

/**
 * The <code>DisplayType</code> enum represents the type of data that can be displayed in the Graphing*.
 * @author Eduard Parachivescu
 * @version 1
 */
public enum DisplayType {
	TEMPERATURE(0), CPU(1), RAM(2), PARTICLES(3);
	
	private static final int size = DisplayType.values().length;
	
	private final int ID;
	
	/**
	 * DisplayType default constructor
	 * @param ID
	 */
	private DisplayType(int ID){
		this.ID = ID;
	}
	
	/**
	 * Accessor for enum's int
	 * @return Returns enum value's int
	 */
	public int getID(){
		return this.ID;
	}
	
	/**
	 * Accessor of the size of the enum
	 * @return Returns size
	 */
	public static int size(){
		return size;
	}
	
	
}
