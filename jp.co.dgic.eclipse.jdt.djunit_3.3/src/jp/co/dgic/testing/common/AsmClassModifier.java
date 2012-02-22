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
package jp.co.dgic.testing.common;

import jp.co.dgic.testing.common.asm.AsmClassReader;
import jp.co.dgic.testing.common.virtualmock.InternalMockObjectManager;
import jp.co.dgic.testing.common.virtualmock.asm.AsmAdviceImplementer;
import jp.co.dgic.testing.common.virtualmock.asm.AsmClassChecker;

import com.jcoverage.coverage.AsmCoverageInstrumenter;


public class AsmClassModifier implements IClassModifier {

	private AbstractAsmModifier modifier;

	public AsmClassModifier() {
		modifier = new AsmCoverageInstrumenter();
		AsmAdviceImplementer vmoModifier = new AsmAdviceImplementer();
		modifier.setNext(vmoModifier);
	}

	public byte[] getModifiedClass(String className) throws Exception {

		InternalMockObjectManager.printConsole("#################################################");
		InternalMockObjectManager.printConsole("#### found class [" + className + "]");
		InternalMockObjectManager.printConsole("#################################################");

//		AsmClassReader cr = new AsmClassReader(className);
		AsmClassReader cr = AsmClassReader.createAsmClassReader(className);
		AsmClassChecker acc = AsmClassChecker.getInstance(className, cr);
//		AsmClassChecker acc = new AsmClassChecker();
//		cr.accept(acc);

		if (acc.isInterface()) {
			InternalMockObjectManager.printConsole(">>>>> " + className + " is Interface.");
			return null;
		}

		// version 0.8.5
		if (acc.isAnnotation()) {
			InternalMockObjectManager.printConsole(">>>>> " + className + " is Annotation.");
			return null;
		}
		
		if (acc.isTestCase()) {
			InternalMockObjectManager.printConsole(">>>>> " + className + " is TestCase.");
			return null;
		}

		return modifier.getModifiedByteCode(className, cr);
	}

}
