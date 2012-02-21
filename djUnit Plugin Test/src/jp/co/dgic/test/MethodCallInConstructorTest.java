package jp.co.dgic.test;

import java.io.File;

import jp.co.dgic.target.AClass;
import jp.co.dgic.target.MethodCallInConstructor;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class MethodCallInConstructorTest extends DJUnitTestCase {
	
	public MethodCallInConstructorTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testNewFile001() throws Exception {
		MethodCallInConstructor target = new MethodCallInConstructor();
		assertEquals("default.txt", target.getFileName());
	}

	public void testNewFile002() throws Exception {
		addReturnValue(File.class, "<init>", new File("test.csv"));
		MethodCallInConstructor target = new MethodCallInConstructor();
		assertEquals("test.csv", target.getFileName());
	}
	
	public void testNewFile003() throws Exception {
		MethodCallInConstructor target = new MethodCallInConstructor("test003.txt");
		assertEquals("test003.txt", target.getFileName());
	}
	
	public void testAClassName001() throws Exception {
		MethodCallInConstructor target = new MethodCallInConstructor();
		assertEquals("AClass", target.getAClassName());
	}

	public void testAClassName002() throws Exception {
		addReturnValue(AClass.class, "<init>", new AClass("AClassName002"));
		MethodCallInConstructor target = new MethodCallInConstructor();
		assertEquals("AClassName002", target.getAClassName());
	}
	
	public void testGetNameA001() throws Exception {
		MethodCallInConstructor target = new MethodCallInConstructor();
		assertEquals("A", target.getNameA());
	}

	public void testGetNameA002() throws Exception {
		MethodCallInConstructor target = new MethodCallInConstructor();
		addReturnValue(AClass.class, "<init>", new AClass("A002"));
		assertEquals("A002", target.getNameA());
	}
	
	public void testGetNameAS001() throws Exception {
		assertEquals("A", MethodCallInConstructor.getNameAS());
	}
	
	public void testGetNameAS002() throws Exception {
		// 別クラスのコンストラクタ(インスタンス)は、差し替え可能
		addReturnValue(AClass.class, "<init>", new AClass("A002"));
		assertEquals("A002", MethodCallInConstructor.getNameAS());
	}
	
	public void testGetOwnName001() throws Exception {
		MethodCallInConstructor target = new MethodCallInConstructor();
		assertEquals("ownName", target.getOwnName());
	}

	public void testGetOwnName002() throws Exception {
		// 同じクラスのコンストラクタ(インスタンス)は、差し替えが失敗する???
		// インスタンスメソッドとstaticの場合で、挙動が違う???
		addReturnValue(MethodCallInConstructor.class, "<init>", new MethodCallInConstructor("testOwnName002"));
		MethodCallInConstructor target = new MethodCallInConstructor();
		assertEquals("testOwnName002", target.getOwnName());
	}
	
	public void testGetOwnNameS001() throws Exception {
		assertEquals("ownNameS", MethodCallInConstructor.getOwnNameS());
	}
	
	public void testGetOwnNameS002() throws Exception {
		// 同じクラスのコンストラクタ(インスタンス)は、差し替えが失敗する???
		// インスタンスメソッドとstaticの場合で、挙動が違う???
		addReturnValue(MethodCallInConstructor.class, "<init>", new MethodCallInConstructor("testOwnNameS002"));
		assertEquals("testOwnNameS002", MethodCallInConstructor.getOwnNameS());
	}
	
}
