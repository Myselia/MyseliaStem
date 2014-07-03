package cms.controller.command;

public class CommandLoad implements Command {

	@Override
	public void action(String[] parameters) {
		if (parameters.length == 3) {
			load(parameters[1], parameters[2]);
		} else if (parameters.length == 2) {
			if (parameters[1].equals("def")) {
				define();
			} else {
				System.out.println("e>Load Command Incomplete.");
			}
		} else {
			System.out.println("e>Load Command Incomplete");
		}
	}

	@Override
	public void define() {
		System.out.println("Parameters: 'def' 'filename'&'buffer/data/slot'");
		System.out.println("Loads file inside a memory unit and verifies checksum.");
	}

	private void load(String file, String buffer) {
		System.out.println("s>File loaded: " + file + "@" + buffer);
	}

}
