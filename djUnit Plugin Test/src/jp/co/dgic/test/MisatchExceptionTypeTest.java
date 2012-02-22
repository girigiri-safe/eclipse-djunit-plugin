package jp.co.dgic.test;

import java.awt.AWTException;
import java.sql.SQLException;

import jp.co.dgic.target.TestTarget;
import jp.co.dgic.testing.common.DJUnitRuntimeException;
import jp.co.dgic.testing.common.virtualmock.MockObjectManager;
import junit.framework.TestCase;

public class MisatchExceptionTypeTest extends TestCase {
	
	private TestTarget target;

	public MisatchExceptionTypeTest(String name) {
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

	public void testMismatchException001() throws Exception {
		MockObjectManager.addReturnValue(TestTarget.class, "throwIOAndSQLException", new AWTException(""));
		
		try {
			target.throwIOAndSQLException();
			fail("実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch exception type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}
	}

	public void testMismatchException002() throws Exception {
		MockObjectManager.addReturnValue(TestTarget.class, "throwIOException", new SQLException(""));
		
		try {
			target.throwIOException();
			fail("実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch exception type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}
	}

	public void testMismatchException003() throws Exception {
		MockObjectManager.addReturnValue(TestTarget.class, "throwIOException", new Exception(""));
		
		try {
			target.throwIOException();
			fail("実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch exception type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}
	}

	public void testMismatchException004() throws Exception {
		MockObjectManager.addReturnValue(TestTarget.class, "getField1", new Exception(""));
		
		try {
			target.getField1();
			fail("実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch exception type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}
	}

}
