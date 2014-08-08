package cms.model.communication.format;

import cms.model.data.BeanNode;

public class TransmissionParser {
	public static void parse(Transmission trans, BeanNode[] core){
		trans.printTransmission();
		int coreid = Integer.parseInt(trans.from);
		
		if(trans.opcode.equals("110")){
			System.out.println("opcode check passed");
			for(int i = 0; i < trans.particles.length; i++){	
				String particleClass = trans.particles[i].getParticleClass();
				
				if(particleClass.equals("temp")){
					System.out.println("istemp");
					core[coreid].setTemperature(Double.parseDouble(trans.particles[i].getContent()));
					
				} else if(particleClass.equals("ram")){
					core[coreid].setRam(Double.parseDouble(trans.particles[i].getContent())/1000);
					
				} else if(particleClass.equals("cpu")){
					core[coreid].setCpu(Double.parseDouble(trans.particles[i].getContent()));
					
				} else if(particleClass.equals("part")){
					core[coreid].setParticles(Double.parseDouble(trans.particles[i].getContent()));
				}

			}
		}
	}

}
