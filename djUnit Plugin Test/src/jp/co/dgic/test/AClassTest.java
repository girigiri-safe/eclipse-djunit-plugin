package jp.co.dgic.test;

import jp.co.dgic.target.AClass;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class AClassTest extends DJUnitTestCase {

	public AClassTest(String name) {
		super(name);
	}

	public void testMain() {

		addReturnValue("AClass$InnerClass", "getA", new Integer(100));

		AClass.main(null);
	}

}
