package cms.controller.command;

import cms.model.DataStore;
import cms.view.panel.AddressBar;

public class CommandMake extends AbstractCommand{
	final static String command_signature = "make";
	
	@Override
	public void action(String arg){
		String[] parameters = super.commandParam(arg);
		if(parameters.length > 1){
			if(parameters[1].equals("def")){
				define();
			}else if(parameters[1].equals("node")){
				make(0);
			}else if(parameters[1].equals("CMS")){
				make(1);
			}else if(parameters[1].equals("AMS")){
				make(2);
			}else if(parameters[1].equals("DB")){
				make(3);
			}else {
				System.out.println("e>" + "Not Supported");
			}
		}else{
			System.out.println("e>" + "Command Incomplete");
		}		
	}
	
	private void make(int type){
		int coreid = DataStore.getSelectedCore();
		DataStore.coreA.get(coreid).setType(type);
		AddressBar.nodeButton(coreid).repaint();
	}

	@Override
	public void define() {
		System.out.println("Changes the type of a node in the cluster.");
		System.out.println("Parameters: 'CMS', 'AMS', 'DB', or 'node'.");
	}
	
	@Override
	public String getCommandSignature(){
		return CommandMake.command_signature;
	}

}
