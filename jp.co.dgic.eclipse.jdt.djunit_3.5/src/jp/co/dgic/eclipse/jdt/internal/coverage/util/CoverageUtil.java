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
package jp.co.dgic.eclipse.jdt.internal.coverage.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import jp.co.dgic.testing.common.coverage.CoverageEntry;
import jp.co.dgic.testing.common.coverage.CoverageResultFactory;
import jp.co.dgic.testing.common.util.DJUnitUtil;

import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import com.jcoverage.coverage.Instrumentation;

public class CoverageUtil {

	public static final String COVERAGE_INCLUDES_PATTERNS_KEY =
		"jp.co.dgic.eclipse.coverage.included.patterns";

	public static final String COVERAGE_EXCLUDES_PATTERNS_KEY =
		"jp.co.dgic.eclipse.coverage.excluded.patterns";

	public static final String COVERAGE_USE_COVERAGE_KEY =
		"jp.co.dgic.eclipse.coverage.usecoverage";

	private static HashMap coverageEntrys;
	
	private static String[] excludedPatterns;
	private static String[] includedPatterns;

	public static void prepareCoverageEntry() {

		coverageEntrys = new HashMap();
		CoverageEntry entry = null;
		Instrumentation i = null;
		Map m = getCoverage();
		for (Iterator it = m.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			i = (Instrumentation) m.get(key);
			entry = new CoverageEntry(key);
			entry.addInstrumentation(i);

			coverageEntrys.put(key, entry);
		}

	}

	public static CoverageEntry getOverallCoverage() {

		if (coverageEntrys == null) {
			prepareCoverageEntry();
		}

		CoverageEntry entry = new CoverageEntry("Overall coverage figures");
		for (Iterator it = coverageEntrys.entrySet().iterator(); it.hasNext();) {
			Map.Entry me = (Map.Entry) it.next();
			entry.addInstrumentation((CoverageEntry) me.getValue());
		}
		Map packageCoverage = getPackageCoverage();
		double sumBranchCoverage = 0d;
		for (Iterator it = packageCoverage.entrySet().iterator(); it.hasNext();) {
			sumBranchCoverage += ((CoverageEntry)((Map.Entry) it.next()).getValue()).getBranchCoverage();
		}
		entry.setBranchCoverage(sumBranchCoverage / (double) packageCoverage.size());
		return entry;
	}

	public static Map getPackageCoverage() {

		if (coverageEntrys == null) {
			prepareCoverageEntry();
		}

		TreeMap entries = new TreeMap();

		CoverageEntry entry = null;
		for (Iterator it = coverageEntrys.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			entry = getEntryBy(entries, getPackageNameFrom(key));
			entry.addInstrumentation((CoverageEntry) coverageEntrys.get(key));
		}
		return entries;
	}

	public static Map getFileCoverage() {

		if (coverageEntrys == null) {
			prepareCoverageEntry();
		}

		TreeMap entries = new TreeMap();

		CoverageEntry entry = null;
		for (Iterator it = coverageEntrys.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			entry = new CoverageEntry(key);
			entry.addInstrumentation((CoverageEntry) coverageEntrys.get(key));

			entries.put(key, entry);
		}
		return entries;
	}

	private static CoverageEntry getEntryBy(Map map, String packageName) {
		CoverageEntry entry = (CoverageEntry) map.get(packageName);
		if (entry == null) {
			entry = new CoverageEntry(packageName);
			map.put(packageName, entry);
		}
		return entry;
	}

	private static Map getCoverage() {
		return CoverageResultFactory.getInstance().getInstrumentation();
	}

	private static String getPackageNameFrom(String sourceFileName) {
		if (sourceFileName == null)
			return null;

		String className = getClassNameFrom(sourceFileName);
		int index = className.lastIndexOf('.');

		if (index < 0) {
			return "default";
		}
		return className.substring(0, index);
	}

	private static String getClassNameFrom(String sourceFileName) {
		if (sourceFileName == null)
			return null;
		if (sourceFileName.endsWith(".java")) {
			return sourceFileName.substring(0, sourceFileName.lastIndexOf('.'));
		}
		return sourceFileName;
	}

	public static boolean isExcluded(String className) {
		String[] patterns = getExcludedPatterns();
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

	public static boolean isIncluded(String className) {
		String[] patterns = getIncludedPatterns();
		if (patterns == null) {	// If this is omitted, All classes are included.
			return true;
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

	private static String[] getExcludedPatterns() {
		if (excludedPatterns != null) {
			return excludedPatterns;
		}
		String patterns = System.getProperty(COVERAGE_EXCLUDES_PATTERNS_KEY);
		if (patterns != null) {
			excludedPatterns = DJUnitUtil.splitValue(patterns);
		}
		return excludedPatterns;
	}

	private static String[] getIncludedPatterns() {
		if (includedPatterns != null) {
			return includedPatterns;
		}
		String patterns = System.getProperty(COVERAGE_INCLUDES_PATTERNS_KEY);
		if (patterns != null) {
			includedPatterns = DJUnitUtil.splitValue(patterns);
		}
		return includedPatterns;
	}

	public static boolean isUseCoverage() {
		String useCoverage = System.getProperty(CoverageUtil.COVERAGE_USE_COVERAGE_KEY);
		if (useCoverage == null) {
			return false;
		}
		if ("true".equalsIgnoreCase(useCoverage)) {
			return true;
		}
		return false;
	}

}
