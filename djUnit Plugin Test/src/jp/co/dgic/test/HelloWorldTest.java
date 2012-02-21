/**
 * This file is part of djUnit plug-in.
 *
 * you can redistribute it and/or modify
 * it under the terms of the CPL Common Public License.
 *
 * See the CPL Common Public License for more details.
 */
package jp.co.dgic.test;

import jp.co.dgic.target.HelloWorld;
import jp.co.dgic.testing.common.virtualmock.MockObjectManager;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class HelloWorldTest extends DJUnitTestCase {

	public HelloWorldTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		MockObjectManager.initialize();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testMain() {

		HelloWorld.main();

		assertCalled("java.io.PrintStream", "println");

		int count = getCallCount("java.io.PrintStream", "println");
		assertEquals(1, count);

		String firstArg = (String) getArgument("java.io.PrintStream", "println", 0);
		assertEquals("Hello World.", firstArg);
	}

	public void testMain2() {

		HelloWorld.main2();

		assertCalled("java.io.PrintStream", "println");

		int count = getCallCount("java.io.PrintStream", "println");
		assertEquals(2, count);

		String argOfFirstCall = (String) getArgument("java.io.PrintStream", "println", 0, 0);
		assertEquals("Hello World.", argOfFirstCall);

		String argOfSecondCall = (String) getArgument("java.io.PrintStream", "println", 1, 0);
		assertEquals("‚±‚ñ‚É‚¿‚í World.", argOfSecondCall);
	}

}
