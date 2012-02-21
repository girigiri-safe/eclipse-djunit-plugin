/**
 * This file is part of djUnit plug-in.
 *
 * you can redistribute it and/or modify
 * it under the terms of the CPL Common Public License.
 *
 * See the CPL Common Public License for more details.
 */
package jp.co.dgic.test;

import jp.co.dgic.target.UseSwingOptionPane;
import jp.co.dgic.testing.common.virtualmock.MockObjectManager;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class UseSwingOptionPaneTest extends DJUnitTestCase {

	/**
	 * Constructor for UseSwingOptionPaneTest.
	 * @param arg0
	 */
	public UseSwingOptionPaneTest(String arg0) {
		super(arg0);
	}

	/*
	 * @see DJUnitTestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		MockObjectManager.initialize();
	}

	/*
	 * @see DJUnitTestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testNormal001() {

		addReturnValue("javax.swing.JOptionPane", "showInputDialog", "inputMessage of testNormal001");

		UseSwingOptionPane.main(null);

		String arg = (String) getArgument("java.io.PrintStream", "println", 0);
		assertEquals("inputMessage of testNormal001", arg);
	}

}
