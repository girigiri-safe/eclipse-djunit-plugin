package jp.co.dgic.test;

import jp.co.dgic.target.StringBuilderUser;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class StringBuilderUserTest extends DJUnitTestCase {
	
	private StringBuilderUser target;

	public StringBuilderUserTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		target = new StringBuilderUser();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		target = null;
	}

	public void testUseStringBuilder() throws Exception {
		assertEquals("abc", target.useStringBuilder());
	}

	public void testUseLength() throws Exception {
		assertEquals(4, target.useLength());
	}
}
