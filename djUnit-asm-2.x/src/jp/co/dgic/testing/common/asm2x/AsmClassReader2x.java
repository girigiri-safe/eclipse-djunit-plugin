package jp.co.dgic.testing.common.asm2x;

import java.io.IOException;

import jp.co.dgic.testing.common.asm.AsmClassReader;

import org.objectweb.asm.ClassVisitor;

public class AsmClassReader2x extends AsmClassReader {
	
	private static final boolean SKIP_DEBUG = false;

	public AsmClassReader2x(String name) throws IOException {
		super(name);
	}

	public AsmClassReader2x(byte[] bytecodes) {
		super(bytecodes);
	}

	public void accept(ClassVisitor cv) {
		super.accept(cv, SKIP_DEBUG);
	}
	
}
