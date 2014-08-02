package cms.model;

import java.util.Random;

import cms.model.data.BeanNetwork;
import cms.model.data.BeanNode;

public class DataFactory {

	public static BeanNode[] core = new BeanNode[8];
	public static BeanNetwork network = new BeanNetwork();

	public DataFactory() {
	}

	public static void build() {
		
		for (int i = 0; i < core.length; i++) {
			core[i] = new BeanNode();
			core[i].setType(0);
			core[i].setId(i);
			core[i].setState(20);
		}
		
		core[0].setType(1);
		core[0].setState(30);
		
		core[1].setType(2);
		core[1].setState(30);
		
		core[2].setType(3);
		core[2].setState(30);
		
		//core[5].setState(40);
		
		//core[13].setState(10);
		
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
}
