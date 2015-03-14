package cms.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import cms.control.user.Command;
import cms.control.user.CommandHelp;

public class CommandSystem {

	private static int index = 0;
	private static String[] history = new String[20];
	private static String[] commandparts;
	private static String command;
	private static ReflectionCommand[] commandClasses = null;

	private static boolean debug = false;

	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages. Fetches all the useful fields for
	 * later use and store them in commandClasses
	 * 
	 * @author Victor Tatai Adapted for project needs by
	 * @author Philippe Hebert
	 * @source http://dzone.com/snippets/get-all-classes-within-package
	 * @param packageName
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static void setClasses(String packageName)
			throws ClassNotFoundException, IOException, NoSuchMethodException,
			IllegalAccessException, InstantiationException,
			InvocationTargetException {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();

		// Error #001 Verification
		if (classLoader == null) {
			System.out
					.println("cms.controller.CommandSystem.setClasses() has encountered an error (#001).");
			System.out
					.println("Please force kill the application and investigate.");
		}
		
		ArrayList<Class<Command>> classes = new ArrayList<Class<Command>>();
		List<File> dirs = new ArrayList<File>();
		
		if (debug) {
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
	  
		} else {
			// TESTING SHIT - IGNORE START
			TESTgetClasses("MyceliaStem.jar", classes);
			// TESTING SHIT - IGNORE END
		}
		
		classes.trimToSize();
		// We don't want abstract classes or interfaces in commandClasses.
		int abstract_count = 0;
		for (Class<Command> c : classes) {
			if (Modifier.isAbstract(c.getModifiers()))
				abstract_count++;
		}
		System.out.println("Populating commandClasses");
		// Populating commandClasses and its members.
		commandClasses = new ReflectionCommand[classes.size() - abstract_count];
		for (int i = 0, j = 0; i < classes.size(); i++) {
			Class<Command> current = classes.get(i);
			// Debug
			System.out.println(current);
			// Method[] allofthem = current.getMethods();
			// Debug
			/*
			 * for(Method m : allofthem){ System.err.println(m); }
			 */
			if (Modifier.isAbstract(current.getModifiers()))
				continue;
			Method[] methods = { current.getMethod("getCommandSignature"),
					current.getMethod("action", new Class[] { String.class }) };
			Command c_obj = (Command) current.getConstructor().newInstance();
			String comm_current = (String) methods[0].invoke(c_obj);
			commandClasses[j] = new ReflectionCommand(current, comm_current,
					methods);
			j++;
		}
		CommandHelp.setCommands(commandClasses);
	}

	/**
	 * Recursive method used to find all classes in a given directory and
	 * subdirs
	 * 
	 * @author Victor Tatai
	 * @source http://dzone.com/snippets/get-all-classes-within-package
	 * @param directory
	 * @param packageName
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	private static List<Class<Command>> findClasses(File directory,
			String packageName) throws ClassNotFoundException, IOException {
		List<Class<Command>> classes = new ArrayList<Class<Command>>();

		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				// assert !file.getName().contains(".");
				classes.addAll(findClasses(file,
						packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add((Class<Command>) Class.forName(packageName
						+ '.'
						+ file.getName().substring(0,
								file.getName().length() - 6)));
			}
		}
		return classes;
	}

	/**
	 * Verifies if command is valid by comparing first word with static variable
	 * of AbstractCommand subclasses. Makes use of quasi-anonymous objects.
	 * Hardly needs any maintenance - adapts to the number of Command classes in
	 * cms.controller.command. Calls the proper method and print() method.
	 * 
	 * @author Philippe Hebert
	 * @param str
	 */
	public static void command(String str) {
		history(str);
		str = str.trim();
		commandparts = str.split(" ");
		command = str;

		/*
		 * Debug System.out.print("command received: "); for(String s :
		 * commandparts){ System.out.print("'" + s + "' "); }
		 */
		int j = 0;
		for (ReflectionCommand c : commandClasses) {
			if (c == null)
				System.out.println("c" + j + " is null");
			try {
				/*
				 * Debug System.out.println("" + methods[0].invoke(c_obj));
				 */
				if (commandparts[0].equalsIgnoreCase(c.getCommand())) {
					print(true);
					Method[] methods = c.getMethods();
					Command c_obj = (Command) c.getClassField()
							.getConstructor().newInstance();
					methods[1].invoke(c_obj, command);
					return;
				}
			} catch (Exception e) {
				System.out
						.println("b>"
								+ "!> "
								+ "cms.controller.CommandSystem.command(String) has encountered an error (#002).");
				System.out.println("b>" + "!> " + "Exception thrown:"
						+ e.getClass().getCanonicalName());
				System.out
						.println("Please force kill the application and investigate.");
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
		if (index < 0) {
			index = 0;
		}
		return history[index];
	}

	// Naughty Testing Stuff
	public static List getClasseNames(String jarName) {
		ArrayList classes = new ArrayList();

		if (debug)
			System.out.println("Jar " + jarName);

		try {
			JarInputStream jarFile = new JarInputStream(new FileInputStream(
					jarName));
			JarEntry jarEntry;

			while (true) {
				jarEntry = jarFile.getNextJarEntry();
				if (jarEntry == null) {
					break;
				}
				if (jarEntry.getName().endsWith(".class")) {
					if (debug)
						System.out.println("Found "
								+ jarEntry.getName().replaceAll("/", "\\."));
					classes.add(jarEntry.getName().replaceAll("/", "\\."));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}

	public static void TESTgetClasses(String jarName, ArrayList classes) throws IOException,
			ClassNotFoundException {
		JarFile jarFile = new JarFile(jarName);
		Enumeration e = jarFile.entries();

		URL[] urls = { new URL("jar:file:" + jarName + "!/") };
		URLClassLoader cl = URLClassLoader.newInstance(urls);

		while (e.hasMoreElements()) {
			JarEntry je = (JarEntry) e.nextElement();
			if (je.isDirectory() || !je.getName().endsWith(".class")) {
				continue;
			}
			// -6 because of .class
			if (je.getName().startsWith("cms/control")) {
				String className = je.getName().substring(0,
						je.getName().length() - 6);
				className = className.replace('/', '.');
				System.out.println("FOUND: " + className);
				//Class c = cl.loadClass(className);
				classes.add((Class<Command>) Class.forName(className));
			}

		}
	}

}
