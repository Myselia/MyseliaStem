package cms.model;

import cms.model.data.BeanNetwork;
import cms.model.data.BeanNode;

public class DataFactory {

	public static BeanNode[] core = new BeanNode[16*1];
	public static BeanNetwork network = new BeanNetwork();

	public DataFactory() {
	}

	public static void build() {
		for (int i = 0; i < core.length; i++) {
			core[i] = new BeanNode();
			core[i].setId(i);
			core[i].setState(0);
		}
		core[0].setState(30);
		core[1].setState(30);
		core[2].setState(30);
		core[3].setState(30);
		core[4].setState(30);
		core[5].setState(30);
		core[6].setState(30);
		core[7].setState(40);
		core[8].setState(30);
		core[9].setState(30);
		core[10].setState(20);
		core[11].setState(20);
		core[12].setState(10);
		core[13].setState(20);
		core[14].setState(0);
		core[15].setState(0);
	}
	
	public static int getSelectedCore(){
		for(int i = 0; i < core.length; i++){
			if(core[i].isSelected()){
				return core[i].getId();
			}
		}
		return 0;
	}
}
