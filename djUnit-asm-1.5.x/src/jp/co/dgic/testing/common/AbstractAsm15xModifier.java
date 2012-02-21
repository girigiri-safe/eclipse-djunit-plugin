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
import jp.co.dgic.testing.common.asm15x.AsmClassReader15x;

public abstract class AbstractAsm15xModifier {

	private String name;
	private AbstractAsm15xModifier nextModifier;
	
	public AbstractAsm15xModifier(String name) {
		this.name = name;
	}

	public AbstractAsm15xModifier setNext(AbstractAsm15xModifier nextModifier) {
		this.nextModifier = nextModifier;
		return nextModifier;
	}

	public String getName() {
		return name;
	}

	public byte[] getModifiedByteCode(String className, AsmClassReader cr) throws Exception {

		byte[] modifiedByteCode = modify(className, cr);

		if (modifiedByteCode != null) {
			cr = new AsmClassReader15x(modifiedByteCode);
		}

		if (nextModifier == null) {
			return cr.b;
		}

		return nextModifier.getModifiedByteCode(className, cr);
	}

	protected abstract byte[] modify(String className, AsmClassReader cr) throws Exception;
}
