package jp.co.dgic.testing.common.instrument;

import java.lang.instrument.Instrumentation;

public class InstrumentUtils {

	private static Instrumentation instrumentation;

	public static void premain(String options, Instrumentation instrumentation) {
		System.out.printf("[premain] instrumentation=%s\n", instrumentation);
		InstrumentUtils.instrumentation = instrumentation;
	}
	
	public static void agentmain(String options, Instrumentation instrumentation) {
		InstrumentUtils.instrumentation = instrumentation;
	}
	
	public static Instrumentation getInstrumentation() {
		return instrumentation;
	}
}
