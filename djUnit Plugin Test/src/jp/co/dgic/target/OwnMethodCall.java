/**
 * This file is part of djUnit plug-in.
 *
 * you can redistribute it and/or modify
 * it under the terms of the CPL Common Public License.
 *
 * See the CPL Common Public License for more details.
 */
package jp.co.dgic.target;

public class OwnMethodCall {

	public void mainMethod() {
		ownMethod();
	}

	public void ownMethod() {
		System.out.println("Own Method Called...");
		try {
			if (false) throw new RuntimeException("Test Exception");
		} catch (Throwable t) {
			System.out.println(t);
		}
	}
}
