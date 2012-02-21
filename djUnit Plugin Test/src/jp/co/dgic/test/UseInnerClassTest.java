package jp.co.dgic.test;

import jp.co.dgic.target.UseInnerClassTarget;
import jp.co.dgic.testing.common.virtualmock.MockObjectManager;
import junit.framework.TestCase;

public class UseInnerClassTest extends TestCase {
	
	private UseInnerClassTarget target;

	public UseInnerClassTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		MockObjectManager.initialize();
		
		target = new UseInnerClassTarget();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
		target = null;
	}
	
	public void testUseMethodA() throws Exception {
		target.useMethodA();
	}

	public void testUseMethodB() throws Exception {
		target.useMethodB();
	}

}
