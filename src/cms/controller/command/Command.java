package cms.controller.command;

public interface Command {
	public abstract void action(String arg);
	public abstract void define();
	public abstract String getCommandSignature();
}
