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
import java.util.Set;

import jp.co.dgic.eclipse.jdt.internal.coverage.ui.CoveragemarkerDeleteProcess;
import jp.co.dgic.eclipse.jdt.internal.coverage.ui.MarkerCreator;
import jp.co.dgic.eclipse.jdt.internal.junit.ui.DJUnitMessages;
import jp.co.dgic.eclipse.jdt.internal.junit.ui.DJUnitPlugin;
import jp.co.dgic.testing.common.coverage.CoverageResultFactory;
import jp.co.dgic.testing.common.util.DJUnitUtil;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;

import com.jcoverage.coverage.Instrumentation;

public class MarkerUtil {

	public static IStatus setCoverageMark(IJavaProject javaProject) {
		return setCoverageMark(javaProject, null);
	}

	public static IStatus setCoverageMark(IJavaProject javaProject, IProgressMonitor monitor) {

		CoveragePluginUtil.setCoverageWorkingDirectory(javaProject.getProject());

		clearCoverageMarker(javaProject.getProject());

		Map m = CoverageResultFactory.getInstance().getInstrumentation();
		Iterator coverageKeys = m.keySet().iterator();

		int classCount = 1;
		int totalClass = m.size();

		String message = DJUnitMessages.getString("MarkerUtil.message.marking");

		if (monitor != null) {
			monitor.beginTask(message + " (" + totalClass + " classes)", totalClass);
		}

		while (coverageKeys.hasNext()) {
			if (monitor != null) {
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				monitor.setTaskName(message + " (" + classCount + "/" + totalClass + ")");
			}
			String key = (String) coverageKeys.next();
			Instrumentation i = (Instrumentation) m.get(key);
			try {
				String javaProjectType = DJUnitUtil.getJavaProjectType(key);
				IType type = javaProject.findType(javaProjectType);
				setMarker(type.getCompilationUnit(), type.getResource(), i, monitor);
			} catch (CoreException ce) {
				ce.printStackTrace();
			} catch (Throwable t) {
				DJUnitPlugin.log(new RuntimeException("[catch Throwable] at Key = " + key));
				DJUnitPlugin.log(t);
			}
			classCount++;
			if (monitor != null) {
				monitor.worked(1);
			}
		}

		if (monitor == null) {
			monitor.done();
		}
		return Status.OK_STATUS;
	}

	public static void clearCoverageMarker(IProject project) {
		try {
			ResourcesPlugin.getWorkspace().run(new CoveragemarkerDeleteProcess(project), null);
		} catch (CoreException e) {
			e.printStackTrace();
			DJUnitPlugin.log(e);
		}
	}

	private static void setMarker(ICompilationUnit unit, IResource resource, Instrumentation i, IProgressMonitor monitor) throws CoreException {

		Map coverage = i.getCoverage();
		Set lineNumbers = i.getSourceLineNumbers();

		Map ranges = getLineRanges(unit);

		Iterator it = lineNumbers.iterator();
		int lineCount = 1;
		int totalLine = lineNumbers.size();
		while (it.hasNext()) {
			if (monitor != null) {
				if (monitor.isCanceled()) {
					break;
				}
				monitor.subTask(i.getSourceFileName() + "(" + lineCount + "/" + totalLine + ")");
			}
			Integer lineNumber = (Integer) it.next();
			if (coverage.containsKey(lineNumber)) {
				continue;
			}

			LineOffsetRange r = (LineOffsetRange) ranges.get(lineNumber);
			if (r == null) {
				r = new LineOffsetRange(-1, -1);
			}

			ResourcesPlugin.getWorkspace().run(new MarkerCreator(resource, lineNumber, r.getStartOffset(), r.getEndOffset()), null);

			lineCount++;
		}
	}

	private static Map getLineRanges(ICompilationUnit unit) throws JavaModelException {
		Document doc = new Document(unit.getBuffer().getContents());
		int numberOfLines = doc.getNumberOfLines();
		Map ranges = new HashMap();
		for (int line = 0; line < numberOfLines; line++) {
			LineOffsetRange range = null;
			try {
				int offset = doc.getLineOffset(line - 1);
				int length = doc.getLineLength(line - 1);
				range = new LineOffsetRange(offset, offset + length);
			} catch (BadLocationException e) {
				range = new LineOffsetRange(-1, -1);
			}
			ranges.put(new Integer(line), range);
		}
		return ranges;
	}

	static class LineOffsetRange {
		private int startOffset;

		private int endOffset;

		public LineOffsetRange(int startOffset, int endOffset) {
			this.startOffset = startOffset;
			this.endOffset = endOffset;
		}

		public int getStartOffset() {
			return startOffset;
		}

		public int getEndOffset() {
			return endOffset;
		}
	}

}