package cms.communication.parsers;

import cms.communication.structures.Transmission;
import cms.databank.OverLord;
import cms.databank.structures.Node;

public class TransmissionParser {
	
	public static void parse(Transmission trans, Node[] core){
		trans.printTransmission();
		int coreid = Integer.parseInt(trans.from);
		
		if(trans.opcode.equals("110")){
			for(int i = 0; i < trans.particles.length; i++){	
				String particleClass = trans.particles[i].getParticleClass();
				
				if(particleClass.equals("temp")){
					core[coreid].setTemperature(Double.parseDouble(trans.particles[i].getContent()));
					
				} else if(particleClass.equals("ram")){
					core[coreid].setRam(Double.parseDouble(trans.particles[i].getContent())/1000);
					
				} else if(particleClass.equals("cpu")){
					core[coreid].setCpu(Double.parseDouble(trans.particles[i].getContent()));
					
				} else if(particleClass.equals("part")){
					core[coreid].setParticles(Double.parseDouble(trans.particles[i].getContent()));
					
				} else if(particleClass.equals("ip")){
					core[coreid].setIp(trans.particles[i].getContent());
				}

			}
		}
	}
	
	public static void parseNew(Transmission trans){
		int coreid = Integer.parseInt(trans.from);
		
		if(trans.opcode.equals("110")){
			for(int i = 0; i < trans.particles.length; i++){	
				String particleClass = trans.particles[i].getParticleClass();
				
				if(particleClass.equals("temp")){
					System.err.println("CoreID: " + coreid);
					OverLord.nodeCore.get(coreid).setTemperature(Double.parseDouble(trans.particles[i].getContent()));
					
				} else if(particleClass.equals("ram")){
					OverLord.nodeCore.get(coreid).setRam(Double.parseDouble(trans.particles[i].getContent())/1000);
					
				} else if(particleClass.equals("cpu")){
					OverLord.nodeCore.get(coreid).setCpu(Double.parseDouble(trans.particles[i].getContent()));
					
				} else if(particleClass.equals("part")){
					OverLord.nodeCore.get(coreid).setParticles(Double.parseDouble(trans.particles[i].getContent()));
					
				}

			}
		}
	}

}
