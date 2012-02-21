package jp.co.dgic.test;

import jp.co.dgic.target.ExceptionCatch;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class ExceptionCatchTest extends DJUnitTestCase {
	
	private ExceptionCatch target;

	public ExceptionCatchTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		target = new ExceptionCatch();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		target = null;
	}
	
	public void testCatchException() throws Exception {
		target.catchException();
	}

}
