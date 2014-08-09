package cms.model.communication.format;

import cms.model.DataStore;
import cms.model.data.BeanNode;

public class TransmissionParser {
	public static void parse(Transmission trans, BeanNode[] core){
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
		trans.printTransmission();
		int coreid = Integer.parseInt(trans.from);
		
		if(trans.opcode.equals("110")){
			for(int i = 0; i < trans.particles.length; i++){	
				String particleClass = trans.particles[i].getParticleClass();
				
				if(particleClass.equals("temp")){
					DataStore.coreA.get(coreid).setTemperature(Double.parseDouble(trans.particles[i].getContent()));
					
				} else if(particleClass.equals("ram")){
					DataStore.coreA.get(coreid).setRam(Double.parseDouble(trans.particles[i].getContent())/1000);
					
				} else if(particleClass.equals("cpu")){
					DataStore.coreA.get(coreid).setCpu(Double.parseDouble(trans.particles[i].getContent()));
					
				} else if(particleClass.equals("part")){
					DataStore.coreA.get(coreid).setParticles(Double.parseDouble(trans.particles[i].getContent()));
					
				} else if(particleClass.equals("ip")){
					DataStore.coreA.get(coreid).setIp(trans.particles[i].getContent());
				}

			}
		}
	}

}
