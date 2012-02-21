package jp.co.dgic.test;

import java.io.File;

import jp.co.dgic.target.ConctructorReturnValue;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class ConctructorReturnValueTest extends DJUnitTestCase {

	public ConctructorReturnValueTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCreateName001() throws Exception {
		assertEquals("foo", ConctructorReturnValue.createName());
	}

	public void testCreateName002() throws Exception {
		addReturnValue(ConctructorReturnValue.class, "getName", "bar");
		assertEquals("bar", ConctructorReturnValue.createName());
	}

	public void testCreateName003() throws Exception {
		ConctructorReturnValue ca = new ConctructorReturnValue("bar");
		assertEquals("bar", ca.getName());
		addReturnValue(ConctructorReturnValue.class, "<init>", ca);
		assertEquals("bar", ConctructorReturnValue.createName());
	}

	public void testCreateFileName001() throws Exception {
		assertEquals("test.txt", ConctructorReturnValue.createFileName());
	}
	
	public void testCreateFileName002() throws Exception {
		addReturnValue(File.class, "getName", "bar");
		assertEquals("bar", ConctructorReturnValue.createFileName());
	}
	
	public void testCreateFileName003() throws Exception {
		addReturnValue(File.class, "<init>", new File("other.txt"));
		assertEquals("other.txt", ConctructorReturnValue.createFileName());
	}
}
