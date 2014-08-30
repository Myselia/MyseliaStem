package cms.communication.parsers;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import cms.communication.structures.Atom;
import cms.communication.structures.Transmission;

public class TransmissionParser {
	public TransmissionParser(){
		
	}
	
	public static String makedoc(Transmission trans){
		String build = null;
		build += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		build += "<transmission>";
		build += header(trans);
		for(int i = 0; i < trans.atoms.length; i++){
			build += atom(trans.atoms[i]);
		}
		build += "</transmission>";
		
		check(build);
		
		return build;
	}
	
	private static String header(Transmission trans){
		String build = null;
		build += "<header ";
		build += "id=\"" + trans.id + "\" ";
		build += "from=\"" + trans.from + "\" ";
		build += "to=\"" + trans.to + "\" ";
		build += ">";
		build += trans.opcode;
		build += "</header>";
		return build;
	}
	
	private static String atom(Atom at){
		String build = null;
		build += "<atom";
		build += "type=\"" + at.getAtomType() + "\" ";
		build += "class=\"" + at.getAtomType() + "\" ";
		build += ">";
		build += at.getContent();
		build += "</atom>";
		return build;
	}
	
	private static void check(String input){
		try {
			InputSource is = new InputSource();
		    is.setCharacterStream(new StringReader(input));
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
