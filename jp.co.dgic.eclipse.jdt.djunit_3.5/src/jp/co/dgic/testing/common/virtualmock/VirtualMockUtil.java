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
package jp.co.dgic.testing.common.virtualmock;

import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import jp.co.dgic.testing.common.util.DJUnitUtil;

public class VirtualMockUtil {

	public static final String VIRTUALMOCK_USE_VIRTUALMOCK_KEY =
		"jp.co.dgic.eclipse.virtualmock.usevirtualmock";

	public static final String VIRTUALMOCK_INCLUDE_CLASS_KEY =
		"jp.co.dgic.eclipse.virtualmock.include.class";

	public static final String VIRTUALMOCK_IGNORE_LIBRARY_KEY =
		"jp.co.dgic.eclipse.virtualmock.ignore.library";

	public static final String VIRTUALMOCK_NOTIGNORE_PATTERNS_KEY =
		"jp.co.dgic.eclipse.virtualmock.notignore.patterns";
	
	private static String[] includes;
	private static String[] notIgnorePatterns;

	public static boolean isUseVirtualMock() {
		String useVirtualMock = System.getProperty(VIRTUALMOCK_USE_VIRTUALMOCK_KEY);
		if (useVirtualMock == null) {
			return false;
		}
		if ("true".equalsIgnoreCase(useVirtualMock)) {
			return true;
		}
		return false;
	}

	public static boolean isIgnoreLibrary() {
		String isIgnoreLibrary = System.getProperty(VIRTUALMOCK_IGNORE_LIBRARY_KEY);
		if (isIgnoreLibrary == null) {
			return false;
		}
		if ("true".equalsIgnoreCase(isIgnoreLibrary)) {
			return true;
		}
		return false;
	}

	public static boolean isNotIgnore(String className) {
		String[] patterns = getNotIgnorePatterns();
		if (patterns == null) {
			return false;
		}
		Perl5Matcher matcher = new Perl5Matcher();
		PatternCompiler compiler = new Perl5Compiler();
		for (int index = 0; index < patterns.length; index++) {
			try {
				if (matcher.matches(className, compiler.compile(patterns[index]))) {
					return true;
				}
			} catch (Exception e) {
				// continue
			}

		}
		return false;
	}

	private static String[] getNotIgnorePatterns() {
		if (notIgnorePatterns != null) {
			return notIgnorePatterns;
		}
		String patterns = System.getProperty(VIRTUALMOCK_NOTIGNORE_PATTERNS_KEY);
		if (patterns != null) {
			notIgnorePatterns = DJUnitUtil.splitValue(patterns);
		}
		return notIgnorePatterns;
	}

	public static String toIncludeValue(String[] fileNames) {
		if (fileNames == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("-D");
		sb.append(VIRTUALMOCK_INCLUDE_CLASS_KEY);
		sb.append("=");
		for (int i = 0; i < fileNames.length; i++) {
			sb.append(toClassName(fileNames[i]));
			sb.append(";");
		}
		return sb.toString();
	}

	public static String getIncludeValue() {
		return System.getProperty(VIRTUALMOCK_INCLUDE_CLASS_KEY);
	}
	
	private static String[] getIncludes() {
		if (includes != null) {
			return includes;
		}
		String includeValue = getIncludeValue();
		if (includeValue != null) {
			includes = DJUnitUtil.splitValue(includeValue);
		}
		return includes;
	}

	public static boolean isInclude(String className) {
		String[] includes = getIncludes();
		if (includes == null) {
			return true;
		}
		for (int i = 0; i < includes.length; i++) {
			if (className.equals(includes[i])) {
				return true;
			}
		}
		return false;
	}

	public static String toClassName(String fileName) {
		String className = null;
		if (fileName.endsWith(".class")) {
			int index = fileName.lastIndexOf('.');
			className = fileName.substring(0, index);
		}
		return className.replace('\\', '.');
	}

}
