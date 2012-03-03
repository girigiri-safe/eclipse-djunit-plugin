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

import junit.framework.Assert;

public class MockObjectManager {

	public static void initialize() {
		InternalMockObjectManager.initialize();
	}

	public static void addReturnValue(String className, String methodName, Object returnValue) {

		VirtualMockInstrumentUtils.instrument(className, methodName);

		// InternalMockObjectManager.getTestData().put(
		// makeKey(className, methodName), returnValue);
		InternalMockObjectManager.putTestData(className, methodName, returnValue);
	}

	public static void addReturnValue(Class cls, String methodName, Object returnValue) {
		addReturnValue(cls.getName(), methodName, returnValue);
	}

	public static void addReturnValue(String className, String methodName) {

		addReturnValue(className, methodName, new IgnoreMethodValue());
	}

	public static void addReturnValue(Class cls, String methodName) {

		addReturnValue(cls.getName(), methodName);
	}

	public static void addReturnNull(String className, String methodName) {

		addReturnValue(className, methodName, new NullReturnValue());
	}

	public static void addReturnNull(Class cls, String methodName) {

		addReturnNull(cls.getName(), methodName);
	}

	public static void setReturnValueAt(String className, String methodName, int index, Object returnValue) {
		VirtualMockInstrumentUtils.instrument(className, methodName);
		// InternalMockObjectManager.getTestData().setAt(
		// makeKey(className, methodName), index, returnValue);
		InternalMockObjectManager.setTestDataAt(className, methodName, index, returnValue);
	}

	public static void setReturnValueAt(Class cls, String methodName, int index, Object returnValue) {

		setReturnValueAt(cls.getName(), methodName, index, returnValue);
	}

	public static void setReturnValueAt(String className, String methodName, int index) {

		setReturnValueAt(className, methodName, index, new IgnoreMethodValue());
	}

	public static void setReturnValueAt(Class cls, String methodName, int index) {

		setReturnValueAt(cls.getName(), methodName, index);
	}

	public static void setReturnNullAt(String className, String methodName, int index) {

		setReturnValueAt(className, methodName, index, new NullReturnValue());
	}

	public static void setReturnNullAt(Class cls, String methodName, int index) {

		setReturnNullAt(cls.getName(), methodName, index);
	}

	public static void setReturnValueAtAllTimes(String className, String methodName, Object returnValue) {
		VirtualMockInstrumentUtils.instrument(className, methodName);
		// InternalMockObjectManager.getTestData().putValueAtAllTimes(
		// makeKey(className, methodName), returnValue);
		InternalMockObjectManager.putTestDataAtAllTimes(className, methodName, returnValue);
	}

	public static void setReturnValueAtAllTimes(Class cls, String methodName, Object returnValue) {

		setReturnValueAtAllTimes(cls.getName(), methodName, returnValue);
	}

	public static void setReturnValueAtAllTimes(String className, String methodName) {

		setReturnValueAtAllTimes(className, methodName, new IgnoreMethodValue());
	}

	public static void setReturnValueAtAllTimes(Class cls, String methodName) {

		setReturnValueAtAllTimes(cls.getName(), methodName);
	}

	public static void setReturnNullAtAllTimes(String className, String methodName) {

		setReturnValueAtAllTimes(className, methodName, new NullReturnValue());
	}

	public static void setReturnNullAtAllTimes(Class cls, String methodName) {

		setReturnNullAtAllTimes(cls.getName(), methodName);
	}

	public static Object getReturnValue(String className, String methodName) {
		return getReturnValue(makeKey(className, methodName));
	}

	public static Object getReturnValue(String classAndMethodName) {
		InternalMockObjectManager.printGetReturnValue(classAndMethodName);

		return InternalMockObjectManager.getReturnValue(classAndMethodName);
	}

	public static Object getReturnValue(Class cls, String methodName) {
		return getReturnValue(cls.getName(), methodName);
	}

	public static void assertCalled(String className, String methodName) {

		if (!isCalled(className, methodName))
			Assert.fail("The method '" + methodName + "' in class '" + className + "' was expected to be called but it wasn't");
	}

	public static void assertCalled(Class cls, String methodName) {

		assertCalled(cls.getName(), methodName);

	}

	public static void assertNotCalled(String className, String methodName) {

		if (isCalled(className, methodName))
			Assert.fail("The method '" + methodName + "' in class '" + className + "' was expected to be not called but it was");
	}

	public static void assertNotCalled(Class cls, String methodName) {

		assertNotCalled(cls.getName(), methodName);
	}

	public static int getCallCount(String className, String methodName) {
		// return InternalMockObjectManager.getCallsMade().size(
		// makeKey(className, methodName));
		return InternalMockObjectManager.getCallCount(className, methodName);
	}

	public static int getCallCount(Class cls, String methodName) {
		return getCallCount(cls.getName(), methodName);
	}

	public static Object getArgument(String className, String methodName, int argumentIndex) {
		return getArgument(className, methodName, 0, argumentIndex);
	}

	public static Object getArgument(Class cls, String methodName, int argumentIndex) {
		return getArgument(cls.getName(), methodName, argumentIndex);
	}

	public static Object getArgument(String className, String methodName, int methodIndex, int argumentIndex) {

		Object argument = null;
		// Object[] arguments = (Object[]) InternalMockObjectManager
		// .getCallsMade()
		// .get(makeKey(className, methodName), methodIndex);
		Object[] arguments = (Object[]) InternalMockObjectManager.getArgument(className, methodName, methodIndex);
		if (arguments != null) {
			argument = arguments[argumentIndex];
		}
		return argument;
	}

	public static Object getArgument(Class cls, String methodName, int methodIndex, int argumentIndex) {

		return getArgument(cls.getName(), methodName, methodIndex, argumentIndex);
	}

	public static boolean isCalled(String className, String methodName) {
		// return InternalMockObjectManager.getCallsMade().get(
		// makeKey(className, methodName)) != null;
		return InternalMockObjectManager.isCalled(className, methodName);
	}

	public static boolean isCalled(Class cls, String methodName) {
		return isCalled(cls.getName(), methodName);
	}

	public static void assertArgumentPassed(String className, String methodName, int argumentIndex, Object argumentValue) {

		Object argument = getArgument(className, methodName, argumentIndex);
		if (argument == null && argumentValue == null)
			return;
		if (argument == null || !argument.equals(argumentValue))
			Assert.fail("The argument index[" + argumentIndex + "] of method '" + methodName + "' in class '" + className + "' should have the value '" + argumentValue + "' but it was '" + argument + "'!");
	}

	public static void assertArgumentPassed(Class cls, String methodName, int argumentIndex, Object argumentValue) {

		assertArgumentPassed(cls.getName(), methodName, argumentIndex, argumentValue);
	}

	private static String makeKey(String className, String methodName) {
		return className + "." + methodName;
	}

}