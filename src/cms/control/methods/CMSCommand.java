package cms.control.methods;

import cms.Main;
import cms.databank.OverLord;
import cms.display.bars.InfoBar;
import cms.display.info.AddressPanel;

public class CMSCommand {

	
	/*
	 * GENERAL NODE OPERATIONS
	 */
	
	/**
	 * Kicks the node with id off the network if possible
	 * @param id	sessionID of the node
	 */
	public static void kick(int id){
		if (OverLord.isNodeAcive(id)) {
			System.out.println("Kicking node " + id);
			OverLord.removeNode(id);
		} else {
			System.out.println("Node " + id + " is not available for modification");
		}
	}
	
	/**
	 * 
	 * @param type
	 */
	public static void make(int type){
		int coreid = OverLord.getSelectedCore();
		if(coreid == -1){
			System.out.println("e>" + "No core currently selected");
			return; /**TODO make(int): verify if the -1 case is checked appropriately**/
		}else{
		OverLord.nodeCore.get(coreid).setType(type);
		AddressPanel.nodeButton(coreid).repaint();
		}
	}
	
	/**
	 * 
	 * @param s
	 */
	public static void call(String s) {
		int call = Integer.parseInt(s);
		AddressPanel.unselectLast(AddressPanel.nodeButton(call));
		AddressPanel.nodeButton(call).select(true);
	}
	
	/*
	 * BROADCAST COMMANDS
	 */
	public static void seekOn() {
		Main.startBCastThread(Main.getBcastRunnable(), Main.getbCastCommunicator());
		System.out.println("Seek mode activated");
		InfoBar.quickbar.seekbutton.setSelect();
	}

	public static void seekOff(){
		Main.getbCastCommunicator().interrupt();
		System.out.println("Seek mode deactivated");
		InfoBar.quickbar.seekbutton.setSelect();
	}
	
	/*
	 * EXIT COMMANDS
	 */
	public static void cleanExit(){
		System.out.println("s>" + "Cleaning");
		System.exit(0);
	}




}
