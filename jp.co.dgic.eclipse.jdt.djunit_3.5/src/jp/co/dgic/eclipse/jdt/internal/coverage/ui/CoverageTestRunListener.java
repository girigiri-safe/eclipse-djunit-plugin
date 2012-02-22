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
package jp.co.dgic.eclipse.jdt.internal.coverage.ui;

import jp.co.dgic.eclipse.jdt.internal.junit.ui.DJUnitPlugin;

import org.eclipse.jdt.junit.ITestRunListener;
import org.eclipse.swt.widgets.Display;

public class CoverageTestRunListener implements ITestRunListener {

	public void testRunEnded(long elapsedTime) {

		if (!DJUnitPlugin.getDefault().isDJUnitTest()) return;

		DJUnitPlugin.getDefault().shutDownClient();

		boolean useMarker = true;

		// update coverage view
		CoverageReportView coverageView = DJUnitPlugin.getCoverageReportView();
		if (coverageView != null) {
			coverageView.asyncBuildView();
			useMarker = coverageView.getUseCoverageMarker();
		}

		// update coverage marker
		if (useMarker) {
			Display.getDefault().asyncExec(new UpdateCoverageMarkerThread());
		}
	}

	private void updateCoverageMarker() {
		CoverageReportView coverageView = DJUnitPlugin.getCoverageReportView();
		if (coverageView == null) return;
		coverageView.updateCoverageMarker();
	}

	private class UpdateCoverageMarkerThread implements Runnable {
		public void run() {
			updateCoverageMarker();
		}
	}

	public void testRunStarted(int testCount) {
	}

	public void testEnded(String testId, String testName) {
	}

	public void testFailed(
		int status,
		String testId,
		String testName,
		String trace) {
	}

	public void testReran(
		String testId,
		String testClass,
		String testName,
		int status,
		String trace) {
	}

	public void testRunStopped(long elapsedTime) {
		if (!DJUnitPlugin.getDefault().isDJUnitTest()) return;
		DJUnitPlugin.getDefault().shutDownClient();
	}

	public void testRunTerminated() {
		if (!DJUnitPlugin.getDefault().isDJUnitTest()) return;
		DJUnitPlugin.getDefault().shutDownClient();
	}

	public void testStarted(String testId, String testName) {
	}

}
