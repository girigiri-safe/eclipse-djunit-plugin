package jp.co.dgic.target;

import java.io.File;

public class MethodCallInConstructor {
	
	private String name;
	private String fileName;
	private AClass aClass;

	public MethodCallInConstructor() {
		this("default.txt");
	}

	public MethodCallInConstructor(String name) {
		this.name = name;
		File f = new File(name);
		this.fileName = f.getName();

		aClass = new AClass("AClass");
	}
	
	public String getName() {
		return name;
	}

	public String getFileName() {
		return fileName;
	}

	public String getAClassName() {
		return aClass.getName();
	}

	public String getNameA() {
		return new AClass("A").getName(); 
	}

	public static String getNameAS() {
		return new AClass("A").getName(); 
	}

	public String getOwnName() {
		return new MethodCallInConstructor("ownName").getName();
	}

	public static String getOwnNameS() {
		return new MethodCallInConstructor("ownNameS").getName();
	}
}