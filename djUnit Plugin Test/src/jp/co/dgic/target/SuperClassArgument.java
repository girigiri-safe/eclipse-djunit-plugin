package jp.co.dgic.target;

public class SuperClassArgument {
	
	private String name = "default";
	
	public SuperClassArgument() {
		
	}
	
	public SuperClassArgument(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
