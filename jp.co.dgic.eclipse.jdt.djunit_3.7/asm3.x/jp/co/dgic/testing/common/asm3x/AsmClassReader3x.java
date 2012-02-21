package jp.co.dgic.testing.common.asm3x;

import java.io.IOException;

import jp.co.dgic.testing.common.asm.AsmClassReader;

import org.objectweb.asm.ClassVisitor;

public class AsmClassReader3x extends AsmClassReader {

	private static final int SKIP_FRAMES = 4;

	public AsmClassReader3x(String name) throws IOException {
		super(name);
	}

	public AsmClassReader3x(byte[] bytecodes) {
		super(bytecodes);
	}

	public void accept(ClassVisitor cv) {
		super.accept(cv, SKIP_FRAMES);
	}

}
