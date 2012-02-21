package jp.co.dgic.test;

import jp.co.dgic.target.SingleLineClass;
import junit.framework.TestCase;

public class SingleLineClassTest extends TestCase {

	public SingleLineClassTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSingleLineClass() throws Exception {
		SingleLineClass.p();
	}
}
