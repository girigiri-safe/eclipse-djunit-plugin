/**
 * This file is part of djUnit plug-in.
 *
 * you can redistribute it and/or modify
 * it under the terms of the CPL Common Public License.
 *
 * See the CPL Common Public License for more details.
 */
package jp.co.dgic.test;

import java.io.PrintStream;

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

		assertCalled(PrintStream.class, "println");

		int count = getCallCount(PrintStream.class, "println");
		assertEquals(1, count);

		String firstArg = (String) getArgument(PrintStream.class, "println", 0);
		assertEquals("Hello World.", firstArg);
	}

	public void testMain2() {

		HelloWorld.main2();

		assertCalled(PrintStream.class, "println");

		int count = getCallCount(PrintStream.class, "println");
		assertEquals(2, count);

		String argOfFirstCall = (String) getArgument(PrintStream.class, "println", 0, 0);
		assertEquals("Hello World.", argOfFirstCall);

		String argOfSecondCall = (String) getArgument(PrintStream.class, "println", 1, 0);
		assertEquals("‚±‚ñ‚É‚¿‚í World.", argOfSecondCall);
	}

}
