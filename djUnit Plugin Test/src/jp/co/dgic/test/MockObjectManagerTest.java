package jp.co.dgic.test;

import java.awt.AWTException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.Hashtable;

import jp.co.dgic.target.AClass;
import jp.co.dgic.target.SubTestTarget;
import jp.co.dgic.target.TestTarget;
import jp.co.dgic.target.VirtualException;
import jp.co.dgic.testing.common.DJUnitRuntimeException;
import jp.co.dgic.testing.common.virtualmock.MockObjectManager;
import jp.co.dgic.testing.common.virtualmock.InternalMockObjectManager;
import junit.framework.TestCase;

/**
 * @author kataoka
 */
public class MockObjectManagerTest extends TestCase {

	private TestTarget testClass;

	/**
	 * Constructor for MockObjectManagerTest.
	 * 
	 * @param name
	 */
	public MockObjectManagerTest(String name) {
		super(name);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		MockObjectManager.initialize();
		testClass = new TestTarget();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();

		testClass = null;
	}

	/**
	 * ReturnValue のテスト
	 */
	public void testGetSetReturnValue() {

		assertNull(MockObjectManager.getReturnValue(AClass.class, "getName"));

		MockObjectManager.addReturnValue(AClass.class, "getName",
				"return value");
		assertEquals("return value",
				MockObjectManager.getReturnValue(AClass.class, "getName"));

	}

	/**
	 * assertClassedのテスト
	 */
	public void testAssertCalled() {

		try {
			MockObjectManager.assertCalled(AClass.class, "getName");
			fail("この行は実行されないはず");
		} catch (Error e) {
			assertEquals(
					"The method 'getName' in class 'jp.co.dgic.target.AClass' was expected to be called but it wasn't",
					e.getMessage());
		}

		InternalMockObjectManager.indicateCalled(AClass.class.getName(),
				"getName", new Object[0]);
		MockObjectManager.assertCalled(AClass.class, "getName");
	}

	// /**
	// * assertClassedのテスト
	// */
	// public void testAssertCalledClassNameOnly() {
	//
	// InternalMockObjectManager.indicateCalled("package.name.class", "method",
	// new Object[0]);
	// MockObjectManager.assertCalled("class", "method");
	// }

	/**
	 * getArgumentのテスト
	 */
	public void testGetArgument() {

		String[] args = { "value1", "value2", "value3" };

		InternalMockObjectManager.indicateCalled(AClass.class.getName(),
				"getName", args);

		assertEquals("value2",
				MockObjectManager.getArgument(AClass.class, "getName", 1));
	}

	// /**
	// * getArgumentのテスト
	// */
	// public void testGetArgumentClassNameOnly() {
	//
	// String[] args = {"value1", "value2", "value3"};
	//
	// InternalMockObjectManager.indicateCalled("package.name.class", "method",
	// args);
	//
	// assertEquals("value2", MockObjectManager.getArgument("class", "method",
	// 1));
	// }

	/**
	 * assertArgumentPassedのテスト
	 */
	public void testAssertArgumentPassed() {

		String[] args = { "value1", "value2", "value3" };

		InternalMockObjectManager.indicateCalled(AClass.class.getName(),
				"getName", args);

		try {
			MockObjectManager.assertArgumentPassed(AClass.class, "getName", 0,
					"value0");
		} catch (Error e) {
			assertEquals("The argument index[0] of method 'getName'"
					+ " in class 'jp.co.dgic.target.AClass' should have the value 'value0'"
					+ " but it was 'value1'!", e.getMessage());
		}

		try {
			MockObjectManager.assertArgumentPassed(AClass.class, "getName", 0,
					"value1");
		} catch (Error e) {
			assertEquals("The argument index[0] of method 'getName'"
					+ " in class 'jp.co.dgic.target.AClass' should have the value 'value1'"
					+ " but it was 'null'!", e.getMessage());
		}

		MockObjectManager.assertArgumentPassed(AClass.class, "getName", 0,
				"value1");
		MockObjectManager.assertArgumentPassed(AClass.class, "getName", 1,
				"value2");
		MockObjectManager.assertArgumentPassed(AClass.class, "getName", 2,
				"value3");
	}

	/**
	 * assertArgumentPassedのテスト、引数の一部がnullだったとき
	 */
	public void testAssertArgumentPassedWithNullArgument() {

		String[] args = { "value1", null, "value3" };

		InternalMockObjectManager.indicateCalled(AClass.class.getName(),
				"getName", args);

		try {
			MockObjectManager.assertArgumentPassed(AClass.class, "getName", 0,
					null);
		} catch (Error e) {
			assertEquals("The argument index[0] of method 'getName'"
					+ " in class 'jp.co.dgic.target.AClass' should have the value 'null'"
					+ " but it was 'value1'!", e.getMessage());
		}

		try {
			MockObjectManager.assertArgumentPassed(AClass.class, "getName", 1,
					"value1");
		} catch (Error e) {
			assertEquals("The argument index[1] of method 'getName'"
					+ " in class 'jp.co.dgic.target.AClass' should have the value 'value1'"
					+ " but it was 'null'!", e.getMessage());
		}

		MockObjectManager.assertArgumentPassed(AClass.class, "getName", 0,
				"value1");
		MockObjectManager
				.assertArgumentPassed(AClass.class, "getName", 1, null);
		MockObjectManager.assertArgumentPassed(AClass.class, "getName", 2,
				"value3");
	}

	/**
	 * assertArgumentPassedのテスト
	 */
	// public void testAssertArgumentPassedClassNameOnly() {
	//
	// String[] args = {"value1", "value2", "value3"};
	//
	// InternalMockObjectManager.indicateCalled("package.name.class", "method",
	// args);
	//
	// try {
	// MockObjectManager.assertArgumentPassed("class", "method", 0, "value0");
	// } catch (Error e) {
	// assertEquals(
	// "The argument index[0] of method 'method'"
	// + " in class 'class' should have the value 'value0'"
	// + " but it was 'value1'!",
	// e.getMessage());
	// }
	//
	// try {
	// MockObjectManager.assertArgumentPassed("class", "method", 0, "value1");
	// } catch (Error e) {
	// assertEquals(
	// "The argument index[0] of method 'method'"
	// + " in class 'class' should have the value 'value1'"
	// + " but it was 'null'!",
	// e.getMessage());
	// }
	//
	// MockObjectManager.assertArgumentPassed("class", "method", 0, "value1");
	// MockObjectManager.assertArgumentPassed("class", "method", 1, "value2");
	// MockObjectManager.assertArgumentPassed("class", "method", 2, "value3");
	// }

	public void testGetCallCount001() {

		testClass.getField1();

		assertEquals(1,
				MockObjectManager.getCallCount(TestTarget.class, "getField1"));

		testClass.getField1();

		assertEquals(2,
				MockObjectManager.getCallCount(TestTarget.class, "getField1"));
	}

	public void testSetReturnValueAt001() {

		testClass.setField1(10);

		MockObjectManager.setReturnValueAt(TestTarget.class, "getField1", 2,
				new Integer(100));

		assertEquals(10, testClass.getField1());
		assertEquals(10, testClass.getField1());
		assertEquals(100, testClass.getField1());
		assertEquals(10, testClass.getField1());
		assertEquals(10, testClass.getField1());
	}

	public void testSetReturnValueAt002() {

		testClass.setField1(10);

		MockObjectManager.setReturnValueAt(TestTarget.class, "getField1", 2,
				new Integer(100));
		MockObjectManager.setReturnValueAt(TestTarget.class, "getField1", 4,
				new Integer(50));
		MockObjectManager.addReturnValue(TestTarget.class, "getField1",
				new Integer(30));

		assertEquals(10, testClass.getField1());
		assertEquals(10, testClass.getField1());
		assertEquals(100, testClass.getField1());
		assertEquals(10, testClass.getField1());
		assertEquals(50, testClass.getField1());
		assertEquals(30, testClass.getField1());
	}

	public void testSetReturnNullAt001() {

		testClass.setField2("value");

		MockObjectManager.setReturnNullAt(TestTarget.class, "getField2", 2);

		assertEquals("value", testClass.getField2());
		assertEquals("value", testClass.getField2());
		assertNull(testClass.getField2());
		assertEquals("value", testClass.getField2());
		assertEquals("value", testClass.getField2());
	}

	public void testSetReturnNullAt002() {

		testClass.setField2("value");

		MockObjectManager.setReturnNullAt(TestTarget.class, "getField2", 2);
		MockObjectManager.setReturnNullAt(TestTarget.class, "getField2", 4);
		MockObjectManager.addReturnNull(TestTarget.class, "getField2");

		assertEquals("value", testClass.getField2());
		assertEquals("value", testClass.getField2());
		assertNull(testClass.getField2());
		assertEquals("value", testClass.getField2());
		assertNull(testClass.getField2());
		assertNull(testClass.getField2());
	}

	public void testSetReturnValueAtAllTimes001() {

		testClass.setField1(10);

		MockObjectManager.setReturnValueAtAllTimes(TestTarget.class,
				"getField1", new Integer(100));

		assertEquals(100, testClass.getField1());
		assertEquals(100, testClass.getField1());
		assertEquals(100, testClass.getField1());
		assertEquals(100, testClass.getField1());
		assertEquals(100, testClass.getField1());
	}

	public void testSetReturnValueAtAllTimes002() {

		testClass.setField1(10);

		MockObjectManager.addReturnValue(TestTarget.class, "getField1",
				new Integer(30));
		MockObjectManager.setReturnValueAt(TestTarget.class, "getField1", 3,
				new Integer(50));
		MockObjectManager.setReturnValueAtAllTimes(TestTarget.class,
				"getField1", new Integer(100));

		assertEquals(100, testClass.getField1());
		assertEquals(100, testClass.getField1());
		assertEquals(100, testClass.getField1());
		assertEquals(100, testClass.getField1());
		assertEquals(100, testClass.getField1());
	}

	public void testSetReturnNullAtAllTimes001() {

		testClass.setField2("value");

		MockObjectManager
				.setReturnNullAtAllTimes(TestTarget.class, "getField2");

		assertNull(testClass.getField2());
		assertNull(testClass.getField2());
		assertNull(testClass.getField2());
		assertNull(testClass.getField2());
		assertNull(testClass.getField2());
		assertNull(testClass.getField2());
		assertNull(testClass.getField2());
	}

	/**
	 * TestTarget#getField1()メソッドの戻り値を、差し替える
	 */
	public void testAround001() {

		assertEquals(0, testClass.getField1());

		MockObjectManager.addReturnValue(TestTarget.class, "getField1",
				new Integer(100));
		assertEquals(100, testClass.getField1());

	}

	/**
	 * TestTarget#getField2()メソッドの戻り値を、差し替える
	 */
	public void testAround002() {

		assertNull(testClass.getField2());

		MockObjectManager
				.addReturnValue(TestTarget.class, "getField2", "value");
		assertEquals("value", testClass.getField2());

	}

	/**
	 * TestTarget#getField3()メソッドの戻り値を、差し替える
	 */
	public void testAround003() {

		assertNull(testClass.getField3());

		MockObjectManager.addReturnValue(TestTarget.class, "getField3",
				new Hashtable());
		assertEquals(new Hashtable(), testClass.getField3());

	}

	/**
	 * 戻り値voidのメソッドに対して、あえて戻り値を設定してみる。 TestTarget#setField1()
	 */
	public void testAround004() {

		assertEquals(0, testClass.getField1());

		testClass.setField1(10);
		assertEquals(10, testClass.getField1());

		// DJUnitRuntimeExceptionがスローされる
		MockObjectManager.addReturnValue(TestTarget.class, "setField1",
				new Integer(100));
		try {
			testClass.setField1(100);
			fail("実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}

	}

	/**
	 * 戻り値voidのメソッドに対して、あえて戻り値を設定してみる。 TestTarget#setField2()
	 */
	public void testAround005() {

		assertNull(testClass.getField2());

		testClass.setField2("value");
		assertEquals("value", testClass.getField2());

		// DJUnitRuntimeExceptionがスローされる
		MockObjectManager.addReturnValue(TestTarget.class, "setField2",
				"newValue");
		try {
			testClass.setField2("newValue");
			fail("実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}

	}

	/**
	 * 戻り値voidのメソッドに対して、あえて戻り値を設定してみる。 TestTarget#setField3()
	 */
	public void testAround006() {

		assertNull(testClass.getField3());

		Hashtable h1 = new Hashtable();
		h1.put("key1", "value1");
		testClass.setField3(h1);
		assertEquals(h1, testClass.getField3());

		// DJUnitRuntimeExceptionがスローされる
		Hashtable h2 = new Hashtable();
		h2.put("key2", "value2");
		MockObjectManager.addReturnValue(TestTarget.class, "setField3", h2);
		try {
			testClass.setField3(h2);
			fail("実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("実行されないはず");
		}

	}

	/**
	 * TestTarget#getField1()メソッドの戻り値を、差し替える クラス名の指定は、フルパスでなくてもよい。
	 */
	public void testAround007() {

		assertEquals(0, testClass.getField1());

		MockObjectManager.addReturnValue(TestTarget.class, "getField1",
				new Integer(100));
		assertEquals(100, testClass.getField1());

	}

	/**
	 * setReturnNullのテスト TestTarget#getField1()メソッドの戻り値を、nullに差し替える
	 * getField()は、戻り値の型がintであるため、返却値は、0となるはず。 クラス名の指定は、フルパスでなくてもよい。
	 */
	public void testAround008() {

		testClass.setField1(100);

		assertEquals(100, testClass.getField1());

		MockObjectManager.addReturnNull(TestTarget.class, "getField1");
		assertEquals(0, testClass.getField1());

	}

	/**
	 * setReturnNullのテスト TestTarget#getField3()メソッドの戻り値を、nullに差し替える
	 * クラス名の指定は、フルパスでなくてもよい。
	 */
	public void testAround009() {

		Hashtable ht = new Hashtable();

		testClass.setField3(ht);

		assertEquals(ht, testClass.getField3());

		MockObjectManager.addReturnNull(TestTarget.class, "getField3");
		assertNull(testClass.getField3());

	}

	/**
	 * TestTarget.getStaticField()メソッドの戻り値を、差し替える クラス名の指定は、フルパスでなくてもよい。
	 */
	public void testAround010() {

		assertEquals(0, TestTarget.getStaticField());

		MockObjectManager.addReturnValue(TestTarget.class, "getStaticField",
				new Integer(100));
		assertEquals(100, TestTarget.getStaticField());

	}

	/**
	 * SubTestTarget extends TestTarget SubTestTarget#getField1()メソッドの戻り値を、差し替える
	 * getField1は、TestTargetから継承したメソッド
	 */
	public void testAroundSubClass001() {

		SubTestTarget subTestClass = new SubTestTarget();
		assertEquals(0, subTestClass.getField1());

		MockObjectManager.addReturnValue(TestTarget.class, "getField1",
				new Integer(100));
		assertEquals(100, subTestClass.getField1());

	}

	/**
	 * SubTestTarget extends TestTarget SubTestTarget#getField2()メソッドの戻り値を、差し替える
	 * getField2は、TestTargetから継承したメソッド
	 */
	public void testAroundSubClass002() {

		SubTestTarget subTestClass = new SubTestTarget();
		assertNull(subTestClass.getField2());

		MockObjectManager.addReturnValue(TestTarget.class, "getField2",
				"return value");
		assertEquals("return value", subTestClass.getField2());

	}

	/**
	 * SubTestTarget extends TestTarget SubTestTarget#getField3()メソッドの戻り値を、差し替える
	 * getField3は、TestTargetのオーバーライドメソッド
	 */
	public void testAroundSubClass003() {

		SubTestTarget subTestClass = new SubTestTarget();
		assertNull(subTestClass.getField3());

		MockObjectManager.addReturnValue(SubTestTarget.class, "getField3",
				new Hashtable());
		assertNotNull(subTestClass.getField3());

	}

	/**
	 * 戻り値に、例外を指定することで、例外を発生させるようになる
	 */
	public void testAroundThrows001() {

		MockObjectManager.addReturnValue(TestTarget.class, "throwException",
				new Exception("test001"));

		try {
			testClass.throwException();
			fail("この行は実行されないはず");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertEquals("test001", e.getMessage());
		}
	}

	/**
	 * 戻り値に、例外を指定することで、例外を発生させるようになる
	 */
	public void testAroundThrows002() {

		MockObjectManager.addReturnValue(TestTarget.class,
				"throwIOException", new IOException("test002"));

		try {
			testClass.throwIOException();
			fail("この行は実行されないはず");
		} catch (IOException e) {
			assertTrue(e instanceof IOException);
			assertEquals("test002", e.getMessage());
		}
	}

	/**
	 * 戻り値に、例外を指定することで、例外を発生させるようになる throws IOException, SQLException
	 */
	public void testAroundThrows003() {

		MockObjectManager.addReturnValue(TestTarget.class,
				"throwIOAndSQLException", new IOException("test003"));

		try {
			testClass.throwIOAndSQLException();
			fail("この行は実行されないはず");
		} catch (IOException ioe) {
			assertTrue(ioe instanceof IOException);
			assertEquals("test003", ioe.getMessage());
		} catch (SQLException sqle) {
			fail("この行は実行されないはず");
		}
	}

	/**
	 * 戻り値に、例外を指定することで、例外を発生させるようになる throws IOException, SQLException
	 */
	public void testAroundThrows004() {

		MockObjectManager.addReturnValue(TestTarget.class,
				"throwIOAndSQLException", new SQLException("test004"));

		try {
			testClass.throwIOAndSQLException();
			fail("この行は実行されないはず");
		} catch (IOException ioe) {
			fail("この行は実行されないはず");
		} catch (SQLException sqle) {
			assertTrue(sqle instanceof SQLException);
			assertEquals("test004", sqle.getMessage());
		}
	}

	/**
	 * 戻り値に、例外を指定することで、例外を発生させるようになる throws Exceptionのメソッドに対して、SQLExceptionを指定する
	 */
	public void testAroundThrows005() {

		MockObjectManager.addReturnValue(TestTarget.class, "throwException",
				new SQLException("test005"));

		try {
			testClass.throwException();
			fail("この行は実行されないはず");
		} catch (SQLException e) {
			assertTrue(e instanceof SQLException);
			assertEquals("test005", e.getMessage());
		} catch (Exception e) {
			fail("この行は実行されないはず");
		}
	}

	/**
	 * 戻り値に、例外を指定することで、例外を発生させるようになる throws IOException,
	 * SQLExceptionのメソッドに対して、AWTExceptionを指定する
	 */
	public void testAroundThrows006() {

		MockObjectManager.addReturnValue(TestTarget.class,
				"throwIOAndSQLException", new AWTException("test006"));

		try {
			testClass.throwIOAndSQLException();
			fail("この行は実行されないはず");
		} catch (IOException ioe) {
			fail("この行は実行されないはず");
		} catch (SQLException sqle) {
			fail("この行は実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
		}
	}

	/**
	 * 戻り値に、例外を指定することで、例外を発生させるようになる throws IOExceptionのメソッドに対して、Exceptionを指定する
	 */
	public void testAroundThrows007() {

		MockObjectManager.addReturnValue(TestTarget.class, "throwIOException",
				new Exception("test007"));

		try {
			testClass.throwIOException();
			fail("この行は実行されないはず");
		} catch (IOException e) {
			fail("この行は実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
		} catch (Exception e) {
			fail("この行は実行されないはず");
		}
	}

	/**
	 * 戻り値に、例外を指定することで、例外を発生させるようになる throws IOException,
	 * SQLExceptionのメソッドに対して、AWTExceptionを指定する
	 */
	public void testAroundThrows008() {

		MockObjectManager.addReturnValue(TestTarget.class,
				"throwIOAndSQLException", new Exception("test008"));

		try {
			testClass.throwIOAndSQLException();
			fail("この行は実行されないはず");
		} catch (IOException ioe) {
			fail("この行は実行されないはず");
		} catch (SQLException sqle) {
			fail("この行は実行されないはず");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
		}
	}

	/**
	 * 戻り値に、例外を指定することで、例外を発生させるようになる throws
	 * VirtualExceptionのメソッドに対して、VirtualExceptionを指定する
	 */
	public void testAroundThrows009() {

		MockObjectManager.addReturnValue(SubTestTarget.class, "getField5",
				new VirtualException());

		SubTestTarget subTarget = new SubTestTarget();

		try {
			subTarget.getField5();
			fail("この行は実行されないはず");
		} catch (VirtualException ve) {
		} catch (Throwable t) {
			fail("この行は実行されないはず");
		}
	}

	/**
	 * 戻り値に、実行時例外を指定することで、例外を発生させるようになる
	 * throws宣言なしのメソッドに対して、RuntimeExceptionを指定する
	 */
	public void testRuntimeException001() {

		MockObjectManager.addReturnValue(TestTarget.class, "getField1",
				new RuntimeException("test001"));

		try {
			testClass.getField1();
			fail("この行は実行されないはず");
		} catch (RuntimeException e) {
			assertTrue(e instanceof RuntimeException);
			assertEquals("test001", e.getMessage());
		}
	}

	/**
	 * 戻り値に、実行時例外を指定することで、例外を発生させるようになる throws
	 * Exceptionのメソッドに対して、RuntimeExceptionを指定する
	 */
	public void testRuntimeException002() {

		MockObjectManager.addReturnValue(TestTarget.class,
				"throwException", new RuntimeException("test002"));

		try {
			testClass.throwException();
			fail("この行は実行されないはず");
		} catch (RuntimeException re) {
			assertTrue(re instanceof RuntimeException);
			assertEquals("test002", re.getMessage());
		} catch (Exception e) {
			fail("この行は実行されないはず");
		}
	}

	/**
	 * 戻り値に、実行時例外を指定することで、例外を発生させるようになる throws
	 * IOExceptionのメソッドに対して、RuntimeExceptionを指定する
	 */
	public void testRuntimeException003() {

		MockObjectManager.addReturnValue(TestTarget.class,
				"throwIOException", new RuntimeException("test003"));

		try {
			testClass.throwIOException();
			fail("この行は実行されないはず");
		} catch (RuntimeException re) {
			assertTrue(re instanceof RuntimeException);
			assertEquals("test003", re.getMessage());
		} catch (IOException ioe) {
			fail("この行は実行されないはず");
		}
	}

	/**
	 * 戻り値に、実行時例外を指定することで、例外を発生させるようになる throws IOException,
	 * SQLExceptionのメソッドに対して、RuntimeExceptionを指定する
	 */
	public void testRuntimeException004() {

		MockObjectManager.addReturnValue(TestTarget.class,
				"throwIOAndSQLException", new RuntimeException("test004"));

		try {
			testClass.throwIOAndSQLException();
			fail("この行は実行されないはず");
		} catch (RuntimeException re) {
			assertTrue(re instanceof RuntimeException);
			assertEquals("test004", re.getMessage());
		} catch (IOException ioe) {
			fail("この行は実行されないはず");
		} catch (SQLException sqle) {
			fail("この行は実行されないはず");
		}
	}

	// NullPointerException

	/**
	 * 戻り値に、実行時例外を指定することで、例外を発生させるようになる
	 * throws宣言なしのメソッドに対して、NullPointerExceptionを指定する
	 */
	public void testRuntimeException005() {

		MockObjectManager.addReturnValue(TestTarget.class,
				"getField1", new NullPointerException("test005"));

		try {
			testClass.getField1();
			fail("この行は実行されないはず");
		} catch (NullPointerException e) {
			assertTrue(e instanceof NullPointerException);
			assertEquals("test005", e.getMessage());
		}
	}

	/**
	 * 戻り値に、実行時例外を指定することで、例外を発生させるようになる throws
	 * Exceptionのメソッドに対して、NullPointerExceptionを指定する
	 */
	public void testRuntimeException006() {

		MockObjectManager.addReturnValue(TestTarget.class,
				"throwException", new NullPointerException("test006"));

		try {
			testClass.throwException();
			fail("この行は実行されないはず");
		} catch (NullPointerException npe) {
			assertTrue(npe instanceof NullPointerException);
			assertEquals("test006", npe.getMessage());
		} catch (Exception e) {
			fail("この行は実行されないはず");
		}
	}

	/**
	 * 戻り値に、実行時例外を指定することで、例外を発生させるようになる throws
	 * IOExceptionのメソッドに対して、NullPointerExceptionを指定する
	 */
	public void testRuntimeException007() {

		MockObjectManager.addReturnValue(TestTarget.class,
				"throwIOException", new NullPointerException("test007"));

		try {
			testClass.throwIOException();
			fail("この行は実行されないはず");
		} catch (NullPointerException npe) {
			assertTrue(npe instanceof NullPointerException);
			assertEquals("test007", npe.getMessage());
		} catch (IOException ioe) {
			fail("この行は実行されないはず");
		}
	}

	/**
	 * 戻り値に、実行時例外を指定することで、例外を発生させるようになる throws IOException,
	 * SQLExceptionのメソッドに対して、NullPointerExceptionを指定する
	 */
	public void testRuntimeException008() {

		MockObjectManager.addReturnValue(TestTarget.class,
				"throwIOAndSQLException", new NullPointerException("test008"));

		try {
			testClass.throwIOAndSQLException();
			fail("この行は実行されないはず");
		} catch (NullPointerException npe) {
			assertTrue(npe instanceof NullPointerException);
			assertEquals("test008", npe.getMessage());
		} catch (IOException ioe) {
			fail("この行は実行されないはず");
		} catch (SQLException sqle) {
			fail("この行は実行されないはず");
		}
	}

	/**
	 * 戻り値に、例外を指定することで、例外を発生させるようになる throws
	 * VirtualExceptionのメソッドに対して、nullPointerExceptionを指定する
	 */
	public void testRuntimeException009() {

		MockObjectManager.addReturnValue(SubTestTarget.class, "getField5",
				new NullPointerException("test009"));

		SubTestTarget subTarget = new SubTestTarget();

		try {
			subTarget.getField5();
			fail("この行は実行されないはず");
		} catch (NullPointerException npe) {
			assertTrue(npe instanceof NullPointerException);
			assertEquals("test009", npe.getMessage());
		} catch (VirtualException ve) {
			fail("この行は実行されないはず");
		}

		// 指定のメソッドは実行されたはず
		assertTrue(MockObjectManager.isCalled(SubTestTarget.class, "getField5"));
	}

	public void testThrowError001() {
		MockObjectManager.addReturnValue(TestTarget.class, "getField1",
				new Error("TestError"));

		try {
			testClass.getField1();
			fail("この行は実行されないはず");
		} catch (Error e) {
			assertEquals("TestError", e.getMessage());
		}

		// 指定のメソッドは実行されたはず
		assertTrue(MockObjectManager.isCalled(TestTarget.class, "getField1"));
	}

	/**
	 * System.exit()の呼び出しを無効にする
	 */
	public void testSystemExit001() {

		testClass.exit(0);

		MockObjectManager.addReturnValue(System.class, "exit");
		testClass.exit(1);
	}

	/**
	 * ObjectInputStream.readObjectで、IOExceptionをthrowさせる。
	 */
	public void testReadObject001() throws Exception {

		MockObjectManager.addReturnValue(ObjectInputStream.class, "readObject",
				new IOException());

		try {
			testClass.readObject();
			fail("実行されないはず");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			fail("実行されないはず");
		}

		// 指定のメソッドは実行されたはず
		assertTrue(MockObjectManager
				.isCalled(ObjectInputStream.class, "readObject"));

		// 2回目は例外は発生しないはず
		testClass.readObject();

	}

	/**
	 * ObjectInputStream.readObjectで、ClassNotFoundExceptionをthrowさせる。
	 */
	public void testReadObject002() throws Exception {

		MockObjectManager.addReturnValue(ObjectInputStream.class, "readObject",
				new ClassNotFoundException());

		try {
			testClass.readObject();
			fail("実行されないはず");
		} catch (IOException ioe) {
			fail("実行されないはず");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}

		// 指定のメソッドは実行されたはず
		assertTrue(MockObjectManager
				.isCalled(ObjectInputStream.class, "readObject"));

		// 2回目は例外は発生しないはず
		testClass.readObject();

	}

}
