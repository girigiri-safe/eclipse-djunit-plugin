/**
 * This file is part of djUnit plug-in.
 *
 * you can redistribute it and/or modify
 * it under the terms of the CPL Common Public License.
 *
 * See the CPL Common Public License for more details.
 */
package jp.co.dgic.test;

import java.io.File;

import jp.co.dgic.target.NewFileObject;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class NewFileObjectTest extends DJUnitTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testMain001() {
		NewFileObject.main(null);

		assertCalled(java.io.PrintStream.class, "println");
		String arg = (String) getArgument(java.io.PrintStream.class, "println", 0);
		assertEquals("newText.txt", arg);
	}

	public void testMain002() {

		addReturnValue(java.io.File.class, "<init>", new File("testMain002.txt"));

		NewFileObject.main(null);

		assertCalled(java.io.PrintStream.class, "println");
		String arg = (String) getArgument(java.io.PrintStream.class, "println", 0);
		assertEquals("testMain002.txt", arg);
	}

	public void testMain003() {

		addReturnValue(java.io.File.class, "<init>", new File("test002.txt"));

		NewFileObject.main(null);

		assertCalled(java.io.PrintStream.class, "println");

		String arg = (String) getArgument(java.io.PrintStream.class, "println", 0);
		assertEquals("test002.txt", arg);
	}
}
