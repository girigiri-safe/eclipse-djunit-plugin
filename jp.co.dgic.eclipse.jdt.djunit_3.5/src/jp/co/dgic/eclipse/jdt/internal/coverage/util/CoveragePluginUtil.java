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

import java.io.File;

import jp.co.dgic.eclipse.jdt.internal.junit.ui.DJUnitProjectPropertyPage;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

import com.jcoverage.coverage.Instrumentation;

public class CoveragePluginUtil {

	public static final String COVERAGE_WORK_DIR_KEY =
		"com.jcoverage.rawcoverage.dir";

	public static void setCoverageWorkingDirectory(IProject project) {
		String directoryName =
			DJUnitProjectPropertyPage.readWorkingDirectory(project);

		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

		IResource directory = null;
		if (directoryName != null) {
			directory =
				workspaceRoot.findMember(
					project.getProject().getName() + directoryName);
		}

		System.setProperty(
			COVERAGE_WORK_DIR_KEY,
			directory.getLocation().toString());

	}

	public static void deleteCoverageResults(IProject project) {
		String directoryName =
			DJUnitProjectPropertyPage.readWorkingDirectory(project);

		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

		IResource directory = null;
		if (directoryName != null) {
			directory =
				workspaceRoot.findMember(
					project.getProject().getName() + directoryName);
		}

		File file =
			new File(
				directory.getLocation().toString()
					+ "/"
					+ Instrumentation.FILE_NAME);
		file.delete();
	}
}
