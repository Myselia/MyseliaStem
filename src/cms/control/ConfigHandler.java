package cms.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigHandler {

	public static int DBCount = 0;
	public static boolean PRINT_DEBUG = false;

	public static LinkedHashMap<String, String> configProperties;
	private static HashMap<String, String> configStructure;

	private static BufferedReader configReader;
	private static FileReader configFile;
	
	private static final String CONFIG_FILE_NAME = "mycelia.cfg";

	public static void init() {
		configStructure = new LinkedHashMap<>();
		configProperties = new LinkedHashMap<>();
		/*
		 * Format: "SECTION IDENTIFIER_MODIFIER" , "attr1,attr2,attr3...etc"
		 * 
		 * MODIFIERS 0 : Cannot have multiple instances of these (ie graphics
		 * settings) 1 : Can have multiple instances of these (ie multiple
		 * databases)
		 */
		configStructure.put("MainWindow_0", "width,height,fullscreen");
		configStructure.put("DB_1", "name,url,dbname,port,user,pass");

		configProperties = new LinkedHashMap<>();

		try {
			configFile = new FileReader(new File(CONFIG_FILE_NAME));
			configReader = new BufferedReader(configFile);
			parseConfigFile();
			if (PRINT_DEBUG) {
				System.out.println("CONFIG STRUCTURE");
				System.out.println("----------------");
				dumpHashmap(configStructure);
				System.out.println();
				System.out.println("CONFIG PROPERTIES");
				System.out.println("----------------");
				dumpHashmap(configProperties);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Cannot find the config file");
			generateDefaultConfig();
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e) {
			System.err.println("Error reading config file");
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}

	private static void parseConfigFile() throws IOException {
		String text;
		String sectionIdentifier = "";
		int tagOffset = 0;
		int dynamicCount = -1;
		int sectionCount = 0;
		int modifier = -1;
		String[] tagsNeeded = null;
		boolean moreThanOne = false;

		while ((text = configReader.readLine()) != null) {
			System.out.println(text);
			String keyHolder = "";

			// Line is a comment or empty, ignore
			if (text.isEmpty() || text.charAt(0) == '#')
				continue;

			// Line represents the beginning of a section identifier
			if (text.charAt(0) == '[') {
				sectionIdentifier = text.substring(1, text.length() - 1);
				// Check if there are modifiers 
				if (hasTag(sectionIdentifier)) {
					for (String key : configStructure.keySet()) {

						if (key.substring(0, key.length() - 2).equals(sectionIdentifier)) {
							keyHolder = key;
							modifier = Integer.parseInt(key.split("_")[1]);

							switch (modifier) {
							case 0:
								moreThanOne = false;
								break;
							case 1:
								moreThanOne = true;
								dynamicCount++;
								break;
							}

						}
					}
				} else {
					System.err.println("The tag [" + sectionIdentifier + "] is not defined for the CMS configuration.");
					System.exit(0);
				}

				if (moreThanOne) {
					configProperties.put(sectionIdentifier + "_" + dynamicCount, "");
				} else if (!moreThanOne && sectionCount == 0) {
					configProperties.put(sectionIdentifier, "");
					sectionCount++;
				} else {
					System.err.println("Cannot have more than one of tag element: " + sectionIdentifier);
					System.exit(0);
				}

				tagsNeeded = configStructure.get(keyHolder).split(",");
				//Go to the next iteration of the loop to process the lines for this block
				continue;
			}

			// After a section ID has been remembered, the system expects the
			// properties in the order they were defined in the configStructure map
			if (text.substring(0, tagsNeeded[tagOffset].length()).equals(tagsNeeded[tagOffset])) {
				String currentTag = tagsNeeded[tagOffset];

				configProperties.put(sectionIdentifier + "_" + ((moreThanOne) ? dynamicCount + "_" : "") + currentTag, text.split("=")[1]);
				tagOffset++;

				// We're finished with this particular block
				if (tagOffset == (tagsNeeded.length)) {
					tagsNeeded = null;
					tagOffset = 0;
				}
			} else {
				// ERROR id:10t. Config structure isnt proper! Thow an error and
				// stop reading whatever filth we were given
				System.err.println("Invalid config file structure: " + text);
				System.err.println("System was expecting element: " + tagsNeeded[tagOffset]);
				System.err.println("Structure for block tag [" + sectionIdentifier + "] is: ");
				printStructure(tagsNeeded);
				System.exit(0);
			}

		}
	}

	private static boolean hasTag(String s) {
		for (String key : configStructure.keySet()) {
			if (key.substring(0, s.length()).equals(s))
				return true;
		}

		return false;
	}

	private static void generateDefaultConfig() {
		// TODO Auto-generated method stub

	}

	private static void printStructure(String[] tagElements) {
		for (String elem : tagElements) {
			System.err.println("\t" + "->" + elem);
		}
	}
	private static void dumpHashmap(HashMap<String, String> map) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
	}
}