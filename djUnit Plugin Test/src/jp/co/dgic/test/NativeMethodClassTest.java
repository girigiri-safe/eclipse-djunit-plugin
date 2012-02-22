/*
 * Created on 2005/02/28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jp.co.dgic.test;

import jp.co.dgic.target.NativeMethodClass;
import jp.co.dgic.testing.common.virtualmock.InternalMockObjectManager;
import jp.co.dgic.testing.common.virtualmock.MockObjectManager;
import junit.framework.TestCase;

/**
 * @author kataoka
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NativeMethodClassTest extends TestCase {

	private NativeMethodClass target;
	
	public NativeMethodClassTest(String name) {
		super(name);
	}
	

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		target = new NativeMethodClass();
	}
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		target = null;
	}
	
	public void testNormal001() throws Exception {
		MockObjectManager.addReturnValue(NativeMethodClass.class, "getString", "value");
		
//		assertEquals("value", target.getString());
		
	}

	public void testNormal002() throws Exception {
		MockObjectManager.addReturnValue(NativeMethodClass.class, "getString", "value");
		
//		assertEquals("value", target.getString());
		
	}
}
