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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class InstrumentData {

	private String className;
	private String sourceFileName;
	private Set sourceLineNumbers = new HashSet();
	private Map conditionalsByMethod = new HashMap();
	private Map sourceLineNumbersByMethod = new HashMap();
	private Set methodNamesAndSignatures = new TreeSet();

	public InstrumentData(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	public void addSourceNumber(String methodName, String signature, int sourceNumber) {
		Set sourceSet = getSourceNumberByMethodSet(methodName, signature);
		Integer number = new Integer(sourceNumber);
		sourceSet.add(number);
		sourceLineNumbers.add(number);
	}

	public Set getSourceLineNumbers() {
		return sourceLineNumbers;
	}

	public Map getMethodLineNumbers() {
		return sourceLineNumbersByMethod;
	}

	public Map getMethodConditionals() {
		return conditionalsByMethod;
	}

	public Set getMethodNamesAndSignatures() {
		return methodNamesAndSignatures;
	}

	public void addMethodNamesAndSignatures(String methodName, String signature) {
		String key = methodName + signature;
		methodNamesAndSignatures.add(key);
		conditionalsByMethod.put(key, new HashSet());
	}

	public void addConditional(String methodName, String signature, int brunchLine, int targetLine) {
		String key = methodName + signature;
		Set conditionalsSet = (Set) conditionalsByMethod.get(key);
		if (conditionalsSet == null) {
			conditionalsSet = new HashSet();
			conditionalsByMethod.put(key, conditionalsSet);
		}

		conditionalsSet.add(new ConditionalImpl(brunchLine, targetLine));
	}

	private Set getSourceNumberByMethodSet(String methodName, String signature) {
		String key = methodName + signature;
		if (!sourceLineNumbersByMethod.containsKey(key)) {
			sourceLineNumbersByMethod.put(key, new TreeSet());
		}
		return (Set) sourceLineNumbersByMethod.get(key);
	}
}
