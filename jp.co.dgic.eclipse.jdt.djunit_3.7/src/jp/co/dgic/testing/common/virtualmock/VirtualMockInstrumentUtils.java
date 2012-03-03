package jp.co.dgic.testing.common.virtualmock;

import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.reflect.Method;

import jp.co.dgic.testing.common.DJUnitRuntimeException;
import jp.co.dgic.testing.common.asm.AsmClassReader;
import jp.co.dgic.testing.common.instrument.InstrumentUtils;
import jp.co.dgic.testing.common.virtualmock.annotation.VirtualMockInstrumented;
import jp.co.dgic.testing.common.virtualmock.asm.AsmAdviceImplementer;

public class VirtualMockInstrumentUtils {

	public static void instrument(String className, String methodName) {

		Class<?> cls;
		try {
			cls = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new DJUnitRuntimeException(e);
		}
		if (!hasSuchMethod(cls, methodName))
			throw new DJUnitRuntimeException(cls.getName() + " has no '" + methodName + "' method.");

		Method targetMethod = getTargetMethod(cls, methodName);
		VirtualMockInstrumented ann = targetMethod.getAnnotation(VirtualMockInstrumented.class);
		if (ann != null) {
			return;
		}
		try {
			AsmClassReader cr = AsmClassReader.createAsmClassReader(className);
			AsmAdviceImplementer asmAdviceImplementer = new AsmAdviceImplementer();
			byte[] modifiedClass = asmAdviceImplementer.getModifiedByteCode(className, cr);
			InstrumentUtils.getInstrumentation().redefineClasses(new ClassDefinition(cls, modifiedClass));
		} catch (IOException e) {
			throw new DJUnitRuntimeException(e);
		} catch (Exception e) {
			throw new DJUnitRuntimeException(e);
		}

	}

	private static boolean hasSuchMethod(Class<?> cls, String methodName) {
		Method[] methods = cls.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return true;
			}
		}
		return false;
	}

	private static Method getTargetMethod(Class<?> cls, String methodName) {
		Method[] methods = cls.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		return null;
	}

}
