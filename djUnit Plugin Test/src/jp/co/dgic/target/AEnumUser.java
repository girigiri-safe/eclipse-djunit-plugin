package jp.co.dgic.target;

public class AEnumUser {

	public void useEnum() {
		// 日本語のコメント(MS932)
		isTwo(AEnum.ONE);
//		AEnum.length();
	}
	
	public boolean isTwo(AEnum e) {
		return e == AEnum.TWO;
	}
}
