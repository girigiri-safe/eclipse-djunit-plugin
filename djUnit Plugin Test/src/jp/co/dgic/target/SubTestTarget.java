package jp.co.dgic.target;

import java.util.Hashtable;

public class SubTestTarget extends TestTarget {
	
	/* (non-Javadoc)
	 * @see jp.co.dgic.target.ITestTarget#getField3()
	 */
	public Hashtable getField3() {
		return super.getField3();
	}
	
	public int getField4() {
		return 0;
	}
	
	public int getField5() throws VirtualException {
		if (false) throw new VirtualException();
		return 0;
	}

}
