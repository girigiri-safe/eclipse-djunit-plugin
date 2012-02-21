package jp.co.dgic.target;

import java.util.HashMap;

public class MethodCallTestTarget {
	
	private HashMap map;
	
	public MethodCallTestTarget() {
		map = new HashMap();
		map.put("a", "value");
	}

	public void method001(Object value) {
		
	}
	
	public void method002() {
		Object value = null;
	}
	
	public void usePrintGet() {
		System.out.println(map.get("a"));
	}
	
	public void useGet() {
//		print(map.get("a"));
	}
	
	public void usePrint() {
		System.out.println(get("a"));
	}
	
	private Object get(String key) {
		return map.get(key);
	}
	
	private void print(Object message) {
		System.out.println(message);
	}
}
