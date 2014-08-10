package cms.model.data;

public class BeanNode implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	// Properties
	private int id = 0;
	private int type = 0;				// core type: 0node, 1cms, 2ams, 3db
	private NodeState state = NodeState.ABSENT;			// core state as viewed by model
	private boolean selected = false; 	// core state as viewed by view
	
	
	public DisplayMemoryStorage[] memstore = new DisplayMemoryStorage[4];
	private double temperature = 25; 		// core temperature
	private double cpu = 50;				// core cpu usage
	private double ram = 256;				// core leftover ram
	private double particles = 1000;		// core ams particles
	
	private String ip = "000.000.000.000";			// ip of core

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

	public NodeState getState() {
		return state;
	}

	public void setState(NodeState state) {
		this.state = state;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getParticles() {
		return particles;
	}

	public void setParticles(double particles) {
		this.particles = particles;
	}

	public double getRam() {
		return ram;
	}

	public void setRam(double ram) {
		this.ram = ram;
	}

	public double getCpu() {
		return cpu;
	}

	public void setCpu(double cpu) {
		this.cpu = cpu;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


}
