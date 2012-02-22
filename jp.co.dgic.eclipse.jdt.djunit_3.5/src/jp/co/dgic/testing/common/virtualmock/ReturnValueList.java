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
package jp.co.dgic.testing.common.virtualmock;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import jp.co.dgic.testing.common.DJUnitRuntimeException;

class ReturnValueList {
    
    private static final String MESSAGE_VALUE_IS_NULL = "Return value must be NOT null.";

	protected Hashtable valueTable = new Hashtable();
	protected Hashtable valueAtAllTimesTable = new Hashtable();

	public void clear() {
		valueTable.clear();
		valueAtAllTimesTable.clear();
	}

	public Object get(String key) {

		return get(key, true);
	}

	public Object get(String key, boolean isRemove) {

		Object valueAtAllTimes = getValueAtAllTimes(key);
		if (valueAtAllTimes != null) {
			return valueAtAllTimes;
		}

		List values = getValueList(key);
		if (values == null) return null;
		if (values.isEmpty()) return null;

		Object value = values.get(0);
		if (isRemove) {
		    values.remove(0);
		}
		return value;
	}

	public void put(String key, Object value) {
	    
	    throwReturnValueMustBeNotNullException(key, value);
	    
		List values = getValueList(key);
		if (values == null) {
			values = new ArrayList();
		}

		values.add(value);

		valueTable.put(key, values);
	}

	public void setAt(String key, int index, Object value) {
	    
	    throwReturnValueMustBeNotNullException(key, value);
	    
		List values = getValueList(key);
		if (values == null) {
			values = new ArrayList();
		}

		int size = values.size();
		int maxIndex = size - 1;
		if (maxIndex < index) {
			for (int i = size; i <= index; i++) {
				values.add(null);
			}
		}
		values.set(index, value);

		valueTable.put(key, values);
	}

	public Enumeration keys() {
		return valueTable.keys();
	}

	protected List getValueList(String key) {
		List values = getValueListByFullName(key);
		if (values == null) {
			values = getValueListBySimpleName(key);
		}
		return values;
	}

	protected List getValueListByFullName(String key) {
		if (!valueTable.containsKey(key)) {
			return null;
		}
		return (List) valueTable.get(key);
	}

	protected List getValueListBySimpleName(String key) {
		Enumeration enum = valueTable.keys();
	
		String keyString = null;
		while (enum.hasMoreElements()) {
			keyString = (String) enum.nextElement();
			if (key.endsWith("." + keyString)) {
				return getValueListByFullName(keyString);
			}
		}
	
		return null;
	}

	protected Object getValueAtAllTimes(String key) {
		Object value = valueAtAllTimesTable.get(key);
		if (value == null) {
			value = getValueAtAllTimesBySimpleName(key);
		}
		return value;
	}

	protected Object getValueAtAllTimesBySimpleName(String key) {
		Enumeration enum = valueAtAllTimesTable.keys();
	
		String keyString = null;
		while (enum.hasMoreElements()) {
			keyString = (String) enum.nextElement();
			if (key.endsWith("." + keyString)) {
				return valueAtAllTimesTable.get(keyString);
			}
		}
	
		return null;
	}

	public void putValueAtAllTimes(String key, Object value) {
	    throwReturnValueMustBeNotNullException(key, value);
		valueAtAllTimesTable.put(key, value);
	}
	
	private void throwReturnValueMustBeNotNullException(String key, Object value) {
	    if (value == null) throw new DJUnitRuntimeException(MESSAGE_VALUE_IS_NULL + "[" + key + "]");
	}

}
