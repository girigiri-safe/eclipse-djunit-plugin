/**
 * Copyright (C)2004 dGIC Corporation.
 *
 * This file is part of djUnit plugin.
 *
 * djUnit plugin is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * djUnit plugin is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with djUnit plugin; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 */
package com.jcoverage.coverage.asm15x;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.CodeAdapter;
import org.objectweb.asm.CodeVisitor;
import org.objectweb.asm.Constants;
import org.objectweb.asm.Label;

import com.jcoverage.coverage.InstrumentData;

public class AsmMethodInstrumenter15x extends CodeAdapter implements Constants {

	private String className;
	private String methodName;
	private String desc;

	private InstrumentData instrumentData;

	private int currentLineNumber = 0;
	private Map labelMap = new HashMap();
	private Set finallyLines;

	public AsmMethodInstrumenter15x(CodeVisitor cv, String className, String name, String desc,
								InstrumentData instrumentData) {
		super(cv);

		this.instrumentData = instrumentData;
		this.className = className;
		this.methodName = name;
		this.desc = desc;
	}
	
	public void setFinallyLines(Set lines) {
		this.finallyLines = lines;
	}

	public void visitJumpInsn(int opcode, Label label) {
		super.visitJumpInsn(opcode, label);

		if (opcode == GOTO || opcode == JSR || opcode == RET) return;
		labelMap.put(label, new Integer(currentLineNumber));
	}

	public void visitLineNumber(int line, Label start) {

		if (finallyLines.contains(new Integer(line))) {
			super.visitLineNumber(line, start);
			return;
		}

		cv.visitMethodInsn(
				INVOKESTATIC,
				"com/jcoverage/coverage/InstrumentationFactory",
				"getInstance",
				"()Lcom/jcoverage/coverage/InstrumentationFactory;");

		cv.visitLdcInsn(className);
		cv.visitMethodInsn(
				INVOKEVIRTUAL,
				"com/jcoverage/coverage/InstrumentationFactory",
				"newInstrumentation",
				"(Ljava/lang/String;)Lcom/jcoverage/coverage/Instrumentation;");

		cv.visitIntInsn(SIPUSH, line);
		cv.visitMethodInsn(
				INVOKEINTERFACE,
				"com/jcoverage/coverage/Instrumentation",
				"touch",
				"(I)V");

		super.visitLineNumber(line, start);

		currentLineNumber = line;

		if (labelMap.containsKey(start)) {
			Integer n = (Integer) labelMap.get(start);
			instrumentData.addConditional(methodName, desc, n.intValue(), line);
		}

		instrumentData.addSourceNumber(methodName, desc, line);

	}

	public void visitTableSwitchInsn(int min, int max, Label defaultLabel, Label[] labels) {
		super.visitTableSwitchInsn(min, max, defaultLabel, labels);

		instrumentData.addConditional(methodName, desc, -1, currentLineNumber);

		labelMap.put(defaultLabel, new Integer(currentLineNumber));

		for (int i = 0; i < labels.length; i++) {
			labelMap.put(labels[i], new Integer(-1));
		}

	}

//	public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
//		if (type == null) {
//			finallyBlocks.add(handler);
//		}
//		super.visitTryCatchBlock(start, end, handler, type);
//	}

}