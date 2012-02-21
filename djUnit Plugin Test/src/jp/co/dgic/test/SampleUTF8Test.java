package jp.co.dgic.test;

import jp.co.dgic.target.SampleUTF8;
import junit.framework.TestCase;

public class SampleUTF8Test extends TestCase {
	private SampleUTF8 sampleUTF8;

	public SampleUTF8Test(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();

		sampleUTF8 = new SampleUTF8();
	}

	protected void tearDown() throws Exception {
		super.tearDown();

		sampleUTF8 = null;
	}

	public void testMethodA() throws Exception {
	}

}
