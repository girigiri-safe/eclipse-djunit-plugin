package jp.co.dgic.test;

import jp.co.dgic.target.Sample;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class SampleTest extends DJUnitTestCase {
	public SampleTest(String name) {
		super(name);
	}
	
	protected void setUp() throws Exception {
		super.setUp();  // ïKê{
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();  // ïKê{
	}
	
	public void test001() {
		Sample com = new Sample();
		
		try{
			com.execute();
		} catch (Exception e) {
			fail("error");
		}
	}
}