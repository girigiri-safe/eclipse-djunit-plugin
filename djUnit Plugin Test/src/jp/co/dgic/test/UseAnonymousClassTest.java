package jp.co.dgic.test;

import jp.co.dgic.target.UseAnonymousClassTarget;
import jp.co.dgic.testing.common.virtualmock.MockObjectManager;
import junit.framework.TestCase;

public class UseAnonymousClassTest extends TestCase {
	
	private UseAnonymousClassTarget target;

	public UseAnonymousClassTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		MockObjectManager.initialize();
		target = new UseAnonymousClassTarget();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
		target = null;
	}
	
	public void testNormal001() throws Exception {
		target.methodA();
	}

	public void testNormal002() throws Exception {
		target.methodB();
	}

}
