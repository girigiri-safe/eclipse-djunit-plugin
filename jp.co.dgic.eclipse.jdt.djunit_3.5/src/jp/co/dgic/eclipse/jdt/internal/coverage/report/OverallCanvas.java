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

import jp.co.dgic.eclipse.jdt.internal.coverage.ui.CoverageReportView;
import jp.co.dgic.eclipse.jdt.internal.coverage.util.CoverageUtil;
import jp.co.dgic.testing.common.coverage.CoverageEntry;

public class OverallCanvas extends CoverageCanvas {

	private static final int[] WIDTHS =
		new int[] { 300, 100, 100, 60, 150, 60, 150 };

	public OverallCanvas(CoverageReportView coverageView) {
		super(coverageView, "Overall");
		init();
	}

	protected void buildContents() {
		CoverageEntry entry = CoverageUtil.getOverallCoverage();
		addRow(entry);
	}

	protected int[] getFieldWidths() {
		return WIDTHS;
	}

	protected void mouseClick(ICoverageLabel label) {
		coverageView.setPackageNameFilter(null);
		coverageView.buildReportView();
	}

}
