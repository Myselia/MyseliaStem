package cms.model.communication.format;

import org.w3c.dom.NodeList;

public interface Transmission {
	
	public void addAttributes(String id, String type, String to, String from);
	public void addParticles(NodeList list);
	public void addOpcode(String opcode);
	
	public void printTransmission();
	
}
