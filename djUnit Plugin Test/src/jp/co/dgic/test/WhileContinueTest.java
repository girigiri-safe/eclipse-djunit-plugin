package jp.co.dgic.test;

import jp.co.dgic.target.WhileContinue;
import junit.framework.TestCase;

public class WhileContinueTest extends TestCase {
	
	private WhileContinue whileContinue;

	public WhileContinueTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		whileContinue = new WhileContinue();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		
		whileContinue = null;
	}
	
	public void testWhileContinue() throws Exception {
		whileContinue.exec();
	}

}
