package com.jcoverage.coverage.asm;

import jp.co.dgic.testing.common.virtualmock.asm.AsmEmptyVisitor;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodAdapter;

public class AsmMethodCoverageTargetLineCollector extends MethodAdapter {

	private AsmClassCoverageTargetLineCollector classLineCollector;

	public AsmMethodCoverageTargetLineCollector(AsmClassCoverageTargetLineCollector classLineCollector) {
		super(new AsmEmptyVisitor());
		this.classLineCollector = classLineCollector;
	}

	public void visitLineNumber(final int line, final Label start) {
		classLineCollector.addLineLabel(line, start);
	}

	public void visitTryCatchBlock(final Label start, final Label end, final Label handler, final String type) {
		if (type == null) {
			classLineCollector.addFinallyBlock(handler);
		}
	}
}
