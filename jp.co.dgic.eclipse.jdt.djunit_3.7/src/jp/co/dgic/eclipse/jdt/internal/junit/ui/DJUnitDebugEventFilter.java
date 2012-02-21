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
package jp.co.dgic.eclipse.jdt.internal.junit.ui;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.IDebugEventFilter;
import org.eclipse.jdt.internal.debug.core.model.JDIDebugTarget;
import org.eclipse.jdt.internal.debug.core.model.JDIThread;

public class DJUnitDebugEventFilter implements IDebugEventFilter {

	private static final String[] DJUNIT_FILTER_PATTERNS = new String[] {
		"jp.co.dgic.testing.*", "com.jcoverage.*", "junit.*",
		"org.eclipse.jdt.internal.junit.*",
		"java.*", "sun.*"
	};

	public DebugEvent[] filterDebugEvents(DebugEvent[] events) {

		if (!DJUnitPlugin.getDefault().isDJUnitTest()) return events;

		if (events == null || events.length == 0) return events;

		for (int i = 0; i < events.length; i++) {
			Object src = events[i].getSource();
			if (!(src instanceof JDIThread)) continue;
			if (!(((JDIThread) src).getDebugTarget() instanceof JDIDebugTarget)) continue;
			JDIDebugTarget target = (JDIDebugTarget) ((JDIThread) src).getDebugTarget();

			target.setStepFiltersEnabled(true);
			target.setStepFilters(addDJUnitFilterPatterns(target.getStepFilters()));

		}

		return events;
	}

	private String[] addDJUnitFilterPatterns(String[] patterns) {
		if (patterns == null || patterns.length == 0) { return DJUNIT_FILTER_PATTERNS; }
		if (containDJUnitPatterns(patterns)) { return patterns; }

		String[] newPatterns = new String[patterns.length + DJUNIT_FILTER_PATTERNS.length];

		System.arraycopy(patterns, 0, newPatterns, 0, patterns.length);

		for (int i = 0; i < DJUNIT_FILTER_PATTERNS.length; i++) {
			newPatterns[i + patterns.length] = DJUNIT_FILTER_PATTERNS[i];
		}

		return newPatterns;
	}

	private boolean containDJUnitPatterns(String[] patterns) {

		if (patterns == null || DJUNIT_FILTER_PATTERNS.length == 0) return false;

		for (int i = 0; i < DJUNIT_FILTER_PATTERNS.length; i++) {
			boolean isFound = false;
			for (int ii = 0; ii < patterns.length; ii++) {
				if (DJUNIT_FILTER_PATTERNS[i].equals(patterns[ii])) {
					isFound = true;
					break;
				}
			}
			if (!isFound) return false;
		}
		return true;
	}

}