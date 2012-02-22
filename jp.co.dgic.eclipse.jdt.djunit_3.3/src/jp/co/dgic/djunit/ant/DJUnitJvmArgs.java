/*
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2000-2002 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "Ant" and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
package jp.co.dgic.djunit.ant;

import java.io.File;
import java.util.StringTokenizer;

import org.apache.tools.ant.types.CommandlineJava;
import org.apache.tools.ant.util.FileUtils;

public class DJUnitJvmArgs {

	public static final String DJUNIT_CLASS_LOADER = "jp.co.dgic.testing.common.DJUnitClassLoader";

	private static final String JUNIT_EXCLUDES_PATHS_KEY = "jp.co.dgic.eclipse.junit.excluded.paths";
	private static final String VIRTUALMOCK_USE_VIRTUALMOCK_KEY = "jp.co.dgic.eclipse.virtualmock.usevirtualmock";
	private static final String COVERAGE_USE_COVERAGE_KEY = "jp.co.dgic.eclipse.coverage.usecoverage";
	private static final String PROJECTS_SOURCE_DIR_KEY = "jp.co.dgic.eclipse.project.source.dir";
	private static final String VIRTUALMOCK_IGNORE_LIBRARY_KEY = "jp.co.dgic.eclipse.virtualmock.ignore.library";
	private static final String VIRTUALMOCK_NOTIGNORE_PATTERNS_KEY = "jp.co.dgic.eclipse.virtualmock.notignore.patterns";
	private static final String BYTECODE_LIBRARY_KEY = "jp.co.dgic.eclipse.classloader.bytecodelibrary";

	private static final String BYTECODE_LIBRARY_ASM = "ASM";
	private static final String BYTECODE_LIBRARY_ASM2 = "ASM2";
	private static final String BYTECODE_LIBRARY_ASM15 = "ASM15";

	private static final String JUNIT_DEFAULT_EXCLUDED_PATHS = "";
//		"sun.*;"
//			+ "com.sun.*;"
//			+ "org.omg.*;"
//			+ "javax.*;"
//			+ "sunw.*;"
//			+ "java.*;"
//			+ "org.w3c.dom.*;"
//			+ "org.xml.sax.*;"
//			+ "net.jini.*";

	private boolean useCoverage = true;
	private boolean useVirtualMock = false;

	private String excludesPaths;
	private String targetSrcDir;

	private boolean isIgnireLibrary = false;
	private String notIgnorePatterns;

	private boolean useNoverify = false;

	private File baseDir;

	private String asmVersion = "ASM";

	public void setBaseDir(File baseDir) {
		this.baseDir = baseDir;
	}

	public void setUseCoverage(boolean useCoverage) {
		this.useCoverage = useCoverage;
	}

	public void setUseVirtualMock(boolean useVirtualMock) {
		this.useVirtualMock = useVirtualMock;
	}

	public void addExcludesPaths(String excludesPaths) {
		this.excludesPaths = excludesPaths;
	}

	public void setTargetSrcDir(String targetSrcDir) {
		this.targetSrcDir = toAbsolutecDir(targetSrcDir);
	}

	public void setIgnoreLibrary(boolean isIgnoreLibrary) {
		this.isIgnireLibrary = isIgnoreLibrary;
	}

	public void setNotIgnorePatterns(String notIgnorePatterns) {
		this.notIgnorePatterns = notIgnorePatterns;
	}

	public void setUseNoverify(boolean useNoverify) {
		this.useNoverify = useNoverify;
	}

	public void setAsmVersion(String version) {
		// ASM, ASM2, ASM15
		this.asmVersion = version;
	}

	public void addJvmArgsTo(CommandlineJava command) {

		command.createVmArgument().setValue("-D" + COVERAGE_USE_COVERAGE_KEY + "=" + useCoverage);
		command.createVmArgument().setValue("-D" + VIRTUALMOCK_USE_VIRTUALMOCK_KEY + "=" + useVirtualMock);
		command.createVmArgument().setValue("-D" + PROJECTS_SOURCE_DIR_KEY + "=" +
											(targetSrcDir == null ? "" : targetSrcDir));
		command.createVmArgument().setValue("-D" + JUNIT_EXCLUDES_PATHS_KEY + "=" + JUNIT_DEFAULT_EXCLUDED_PATHS +
											(excludesPaths == null ? "" : ";" + excludesPaths));
		command.createVmArgument().setValue("-D" + VIRTUALMOCK_IGNORE_LIBRARY_KEY + "=" + isIgnireLibrary);
		command.createVmArgument().setValue("-D" + VIRTUALMOCK_NOTIGNORE_PATTERNS_KEY + "=" +
											(notIgnorePatterns == null ? "" : notIgnorePatterns));

		command.createVmArgument().setValue("-D" + BYTECODE_LIBRARY_KEY + "=" + getAsmVersion());

		command.createVmArgument().setValue("-Djava.system.class.loader=" + DJUNIT_CLASS_LOADER);
		if (useNoverify) {
			command.createVmArgument().setValue("-noverify");
		}
	}

	private String getAsmVersion() {
		if (BYTECODE_LIBRARY_ASM2.equalsIgnoreCase(asmVersion)) return BYTECODE_LIBRARY_ASM2;
		if (BYTECODE_LIBRARY_ASM15.equalsIgnoreCase(asmVersion)) return BYTECODE_LIBRARY_ASM15;
		return BYTECODE_LIBRARY_ASM;
	}

	private String toAbsolutecDir(String targetSrcDir) {
		String[] dirs = splitValue(targetSrcDir);
		if (dirs == null) return targetSrcDir;

		FileUtils fileUtils = FileUtils.newFileUtils();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dirs.length; i++) {
			File dir = fileUtils.resolveFile(baseDir, dirs[i]);
			sb.append(dir.getAbsolutePath());
			sb.append(";");
		}

		return sb.toString();
	}

	private String[] splitValue(String value) {

		if (value == null) {
			return null;
		}

		StringTokenizer st = new StringTokenizer(value, ";");
		String[] values = new String[st.countTokens()];
		for (int index = 0; index < values.length; index++) {
			values[index] = st.nextToken();
		}
		return values;
	}

}
