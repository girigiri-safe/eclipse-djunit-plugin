package jp.co.dgic.test;

import jp.co.dgic.target.StringBuilderException;

import org.junit.Assert;
import org.junit.Test;


public class StringBuilderExceptionTest4 {
	@Test
	public void testStringBuilderException001() {
		final StringBuilderException e = new StringBuilderException();
		Assert.assertEquals("args", e.getMessage());
	}
}
