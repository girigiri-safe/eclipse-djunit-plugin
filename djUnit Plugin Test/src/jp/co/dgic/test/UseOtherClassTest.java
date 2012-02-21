package jp.co.dgic.test;

import jp.co.dgic.target.OtherClass;
import jp.co.dgic.target.UseOtherClass;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class UseOtherClassTest extends DJUnitTestCase {

	public UseOtherClassTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testNormal001() throws Exception {
		
		UseOtherClass useOtherClass = new UseOtherClass();
		
		assertEquals("default", useOtherClass.getOtherClassName());
	}

	public void testNormal002() throws Exception {
		
		UseOtherClass useOtherClass = new UseOtherClass();
		
		addReturnValue("jp.co.dgic.target.OtherClass", "<init>", new OtherClass("Test"));
		
		assertEquals("Test", useOtherClass.getOtherClassName());
	}

}
