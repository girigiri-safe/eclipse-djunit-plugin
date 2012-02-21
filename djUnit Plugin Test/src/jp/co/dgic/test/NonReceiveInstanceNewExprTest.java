package jp.co.dgic.test;

import jp.co.dgic.target.NonReceiveInstanceNewExpr;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class NonReceiveInstanceNewExprTest extends DJUnitTestCase {
	
	private NonReceiveInstanceNewExpr target;

	public NonReceiveInstanceNewExprTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		target = new NonReceiveInstanceNewExpr();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
		target = null;
	}
	
	public void testNormal001() throws Exception {
		target.createThread();
	}

}
