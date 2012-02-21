package jp.co.dgic.test;

import jp.co.dgic.target.TestInterface;
import jp.co.dgic.target.TestInterfaceImpl;
import jp.co.dgic.target.TestInterfaceUser;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class InterfaceTest extends DJUnitTestCase {
	
	private TestInterfaceUser target;

	public InterfaceTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		target = new TestInterfaceUser();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		target = null;
	}
	
	public void testGetString() throws Exception {
		assertNull(target.useInterfaceMethod());
		
		// test with implemented class name
		addReturnValue(TestInterfaceImpl.class, "getString", "hello");
		assertEquals("hello", target.useInterfaceMethod());
		isCalled(TestInterfaceImpl.class, "getString");

		// test with interface name
		addReturnValue(TestInterface.class, "getString", "dGIC");
		assertEquals("dGIC", target.useInterfaceMethod());
		isCalled(TestInterface.class, "getString");
	}

}
