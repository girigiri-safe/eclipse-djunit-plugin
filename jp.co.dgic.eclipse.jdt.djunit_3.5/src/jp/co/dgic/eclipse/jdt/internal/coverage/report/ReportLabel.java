package jp.co.dgic.eclipse.jdt.internal.coverage.report;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ReportLabel extends Label {
	
	private Font font;

	public ReportLabel(Composite parent, int style) {
		super(parent, style);
	}

	protected void checkSubclass() {
	}

	public void setFont(Font font) {
		super.setFont(font);
		this.font = font;
	}

	public void dispose() {
		super.dispose();
		if (font != null) {
			font.dispose();
		}
	}

}
