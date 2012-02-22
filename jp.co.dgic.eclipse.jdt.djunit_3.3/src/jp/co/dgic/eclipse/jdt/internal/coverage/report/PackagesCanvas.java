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

public class PackagesCanvas extends CoverageCanvas {

	private static final int[] WIDTHS =
		new int[] { 500, 100, 100, 60, 150, 60, 150 };

	public PackagesCanvas(CoverageReportView coverageView) {
		super(coverageView, "packagename");
		init();
	}

	protected void buildContents() {
		Map entries = CoverageUtil.getPackageCoverage();

		for (Iterator it = entries.entrySet().iterator(); it.hasNext();) {

			Map.Entry me = (Map.Entry) it.next();
			CoverageEntry entry = (CoverageEntry) me.getValue();
			if (isFilteredpackage(entry)) {
				continue;
			}

			addRow(entry);
		}

	}

	protected int[] getFieldWidths() {
		return WIDTHS;
	}

	private boolean isFilteredpackage(CoverageEntry entry) {
		String packageName = coverageView.getPackageNameFilter();

		if (packageName == null) {
			return false;
		}

		return !packageName.equals(entry.getName());
	}

	protected void mouseClick(ICoverageLabel label) {
		coverageView.setPackageNameFilter(label.getText());
		coverageView.setCurrentPage(1);
		coverageView.buildReportView();
	}

}
