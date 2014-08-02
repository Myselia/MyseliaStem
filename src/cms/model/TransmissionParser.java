package cms.model;

import cms.model.communication.format.*;
import cms.model.data.BeanNode;

public class TransmissionParser {
	public static void parse(Transmission trans, BeanNode[] core){
		int coreid = Integer.parseInt(trans.from);
		
		if(trans.opcode.equals("100")){
			for(int i = 0; i < trans.particles.length; i++){
				
				if(trans.particles[i].getParticleClass().equals("temp")){
					core[coreid].setTemperature(Double.parseDouble(trans.particles[i].getContent()));
					
				} else if(trans.particles[i].getParticleClass().equals("ram")){
					core[coreid].setRam(Double.parseDouble(trans.particles[i].getContent()));
					
				} else if(trans.particles[i].getParticleClass().equals("cpu")){
					core[coreid].setCpu(Double.parseDouble(trans.particles[i].getContent()));
					
				} else if(trans.particles[i].getParticleClass().equals("part")){
					core[coreid].setParticles(Double.parseDouble(trans.particles[i].getContent()));
				}
				
			}
		}
	}

}
