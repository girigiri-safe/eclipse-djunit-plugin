package jp.co.dgic.testing.common.asm15x;

import java.io.IOException;

import jp.co.dgic.testing.common.asm.AsmClassReader;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.attrs.Attributes;

public class AsmClassReader15x extends AsmClassReader {
	
	private static final boolean NO_SKIP_DEBUG = false;

	public AsmClassReader15x(String name) throws IOException {
		super(name);
	}

	public AsmClassReader15x(byte[] bytecodes) {
		super(bytecodes);
	}

	public void accept(ClassVisitor cv) {
		super.accept(cv, Attributes.getDefaultAttributes(), NO_SKIP_DEBUG);
	}
	
}
