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
package com.jcoverage.coverage;

import jp.co.dgic.eclipse.jdt.internal.coverage.util.CoverageUtil;
import jp.co.dgic.testing.common.AbstractAsm15xModifier;
import jp.co.dgic.testing.common.asm.AsmClassReader;
import jp.co.dgic.testing.common.asm15x.AsmClassWriter15x;
import jp.co.dgic.testing.common.virtualmock.asm15x.AsmClassChecker15x;

import org.objectweb.asm.ClassWriter;

import com.jcoverage.coverage.asm15x.AsmClassCoverageTargetLineCollector15x;
import com.jcoverage.coverage.asm15x.AsmClassInstrumenter15x;

public class AsmCoverageInstrumenter15x extends AbstractAsm15xModifier {

	public AsmCoverageInstrumenter15x() {
		super("Coverage");
	}

	protected byte[] modify(String className, AsmClassReader cr) throws Exception {

		if (!CoverageUtil.isUseCoverage())  return null;
		if (!CoverageUtil.isIncluded(className)) return null;
		if (CoverageUtil.isExcluded(className)) return null;

// version 0.8.5
		AsmClassCoverageTargetLineCollector15x collector = new AsmClassCoverageTargetLineCollector15x();
		cr.accept(collector);
//		AsmClassChecker15x acc = new AsmClassChecker15x();
//		cr.accept(acc);

//		if (acc.isInterface() || acc.isAnnotation() || acc.isEnum()) return null;

		ClassWriter cw = new AsmClassWriter15x();
		AsmClassInstrumenter15x cv = new AsmClassInstrumenter15x(cw);
		cv.setEnum(collector.isEnum());
		cv.setFinallyLines(collector.getFinallyLines());
		cr.accept(cv);

		InstrumentationInternal i =
			(InstrumentationInternal) InstrumentationFactory
				.getInstance()
				.newInstrumentation(className);

		InstrumentData data = cv.getInstrumentData();
		i.setSourceLineNumbers(data.getSourceLineNumbers());
		i.setSourceFileName(data.getSourceFileName());
		i.setSourceLineNumbersByMethod(data.getMethodLineNumbers());
		i.setConditionalsByMethod(data.getMethodConditionals());
		i.setMethodNamesAndSignatures(
				data.getMethodNamesAndSignatures());

		return cw.toByteArray();
	}

}
