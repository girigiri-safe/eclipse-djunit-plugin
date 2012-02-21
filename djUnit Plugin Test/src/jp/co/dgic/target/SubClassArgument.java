package jp.co.dgic.target;

public class SubClassArgument extends SuperClassArgument {

	public SubClassArgument() {
		super();
	}
	
	public SubClassArgument(String name) {
		super(name);
	}
	
	public String getName() {
		return super.getName();
	}
	
	public void setName(String name) {
		super.setName(name);
	}
}
