package jp.co.dgic.target;

import java.util.HashMap;

public class ReturnValueProviderForSystemClassTestTarget {

	private HashMap map = new HashMap();

	public Object get(Object key) {
		return map.get(key);
	}

	public Object put(Object key, Object value) {
		return map.put(key, value);
	}
	
	public void clear() {
		map.clear();
	}
}
