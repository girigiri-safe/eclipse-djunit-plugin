package jp.co.dgic.target;

public class AEnumUser {

	public void useEnum() {
		// ���{��̃R�����g(MS932)
		isTwo(AEnum.ONE);
//		AEnum.length();
	}
	
	public boolean isTwo(AEnum e) {
		return e == AEnum.TWO;
	}
}
