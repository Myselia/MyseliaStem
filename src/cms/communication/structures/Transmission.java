package cms.communication.structures;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Transmission {
	
	public String id, from, to, opcode;	
	public Atom[] atoms;
	
	public Transmission(String id, String from, String to){
		this.id = id;
		this.from = from;
		this.to = to;
	}
	public Transmission(Transmission trans){
		this.id = trans.id;
		this.from = trans.from;
		this.to = trans.to;
		this.opcode = trans.opcode;
		this.atoms = trans.atoms;
	}
	public Transmission(){
		
	}
	public void addAttributes(String id, String from, String to){
		this.id = id;
		this.from = from;
		this.to = to;
	}
	public void addAtoms(ArrayList<Atom> list){
		atoms = new Atom[list.size()];
		for(int i = 0; i < atoms.length; i++){
			atoms[i] = list.get(i);
		}
	}
	
	public void addParticles(NodeList list){
		this.atoms = new Atom[list.getLength()];
		
		for(int i = 0; i < list.getLength(); i++){
			Node node = list.item(i);
			Element element = (Element) node;
			
			atoms[i] = new Atom(element.getAttribute("type"), element.getAttribute("class"));
			atoms[i].addContent(element.getTextContent());
		}
	}
	public void addOpcode(String opcode){
		this.opcode = opcode;
	}
	public void printTransmission(){
		System.out.print("id:" + id);
		System.out.print(" to:" + to + " from:" + from);
		System.out.println(" opcode:" + opcode + " length:" + atoms.length);
	}
	
}
