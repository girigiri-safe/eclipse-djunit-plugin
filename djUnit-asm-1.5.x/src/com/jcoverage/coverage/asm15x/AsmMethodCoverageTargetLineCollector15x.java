package com.jcoverage.coverage.asm15x;

import jp.co.dgic.testing.common.virtualmock.asm15x.AsmEmptyVisitor15x;

import org.objectweb.asm.CodeAdapter;
import org.objectweb.asm.Label;

public class AsmMethodCoverageTargetLineCollector15x extends CodeAdapter {

	private AsmClassCoverageTargetLineCollector15x classLineCollector;

	public AsmMethodCoverageTargetLineCollector15x(AsmClassCoverageTargetLineCollector15x classLineCollector) {
		super(new AsmEmptyVisitor15x());
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
