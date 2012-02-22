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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class CoveragemarkerDeleteProcess implements IWorkspaceRunnable {

	private IProject project;

	public CoveragemarkerDeleteProcess(IProject project) {
		this.project = project;
	}

	public void run(IProgressMonitor monitor) throws CoreException {
		try {
			project.deleteMarkers(CoverageMarker.MARKER_ID, true, IResource.DEPTH_INFINITE);
			IProject[] referenceProjects = project.getReferencedProjects();
			if (referenceProjects == null) return;
			for (int index = 0; index < referenceProjects.length; index++) {
				try {
					referenceProjects[index].deleteMarkers(CoverageMarker.MARKER_ID, true, IResource.DEPTH_INFINITE);
				} catch (CoreException e) {
					e.printStackTrace();
					DJUnitPlugin.log(e);
				}
			}
			
		} catch (CoreException e) {
			e.printStackTrace();
			DJUnitPlugin.log(e);
		}
	}

}
