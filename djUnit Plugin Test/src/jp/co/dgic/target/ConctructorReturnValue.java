package jp.co.dgic.target;

import java.io.File;

public class ConctructorReturnValue {
	private String name;

	public ConctructorReturnValue(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public static String createName() {
		ConctructorReturnValue ca = new ConctructorReturnValue("foo");
		return ca.getName();
	}
	
	public static String createFileName() {
		File f = new File("test.txt");
		return f.getName();
	}
}
