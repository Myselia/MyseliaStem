package cms.controller;

import cms.controller.command.*;

public class CommandSystem {

	private static CommandCall call = new CommandCall();
	private static CommandLoad load = new CommandLoad();
	private static CommandMake make = new CommandMake();

	private static CommandTest test = new CommandTest();
	private static CommandHelp help = new CommandHelp();
	private static CommandExit exit = new CommandExit();

	private static int index = -1;
	private static String[] history = new String[20];
	private static String[] commandparts;
	private static String command;

	public static void command(String str) {
		history(str);
		str = str.trim();
		commandparts = str.split(" ");
		command = str;

		switch (commandparts[0]) {

		case "call":
			print(true);
			call.action(commandparts);
			break;
		case "load":
			print(true);
			load.action(commandparts);
			break;
		case "make":
			print(true);
			make.action(commandparts);
			break;

		case "test":
			print(true);
			test.action(commandparts);
			break;
		case "help":
			print(true);
			help.action(commandparts);
			break;
		case "exit":
			print(true);
			exit.action(commandparts);
			break;

		default:
			print(false);
			break;
		}

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
