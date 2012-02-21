package jp.co.dgic.test;
import java.io.IOException;

import jp.co.dgic.target.ReturnValueProviderForSystemClassTestTarget;
import jp.co.dgic.testing.common.virtualmock.IReturnValueProvider;
import jp.co.dgic.testing.common.virtualmock.MockObjectManager;
import junit.framework.TestCase;

public class ReturnValueProviderForSystemClassTest extends TestCase {

	private class ReturnValueProviderForMapGet implements IReturnValueProvider {

		public Object createReturnValue(Object[] args) throws Throwable {
			String arg = (String) args[0];
			if ("a".equals(arg)) {
				return "aaa";
			}
			if ("b".equals(arg)) {
				return "bbb";
			}
			if ("c".equals(arg)) {
				return "ccc";
			}
			if ("e".equals(arg)) throw new RuntimeException("Test RuntimeException");
			if ("ioe".equals(arg)) throw new IOException("Test IOException");
			return null;
		}
	}
	
	private class ReturnValueProviderForMapClear implements IReturnValueProvider {

		public Object createReturnValue(Object[] args) throws Throwable {
			return null;
		}
	}
	
	private ReturnValueProviderForSystemClassTestTarget map;

	public ReturnValueProviderForSystemClassTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		MockObjectManager.initialize();
		
		map = new ReturnValueProviderForSystemClassTestTarget();
		map.put("a", "a");
		map.put("b", "b");
		map.put("c", "c");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
		map = null;
	}
	
	public void testNormal001() throws Exception {
		assertEquals("a", map.get("a"));
		assertEquals("b", map.get("b"));
		assertEquals("c", map.get("c"));
	}

	public void testNormal002() throws Exception {
		
		MockObjectManager.addReturnValue("java.util.HashMap", "get", "x");
		MockObjectManager.addReturnValue("java.util.HashMap", "get", "y");
		MockObjectManager.addReturnValue("java.util.HashMap", "get", "z");
		
		assertEquals("x", map.get("a"));
		assertEquals("y", map.get("b"));
		assertEquals("z", map.get("c"));
	}

	public void testNormal003() throws Exception {
		
		MockObjectManager.setReturnValueAtAllTimes("java.util.HashMap", "get", new ReturnValueProviderForMapGet());
		
		assertEquals("aaa", map.get("a"));
		assertEquals("bbb", map.get("b"));
		assertEquals("ccc", map.get("c"));
		
		assertNull(map.get("z"));
		
		try {
			map.get("e");
			fail("é¿çsÇ≥ÇÍÇ»Ç¢ÇÕÇ∏");
		} catch (RuntimeException e) {
			e.printStackTrace();
			assertEquals("Test RuntimeException", e.getMessage());
		}
	}

	public void testNormal004() throws Exception {
		
		MockObjectManager.setReturnValueAtAllTimes("java.util.HashMap", "get", new ReturnValueProviderForMapGet());
		
		assertEquals("aaa", map.get("a"));
		assertEquals("bbb", map.get("b"));
		assertEquals("ccc", map.get("c"));
		
		assertNull(map.get("z"));
		
		try {
			map.get("ioe");
			fail("é¿çsÇ≥ÇÍÇ»Ç¢ÇÕÇ∏");
		} catch (Exception e) {
			e.printStackTrace();
			assertEquals("Test IOException", e.getMessage());
		}
	}

	public void testNormal005() throws Exception {
		
		MockObjectManager.setReturnValueAtAllTimes("java.util.HashMap", "clear", new ReturnValueProviderForMapClear());
		
		map.clear();
	}

}
