package jp.co.dgic.target;

public enum AEnum {
	ONE, 
	TWO,
	THREE;
	
	public static int length() {
		// 日本語のコメント(MS932)
		return AEnum.values().length;
	}
}
