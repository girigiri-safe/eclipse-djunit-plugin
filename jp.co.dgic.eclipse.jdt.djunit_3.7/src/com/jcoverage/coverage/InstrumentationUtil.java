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
package com.jcoverage.coverage;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jp.co.dgic.testing.common.coverage.ConditionLine;

public class InstrumentationUtil {

	public static Map getSourceLineNumbersByMethod(Instrumentation i) {
		return ((InstrumentationImpl)i).getSourceLineNumbersByMethod();
	}

	public static Map getConditionalsByMethod(Instrumentation i) {
		return ((InstrumentationImpl)i).getConditionalsByMethod();
	}

//	public static int getLineNumber(Map conditions, String methodNameAndSignature) {
//		System.out.println(conditions.get(methodNameAndSignature).getClass().getName());
//		Conditional condition =  (Conditional) conditions.get(methodNameAndSignature);
//		return condition.getLineNumber();
//	}
//
//	public static int getTargetLineNumber(Map conditions, String methodNameAndSignature) {
//		Conditional condition =  (Conditional) conditions.get(methodNameAndSignature);
//		return condition.getTargetLineNumber();
//	}

	public static Set getConditionLines(Map conditions, String methodNameAndSignature) {
		Set lines = new HashSet();
		Set conditionLines = (Set) conditions.get(methodNameAndSignature);
		for (Iterator it = conditionLines.iterator(); it.hasNext();) {
			Conditional c = (Conditional) it.next();
			lines.add(new ConditionLine(c.getLineNumber(), c.getTargetLineNumber()));
		}
		return lines;
	}

}
