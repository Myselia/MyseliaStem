package cms.databank.structures;


public class Node implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	// Properties
	private volatile int id = 0;
	private volatile int type = 0;				// core type: 0node, 1cms, 2ams, 3db
	private volatile NodeState state = NodeState.ABSENT;			// core state as viewed by model
	private volatile boolean selected = false; 	// core state as viewed by view
	
	public volatile EchoedValueStorage[] memstore = new EchoedValueStorage[4];
	private volatile double temperature = 25; 		// core temperature
	private volatile double cpu = 50;				// core cpu usage
	private volatile double ram = 256;				// core leftover ram
	private volatile double particles = 1000;		// core ams particles
	
	private volatile String ip = "000.000.000.000";			// ip of core

	// Constructor
	public Node() {
		for(int i = 0; i < 4; i++){
			memstore[i] = new EchoedValueStorage();
		}
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
		memstore[0].add(temperature);
	}

	public double getParticles() {
		return particles;
	}

	public void setParticles(double particles) {
		this.particles = particles;
		memstore[3].add(particles);
	}

	public double getRam() {
		return ram;
	}

	public void setRam(double ram) {
		this.ram = ram;
		memstore[2].add(ram);
	}

	public double getCpu() {
		return cpu;
	}

	public void setCpu(double cpu) {
		this.cpu = cpu;
		memstore[1].add(cpu);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


}
