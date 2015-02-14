package cms.databank.structures;

public class Network implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	// Properties
	private int elements;
	private int calculations;

	// Constructor
	public Network() {
	}

	// Getters
	public int getElements() {
		return elements;
	}

	public int getCalculations() {
		return calculations;
	}

	// Setters
	public void setElements(int elements) {
		this.elements = elements;
	}

	public void setCalculations(int calculations) {
		this.calculations = calculations;
	}

}
