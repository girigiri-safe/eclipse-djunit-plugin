package jp.co.dgic.target;

public class ConstructorTestTarget {

	private int value = 0;

	public ConstructorTestTarget() {
		value = 1;
	}

	public ConstructorTestTarget(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}