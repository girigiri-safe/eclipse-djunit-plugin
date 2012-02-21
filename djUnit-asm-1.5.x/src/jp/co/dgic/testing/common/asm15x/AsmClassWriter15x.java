package jp.co.dgic.testing.common.asm15x;

import org.objectweb.asm.ClassWriter;

public class AsmClassWriter15x extends ClassWriter {
	
	private static final boolean COMPUTE_MAXS = true;

	public AsmClassWriter15x() {
		super(COMPUTE_MAXS);
	}

}
