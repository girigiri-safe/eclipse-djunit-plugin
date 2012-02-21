package jp.co.dgic.target;

public class TestInterfaceUser {

	public String useInterfaceMethod() {
		TestInterface test = new TestInterfaceImpl();
		return test.getString();
	}
}
