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
package jp.co.dgic.eclipse.jdt.internal.coverage.report;

import java.util.Iterator;
import java.util.Map;

import jp.co.dgic.eclipse.jdt.internal.coverage.ui.CoverageReportView;
import jp.co.dgic.eclipse.jdt.internal.coverage.util.CoverageUtil;
import jp.co.dgic.testing.common.coverage.CoverageEntry;
import jp.co.dgic.testing.common.util.DJUnitUtil;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.ui.javaeditor.EditorUtility;

public class FilesCanvas extends CoverageCanvas {

	private static final int[] WIDTHS =
		new int[] { 600, 0, 100, 60, 150, 60, 150 };

	private int minLineNumber = -1;
	private int maxLineNumber = -1;

	public FilesCanvas(CoverageReportView coverageView, int minLineNumber, int maxLineNumber) {
		super(coverageView, "file");
		this.minLineNumber = minLineNumber;
		this.maxLineNumber = maxLineNumber;
		init();
	}

	protected void buildContents() {

		int lineCount = -1;
		Map entries = CoverageUtil.getFileCoverage();
		for (Iterator it = entries.entrySet().iterator(); it.hasNext();) {

			Map.Entry me = (Map.Entry) it.next();
			CoverageEntry entry = (CoverageEntry) me.getValue();

			if (isFilteredPackage(entry)) continue;

			lineCount++;
			if (lineCount >= maxLineNumber) break;
			if (lineCount < minLineNumber) continue;

			addRow(entry);
		}
	}

	private boolean isFilteredPackage(CoverageEntry entry) {

		String packageName = coverageView.getPackageNameFilter();

		if (packageName == null) {
			return false;
		}

		if (packageName.equalsIgnoreCase("default")) {
			if (entry.getName().indexOf('.') >= 0) {
				return true;
			}
			return false;
		}

		return !entry.getName().startsWith(packageName + ".");
	}

	protected int[] getFieldWidths() {
		return WIDTHS;
	}

	protected void mouseClick(ICoverageLabel label) {
		IJavaProject project = coverageView.getJavaProject();
		if (project == null) {
			return;
		}
		try {
			// findType ...
			IJavaElement element = project.findType(DJUnitUtil.getJavaProjectType(label.getText()));
			EditorUtility.openInEditor(element, true);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
