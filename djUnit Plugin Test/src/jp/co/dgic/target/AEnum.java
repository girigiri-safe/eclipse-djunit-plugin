package jp.co.dgic.target;

public enum AEnum {
	ONE, 
	TWO,
	THREE;
	
	public static int length() {
		// ���{��̃R�����g(MS932)
		return AEnum.values().length;
	}
}
