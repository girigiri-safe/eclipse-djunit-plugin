package jp.co.dgic.test;

import jp.co.dgic.target.SubClass;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class SubClassTest extends DJUnitTestCase {
	
	private SubClass target;

	public SubClassTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		target = new SubClass();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
		target = null;
	}
	
	public void testConstructor() throws Exception {
		
	}

}
