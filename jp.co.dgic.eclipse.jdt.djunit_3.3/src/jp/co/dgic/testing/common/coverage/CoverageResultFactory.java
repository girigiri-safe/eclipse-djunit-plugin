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
package jp.co.dgic.testing.common.coverage;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.Map;

import com.jcoverage.coverage.InstrumentationPersistence;

public class CoverageResultFactory extends InstrumentationPersistence {
	
	private static final int RELOAD_TRY_COUNT = 5;

	private static CoverageResultFactory factory = new CoverageResultFactory();

	protected CoverageResultFactory() {
		super();
	}

	public static CoverageResultFactory getInstance() {
		return factory;
	}

	public Map getInstrumentation() {
		Map m = null;
		for (int count = 0; count < RELOAD_TRY_COUNT; count++) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			m = factory.loadInstrumentation();
			if (m != null) break;
		}
		if (m == null) return Collections.EMPTY_MAP;
		return m;
	}

	protected Map loadInstrumentation(InputStream is) {
		ObjectInputStream objects = null;
		try {
			objects = new ObjectInputStream(is);
			Map m = (Map) objects.readObject();
			return m;
		} catch (EOFException eofe) {
			return null;
		} catch (ClassNotFoundException ex) {
			return Collections.EMPTY_MAP;
		} catch (IOException ex) {
			return Collections.EMPTY_MAP;
		} finally {
			if (objects != null) {
				try {
					objects.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

			if (is != null) {
				try {
					is.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

}
