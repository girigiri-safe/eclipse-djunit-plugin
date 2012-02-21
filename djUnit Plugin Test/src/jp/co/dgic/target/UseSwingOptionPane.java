/**
 * This file is part of djUnit plug-in.
 *
 * you can redistribute it and/or modify
 * it under the terms of the CPL Common Public License.
 *
 * See the CPL Common Public License for more details.
 */
package jp.co.dgic.target;

import javax.swing.JOptionPane;

public class UseSwingOptionPane {

	public static void main(String[] args) {
		String message = JOptionPane.showInputDialog("INPUT MESSAGE...");
		System.out.println(message);
	}
}
