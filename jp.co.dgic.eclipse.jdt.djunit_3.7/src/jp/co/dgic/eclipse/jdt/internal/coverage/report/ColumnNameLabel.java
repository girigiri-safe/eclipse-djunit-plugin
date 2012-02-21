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
package jp.co.dgic.eclipse.jdt.internal.coverage.report;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

public class ColumnNameLabel extends CLabel implements ICoverageLabel {

	private static final int STYLE;

	static {
		String osName = System.getProperty("os.name");
		if (osName != null && osName.toLowerCase().indexOf("linux") >= 0) {
			STYLE = SWT.NONE;
		} else {
			STYLE = SWT.CENTER;
		}
	}

	private boolean isTail;

	private static final String FONT_NAME = "Franklin Gothic Medium";
	private static final int FONT_SIZE = 10;
	private static final int FONT_STYLE = SWT.NONE;

	private static final RGB RGB_BACKGROUND = new RGB(220, 236, 255);
	private static final RGB RGB_LINE = new RGB(170, 186, 205);

	private Font font;
	
	public ColumnNameLabel(Composite parent) {
		this(parent, false);
	}

	public ColumnNameLabel(Composite parent, boolean isTail) {
		super(parent, STYLE);

		this.isTail = isTail;

		font = new Font(getDisplay(), new FontData(FONT_NAME, FONT_SIZE, FONT_STYLE));
		setFont(font);
		setBackground(new Color(getDisplay(), RGB_BACKGROUND));

		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				draw(e.gc);
			}
		});
	}

	private void draw(GC gc) {
		gc.setForeground(new Color(getDisplay(), RGB_LINE));
		Rectangle r = getBounds();
		int[] pointArray = {r.width, 0, 0, 0, 0, r.height - 1, r.width, r.height - 1};
		gc.drawPolyline(pointArray);

		if (isTail) {
			gc.drawLine(r.width - 1, 0, r.width - 1, r.height - 1);
		}
	}

	protected void checkSubclass() {
	}

	public Point computeSize(int wHint, int hHint, boolean changed) {
		return getSize();
	}

	public void setLineNumber(int lineNumber) {}

	public void dispose() {
		super.dispose();
		if (font != null) {
			font.dispose();
		}
	}

}
