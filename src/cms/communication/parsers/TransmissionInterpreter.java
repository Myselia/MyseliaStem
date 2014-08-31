package cms.communication.parsers;

import cms.communication.structures.Transmission;
import cms.databank.OverLord;
import cms.databank.structures.Node;

public class TransmissionInterpreter {
	
	public static void interpret(Transmission trans){
		int coreid = Integer.parseInt(trans.from.substring(4,trans.from.length()));
		
		if(trans.opcode.equals("110")){
			for(int i = 0; i < trans.atoms.length; i++){	
				String particleClass = trans.atoms[i].getAtomClass();
				
				if(particleClass.equals("temp")){
					System.err.println("CoreID: " + coreid);
					OverLord.nodeCore.get(coreid).setTemperature(Double.parseDouble(trans.atoms[i].getContent()));
					
				} else if(particleClass.equals("ram")){
					OverLord.nodeCore.get(coreid).setRam(Double.parseDouble(trans.atoms[i].getContent())/1000);
					
				} else if(particleClass.equals("cpu")){
					OverLord.nodeCore.get(coreid).setCpu(Double.parseDouble(trans.atoms[i].getContent()));
					
				} else if(particleClass.equals("part")){
					OverLord.nodeCore.get(coreid).setParticles(Integer.parseInt(trans.atoms[i].getContent()));
					
				}

			}
		}
	}

}
