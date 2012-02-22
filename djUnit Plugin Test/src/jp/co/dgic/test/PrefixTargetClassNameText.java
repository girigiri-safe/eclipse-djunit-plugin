package jp.co.dgic.test;

import jp.co.dgic.target.PrefixTargetClassName;
import jp.co.dgic.target.TargetClassName;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class PrefixTargetClassNameText extends DJUnitTestCase {
	
	private TargetClassName target;
	private PrefixTargetClassName prefixTarget;
	

	public PrefixTargetClassNameText(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		target = new TargetClassName();
		prefixTarget = new PrefixTargetClassName();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
		target = null;
		prefixTarget = null;
	}
	
	public void testNormal001()  throws Exception {
		assertEquals("Target", target.getString());
		assertEquals("Prefix target", prefixTarget.getString());
	}

	/**
	 * Full package name use 
	 * @throws Exception
	 */
	public void testNormal002()  throws Exception {
		
		addReturnValue(TargetClassName.class, "getString", "NON");
		addReturnValue(PrefixTargetClassName.class, "getString", "PREFIX");
		
		assertEquals("NON", target.getString());
		assertEquals("PREFIX", prefixTarget.getString());
	}

	/**
	 * Simple class name use 
	 * @throws Exception
	 */
	public void testNormal003()  throws Exception {
		
		addReturnValue(TargetClassName.class, "getString", "NON");
		
		assertEquals("NON", target.getString());
		assertEquals("Prefix target", prefixTarget.getString());
	}

	/**
	 * Simple class name use 
	 * @throws Exception
	 */
	public void testNormal004_1()  throws Exception {
		
		addReturnValue(TargetClassName.class, "getString", "NON");
		
		assertEquals("Prefix target", prefixTarget.getString());
		assertEquals("NON", target.getString());
	}

	/**
	 * Simple class name use 
	 * @throws Exception
	 */
	public void testNormal004_2()  throws Exception {
		
		setReturnValueAtAllTimes(TargetClassName.class, "getString", "NON");
		
		assertEquals("Prefix target", prefixTarget.getString());
		assertEquals("NON", target.getString());
	}

	/**
	 * Simple class name use 
	 * @throws Exception
	 */
	public void testNormal005()  throws Exception {
		
		addReturnValue(PrefixTargetClassName.class, "getString", "PREFIX");
		
		assertEquals("Target", target.getString());
		assertEquals("PREFIX", prefixTarget.getString());
	}

	/**
	 * Simple class name use 
	 * @throws Exception
	 */
	public void testNormal006()  throws Exception {
		
		addReturnValue(PrefixTargetClassName.class, "getString", "PREFIX");
		
		assertEquals("PREFIX", prefixTarget.getString());
		assertEquals("Target", target.getString());
	}

	/**
	 * Full package name use 
	 * @throws Exception
	 */
	public void testNormal007()  throws Exception {
		
		target.getString();
		prefixTarget.getString();
		
		assertCalled(TargetClassName.class, "getString");
		assertCalled(PrefixTargetClassName.class, "getString");
	}

	/**
	 * Full package name use 
	 * @throws Exception
	 */
	public void testNormal008()  throws Exception {
		
		target.getString();
//		prefixTarget.getString();
		
		assertCalled(TargetClassName.class, "getString");
		assertNotCalled(PrefixTargetClassName.class, "getString");
	}

	/**
	 * Full package name use 
	 * @throws Exception
	 */
	public void testNormal009()  throws Exception {
		
//		target.getString();
		prefixTarget.getString();
		
		assertNotCalled(TargetClassName.class, "getString");
		assertCalled(PrefixTargetClassName.class, "getString");
	}

	/**
	 * Simple class name use 
	 * @throws Exception
	 */
	public void testNormal010()  throws Exception {
		
		target.getString();
//		prefixTarget.getString();
		
		assertCalled(TargetClassName.class, "getString");
		assertNotCalled(PrefixTargetClassName.class, "getString");
	}

	/**
	 * Simple class name use 
	 * @throws Exception
	 */
	public void testNormal011()  throws Exception {
		
//		target.getString();
		prefixTarget.getString();
		
		assertNotCalled(TargetClassName.class, "getString");
		assertCalled(PrefixTargetClassName.class, "getString");
	}

}
