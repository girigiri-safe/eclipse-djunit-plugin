package jp.co.dgic.test;

import jp.co.dgic.target.SubClassArgument;
import jp.co.dgic.target.SuperClassArgument;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class SuperClassArgumentTest extends DJUnitTestCase {

	public SuperClassArgumentTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testNormal001() {
		SubClassArgument sub = new SubClassArgument();
		
		assertEquals("default", sub.getName());
	}

	public void testNormal002() {
		SubClassArgument sub = new SubClassArgument("new name");
		
		assertEquals("new name", sub.getName());
		
		String subArg = (String) getArgument(SubClassArgument.class, "<init>", 0);
		String superArg = (String) getArgument(SuperClassArgument.class, "<init>", 0);
		
		assertEquals("new name", subArg);
		assertEquals("new name", superArg);
		
		int subCallCount = getCallCount(SubClassArgument.class, "<init>");
		int superCallCount = getCallCount(SuperClassArgument.class, "<init>");
		
		assertEquals(1, subCallCount);
		assertEquals(1, superCallCount);
	}
	
}
