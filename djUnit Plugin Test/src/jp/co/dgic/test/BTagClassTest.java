package jp.co.dgic.test;

import javax.faces.webapp.ValidatorTag;

import jp.co.dgic.target.ATagClass;
import jp.co.dgic.target.BTagClass;

import jp.co.dgic.testing.framework.DJUnitTestCase;

public class BTagClassTest extends DJUnitTestCase {

	private BTagClass _tag;

	protected void setUp() throws Exception {
		super.setUp();
		_tag = (BTagClass) BTagClass.class.newInstance();
	}

	public void testPrint() throws Exception {
		// 試験実行
		try {
			_tag.doStartTag();
		} catch (Throwable t) {
			// do nothing.
		}

		// トレース
		System.out.println("************************************************");
		System.out.println("* BTag#doStartTag()："
						   + getCallCount(BTagClass.class, "doStartTag")
						   + "回呼び出し済み");
		System.out.println("* ATag#doStartTag()："
						   + getCallCount(ATagClass.class, "doStartTag")
						   + "回呼び出し済み");
		System.out.println("* ValidatorTag#doStartTag()："
						   + getCallCount(ValidatorTag.class, "doStartTag")
						   + "回呼び出し済み");
		System.out.println("************************************************");

		// 検証
		assertEquals(1, getCallCount(BTagClass.class, "doStartTag"));
		assertEquals(0, getCallCount(ATagClass.class, "doStartTag"));
		assertEquals(1, getCallCount(ValidatorTag.class, "doStartTag"));

		assertCalled(BTagClass.class, "doStartTag");
		assertCalled(ValidatorTag.class, "doStartTag");

	}

	public void testRelease() throws Exception {
		// 試験実行
		try {
			_tag.release();
		} catch (Throwable t) {
			// do nothing.
		}

		// 検証
		assertEquals(1, getCallCount(BTagClass.class, "release"));
		assertEquals(1, getCallCount(ATagClass.class, "release"));
		assertEquals(1, getCallCount(ValidatorTag.class, "release"));

		assertCalled(BTagClass.class, "release");
		assertCalled(ATagClass.class, "release");
		assertCalled(ValidatorTag.class, "release");

	}

}