package cms.communication.structures;

public class Atom {
	private String atom_type, atom_class;
	private String content;
	
	public Atom(String atom_type, String atom_class){
		this.atom_type = atom_type;
		this.atom_class = atom_class;
	}
	
	public void addContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return content;
	}
	
	public String getParticleType(){
		return atom_type;
	}
	
	public String getParticleClass(){
		return atom_class;
	}

}
