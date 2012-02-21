package jp.co.dgic.target;

import java.io.IOException;

public class ReturnValueProviderTestTarget {

	public int getIntValue(String arg) throws IOException {
		return 5;
	}
	
	public TestTarget getTestTarget(String arg) {
		TestTarget target = new TestTarget();
		target.setField1(5);
		return target;
	}
	
	public String getString(String arg) {
		return new String(arg);
	}
	
	public void addString(String name) {
		System.out.println("ReturnValueProviderTestTarget#addString");
	}
}
