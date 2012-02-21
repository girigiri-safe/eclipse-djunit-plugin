/**
 * This file is part of djUnit plug-in.
 *
 * you can redistribute it and/or modify
 * it under the terms of the CPL Common Public License.
 *
 * See the CPL Common Public License for more details.
 */
package jp.co.dgic.target;

import java.io.File;

public class NewFileObject {

	public static void main(String[] args) {
		File f = new File("newText.txt");
		System.out.println(f.getName());
	}
}
