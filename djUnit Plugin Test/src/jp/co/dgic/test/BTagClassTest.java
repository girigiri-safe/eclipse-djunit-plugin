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
		// �������s
		try {
			_tag.doStartTag();
		} catch (Throwable t) {
			// do nothing.
		}

		// �g���[�X
		System.out.println("************************************************");
		System.out.println("* BTag#doStartTag()�F"
						   + getCallCount(BTagClass.class.getName(), "doStartTag")
						   + "��Ăяo���ς�");
		System.out.println("* ATag#doStartTag()�F"
						   + getCallCount(ATagClass.class.getName(), "doStartTag")
						   + "��Ăяo���ς�");
		System.out.println("* ValidatorTag#doStartTag()�F"
						   + getCallCount(ValidatorTag.class.getName(), "doStartTag")
						   + "��Ăяo���ς�");
		System.out.println("************************************************");

		// ����
		assertEquals(1, getCallCount(BTagClass.class.getName(), "doStartTag"));
		assertEquals(0, getCallCount(ATagClass.class.getName(), "doStartTag"));
		assertEquals(1, getCallCount(ValidatorTag.class.getName(), "doStartTag"));

		assertCalled(BTagClass.class.getName(), "doStartTag");
		assertCalled(ValidatorTag.class.getName(), "doStartTag");

	}

	public void testRelease() throws Exception {
		// �������s
		try {
			_tag.release();
		} catch (Throwable t) {
			// do nothing.
		}

		// ����
		assertEquals(1, getCallCount(BTagClass.class.getName(), "release"));
		assertEquals(1, getCallCount(ATagClass.class.getName(), "release"));
		assertEquals(1, getCallCount(ValidatorTag.class.getName(), "release"));

		assertCalled(BTagClass.class.getName(), "release");
		assertCalled(ATagClass.class.getName(), "release");
		assertCalled(ValidatorTag.class.getName(), "release");

	}

}