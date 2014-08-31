package cms.communication.parsers;

import cms.communication.structures.Transmission;
import cms.databank.OverLord;

public class TransmissionInterpreter {
	
	public static void interpret(Transmission trans){
		int coreid = Integer.parseInt(trans.from.split(":")[1]);
		
		if(trans.opcode.equals("110")){
			for(int i = 0; i < trans.atoms.length; i++){	
				String particleClass = trans.atoms[i].getAtomClass();
				
				if(particleClass.equals("temp")){
					OverLord.nodeCore.get(coreid).setTemperature(Double.parseDouble(trans.atoms[i].getContent()));
					
				} else if(particleClass.equals("ram")){
					OverLord.nodeCore.get(coreid).setRam(Double.parseDouble(trans.atoms[i].getContent())/1000);
					
				} else if(particleClass.equals("cpu")){
					OverLord.nodeCore.get(coreid).setCpu(Double.parseDouble(trans.atoms[i].getContent()));
					
				} else if(particleClass.equals("part")){
					OverLord.nodeCore.get(coreid).setParticles(Double.parseDouble(trans.atoms[i].getContent()));
					
				}

			}
		}
	}

}