package cms.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

class XMLTester {
	
	public static String getXML(){
		BufferedReader reader = getBufferedReader();
		String xml = "", current = "";
		
		try {
			while ((current = reader.readLine()) != null) {
				xml += current;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return xml;
	}

	private static BufferedReader getBufferedReader() {
		
		File f = new File("res\\transmissionprototype.xml");
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			fr = new FileReader(f);
			br  = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return br;
 
	}
	
	
}
