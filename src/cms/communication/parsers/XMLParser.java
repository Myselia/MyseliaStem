package cms.communication.parsers;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import cms.communication.structures.Transmission;

public class XMLParser {

	private Document doc;
	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;

	private NodeList particles;
	private Element element;

	private Transmission transmission;

	public XMLParser() {
		this.doc = null;
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Transmission makedoc(String input) {
		
		try {
			InputSource is = new InputSource();
		    is.setCharacterStream(new StringReader(input));
			doc = dBuilder.parse(is);
		    //System.out.println("||" + doc.toString() + "||");
			particles = doc.getElementsByTagName("atom");
		} catch (Exception e) {
			e.printStackTrace();
		} 

		try {
			element = (Element) doc.getElementsByTagName("header").item(0);
			transmission = new Transmission();
			return construct(element, transmission);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;

	}
	
	private Transmission construct(Element element, Transmission transmission){
		String[] tags = new String[4];
		
		tags[0] = element.getTextContent();
		tags[1] = element.getAttribute("id");
		tags[2] = element.getAttribute("from");
		tags[3] = element.getAttribute("to");
		
		transmission.addAttributes(tags[1], tags[2], tags[3]);
		transmission.addOpcode(tags[0]);
		transmission.addParticles(particles);
		
		return transmission;
	}
}
