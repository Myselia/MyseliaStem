package cms.model.communication.format;

public class Particle {
	private String particle_type, particle_class;
	private String content;
	
	public Particle(String particle_type, String particle_class){
		this.particle_type = particle_type;
		this.particle_class = particle_class;
	}
	
	public void addContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return content;
	}
	
	public String getParticleType(){
		return particle_type;
	}
	
	public String getParticleClass(){
		return particle_class;
	}

}
