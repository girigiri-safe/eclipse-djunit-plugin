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

import jp.co.dgic.testing.common.asm15x.AsmClassWriter15x;
import jp.co.dgic.testing.common.virtualmock.InternalMockObjectManager;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.CodeVisitor;
import org.objectweb.asm.Constants;

public class AsmClassVisitor15x extends AsmClassWriter15x {

	protected AsmClassChecker15x acc;
	protected String className;

	public AsmClassVisitor15x(AsmClassChecker15x acc) {
		this.acc = acc;
	}

	public void visit(int version, int access, String name, String superName, String[] interfaces, String sourceFile) {
		InternalMockObjectManager.printConsole("[modify class] " + name + " " + sourceFile);
		InternalMockObjectManager.printConsole("[class version] " + version);
		this.className = name.replace('/', '.');
		super.visit(version, access, name, superName, interfaces, sourceFile);
	}

	public CodeVisitor visitMethod(int access, String name, String desc, String[] exceptions, Attribute attrs) {
		boolean isStatic = false;
		if ((access & Constants.ACC_STATIC) > 0) {
			isStatic = true;
		}

		InternalMockObjectManager.printConsole("#################################################################");
		InternalMockObjectManager.printConsole("#################################################################");
		InternalMockObjectManager.printConsole("### " + access + (isStatic ? " static " : " ") + name + " " + desc);
		InternalMockObjectManager.printConsole("#################################################################");
		InternalMockObjectManager.printConsole("#################################################################");

		CodeVisitor cv = super.visitMethod(access, name, desc, exceptions, attrs);

		// is abstract or native
		if ((access & Constants.ACC_ABSTRACT) > 0) return cv;
		if ((access & Constants.ACC_NATIVE) > 0) return cv;
		if ((access & Constants.ACC_BRIDGE) > 0) return cv;

		int maxLocals = acc.getMaxLocals(name, desc);

		AbstractAsmMethodVisitor15x amv = createMethodVisitor(cv, name, desc, isStatic, exceptions, maxLocals);
		amv.visitCode();
		return amv;
	}

	protected AbstractAsmMethodVisitor15x createMethodVisitor(CodeVisitor cv, String name, String desc, boolean isStatic, String[] exceptions, int maxLocals) {
		if ("<init>".equalsIgnoreCase(name)) return new AsmConstractorVisitor15x(cv, this.className, name, desc, exceptions, maxLocals, acc.getSuperClassNames());
		return new AsmMethodVisitor15x(cv, this.className, name, desc, isStatic, exceptions, maxLocals, acc.getSuperClassNames());
	}

}
