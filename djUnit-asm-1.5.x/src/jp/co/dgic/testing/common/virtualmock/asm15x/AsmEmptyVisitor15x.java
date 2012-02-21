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
package jp.co.dgic.testing.common.virtualmock.asm15x;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.CodeVisitor;

public class AsmEmptyVisitor15x implements ClassVisitor, CodeVisitor {

	public void visit(int version, int access, String name, String superName, String[] interfaces, String sourceFile) {}

	public void visitSource(String source, String debug) {}

	public void visitOuterClass(String owner, String name, String desc) {}

	public void visitAttribute(Attribute attr) {}

	public void visitInnerClass(String name, String outerName, String innerName, int access) {}

	public void visitField(int access, String name, String desc, Object value, Attribute attrs) {
	}

	public CodeVisitor visitMethod(int access, String name, String desc, String[] exceptions, Attribute attrs) {
		return this;
	}

	public void visitEnd() {}

	public void visitCode() {}

	public void visitInsn(int opcode) {}

	public void visitIntInsn(int opcode, int operand) {}

	public void visitVarInsn(int opcode, int var) {}

	public void visitTypeInsn(int opcode, String desc) {}

	public void visitFieldInsn(int opcode, String owner, String name, String desc) {}

	public void visitMethodInsn(int opcode, String owner, String name, String desc) {}

	public void visitJumpInsn(int opcode, Label label) {}

	public void visitLabel(Label label) {}

	public void visitLdcInsn(Object cst) {}

	public void visitIincInsn(int var, int increment) {}

	public void visitTableSwitchInsn(int min, int max, Label dflt, Label labels[]) {}

	public void visitLookupSwitchInsn(Label dflt, int keys[], Label labels[]) {}

	public void visitMultiANewArrayInsn(String desc, int dims) {}

	public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {}

	public void visitLocalVariable(String name, String desc, Label start, Label end, int index) {}

	public void visitLineNumber(int line, Label start) {}

	public void visitMaxs(int maxStack, int maxLocals) {}

	public void visit(String name, Object value) {}

	public void visitEnum(String name, String desc, String value) {}

	public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {}
}