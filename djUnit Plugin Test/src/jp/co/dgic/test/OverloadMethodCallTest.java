/**
 * This file is part of djUnit plug-in.
 *
 * you can redistribute it and/or modify
 * it under the terms of the CPL Common Public License.
 *
 * See the CPL Common Public License for more details.
 */
package jp.co.dgic.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.co.dgic.target.OverloadMethodCall;
import jp.co.dgic.testing.common.virtualmock.MockObjectManager;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class OverloadMethodCallTest extends DJUnitTestCase {

	private OverloadMethodCall target;

	public OverloadMethodCallTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		MockObjectManager.initialize();

		target = new OverloadMethodCall();
	}

	protected void tearDown() throws Exception {
		super.tearDown();

		target = null;
	}

	public void testOverloadMethodNormal001() {
		assertEquals(0, target.overloadMethod(0));
		assertEquals(0, target.overloadMethod("0"));
	}

	public void testOverloadMethodNormal002() {
		assertEquals(0, target.overloadMethod(0));

		addReturnValue(OverloadMethodCall.class, "overloadMethod", new Integer(5));
		addReturnValue(OverloadMethodCall.class, "overloadMethod", new Integer(12));
		assertEquals(5, target.overloadMethod("0"));
		assertEquals(12, target.overloadMethod(6));

		assertEquals(100, target.overloadMethod(100));
	}

}
