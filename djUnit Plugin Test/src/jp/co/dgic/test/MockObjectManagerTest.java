package jp.co.dgic.test;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;

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
	 * ReturnValue �̃e�X�g
	 */
	public void testGetSetReturnValue() {

		assertNull(MockObjectManager.getReturnValue("dummy", "dummy"));

		MockObjectManager.addReturnValue("class", "method", "return value");
		assertEquals("return value", MockObjectManager.getReturnValue("class", "method"));

	}

	/**
	 * assertClassed�̃e�X�g
	 */
	public void testAssertCalled() {

		try {
			MockObjectManager.assertCalled("dummy", "dummy");
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (Error e) {
			assertEquals("The method 'dummy' in class 'dummy' was expected to be called but it wasn't", e.getMessage());
		}

		InternalMockObjectManager.indicateCalled("class", "method", new Object[0]);
		MockObjectManager.assertCalled("class", "method");
	}

	/**
	 * assertClassed�̃e�X�g
	 */
	public void testAssertCalledClassNameOnly() {

		InternalMockObjectManager.indicateCalled("package.name.class", "method", new Object[0]);
		MockObjectManager.assertCalled("class", "method");
	}

	/**
	 * getArgument�̃e�X�g
	 */
	public void testGetArgument() {

		String[] args = {"value1", "value2", "value3"};

		InternalMockObjectManager.indicateCalled("class", "method", args);

		assertEquals("value2", MockObjectManager.getArgument("class", "method", 1));
	}

	/**
	 * getArgument�̃e�X�g
	 */
	public void testGetArgumentClassNameOnly() {

		String[] args = {"value1", "value2", "value3"};

		InternalMockObjectManager.indicateCalled("package.name.class", "method", args);

		assertEquals("value2", MockObjectManager.getArgument("class", "method", 1));
	}

	/**
	 * assertArgumentPassed�̃e�X�g
	 */
	public void testAssertArgumentPassed() {

		String[] args = {"value1", "value2", "value3"};

		InternalMockObjectManager.indicateCalled("class", "method", args);

		try {
			MockObjectManager.assertArgumentPassed("class", "method", 0, "value0");
		} catch (Error e) {
			assertEquals(
				"The argument index[0] of method 'method'"
					+ " in class 'class' should have the value 'value0'"
					+ " but it was 'value1'!",
				e.getMessage());
		}

		try {
			MockObjectManager.assertArgumentPassed("class", "method", 0, "value1");
		} catch (Error e) {
			assertEquals(
				"The argument index[0] of method 'method'"
					+ " in class 'class' should have the value 'value1'"
					+ " but it was 'null'!",
				e.getMessage());
		}

		MockObjectManager.assertArgumentPassed("class", "method", 0, "value1");
		MockObjectManager.assertArgumentPassed("class", "method", 1, "value2");
		MockObjectManager.assertArgumentPassed("class", "method", 2, "value3");
	}

	/**
	 * assertArgumentPassed�̃e�X�g�A�����̈ꕔ��null�������Ƃ� 
	 */
	public void testAssertArgumentPassedWithNullArgument() {

		String[] args = {"value1", null, "value3"};

		InternalMockObjectManager.indicateCalled("class", "method", args);

		try {
			MockObjectManager.assertArgumentPassed("class", "method", 0, null);
		} catch (Error e) {
			assertEquals(
				"The argument index[0] of method 'method'"
					+ " in class 'class' should have the value 'null'"
					+ " but it was 'value1'!",
				e.getMessage());
		}

		try {
			MockObjectManager.assertArgumentPassed("class", "method", 1, "value1");
		} catch (Error e) {
			assertEquals(
				"The argument index[1] of method 'method'"
					+ " in class 'class' should have the value 'value1'"
					+ " but it was 'null'!",
				e.getMessage());
		}

		MockObjectManager.assertArgumentPassed("class", "method", 0, "value1");
		MockObjectManager.assertArgumentPassed("class", "method", 1, null);
		MockObjectManager.assertArgumentPassed("class", "method", 2, "value3");
	}

	/**
	 * assertArgumentPassed�̃e�X�g
	 */
	public void testAssertArgumentPassedClassNameOnly() {

		String[] args = {"value1", "value2", "value3"};

		InternalMockObjectManager.indicateCalled("package.name.class", "method", args);

		try {
			MockObjectManager.assertArgumentPassed("class", "method", 0, "value0");
		} catch (Error e) {
			assertEquals(
				"The argument index[0] of method 'method'"
					+ " in class 'class' should have the value 'value0'"
					+ " but it was 'value1'!",
				e.getMessage());
		}

		try {
			MockObjectManager.assertArgumentPassed("class", "method", 0, "value1");
		} catch (Error e) {
			assertEquals(
				"The argument index[0] of method 'method'"
					+ " in class 'class' should have the value 'value1'"
					+ " but it was 'null'!",
				e.getMessage());
		}

		MockObjectManager.assertArgumentPassed("class", "method", 0, "value1");
		MockObjectManager.assertArgumentPassed("class", "method", 1, "value2");
		MockObjectManager.assertArgumentPassed("class", "method", 2, "value3");
	}


	public void testGetCallCount001() {

		testClass.getField1();

		assertEquals(1, MockObjectManager.getCallCount("TestTarget", "getField1"));

		testClass.getField1();

		assertEquals(2, MockObjectManager.getCallCount("TestTarget", "getField1"));
	}

	public void testSetReturnValueAt001() {
		
		testClass.setField1(10);

		MockObjectManager.setReturnValueAt("TestTarget", "getField1", 2, new Integer(100));

		assertEquals(10, testClass.getField1());
		assertEquals(10, testClass.getField1());
		assertEquals(100, testClass.getField1());
		assertEquals(10, testClass.getField1());
		assertEquals(10, testClass.getField1());
	}

	public void testSetReturnValueAt002() {
		
		testClass.setField1(10);

		MockObjectManager.setReturnValueAt("TestTarget", "getField1", 2, new Integer(100));
		MockObjectManager.setReturnValueAt("TestTarget", "getField1", 4, new Integer(50));
		MockObjectManager.addReturnValue("TestTarget", "getField1", new Integer(30));

		assertEquals(10, testClass.getField1());
		assertEquals(10, testClass.getField1());
		assertEquals(100, testClass.getField1());
		assertEquals(10, testClass.getField1());
		assertEquals(50, testClass.getField1());
		assertEquals(30, testClass.getField1());
	}

	public void testSetReturnNullAt001() {
		
		testClass.setField2("value");

		MockObjectManager.setReturnNullAt("TestTarget", "getField2", 2);

		assertEquals("value", testClass.getField2());
		assertEquals("value", testClass.getField2());
		assertNull(testClass.getField2());
		assertEquals("value", testClass.getField2());
		assertEquals("value", testClass.getField2());
	}

	public void testSetReturnNullAt002() {
		
		testClass.setField2("value");

		MockObjectManager.setReturnNullAt("TestTarget", "getField2", 2);
		MockObjectManager.setReturnNullAt("TestTarget", "getField2", 4);
		MockObjectManager.addReturnNull("TestTarget", "getField2");

		assertEquals("value", testClass.getField2());
		assertEquals("value", testClass.getField2());
		assertNull(testClass.getField2());
		assertEquals("value", testClass.getField2());
		assertNull(testClass.getField2());
		assertNull(testClass.getField2());
	}

	public void testSetReturnValueAtAllTimes001() {
		
		testClass.setField1(10);

		MockObjectManager.setReturnValueAtAllTimes("TestTarget", "getField1", new Integer(100));

		assertEquals(100, testClass.getField1());
		assertEquals(100, testClass.getField1());
		assertEquals(100, testClass.getField1());
		assertEquals(100, testClass.getField1());
		assertEquals(100, testClass.getField1());
	}

	public void testSetReturnValueAtAllTimes002() {
		
		testClass.setField1(10);

		MockObjectManager.addReturnValue("TestTarget", "getField1", new Integer(30));
		MockObjectManager.setReturnValueAt("TestTarget", "getField1", 3, new Integer(50));
		MockObjectManager.setReturnValueAtAllTimes("TestTarget", "getField1", new Integer(100));

		assertEquals(100, testClass.getField1());
		assertEquals(100, testClass.getField1());
		assertEquals(100, testClass.getField1());
		assertEquals(100, testClass.getField1());
		assertEquals(100, testClass.getField1());
	}

	public void testSetReturnNullAtAllTimes001() {
		
		testClass.setField2("value");

		MockObjectManager.setReturnNullAtAllTimes("TestTarget", "getField2");

		assertNull(testClass.getField2());
		assertNull(testClass.getField2());
		assertNull(testClass.getField2());
		assertNull(testClass.getField2());
		assertNull(testClass.getField2());
		assertNull(testClass.getField2());
		assertNull(testClass.getField2());
	}

	/**
	 * TestTarget#getField1()���\�b�h�̖߂�l���A�����ւ���
	 */
	public void testAround001() {

		assertEquals(0, testClass.getField1());

		MockObjectManager.addReturnValue("jp.co.dgic.target.TestTarget", "getField1", new Integer(100));
		assertEquals(100, testClass.getField1());

	}

	/**
	 * TestTarget#getField2()���\�b�h�̖߂�l���A�����ւ���
	 */
	public void testAround002() {

		assertNull(testClass.getField2());

		MockObjectManager.addReturnValue("jp.co.dgic.target.TestTarget", "getField2", "value");
		assertEquals("value", testClass.getField2());

	}

	/**
	 * TestTarget#getField3()���\�b�h�̖߂�l���A�����ւ���
	 */
	public void testAround003() {

		assertNull(testClass.getField3());

		MockObjectManager.addReturnValue("jp.co.dgic.target.TestTarget", "getField3", new Hashtable());
		assertEquals(new Hashtable(), testClass.getField3());

	}

	/**
	 * �߂�lvoid�̃��\�b�h�ɑ΂��āA�����Ė߂�l��ݒ肵�Ă݂�B
	 * TestTarget#setField1()
	 */
	public void testAround004() {

		assertEquals(0, testClass.getField1());

		testClass.setField1(10);
		assertEquals(10, testClass.getField1());

		// DJUnitRuntimeException���X���[�����
		MockObjectManager.addReturnValue("jp.co.dgic.target.TestTarget", "setField1", new Integer(100));
		try {
			testClass.setField1(100);
			fail("���s����Ȃ��͂�");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("���s����Ȃ��͂�");
		}

	}

	/**
	 * �߂�lvoid�̃��\�b�h�ɑ΂��āA�����Ė߂�l��ݒ肵�Ă݂�B
	 * TestTarget#setField2()
	 */
	public void testAround005() {

		assertNull(testClass.getField2());

		testClass.setField2("value");
		assertEquals("value", testClass.getField2());

		// DJUnitRuntimeException���X���[�����
		MockObjectManager.addReturnValue("jp.co.dgic.target.TestTarget", "setField2", "newValue");
		try {
			testClass.setField2("newValue");
			fail("���s����Ȃ��͂�");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("���s����Ȃ��͂�");
		}

	}

	/**
	 * �߂�lvoid�̃��\�b�h�ɑ΂��āA�����Ė߂�l��ݒ肵�Ă݂�B
	 * TestTarget#setField3()
	 */
	public void testAround006() {

		assertNull(testClass.getField3());

		Hashtable h1 = new Hashtable();
		h1.put("key1", "value1");
		testClass.setField3(h1);
		assertEquals(h1, testClass.getField3());

		// DJUnitRuntimeException���X���[�����
		Hashtable h2 = new Hashtable();
		h2.put("key2", "value2");
		MockObjectManager.addReturnValue("jp.co.dgic.target.TestTarget", "setField3", h2);
		try {
			testClass.setField3(h2);
			fail("���s����Ȃ��͂�");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch return type."));
		} catch (Throwable t) {
			fail("���s����Ȃ��͂�");
		}

	}

	/**
	 * TestTarget#getField1()���\�b�h�̖߂�l���A�����ւ���
	 * �N���X���̎w��́A�t���p�X�łȂ��Ă��悢�B
	 */
	public void testAround007() {

		assertEquals(0, testClass.getField1());

		MockObjectManager.addReturnValue("TestTarget", "getField1", new Integer(100));
		assertEquals(100, testClass.getField1());

	}

	/**
	 * setReturnNull�̃e�X�g
	 * TestTarget#getField1()���\�b�h�̖߂�l���Anull�ɍ����ւ���
	 * getField()�́A�߂�l�̌^��int�ł��邽�߁A�ԋp�l�́A0�ƂȂ�͂��B
	 * �N���X���̎w��́A�t���p�X�łȂ��Ă��悢�B
	 */
	public void testAround008() {

		testClass.setField1(100);

		assertEquals(100, testClass.getField1());

		MockObjectManager.addReturnNull("TestTarget", "getField1");
		assertEquals(0, testClass.getField1());

	}

	/**
	 * setReturnNull�̃e�X�g
	 * TestTarget#getField3()���\�b�h�̖߂�l���Anull�ɍ����ւ���
	 * �N���X���̎w��́A�t���p�X�łȂ��Ă��悢�B
	 */
	public void testAround009() {

		Hashtable ht = new Hashtable();

		testClass.setField3(ht);

		assertEquals(ht, testClass.getField3());

		MockObjectManager.addReturnNull("TestTarget", "getField3");
		assertNull(testClass.getField3());

	}

	/**
	 * TestTarget.getStaticField()���\�b�h�̖߂�l���A�����ւ���
	 * �N���X���̎w��́A�t���p�X�łȂ��Ă��悢�B
	 */
	public void testAround010() {

		assertEquals(0, TestTarget.getStaticField());

		MockObjectManager.addReturnValue("TestTarget", "getStaticField", new Integer(100));
		assertEquals(100, TestTarget.getStaticField());

	}

	/**
	 * SubTestTarget extends TestTarget
	 * SubTestTarget#getField1()���\�b�h�̖߂�l���A�����ւ���
	 * getField1�́ATestTarget����p���������\�b�h
	 */
	public void testAroundSubClass001() {

		SubTestTarget subTestClass = new SubTestTarget();
		assertEquals(0, subTestClass.getField1());

		MockObjectManager.addReturnValue("TestTarget", "getField1", new Integer(100));
		assertEquals(100, subTestClass.getField1());

	}

	/**
	 * SubTestTarget extends TestTarget
	 * SubTestTarget#getField2()���\�b�h�̖߂�l���A�����ւ���
	 * getField2�́ATestTarget����p���������\�b�h
	 */
	public void testAroundSubClass002() {

		SubTestTarget subTestClass = new SubTestTarget();
		assertNull(subTestClass.getField2());

		MockObjectManager.addReturnValue("TestTarget", "getField2", "return value");
		assertEquals("return value", subTestClass.getField2());

	}

	/**
	 * SubTestTarget extends TestTarget
	 * SubTestTarget#getField3()���\�b�h�̖߂�l���A�����ւ���
	 * getField3�́ATestTarget�̃I�[�o�[���C�h���\�b�h
	 */
	public void testAroundSubClass003() {

		SubTestTarget subTestClass = new SubTestTarget();
		assertNull(subTestClass.getField3());

		MockObjectManager.addReturnValue("SubTestTarget", "getField3", new Hashtable());
		assertNotNull(subTestClass.getField3());

	}

	/**
	 * �߂�l�ɁA��O���w�肷�邱�ƂŁA��O�𔭐�������悤�ɂȂ�
	 */
	public void testAroundThrows001() {

		MockObjectManager.addReturnValue("jp.co.dgic.target.TestTarget", "throwException", new Exception("test001"));

		try {
			testClass.throwException();
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
			assertEquals("test001", e.getMessage());
		}
	}

	/**
	 * �߂�l�ɁA��O���w�肷�邱�ƂŁA��O�𔭐�������悤�ɂȂ�
	 */
	public void testAroundThrows002() {

		MockObjectManager.addReturnValue(
			"jp.co.dgic.target.TestTarget",
			"throwIOException",
			new IOException("test002"));

		try {
			testClass.throwIOException();
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (IOException e) {
			assertTrue(e instanceof IOException);
			assertEquals("test002", e.getMessage());
		}
	}

	/**
	 * �߂�l�ɁA��O���w�肷�邱�ƂŁA��O�𔭐�������悤�ɂȂ�
	 * throws IOException, SQLException
	 */
	public void testAroundThrows003() {

		MockObjectManager.addReturnValue(
			"jp.co.dgic.target.TestTarget",
			"throwIOAndSQLException",
			new IOException("test003"));

		try {
			testClass.throwIOAndSQLException();
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (IOException ioe) {
			assertTrue(ioe instanceof IOException);
			assertEquals("test003", ioe.getMessage());
		} catch (SQLException sqle) {
			fail("���̍s�͎��s����Ȃ��͂�");
		}
	}

	/**
	 * �߂�l�ɁA��O���w�肷�邱�ƂŁA��O�𔭐�������悤�ɂȂ�
	 * throws IOException, SQLException
	 */
	public void testAroundThrows004() {

		MockObjectManager.addReturnValue(
			"jp.co.dgic.target.TestTarget",
			"throwIOAndSQLException",
			new SQLException("test004"));

		try {
			testClass.throwIOAndSQLException();
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (IOException ioe) {
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (SQLException sqle) {
			assertTrue(sqle instanceof SQLException);
			assertEquals("test004", sqle.getMessage());
		}
	}

	/**
	 * �߂�l�ɁA��O���w�肷�邱�ƂŁA��O�𔭐�������悤�ɂȂ�
	 * throws Exception�̃��\�b�h�ɑ΂��āASQLException���w�肷��
	 */
	public void testAroundThrows005() {

		MockObjectManager.addReturnValue("jp.co.dgic.target.TestTarget", "throwException", new SQLException("test005"));

		try {
			testClass.throwException();
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (SQLException e) {
			assertTrue(e instanceof SQLException);
			assertEquals("test005", e.getMessage());
		} catch (Exception e) {
			fail("���̍s�͎��s����Ȃ��͂�");
		}
	}

	/**
	 * �߂�l�ɁA��O���w�肷�邱�ƂŁA��O�𔭐�������悤�ɂȂ�
	 * throws IOException, SQLException�̃��\�b�h�ɑ΂��āAAWTException���w�肷��
	 */
	public void testAroundThrows006() {

		MockObjectManager.addReturnValue(
			"jp.co.dgic.target.TestTarget",
			"throwIOAndSQLException",
			new AWTException("test006"));

		try {
			testClass.throwIOAndSQLException();
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (IOException ioe) {
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (SQLException sqle) {
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
		}
	}

	/**
	 * �߂�l�ɁA��O���w�肷�邱�ƂŁA��O�𔭐�������悤�ɂȂ�
	 * throws IOException�̃��\�b�h�ɑ΂��āAException���w�肷��
	 */
	public void testAroundThrows007() {

		MockObjectManager.addReturnValue("jp.co.dgic.target.TestTarget", "throwIOException", new Exception("test007"));

		try {
			testClass.throwIOException();
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (IOException e) {
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
		} catch (Exception e) {
			fail("���̍s�͎��s����Ȃ��͂�");
		}
	}

	/**
	 * �߂�l�ɁA��O���w�肷�邱�ƂŁA��O�𔭐�������悤�ɂȂ�
	 * throws IOException, SQLException�̃��\�b�h�ɑ΂��āAAWTException���w�肷��
	 */
	public void testAroundThrows008() {

		MockObjectManager.addReturnValue(
			"jp.co.dgic.target.TestTarget",
			"throwIOAndSQLException",
			new Exception("test008"));

		try {
			testClass.throwIOAndSQLException();
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (IOException ioe) {
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (SQLException sqle) {
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
		}
	}

	/**
	 * �߂�l�ɁA��O���w�肷�邱�ƂŁA��O�𔭐�������悤�ɂȂ�
	 * throws VirtualException�̃��\�b�h�ɑ΂��āAVirtualException���w�肷��
	 */
	public void testAroundThrows009() {

		MockObjectManager.addReturnValue("SubTestTarget", "getField5", new VirtualException());

		SubTestTarget subTarget = new SubTestTarget();

		try {
			subTarget.getField5();
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (VirtualException ve) {
		} catch (Throwable t) {
			fail("���̍s�͎��s����Ȃ��͂�");
		}
	}

	/**
	 * �߂�l�ɁA���s����O���w�肷�邱�ƂŁA��O�𔭐�������悤�ɂȂ�
	 * throws�錾�Ȃ��̃��\�b�h�ɑ΂��āARuntimeException���w�肷��
	 */
	public void testRuntimeException001() {

		MockObjectManager.addReturnValue("jp.co.dgic.target.TestTarget", "getField1", new RuntimeException("test001"));

		try {
			testClass.getField1();
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (RuntimeException e) {
			assertTrue(e instanceof RuntimeException);
			assertEquals("test001", e.getMessage());
		}
	}

	/**
	 * �߂�l�ɁA���s����O���w�肷�邱�ƂŁA��O�𔭐�������悤�ɂȂ�
	 * throws Exception�̃��\�b�h�ɑ΂��āARuntimeException���w�肷��
	 */
	public void testRuntimeException002() {

		MockObjectManager.addReturnValue(
			"jp.co.dgic.target.TestTarget",
			"throwException",
			new RuntimeException("test002"));

		try {
			testClass.throwException();
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (RuntimeException re) {
			assertTrue(re instanceof RuntimeException);
			assertEquals("test002", re.getMessage());
		} catch (Exception e) {
			fail("���̍s�͎��s����Ȃ��͂�");
		}
	}

	/**
	 * �߂�l�ɁA���s����O���w�肷�邱�ƂŁA��O�𔭐�������悤�ɂȂ�
	 * throws IOException�̃��\�b�h�ɑ΂��āARuntimeException���w�肷��
	 */
	public void testRuntimeException003() {

		MockObjectManager.addReturnValue(
			"jp.co.dgic.target.TestTarget",
			"throwIOException",
			new RuntimeException("test003"));

		try {
			testClass.throwIOException();
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (RuntimeException re) {
			assertTrue(re instanceof RuntimeException);
			assertEquals("test003", re.getMessage());
		} catch (IOException ioe) {
			fail("���̍s�͎��s����Ȃ��͂�");
		}
	}

	/**
	 * �߂�l�ɁA���s����O���w�肷�邱�ƂŁA��O�𔭐�������悤�ɂȂ�
	 * throws IOException, SQLException�̃��\�b�h�ɑ΂��āARuntimeException���w�肷��
	 */
	public void testRuntimeException004() {

		MockObjectManager.addReturnValue(
			"jp.co.dgic.target.TestTarget",
			"throwIOAndSQLException",
			new RuntimeException("test004"));

		try {
			testClass.throwIOAndSQLException();
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (RuntimeException re) {
			assertTrue(re instanceof RuntimeException);
			assertEquals("test004", re.getMessage());
		} catch (IOException ioe) {
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (SQLException sqle) {
			fail("���̍s�͎��s����Ȃ��͂�");
		}
	}

	// NullPointerException

	/**
	 * �߂�l�ɁA���s����O���w�肷�邱�ƂŁA��O�𔭐�������悤�ɂȂ�
	 * throws�錾�Ȃ��̃��\�b�h�ɑ΂��āANullPointerException���w�肷��
	 */
	public void testRuntimeException005() {

		MockObjectManager.addReturnValue(
			"jp.co.dgic.target.TestTarget",
			"getField1",
			new NullPointerException("test005"));

		try {
			testClass.getField1();
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (NullPointerException e) {
			assertTrue(e instanceof NullPointerException);
			assertEquals("test005", e.getMessage());
		}
	}

	/**
	 * �߂�l�ɁA���s����O���w�肷�邱�ƂŁA��O�𔭐�������悤�ɂȂ�
	 * throws Exception�̃��\�b�h�ɑ΂��āANullPointerException���w�肷��
	 */
	public void testRuntimeException006() {

		MockObjectManager.addReturnValue(
			"jp.co.dgic.target.TestTarget",
			"throwException",
			new NullPointerException("test006"));

		try {
			testClass.throwException();
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (NullPointerException npe) {
			assertTrue(npe instanceof NullPointerException);
			assertEquals("test006", npe.getMessage());
		} catch (Exception e) {
			fail("���̍s�͎��s����Ȃ��͂�");
		}
	}

	/**
	 * �߂�l�ɁA���s����O���w�肷�邱�ƂŁA��O�𔭐�������悤�ɂȂ�
	 * throws IOException�̃��\�b�h�ɑ΂��āANullPointerException���w�肷��
	 */
	public void testRuntimeException007() {

		MockObjectManager.addReturnValue(
			"jp.co.dgic.target.TestTarget",
			"throwIOException",
			new NullPointerException("test007"));

		try {
			testClass.throwIOException();
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (NullPointerException npe) {
			assertTrue(npe instanceof NullPointerException);
			assertEquals("test007", npe.getMessage());
		} catch (IOException ioe) {
			fail("���̍s�͎��s����Ȃ��͂�");
		}
	}

	/**
	 * �߂�l�ɁA���s����O���w�肷�邱�ƂŁA��O�𔭐�������悤�ɂȂ�
	 * throws IOException, SQLException�̃��\�b�h�ɑ΂��āANullPointerException���w�肷��
	 */
	public void testRuntimeException008() {

		MockObjectManager.addReturnValue(
			"jp.co.dgic.target.TestTarget",
			"throwIOAndSQLException",
			new NullPointerException("test008"));

		try {
			testClass.throwIOAndSQLException();
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (NullPointerException npe) {
			assertTrue(npe instanceof NullPointerException);
			assertEquals("test008", npe.getMessage());
		} catch (IOException ioe) {
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (SQLException sqle) {
			fail("���̍s�͎��s����Ȃ��͂�");
		}
	}

	/**
	 * �߂�l�ɁA��O���w�肷�邱�ƂŁA��O�𔭐�������悤�ɂȂ�
	 * throws VirtualException�̃��\�b�h�ɑ΂��āAnullPointerException���w�肷��
	 */
	public void testRuntimeException009() {

		MockObjectManager.addReturnValue("SubTestTarget", "getField5", new NullPointerException("test009"));

		SubTestTarget subTarget = new SubTestTarget();

		try {
			subTarget.getField5();
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (NullPointerException npe) {
			assertTrue(npe instanceof NullPointerException);
			assertEquals("test009", npe.getMessage());
		} catch (VirtualException ve) {
			fail("���̍s�͎��s����Ȃ��͂�");
		}

		// �w��̃��\�b�h�͎��s���ꂽ�͂�
		assertTrue(MockObjectManager.isCalled("SubTestTarget", "getField5"));
	}

	public void testThrowError001() {
		MockObjectManager.addReturnValue("jp.co.dgic.target.TestTarget", "getField1", new Error("TestError"));

		try {
			testClass.getField1();
			fail("���̍s�͎��s����Ȃ��͂�");
		} catch (Error e) {
			assertEquals("TestError", e.getMessage());
		}

		// �w��̃��\�b�h�͎��s���ꂽ�͂�
		assertTrue(MockObjectManager.isCalled("jp.co.dgic.target.TestTarget", "getField1"));
	}

	/**
	 * System.exit()�̌Ăяo���𖳌��ɂ���
	 */
	public void testSystemExit001() {

		testClass.exit(0);

		MockObjectManager.addReturnValue("System", "exit");
		testClass.exit(1);
	}

	/**
	 * ObjectInputStream.readObject�ŁAIOException��throw������B
	 */
	public void testReadObject001() throws Exception {

		MockObjectManager.addReturnValue("ObjectInputStream", "readObject", new IOException());

		try {
			testClass.readObject();
			fail("���s����Ȃ��͂�");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			fail("���s����Ȃ��͂�");
		}

		// �w��̃��\�b�h�͎��s���ꂽ�͂�
		assertTrue(MockObjectManager.isCalled("ObjectInputStream", "readObject"));

		// 2��ڂ͗�O�͔������Ȃ��͂�
		testClass.readObject();

	}

	/**
	 * ObjectInputStream.readObject�ŁAClassNotFoundException��throw������B
	 */
	public void testReadObject002() throws Exception {

		MockObjectManager.addReturnValue("ObjectInputStream", "readObject", new ClassNotFoundException());

		try {
			testClass.readObject();
			fail("���s����Ȃ��͂�");
		} catch (IOException ioe) {
			fail("���s����Ȃ��͂�");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}

		// �w��̃��\�b�h�͎��s���ꂽ�͂�
		assertTrue(MockObjectManager.isCalled("ObjectInputStream", "readObject"));

		// 2��ڂ͗�O�͔������Ȃ��͂�
		testClass.readObject();

	}

}