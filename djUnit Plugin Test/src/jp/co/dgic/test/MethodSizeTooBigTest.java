/*
 * Created on 2005/09/05
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jp.co.dgic.test;

import jp.co.dgic.target.MethodSizeTooBig;
import jp.co.dgic.testing.framework.DJUnitTestCase;

/**
 * @author kataoka
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MethodSizeTooBigTest extends DJUnitTestCase {

	public void testThisIsBigmethod() throws Exception {
		MethodSizeTooBig target = new MethodSizeTooBig();
		target.thisIsBigSizeMethod();
	}
}
