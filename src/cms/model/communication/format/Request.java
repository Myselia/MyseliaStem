package cms.model.communication.format;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Request implements Transmission {

	private String id, type, from, to;
	public Particle[] particles;
	// TempString
	private String opcode;

	public Request() {
		
	}
	
	public void addAttributes(String id, String type, String to, String from) {
		this.id = id;
		this.type = type;
		this.from = from;
		this.to = to;
	}

	public void addParticles(NodeList list) {
		this.particles = new Particle[list.getLength()];
		
		for(int i = 0; i < list.getLength(); i++){
			Node node = list.item(i);
			Element element = (Element) node;
			
			particles[i] = new Particle(element.getAttribute("type"), element.getAttribute("class"));
			particles[i].addContent(element.getTextContent());
		}
	}

	public void addOpcode(String opcode) {
		this.opcode = opcode;
	}

	public void printTransmission() {
		System.out.print(type + " " + id + " ");
		System.out.print(to + " " + from + " ");
		System.out.println(opcode);
	}

}
