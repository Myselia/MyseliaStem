package cms.controller.command;

public class CommandExit implements Command {

	public void action(String[] parameters) {
		if (parameters.length > 1) {
			switch (parameters[1]) {
			case "1":
				System.exit(0);
				break;
			case "now":
				System.exit(0);
				break;
			case "def":
				define();
				break;
			case "clean":
				clean();
				break;
			default: /* nothing */
				;
				break;
			}
		} else {
			System.out.println("e>Exit Command Incomplete");
		}

	}

	@Override
	public void define() {
		System.out.println("Parameters: 'def' 'now' 'clean' ");
		System.out.println("Exits and closes all processes.");
	}
	
	private void clean() {
		
	}

}
