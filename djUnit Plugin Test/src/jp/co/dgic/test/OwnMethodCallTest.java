/**
 * This file is part of djUnit plug-in.
 *
 * you can redistribute it and/or modify
 * it under the terms of the CPL Common Public License.
 *
 * See the CPL Common Public License for more details.
 */
package jp.co.dgic.test;

import jp.co.dgic.target.ConcreteOwnMethodCall;
import jp.co.dgic.testing.common.virtualmock.MockObjectManager;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class OwnMethodCallTest extends DJUnitTestCase {

	private ConcreteOwnMethodCall testClass;

	public OwnMethodCallTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		MockObjectManager.initialize();

		testClass = new ConcreteOwnMethodCall();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testOwnMethodCall001() {

		testClass.mainMethod();

		assertCalled("OwnMethodCall", "mainMethod");
		assertNotCalled("ConcreteOwnMethodCall", "mainMethod");
		assertCalled("ConcreteOwnMethodCall", "ownMethod");
		assertCalled("OwnMethodCall", "ownMethod");
	}
}
