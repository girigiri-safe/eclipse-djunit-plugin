package jp.co.dgic.testing.common.asm2x;

import org.objectweb.asm.ClassWriter;

public class AsmClassWriter2x extends ClassWriter {
	
	private static final boolean COMPUTE_MAXS = true;

	public AsmClassWriter2x() {
		super(COMPUTE_MAXS);
	}

}
