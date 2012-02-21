package jp.co.dgic.target;

public enum AEnum {
	ONE, 
	TWO,
	THREE;
	
	public static int length() {
		// “ú–{Œê‚ÌƒRƒƒ“ƒg(MS932)
		return AEnum.values().length;
	}
}
