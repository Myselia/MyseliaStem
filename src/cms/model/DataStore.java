package cms.model;

import java.util.ArrayList;
import java.util.Random;

import cms.model.communication.format.Transmission;
import cms.model.communication.format.TransmissionParser;
import cms.model.data.BeanNetwork;
import cms.model.data.BeanNode;
import cms.model.data.NodeState;

public class DataStore {

	// The minimum number of cores to initialize
	private static int INITIAL_CORE_NUM = 2;
	// The ArrayList in charge of storing BeanNode objects. All calls to
	// individual BeanNode instances must be done from this ArrayList
	public volatile static ArrayList<BeanNode> coreA = new ArrayList<BeanNode>(
			16);
	public volatile static BeanNetwork network = new BeanNetwork();
	// This int keeps track of the amount of cores currently connected to the
	// server and is used to assign core ID
	public volatile static int nodeCount = -1;

	public static void build() {
		for (int i = 0; i < INITIAL_CORE_NUM; i++) {
			coreA.add(new BeanNode());
			coreA.get(i).setType(0);
			coreA.get(i).setId(i);
			coreA.get(i).setState(NodeState.ABSENT);
		}
	}

	public static int getSelectedCore() {
		for (int i = 0; i < coreA.size(); i++) {
			if (coreA.get(i).isSelected()) {
				return coreA.get(i).getId();
			}
		}
		return -1;
	}

	public static void newData(){
		Random rand = new Random();	
		for(int i = 0; i < coreA.size(); i++){
			if(coreA.get(i).getState() == NodeState.ABSENT){
				coreA.get(i).setTemperature((int)(coreA.get(i).getTemperature()*1 + (rand.nextInt()%2)*1));
				coreA.get(i).setCpu((int)(coreA.get(i).getCpu()*1 + (rand.nextInt()%2)*1));
				coreA.get(i).setRam((int)(coreA.get(i).getRam()*1 + (rand.nextInt()%2)*1));
				coreA.get(i).setParticles((int)(coreA.get(i).getParticles()*1 + (rand.nextInt()%2)*1));
			}
		}
	}

	public static void insertData(Transmission trans) {
		TransmissionParser.parseNew(trans);
	}

	public static void removeNode(int id) {
		coreA.remove(id + INITIAL_CORE_NUM);
		nodeCount--;
	}

	public static int nextNodeID() {
		nodeCount++;
		return nodeCount;
	}
}
