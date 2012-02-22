package jp.co.dgic.test;

import java.io.IOException;

import jp.co.dgic.target.ReturnValueProviderTestTarget;
import jp.co.dgic.target.TestTarget;
import jp.co.dgic.testing.common.virtualmock.IReturnValueProvider;
import jp.co.dgic.testing.common.virtualmock.MockObjectManager;
import junit.framework.TestCase;

public class ReturnValueProviderTest extends TestCase {

	private class ReturnValueProviderForMethod implements IReturnValueProvider {

		public Object createReturnValue(Object[] args) throws Throwable {
			String arg = (String) args[0];
			if ("a".equals(arg)) {
				return new Integer(1);
			}
			if ("b".equals(arg)) {
				return new Integer(100);
			}
			if ("c".equals(arg)) {
				return new Integer(-1);
			}
			if ("e".equals(arg))
				throw new IOException("Test IOException");
			return null;
		}
	}

	private class ReturnValueProviderForNewExpr implements IReturnValueProvider {

		public Object createReturnValue(Object[] args)
				throws ClassCastException {
			String arg = (String) args[0];
			if ("a".equals(arg)) {
				return new TestTarget() {
					public int getField1() {
						return 10;
					}
				};
			}
			if ("b".equals(arg)) {
				return new TestTarget() {
					public int getField1() {
						return 20;
					}
				};
			}
			if ("c".equals(arg)) {
				return new TestTarget() {
					public int getField1() {
						return -100;
					}
				};
			}
			if ("e".equals(arg))
				throw new ClassCastException();
			return null;
		}
	}

	private class ReturnValueProviderForNewString implements
			IReturnValueProvider {

		public Object createReturnValue(Object[] args) {

			String arg = (String) args[0];

			if ("a".equals(arg))
				return "aaa";
			if ("b".equals(arg))
				return "bbb";
			if ("c".equals(arg))
				return "ccc";

			return null;
		}
	}

	private ReturnValueProviderTestTarget testClass;

	public ReturnValueProviderTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();

		MockObjectManager.initialize();
		testClass = new ReturnValueProviderTestTarget();
	}

	protected void tearDown() throws Exception {
		super.tearDown();

		testClass = null;
	}

	public void testMethodNormal001() throws Exception {
		assertEquals(5, testClass.getIntValue(null));
		assertEquals(5, testClass.getIntValue(""));
		assertEquals(5, testClass.getIntValue("aaa"));
	}

	public void testMethodNormal002() throws Exception {

		MockObjectManager.addReturnValue(ReturnValueProviderTestTarget.class,
				"getIntValue", new Integer(1));
		MockObjectManager.addReturnValue(ReturnValueProviderTestTarget.class,
				"getIntValue", new Integer(-1));
		MockObjectManager.addReturnValue(ReturnValueProviderTestTarget.class,
				"getIntValue", new Integer(100));

		assertEquals(1, testClass.getIntValue(null));
		assertEquals(-1, testClass.getIntValue(""));
		assertEquals(100, testClass.getIntValue("aaa"));
	}

	public void testMethodNormal003() throws Exception {

		ReturnValueProviderForMethod valueProvider = new ReturnValueProviderForMethod();

		MockObjectManager.setReturnValueAtAllTimes(
				ReturnValueProviderTestTarget.class, "getIntValue",
				valueProvider);

		assertEquals(1, testClass.getIntValue("a"));
		assertEquals(100, testClass.getIntValue("b"));
		assertEquals(-1, testClass.getIntValue("c"));
		assertEquals(0, testClass.getIntValue(""));

		try {
			testClass.getIntValue("e");
			fail("Ç±ÇÃçsÇÕÅAé¿çsÇ≥ÇÍÇ»Ç¢ÇÕÇ∏");
		} catch (IOException ioe) {
			assertEquals("Test IOException", ioe.getMessage());
			ioe.printStackTrace();
		}
	}

	public void testNewExprNormal001() throws Exception {

		assertEquals(5, testClass.getTestTarget(null).getField1());
		assertEquals(5, testClass.getTestTarget("").getField1());
		assertEquals(5, testClass.getTestTarget("aaa").getField1());
	}

	public void testNewExprNormal002() throws Exception {

		assertEquals(0,
				MockObjectManager.getCallCount(TestTarget.class, "<init>"));

		TestTarget t1 = new TestTarget() {
			public int getField1() {
				return 10;
			}
		};

		TestTarget t2 = new TestTarget() {
			public int getField1() {
				return 100;
			}
		};

		TestTarget t3 = new TestTarget() {
			public int getField1() {
				return 1000;
			}
		};

		MockObjectManager.addReturnValue(TestTarget.class, "<init>", t1);
		MockObjectManager.addReturnValue(TestTarget.class, "<init>", t2);
		MockObjectManager.addReturnValue(TestTarget.class, "<init>", t3);

		assertEquals(3,
				MockObjectManager.getCallCount(TestTarget.class, "<init>"));

		assertEquals(10, testClass.getTestTarget(null).getField1());
		assertEquals(100, testClass.getTestTarget("").getField1());
		assertEquals(1000, testClass.getTestTarget("aaa").getField1());

		assertEquals(6,
				MockObjectManager.getCallCount(TestTarget.class, "<init>"));

	}

	public void testNewExprNormal003() throws Exception {

		ReturnValueProviderForNewExpr valueProvider = new ReturnValueProviderForNewExpr();

		MockObjectManager.setReturnValueAtAllTimes(
				ReturnValueProviderTestTarget.class, "getTestTarget",
				valueProvider);

		assertEquals(10, testClass.getTestTarget("a").getField1());
		assertEquals(20, testClass.getTestTarget("b").getField1());
		assertEquals(-100, testClass.getTestTarget("c").getField1());

		assertNull(testClass.getTestTarget(null));
		assertNull(testClass.getTestTarget(""));
	}

	public void testStringNormal001() throws Exception {

		assertEquals("zzz", testClass.getString("zzz"));
	}

	public void testStringNormal002() throws Exception {

		MockObjectManager.addReturnValue(String.class, "<init>", "eee");

		assertEquals("eee", testClass.getString(null));
	}

	public void testStringNormal003() throws Exception {

		ReturnValueProviderForNewString valueProvider = new ReturnValueProviderForNewString();

		MockObjectManager.setReturnValueAtAllTimes(String.class,
				"<init>", valueProvider);

		assertEquals("aaa", testClass.getString("a"));
		assertEquals("bbb", testClass.getString("b"));
		assertEquals("ccc", testClass.getString("c"));

		assertNull(testClass.getString(null));
		assertNull(testClass.getString(""));
		assertNull(testClass.getString("zzz"));
	}

	public void testVoidMethodNormal001() throws Exception {
		ReturnValueProviderForMethod valueProvider = new ReturnValueProviderForMethod();

		MockObjectManager.addReturnValue(ReturnValueProviderTestTarget.class,
				"addString", valueProvider);

		testClass.addString("name");
	}

}