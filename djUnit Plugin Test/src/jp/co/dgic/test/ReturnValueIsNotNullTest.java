package jp.co.dgic.test;

import jp.co.dgic.testing.common.DJUnitRuntimeException;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class ReturnValueIsNotNullTest extends DJUnitTestCase {

	public ReturnValueIsNotNullTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddReturnValue() throws Exception {
		try {
			addReturnValue("class", "method", null);
			fail("この行は実行されないはず");
		} catch (DJUnitRuntimeException dre) {
			assertEquals((Object)"Return value must be NOT null.[class.method]", (Object)dre.getMessage());
		}
	}

	public void testSetReturnValueAt() throws Exception {
		try {
			setReturnValueAt("class", "method", 0, null);
			fail("この行は実行されないはず");
		} catch (DJUnitRuntimeException dre) {
			assertEquals("Return value must be NOT null.[class.method]", dre.getMessage());
		}
	}
	public void testSetReturnValueAtAllTimes() throws Exception {
		try {
			setReturnValueAtAllTimes("class", "method", null);
			fail("この行は実行されないはず");
		} catch (DJUnitRuntimeException dre) {
			assertEquals("Return value must be NOT null.[class.method]", dre.getMessage());
		}
	}
}
