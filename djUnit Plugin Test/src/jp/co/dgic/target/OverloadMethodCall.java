/**
 * This file is part of djUnit plug-in.
 *
 * you can redistribute it and/or modify
 * it under the terms of the CPL Common Public License.
 *
 * See the CPL Common Public License for more details.
 */
package jp.co.dgic.target;

public class OverloadMethodCall {

	public int overloadMethod(int value) {
		return value;
	}

	public int overloadMethod(String valueString) {
		try {
			return Integer.valueOf(valueString).intValue();
		} catch (NumberFormatException e) {
		}
		return 0;
	}

}
