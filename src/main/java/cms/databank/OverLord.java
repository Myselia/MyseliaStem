package cms.databank;

import java.util.ArrayList;
import java.util.Random;

import com.mycelia.common.communication.structures.Atom;
import com.mycelia.common.communication.structures.Header;
import com.mycelia.common.communication.structures.Transmission;

import cms.databank.structures.Network;
import cms.databank.structures.Node;
import cms.databank.structures.NodeState;
import cms.display.graphing.GraphingHistogram;
import cms.display.info.AddressPanel;

public class OverLord {
	// The minimum number of cores to initialize
	private static int INITIAL_CORE_NUM = 6;
	// The ArrayList in charge of storing BeanNode objects. All calls to
	// individual BeanNode instances must be done from this ArrayList
	public static ArrayList<Node> nodeCore = new ArrayList<Node>(10);
	public static Network network = new Network();
	// This int keeps track of the amount of cores currently connected to the
	// server and is used to assign core ID
	public static volatile int nodeCount = -1;
	public static volatile int coreIDTracker = 0;

	public static void build() {
		coreIDTracker = INITIAL_CORE_NUM - 1;

		for (int i = 0; i < INITIAL_CORE_NUM; i++) {
			nodeCore.add(new Node());
			nodeCore.get(i).setType(0);
			nodeCore.get(i).setId(i);
			nodeCore.get(i).setState(NodeState.ABSENT);
		}
	}

	public static int getSelectedCore() {
		for (int i = 0; i < nodeCore.size(); i++) {
			if (nodeCore.get(i).isSelected()) {
				return nodeCore.get(i).getId();
			}
		}
		return -1;
	}

	public static void insertData(Transmission trans) {
		if (trans.get_header().get_opcode() == 1100) {
			int id = Integer.parseInt(Header.get_part(trans.get_header().get_from(), 1));
			ArrayList<Atom> list = trans.get_atoms();
			for (Atom atom : list) {
				switch (atom.get_field()) {
				case "temp":
					nodeCore.get(id).setTemperature(Double.parseDouble(atom.get_value())); break;
				case "cpu":
					nodeCore.get(id).setCpu(Double.parseDouble(atom.get_value())); break;
				case "ram":
					nodeCore.get(id).setRam(Double.parseDouble(atom.get_value())); break;
				case "part":
					nodeCore.get(id).setParticles(Integer.parseInt(atom.get_value())); break;
				}
			}
		}
	}

	public static void newData() {
		Random rand = new Random();
		for (int i = 0; i < nodeCore.size(); i++) {
			if (nodeCore.get(i).getState() == NodeState.ABSENT) {
				nodeCore.get(i).setTemperature((int) (nodeCore.get(i).getTemperature() * 1 + (rand.nextInt() % 2) * 1));
				nodeCore.get(i).setCpu((int) (nodeCore.get(i).getCpu() * 1 + (rand.nextInt() % 2) * 1));
				nodeCore.get(i).setRam((int) (nodeCore.get(i).getRam() * 1 + (rand.nextInt() % 2) * 1));
				nodeCore.get(i).setParticles((int) (nodeCore.get(i).getAtoms() * 1 + (rand.nextInt() % 2) * 1));
			}
		}
	}

	public static void removeNode(int id) {
		nodeCore.remove(id);
		nodeCount--;
		coreIDTracker--;
		AddressPanel.updateButtonList();
		GraphingHistogram.updateBarCount();
	}

	public static boolean isNodeAcive(int id) {
		// if the id is smaller than the core size and larger than zero and it
		// is currently available, return true.
		System.out.println(nodeCore.get(id).getState());
		if (id <= (nodeCore.size() - 1) && id >= 0 && nodeCore.get(id).getState().equals(NodeState.AVAILABLE))
			return true;

		return false;
	}

	public static int getIPInClusterPosition(String ip) {

		for (int i = 0; i < nodeCore.size(); i++) {

			if (nodeCore.get(i).getIp().equals(ip))
				return i;
		}

		return -1;
	}

	public synchronized static int nextNodeID() {
		coreIDTracker++;
		nodeCount++;
		return coreIDTracker;
	}
}
