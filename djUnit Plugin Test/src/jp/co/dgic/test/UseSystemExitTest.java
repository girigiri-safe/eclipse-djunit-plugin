package jp.co.dgic.test;

import jp.co.dgic.target.UseSystemExit;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class UseSystemExitTest extends DJUnitTestCase {


	public void testNormal001() {

		addReturnValue("java.lang.System", "exit");

		UseSystemExit.main();

		Integer arg = (Integer) getArgument("java.lang.System", "exit", 0);
		assertEquals(0, arg.intValue());
	}

}
