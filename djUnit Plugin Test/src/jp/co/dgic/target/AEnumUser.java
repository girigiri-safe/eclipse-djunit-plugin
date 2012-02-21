package jp.co.dgic.target;

public class AEnumUser {

	public void useEnum() {
		// “ú–{Œê‚ÌƒRƒƒ“ƒg(MS932)
		isTwo(AEnum.ONE);
//		AEnum.length();
	}
	
	public boolean isTwo(AEnum e) {
		return e == AEnum.TWO;
	}
}
