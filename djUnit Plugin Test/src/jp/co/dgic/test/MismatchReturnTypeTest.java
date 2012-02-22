package jp.co.dgic.test;

import java.awt.AWTException;
import java.util.HashMap;

import jp.co.dgic.target.TestTarget;
import jp.co.dgic.testing.common.DJUnitRuntimeException;
import jp.co.dgic.testing.common.virtualmock.IgnoreMethodValue;
import jp.co.dgic.testing.common.virtualmock.MockObjectManager;
import jp.co.dgic.testing.common.virtualmock.NullReturnValue;
import junit.framework.TestCase;

public class MismatchReturnTypeTest extends TestCase {

	private TestTarget target;

	public MismatchReturnTypeTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		MockObjectManager.initialize();

		target = new TestTarget();
	}

	protected void tearDown() throws Exception {
		super.tearDown();

		target = null;
	}

	public void testMismatchReturnType001() throws Exception {
		MockObjectManager.addReturnValue(TestTarget.class,
				"getBooleanValue", new Integer(0));

		try {
			target.getBooleanValue();
			fail("実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}
	}

	public void testMismatchReturnType002() throws Exception {
		MockObjectManager.addReturnValue(TestTarget.class,
				"getByteValue", new Integer(0));

		try {
			target.getByteValue();
			fail("実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}
	}

	public void testMismatchReturnType003() throws Exception {
		MockObjectManager.addReturnValue(TestTarget.class,
				"getCharValue", new Integer(0));

		try {
			target.getCharValue();
			fail("実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}
	}

	public void testMismatchReturnType004() throws Exception {
		MockObjectManager.addReturnValue(TestTarget.class,
				"getShortValue", new Integer(0));

		try {
			target.getShortValue();
			fail("実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}
	}

	public void testMismatchReturnType005() throws Exception {
		MockObjectManager.addReturnValue(TestTarget.class,
				"getIntValue", new Long(0));

		try {
			target.getIntValue();
			fail("実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}
	}

	public void testMismatchReturnType006() throws Exception {
		MockObjectManager.addReturnValue(TestTarget.class,
				"getLongValue", new Integer(0));

		try {
			target.getLongValue();
			fail("実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}
	}

	public void testMismatchReturnType007() throws Exception {
		MockObjectManager.addReturnValue(TestTarget.class,
				"getFloatValue", new Integer(0));

		try {
			target.getFloatValue();
			fail("実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}
	}

	public void testMismatchReturnType008() throws Exception {
		MockObjectManager.addReturnValue(TestTarget.class,
				"getDoubleValue", new Integer(0));

		try {
			target.getDoubleValue();
			fail("実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}
	}

	public void testMismatchReturnType009() throws Exception {
		MockObjectManager.addReturnValue(TestTarget.class,
				"getField2", new HashMap());

		try {
			target.getField2();
			fail("実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}
	}

	public void testMismatchReturnType010() throws Exception {
		MockObjectManager.addReturnValue(TestTarget.class,
				"setField1", new Object());

		try {
			target.setField1(1);
			fail("実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}
	}

	public void testMismatchReturnType011() throws Exception {
		MockObjectManager.addReturnValue(TestTarget.class,
				"setField1", new IgnoreMethodValue());

		target.setField1(1);
	}

	public void testMismatchReturnType012() throws Exception {
		MockObjectManager.addReturnValue(TestTarget.class,
				"setField1", new NullReturnValue());

		target.setField1(1);
	}

}