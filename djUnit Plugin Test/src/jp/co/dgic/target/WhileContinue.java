package jp.co.dgic.target;

public class WhileContinue {

	public void exec() throws Exception {
		int elm = 0;
		while (elm < 50) {
			while (elm < 50) {
				if (elm < 300) {
					elm++;
					if (elm < 0) {
						throw new Exception();
					}
					continue;
				}
			}
		}
		
		switch (elm) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			default:
				break;
		}
	}
}
