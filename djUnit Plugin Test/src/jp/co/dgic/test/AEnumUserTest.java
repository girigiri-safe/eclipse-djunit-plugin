package jp.co.dgic.test;

import jp.co.dgic.target.AEnum;
import jp.co.dgic.target.AEnumUser;
import jp.co.dgic.testing.framework.DJUnitTestCase;

public class AEnumUserTest extends DJUnitTestCase {

	private AEnumUser target;
	
	public AEnumUserTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		this.target = new AEnumUser();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		this.target = null;
	}
	
	public void testUseEnum() throws Exception {
		target.useEnum();
		assertEquals(3, AEnum.length());
		addReturnValue(AEnum.class, "length", new Integer(5));
		assertEquals(5, AEnum.length());
	}

}
