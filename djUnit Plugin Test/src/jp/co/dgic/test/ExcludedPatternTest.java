package jp.co.dgic.test;

import jp.co.dgic.testing.framework.DJUnitTestCase;

public class ExcludedPatternTest extends DJUnitTestCase {

	public ExcludedPatternTest(String arg0) {
		super(arg0);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testNormal001() throws Exception {
		System.out.println("jp.co.dgic.eclipse.junit.excluded.paths : " + System.getProperty("jp.co.dgic.eclipse.junit.excluded.paths"));
		System.out.println("jp.co.dgic.eclipse.trace.include.patterns : " + System.getProperty("jp.co.dgic.eclipse.trace.include.patterns"));
		System.out.println("jp.co.dgic.eclipse.coverage.included.patterns : " + System.getProperty("jp.co.dgic.eclipse.coverage.included.patterns"));
		System.out.println("jp.co.dgic.eclipse.coverage.excluded.patterns : " + System.getProperty("jp.co.dgic.eclipse.coverage.excluded.patterns"));
	}

}
