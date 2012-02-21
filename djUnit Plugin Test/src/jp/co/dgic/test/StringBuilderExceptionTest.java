package jp.co.dgic.test;

import jp.co.dgic.target.StringBuilderException;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class StringBuilderExceptionTest extends DJUnitTestCase {

	private StringBuilderException target;

	public StringBuilderExceptionTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		target = new StringBuilderException();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		target = null;
	}
	
	public void testStringBuilderException001() throws Exception {
		assertEquals("args", target.getMessage());
	}

	public void testStringBuilderException002() throws Exception {
		final StringBuilderException e = new StringBuilderException();
		assertEquals("args", e.getMessage());
	}
	
}
