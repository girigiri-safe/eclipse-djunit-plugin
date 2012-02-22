/**
 * Copyright (C)2004 dGIC Corporation.
 *
 * This file is part of djUnit plugin.
 *
 * djUnit plugin is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * djUnit plugin is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with djUnit plugin; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 */
package jp.co.dgic.eclipse.jdt.internal.junit.runner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URLClassLoader;

public class DJUnitRunner {

	private static final String CLASSLOADER_CLASS_NAME = "jp.co.dgic.testing.common.DJUnitEclipseClassLoader";
	private static final String TEST_RUNNER_CLASSNAME = "org.eclipse.jdt.internal.junit.runner.RemoteTestRunner";

	public static void main(String[] args) throws Throwable {

		checkAndCreateClassloader();

		URLClassLoader loader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
		Class clas = loader.loadClass(TEST_RUNNER_CLASSNAME);

		// set client host & port
		// invoke DJUnitTestCaseClassLoader#initialize
		Method initializemethod = loader.getClass().getMethod("initialize", new Class[] { String[].class });
		initializemethod.invoke(loader, new Object[] { args });

		try {
			// invoke "main" method of target class
			Method main = clas.getDeclaredMethod("main", new Class[] { args.getClass() });
			Thread.currentThread().setContextClassLoader(loader);
			main.invoke(null, new Object[] { args });
		} finally {
			// invoke DJUnitTestCaseClassLoader#shutDownMessageSender();
			Method shutdownMethod = loader.getClass().getMethod("shutDownMessageSender", new Class[0]);
			shutdownMethod.invoke(loader, new Object[0]);
		}
	}

	private static void checkAndCreateClassloader() {

		URLClassLoader loader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
		if (!loader.getClass().getName().equalsIgnoreCase(CLASSLOADER_CLASS_NAME)) {
			try {
				Class cls = loader.loadClass(CLASSLOADER_CLASS_NAME);
				Constructor c = cls.getConstructor(new Class[] {ClassLoader.class});
				Object newLoader = c.newInstance(new Object[] {loader});
				Thread.currentThread().setContextClassLoader((ClassLoader) newLoader);
			} catch (Throwable t) {
				t.printStackTrace();
				System.exit(-1);
			}
		}
	}

}
