package jp.co.dgic.target;

import javax.servlet.jsp.JspException;

public class BTagClass extends ATagClass {
	public int doStartTag() throws JspException {
		return super.doStartTag();
	}

	public void release() {
		super.release();
	}
}