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

import java.util.HashMap;
import java.util.Map;

import jp.co.dgic.eclipse.jdt.internal.junit.ui.DJUnitMessages;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

public class MarkerCreator implements IWorkspaceRunnable {

    private IResource resource;
    private Integer lineNumber;
    private int startOffset;
    private int endOffset;

    public MarkerCreator(IResource resource, Integer lineNumber, int startOffset, int endOffset) {
        this.resource = resource;
        this.lineNumber = lineNumber;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    public void run(IProgressMonitor monitor) throws CoreException {
        createMarkerToLine();
    }

    private void createMarkerToLine() throws CoreException {

        IMarker marker = resource.createMarker(CoverageMarker.MARKER_ID);
        Map attributes = new HashMap();
        attributes.put(IMarker.MESSAGE, DJUnitMessages.getFormattedString("MarkerUtil.message.nohits", lineNumber));
        attributes.put(IMarker.LINE_NUMBER, lineNumber);
        attributes.put(IMarker.PRIORITY, new Integer(IMarker.PRIORITY_HIGH));
        attributes.put(IMarker.USER_EDITABLE, new Boolean(false));

        if (startOffset != -1 && endOffset != -1) {
            attributes.put(IMarker.CHAR_START, new Integer(startOffset));
            attributes.put(IMarker.CHAR_END, new Integer(endOffset));
        }

        marker.setAttributes(attributes);
    }

}