package jp.co.dgic.test;

import java.util.HashMap;

import jp.co.dgic.testing.common.DJUnitRuntimeException;
import jp.co.dgic.testing.common.virtualmock.IgnoreMethodValue;
import jp.co.dgic.testing.common.virtualmock.MockObjectManager;
import jp.co.dgic.testing.common.virtualmock.NullReturnValue;
import junit.framework.TestCase;

public class MismatchReturnTypeForMethodCallTest extends TestCase {
	
	private class SystemClassMethodCallTarget {
		
		public String getString() {
			String message = "message";
			return message.substring(4);
		}
		
		public void callVoidMethod() {
			HashMap map = new HashMap();
			map.clear();
		}
	}
	
	private SystemClassMethodCallTarget target;

	public MismatchReturnTypeForMethodCallTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		MockObjectManager.initialize();
		
		target = new SystemClassMethodCallTarget();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testNormal001() throws Exception {
		assertEquals("age", target.getString());
	}

	public void testNormal002() throws Exception {
		
		MockObjectManager.addReturnValue("java.lang.String", "substring", "zzz");
		
		assertEquals("zzz", target.getString());
	}

	public void testNormal003() throws Exception {
		
		MockObjectManager.addReturnNull("java.lang.String", "substring");
		
		assertNull(target.getString());
	}

	public void testError001() throws Exception {
		
		MockObjectManager.addReturnValue("java.lang.String", "substring");
		
		try {
			target.getString();
			fail("実行されないはず");
		} catch(DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}
	}

	public void testError002() throws Exception {
		
		MockObjectManager.addReturnValue("java.lang.String", "substring", new HashMap());
		
		try {
			target.getString();
			fail("実行されないはず");
		} catch(DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}
	}

	public void testError003() throws Exception {
		
		MockObjectManager.addReturnValue("java.util.HashMap", "clear", new Object());
		
		try {
			target.callVoidMethod();
			fail("実行されないはず");
		} catch(DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}
	}

	public void testNormal004() throws Exception {
		
		MockObjectManager.addReturnValue("java.util.HashMap", "clear", new NullReturnValue());
		
		target.callVoidMethod();
	}

	public void testNormal005() throws Exception {
		
		MockObjectManager.addReturnValue("java.util.HashMap", "clear", new IgnoreMethodValue());
		
		target.callVoidMethod();
	}

}
