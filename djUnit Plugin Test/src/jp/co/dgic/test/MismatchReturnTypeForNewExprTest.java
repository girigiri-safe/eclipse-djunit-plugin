package jp.co.dgic.test;

import java.util.HashMap;

import jp.co.dgic.target.TestTarget;
import jp.co.dgic.testing.common.DJUnitRuntimeException;
import jp.co.dgic.testing.common.virtualmock.IgnoreMethodValue;
import jp.co.dgic.testing.common.virtualmock.MockObjectManager;
import jp.co.dgic.testing.common.virtualmock.NullReturnValue;
import junit.framework.TestCase;

public class MismatchReturnTypeForNewExprTest extends TestCase {

	private class TargetForNewExpr {

		public HashMap getNewHashMap() {
			return new HashMap();
		}

		public TestTarget getNewTestTarget() {
			return new TestTarget();
		}
	}

	private TargetForNewExpr target;

	public MismatchReturnTypeForNewExprTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		MockObjectManager.initialize();
		target = new TargetForNewExpr();
	}

	protected void tearDown() throws Exception {
		super.tearDown();

		target = null;
	}

	public void testMismatchReturnType001() throws Exception {
		MockObjectManager.addReturnValue(HashMap.class, "<init>",
				new HashMap());

		target.getNewHashMap();
	}

	public void testMismatchReturnType002() throws Exception {
		MockObjectManager.addReturnValue(HashMap.class, "<init>",
				new NullReturnValue());

		target.getNewHashMap();
	}

	public void testMismatchReturnType003() throws Exception {
		MockObjectManager.addReturnValue(HashMap.class, "<init>",
				new IgnoreMethodValue());

		try {
			target.getNewHashMap();
			fail("実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch object type."));
		} catch (Throwable t) {
			t.printStackTrace();
			fail("実行されないはず");
		}
	}

	public void testMismatchReturnType004() throws Exception {
		MockObjectManager.addReturnValue(HashMap.class, "<init>",
				new Object());

		try {
			target.getNewHashMap();
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
				"<init>", new TestTarget());

		target.getNewTestTarget();
	}

	public void testMismatchReturnType011() throws Exception {
		MockObjectManager.addReturnValue(TestTarget.class,
				"<init>", new NullReturnValue());

		target.getNewTestTarget();
	}

	public void testMismatchReturnType012() throws Exception {
		MockObjectManager.addReturnValue(TestTarget.class,
				"<init>", new IgnoreMethodValue());

		target.getNewTestTarget();
	}

	public void testMismatchReturnType013() throws Exception {
		MockObjectManager.addReturnValue(TestTarget.class,
				"<init>", new Object());

		try {
			target.getNewTestTarget();
			fail("実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}
	}

}