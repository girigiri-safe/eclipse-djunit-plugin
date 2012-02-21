package jp.co.dgic.test;

import jp.co.dgic.target.MethodCallTestTarget;
import jp.co.dgic.testing.common.virtualmock.MockObjectManager;
import junit.framework.TestCase;

public class MethodCallTest extends TestCase {
	
	private MethodCallTestTarget target;

	public MethodCallTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		MockObjectManager.initialize();
		
		target = new MethodCallTestTarget();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testNormal001() throws Exception {
		target.usePrintGet();
	}

	public void testNormal002() throws Exception {
		target.useGet();
	}

	public void testNormal003() throws Exception {
		target.usePrint();
	}

}
