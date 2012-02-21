package com.jcoverage.coverage.asm15x;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jp.co.dgic.testing.common.virtualmock.asm15x.AsmEmptyVisitor15x;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.CodeVisitor;
import org.objectweb.asm.Constants;
import org.objectweb.asm.Label;

public class AsmClassCoverageTargetLineCollector15x extends ClassAdapter {

	private boolean isEnum = false;
	private Map lineNumberLabels = new HashMap();
	private Set finallyBlocks = new HashSet();

	public AsmClassCoverageTargetLineCollector15x() {
		super(new AsmEmptyVisitor15x());
	}

	public boolean isEnum() {
		return isEnum;
	}
	
	public void visit(int version, int access, String name, String superName, String[] interfaces, String sourceFile) {
		isEnum = (access & Constants.ACC_ENUM) > 0;
	}

	public CodeVisitor visitMethod(int access, String name, String desc, String[] exceptions, Attribute attrs) {
		return new AsmMethodCoverageTargetLineCollector15x(this);
	}

	public void addLineLabel(int line, Label start) {
		lineNumberLabels.put(start.toString(), new Integer(line));
	}

	public void addFinallyBlock(Label handler) {
		finallyBlocks.add(handler.toString());
	}

	public Set getFinallyLines() {
		Set lines = new HashSet();
		String[] blocks = (String[]) finallyBlocks.toArray(new String[finallyBlocks.size()]);
		for (int i = 0; i < blocks.length; i++) {
			Integer line = (Integer) lineNumberLabels.get(blocks[i]);
			if (line != null) {
				lines.add(line);
			}
		}
		return lines;
	}
}
