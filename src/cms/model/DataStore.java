package cms.model;

import java.util.Random;

import cms.model.communication.format.Transmission;
import cms.model.communication.format.TransmissionParser;
import cms.model.data.BeanNetwork;
import cms.model.data.BeanNode;

public class DataStore {

	public static BeanNode[] core = new BeanNode[8];
	public static BeanNetwork network = new BeanNetwork();

	public DataStore() {
	}

	public static void build() {
		for (int i = 0; i < core.length; i++) {
			core[i] = new BeanNode();
			core[i].setType(0);
			core[i].setId(i);
			core[i].setState(20);
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
}
