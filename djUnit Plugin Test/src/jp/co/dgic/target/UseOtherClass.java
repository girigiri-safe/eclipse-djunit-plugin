package jp.co.dgic.target;

public class UseOtherClass {

	public String getOtherClassName() {
		OtherClass otherClass = new OtherClass();
		
		return otherClass.getName();
	}
}
