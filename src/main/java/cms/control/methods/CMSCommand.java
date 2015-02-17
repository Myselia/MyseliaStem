package cms.control.methods;

import cms.Main;
import cms.databank.OverLord;
import cms.display.info.AddressPanel;
import cms.display.info.QuickPanel;

public class CMSCommand {

	/*
	 * GENERAL NODE OPERATIONS
	 */

	/**
	 * Kicks the node with id off the network if possible
	 * 
	 * @param id
	 *            sessionID of the node
	 */
	public static void kick(int id) {
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
	public static void make(int type) {
		int coreid = OverLord.getSelectedCore();
		if (coreid == -1) {
			System.out.println("e>" + "No core currently selected");
			return;
			/** TODO make(int): verify if the -1 case is checked appropriately **/
		} else {
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
	public static void seek(boolean seek) {
		if (seek) {
			Main.startBCastThread(Main.getBcastRunnable(), Main.getbCastCommunicator());
			QuickPanel.setSeekSelect();
		} else {
			Main.getbCastCommunicator().interrupt();
			QuickPanel.setSeekSelect();
		}
	}

	/*
	 * EXIT COMMANDS
	 */
	public static void cleanExit() {
		System.out.println("s>" + "Cleaning");
		System.exit(0);
	}

}
