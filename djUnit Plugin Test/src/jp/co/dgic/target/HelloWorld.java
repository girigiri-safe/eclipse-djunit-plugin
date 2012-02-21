/**
 * This file is part of djUnit plug-in.
 *
 * you can redistribute it and/or modify
 * it under the terms of the CPL Common Public License.
 *
 * See the CPL Common Public License for more details.
 */
package jp.co.dgic.target;

public class HelloWorld {

	public static void main() {
		System.out.println("Hello World.");
	}

	public static void main2() {
		System.out.println("Hello World.");
		System.out.println("‚±‚ñ‚É‚¿‚í World.");
		
		HelloWorld world = new HelloWorld();
	}
	
	public int execute() {
		return exec();
	}
	
	public int exec() {
		return 2;
	}
}