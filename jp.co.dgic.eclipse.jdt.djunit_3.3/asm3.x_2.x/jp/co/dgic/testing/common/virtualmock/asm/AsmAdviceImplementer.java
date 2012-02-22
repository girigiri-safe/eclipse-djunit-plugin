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
package jp.co.dgic.testing.common.virtualmock.asm;

import java.io.IOException;

import jp.co.dgic.testing.common.AbstractAsmModifier;
import jp.co.dgic.testing.common.asm.AsmClassReader;
import jp.co.dgic.testing.common.virtualmock.VirtualMockUtil;

import org.objectweb.asm.ClassWriter;

public class AsmAdviceImplementer extends AbstractAsmModifier {

	protected static final String CONSTRUCTOR_METHOD_NAME = "<init>";
	protected static final String MANAGER_PACKAGE_NAME = "jp.co.dgic.testing.common.virtualmock.";
	protected static final String MANAGER_CLASS_NAME = MANAGER_PACKAGE_NAME + "InternalMockObjectManager";

	public AsmAdviceImplementer() {
		super("VirtualMockObjects");
	}

	protected byte[] modify(String className, AsmClassReader cr) throws IOException {

		if (!VirtualMockUtil.isUseVirtualMock()) return null;
		if (!VirtualMockUtil.isInclude(className)) return null;

		// version 0.8.5
		AsmClassChecker acc = AsmClassChecker.getInstance(className, cr);
//		AsmClassChecker acc = new AsmClassChecker();
//		cr.accept(acc);

		// version 0.8.5
//		if (acc.isInterface() || acc.isAnnotation() || acc.isEnum()) return null;
//		if (acc.isTestCase()) {
//			InternalMockObjectManager.printConsole("class [" + className + "] is TestCase");
//			return null;
//		}

//		AsmClassVisitor asv = new AsmClassVisitor(acc);
		ClassWriter asv = AsmClassReader.createClassVisitor(acc);
		cr.accept(asv);
		return asv.toByteArray();
	}

}
