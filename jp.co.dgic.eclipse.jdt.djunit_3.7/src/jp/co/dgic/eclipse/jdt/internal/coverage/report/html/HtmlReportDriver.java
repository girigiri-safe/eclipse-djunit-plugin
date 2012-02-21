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
package jp.co.dgic.eclipse.jdt.internal.coverage.report.html;

import java.io.File;

import com.jcoverage.coverage.Instrumentation;
import com.jcoverage.coverage.reporting.collation.JavaFileLine;
import com.jcoverage.coverage.reporting.collation.JavaFilePage;
import com.jcoverage.coverage.reporting.collation.PackageSummaryPage;
import com.jcoverage.coverage.reporting.collation.ReportImpl;
import com.jcoverage.coverage.reporting.collation.ReportSummaryPackageLine;
import com.jcoverage.coverage.reporting.collation.ReportSummaryPage;
import com.jcoverage.coverage.reporting.collation.StaticFileCollator;
import com.jcoverage.coverage.reporting.html.MultiViewStaticHtmlFormat;
import com.jcoverage.reporting.Collator;
import com.jcoverage.reporting.FileSerializer;
import com.jcoverage.reporting.Format;
import com.jcoverage.reporting.Line;
import com.jcoverage.reporting.Page;
import com.jcoverage.reporting.Report;
import com.jcoverage.reporting.Serializer;
import com.jcoverage.util.ClassHelper;

public class HtmlReportDriver {

	File[] javaSourceDirectorys;

	Report report = new ReportImpl();

	Page indexPage;

	public HtmlReportDriver(File[] javaSourceDirectory) {
		this.javaSourceDirectorys = javaSourceDirectory;
		indexPage = report.createFrontPage();
	}

	public synchronized void addInstrumentation(String clzName, Instrumentation instrumentation) {


		String ownerClzName = clzName;
		if (isInnerClass(clzName)) {
			ownerClzName = getOwnerClassNameOf(clzName);
		}

		String id = getSourceFileId(ownerClzName, instrumentation);

		String sourcePath = id.replace('.', File.separatorChar) + ".java";

		String packageName = ClassHelper.getPackageName(id);
		if (packageName.equals("")) {
			packageName = "default";
		}

		// Need to add a package line for this
		Line packageLine = indexPage.lookupLineByField(ReportSummaryPage.CATEGORY_PACKAGE_SUMMARY, ReportSummaryPackageLine.COLUMN_PACKAGE_NAME,
						packageName);
		Page packageDetailPage = null;
		if (packageLine == null) {

			packageLine = indexPage.createLine(ReportSummaryPage.CATEGORY_PACKAGE_SUMMARY);
			packageLine.setField(ReportSummaryPackageLine.COLUMN_PACKAGE_NAME, packageName);
			packageDetailPage = packageLine.openDetailPage();
		} else {
			packageDetailPage = packageLine.getDetailPage();
		}

		// Now add the class line
		Line javaFileLine = packageDetailPage.lookupLineByField(PackageSummaryPage.CATEGORY_JAVAFILES, JavaFileLine.COLUMN_FILE_NAME, clzName);
		Page javaFileDetailPage = null;
		if (javaFileLine == null) {
			javaFileLine = packageDetailPage.createLine(PackageSummaryPage.CATEGORY_JAVAFILES);
			javaFileLine.setField(JavaFileLine.COLUMN_FILE_NAME, clzName);
			javaFileLine.setField(JavaFileLine.COLUMN_PATH, getSourceFile(javaSourceDirectorys, sourcePath).getAbsolutePath());
			javaFileDetailPage = javaFileLine.openDetailPage();
		} else {
			javaFileDetailPage = javaFileLine.getDetailPage();
		}

		// Add class line to summary
		indexPage.addLineReference(javaFileLine, PackageSummaryPage.CATEGORY_JAVAFILES);
		((JavaFilePage) javaFileDetailPage).addInstrumentation(instrumentation);
	}

	public void generate(File outputDir) throws Exception {
		Collator collator = new StaticFileCollator(".html");
		report.setCollator(collator);
		Format htmlFormat = new MultiViewStaticHtmlFormat();
		Serializer serializer = new FileSerializer(outputDir);
		collator.addOutputter(htmlFormat, serializer);
		indexPage.close();
	}

	public static String getSourceFileId(String clzName, Instrumentation instrumentation) {
		if (isInnerClass(clzName)) {
			throw new IllegalStateException("Cannot call this method (getSourceFileId) for an inner class");
		}
		String pkgname = ClassHelper.getPackageName(clzName);

		if (instrumentation.getSourceFileName() == null) {
			return clzName;
		}

		if (pkgname.equals("")) {
			return stripJavaSuffix(instrumentation.getSourceFileName());
		} else {
			return pkgname + "." + stripJavaSuffix(instrumentation.getSourceFileName());
		}
	}

	public static String stripJavaSuffix(String s) {
		return s.substring(0, s.length() - ".java".length());
	}

	public static boolean isInnerClass(String clzName) {
		return clzName.indexOf("$") != -1;
	}

	private static File getSourceFile(File[] directories, String sourcePath) {

		if (directories == null || directories.length == 0) {
			return new File(sourcePath);
		}

		for (int i = 0; i < directories.length; i++) {
			File f = new File(directories[i], sourcePath);
			if (f.exists()) {
				return f;
			}
		}

		return new File(sourcePath);
	}

	private static String getOwnerClassNameOf(String innerClassName) {

		if (!isInnerClass(innerClassName)) {
			return innerClassName;
		}

		int index = innerClassName.lastIndexOf('$');
		String ownerClassName = innerClassName.substring(0, index);

		return getOwnerClassNameOf(ownerClassName);
	}

}