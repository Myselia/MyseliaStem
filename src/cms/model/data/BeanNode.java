package cms.model.data;

public class BeanNode implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	// Properties
	private int id = 0;
	private int type = 0;
	private int state = 0; 				// core state as viewed by model
	private boolean selected = false; 	// core state as viewed by view
	private int temperature = 25; 		// core temperature

	// Constructor
	public BeanNode() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}


}
