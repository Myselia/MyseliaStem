package cms.communication.structures;

import java.util.ArrayList;

public class TransmissionBuilder {
	private static Transmission transmission;
	private static int count;
	private static ArrayList<Atom> list;
	
	static {
		list = new ArrayList<Atom>();
		count = 0;
	}
	
	public static void addAtom(String atom_type, String atom_class, String content){
		Atom atom = new Atom(atom_type, atom_class);
		atom.addContent(content);
		list.add(atom);
	}
	
	public static void newTransmission(String from, String to, String opcode){
		transmission = new Transmission(Integer.toString(count), from, to);
		transmission.addOpcode(opcode);
	}
	
	public static Transmission getTransmission(){
		transmission.addAtoms(list);
		Transmission ret = new Transmission(transmission);
		transmission = null;
		list.clear();
		count++;
		return ret;
	}

}
