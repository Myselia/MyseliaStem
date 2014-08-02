package cms.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import cms.controller.command.*;

public class CommandSystem {

	private static int index = -1;
	private static String[] history = new String[20];
	private static String[] commandparts;
	private static String command;
	@SuppressWarnings("rawtypes")
	private static Class[] commandClasses = null;
	
	
	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages.
	 * @author Victor Tatai
	 * @source http://dzone.com/snippets/get-all-classes-within-package
	 * @param packageName
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static void setClasses(String packageName) throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		
		//Error #001 Verification
		if(classLoader == null){
			System.out.println("b>" + "!> " + "cms.controller.CommandSystem.setClasses() has encountered an error (#001).");
			System.out.println("b>" + "!> " + "Please force kill the application and investigate.");
		}
		
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		
		ArrayList<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		commandClasses = classes.toArray(new Class[classes.size()]);
	}
	
	/**
	 * Recursive method used to find all classes in a given directory and 
	 * subdirs
	 * @author Victor Tatai
	 * @source http://dzone.com/snippets/get-all-classes-within-package
	 * @param directory
	 * @param packageName
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException, IOException {
		List<Class> classes = new ArrayList<Class>();
		
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}
	
	/**
	 * Verifies if command is valid by comparing first word with static variable of AbstractCommand subclasses.
	 * Makes use of quasi-anonymous objects. Hardly needs any maintenance - 
	 * adapts to the number of Command classes in cms.controller.command.
	 * Calls the proper method and print() method.
	 * @author Philippe Hebert
	 * @param str
	 */
	@SuppressWarnings("unchecked")
	public static void command(String str) {
		history(str);
		str = str.trim();
		commandparts = str.split(" ");
		command = str;
		
		/*Debug
		System.out.print("command received: ");
		for(String s : commandparts){
			System.out.print("'" + s + "' ");
		}*/
		for(@SuppressWarnings("rawtypes") Class c :commandClasses){
			if(c.isInterface() || c.getCanonicalName() == "cms.controller.command.AbstractCommand") continue;
			try{
				Method[] methods = {c.getMethod("getCommandSignature", null), c.getMethod("action", new Class[]{String.class})};
				Command c_obj = (Command) c.getConstructor().newInstance();
				/*Debug
				System.out.println("" + methods[0].invoke(c_obj));*/
				if(commandparts[0].equalsIgnoreCase((String) methods[0].invoke(c_obj))){
					print(true);
					methods[1].invoke(c_obj, command);
					return;
				}
			}catch(Exception e){
				System.out.println("b>" + "!> " + "cms.controller.CommandSystem.command(String) has encountered an error (#002).");
				System.out.println("b>" + "!> " + "Exception thrown:" + e.getClass().getCanonicalName());
				System.out.println("Please force kill the application and investigate.");
				e.printStackTrace();
				return;
			}	
		}
		print(false);
	}

	private static void print(boolean good) {
		if (good) {
			System.out.println("g>" + "&> " + command);
		} else {
			System.out.println("b>" + "!> " + command);
		}
	}

	private static void history(String last) {
		for (int i = (history.length - 1); i > 1; i--) {
			history[i] = history[i - 1];
		}
		history[1] = last;
		history[0] = "";
	}

	public static void index_increase() {
		if (index < history.length) {
			index++;
		}
	}

	public static void index_decrease() {
		if (index > 0) {
			index--;
		}
	}

	public static void index_reset() {
		index = -1;
	}

	public static String get_command() {
		if(index < 0){
			index = 0;
		}
		return history[index];
	}
}
