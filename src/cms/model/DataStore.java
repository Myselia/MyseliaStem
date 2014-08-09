package cms.model;

import java.util.ArrayList;
import java.util.Random;

import cms.model.communication.format.Transmission;
import cms.model.communication.format.TransmissionParser;
import cms.model.data.BeanNetwork;
import cms.model.data.BeanNode;
import cms.model.data.NodeState;

public class DataStore {

	//public static ArrayList<BeanNode> coreA = new ArrayList<BeanNode>();
	public static BeanNode[] core = new BeanNode[16];
	public static BeanNetwork network = new BeanNetwork();
	public static int nodeCount = -1;

	public DataStore() {
		
	}

	public static void build() {
		for (int i = 0; i < core.length; i++) {
			core[i] = new BeanNode();
			core[i].setType(0);
			core[i].setId(i);
			core[i].setState(NodeState.ABSENT);
		}
	}
	
	public static int getSelectedCore(){
		for(int i = 0; i < core.length; i++){
			if(core[i].isSelected()){
				return core[i].getId();
			}
		}
		return 0;
	}
	
	public static void newData(){
		Random rand = new Random();	
		for(int i = 0; i < core.length; i++){
			core[i].setTemperature((int)(core[i].getTemperature()*1 + (rand.nextInt()%2)*1));
			//LogSystem.log(true,false, "NewValue : " + core[i].getTemperature());
		}
	}
	
	public static void insertData(Transmission trans){
		TransmissionParser.parse(trans, core);
	}
	
	public static int nextNodeID() {
		nodeCount++;
		return nodeCount;
	}
}
