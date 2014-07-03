package cms.controller.command;

public interface Command {
	public void action(String[] parameters);
	public void define();
}
