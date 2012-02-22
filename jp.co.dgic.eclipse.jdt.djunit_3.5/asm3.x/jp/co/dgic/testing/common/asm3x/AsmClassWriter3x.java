package jp.co.dgic.testing.common.asm3x;

import org.objectweb.asm.ClassWriter;

public class AsmClassWriter3x extends ClassWriter {
	private static final int COMPUTE_MAXS = 1;

	public AsmClassWriter3x() {
		super(COMPUTE_MAXS);
	}
}
