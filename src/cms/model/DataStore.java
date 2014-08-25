package cms.model;

import java.util.ArrayList;
import java.util.Random;

import cms.model.communication.format.Transmission;
import cms.model.communication.format.TransmissionParser;
import cms.model.data.BeanNetwork;
import cms.model.data.BeanNode;
import cms.model.data.NodeState;
import cms.view.element.GraphingHistogram;
import cms.view.panel.AddressBar;

public class DataStore {

	//The minimum number of cores to initialize
	private static int INITIAL_CORE_NUM = 0;
	//The ArrayList in charge of storing BeanNode objects. All calls to individual BeanNode instances must be done from this ArrayList
	public static ArrayList<BeanNode> coreA = new ArrayList<BeanNode>(10);
	public static BeanNetwork network = new BeanNetwork();
	//This int keeps track of the amount of cores currently connected to the server and is used to assign core ID
	public static volatile int nodeCount = -1;
	public static volatile int coreIDTracker = 0;

	public static void build() {
		coreIDTracker = INITIAL_CORE_NUM - 1;
	
		for (int i = 0; i < INITIAL_CORE_NUM; i++) {
			coreA.add(new BeanNode());
			coreA.get(i).setType(0);
			coreA.get(i).setId(i);
			coreA.get(i).setState(NodeState.ABSENT);
		}
	}
	
	public static int getSelectedCore(){
		for(int i = 0; i < coreA.size(); i++){
			if(coreA.get(i).isSelected()){
				return coreA.get(i).getId();
			}
		}
		return 0;
	}
	
	public static void newData(){
		Random rand = new Random();	
		for(int i = 0; i < coreA.size(); i++){
			coreA.get(i).setTemperature((int)(coreA.get(i).getTemperature()*1 + (rand.nextInt()%2)*1));
			//LogSystem.log(true,false, "NewValue : " + core[i].getTemperature());
		}
	}
	
	public static void insertData(Transmission trans){
		TransmissionParser.parseNew(trans);
	}
	
	public static void removeNode(int id) {
		coreA.remove(id);
		nodeCount--;
		coreIDTracker--;
		AddressBar.updateButtonList();
		GraphingHistogram.updateBarCount();
	}
	
	public static boolean isNodeAcive(int id) {
		//if the id is smaller than the core size and larger than zero and it is currently available, return true.
		System.out.println( coreA.get(id).getState());
		if (id <= (coreA.size() - 1) && id >= 0 && coreA.get(id).getState().equals(NodeState.AVAILABLE))
			return true;
		
		return false;
	}

	public static int getIPInClusterPosition(String ip) {
	
		for (int i = 0; i < coreA.size(); i++) {
		
			if (coreA.get(i).getIp().equals(ip)) 
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
