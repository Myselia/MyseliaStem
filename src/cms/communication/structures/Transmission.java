package cms.communication.structures;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Transmission {
	
	public String id, type, to, from, opcode;	
	public Particle[] particles;
	
	public Transmission(){
		
	}
	public void addAttributes(String id, String type, String from, String to){
		this.id = id;
		this.type = type;
		this.from = from;
		this.to = to;
	}
	public void addParticles(NodeList list){
		this.particles = new Particle[list.getLength()];
		
		for(int i = 0; i < list.getLength(); i++){
			Node node = list.item(i);
			Element element = (Element) node;
			
			particles[i] = new Particle(element.getAttribute("type"), element.getAttribute("class"));
			particles[i].addContent(element.getTextContent());
		}
	}
	public void addOpcode(String opcode){
		this.opcode = opcode;
	}
	public void printTransmission(){
		System.out.print("id:" + id + " type:" + type);
		System.out.print(" to:" + to + " from:" + from);
		System.out.println(" opcode:" + opcode + " length:" + particles.length);
	}
	
}
