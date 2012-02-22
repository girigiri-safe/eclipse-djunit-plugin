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
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class IndicatorLabel extends Canvas implements ICoverageLabel, PaintListener {

	private static final RGB RGB_GREEN = new RGB(0, 240, 0);
	private static final RGB RGB_RED = new RGB(224, 0, 0);
	private static final RGB RGB_GREEN_SHADOW = new RGB(0, 128, 0);
	private static final RGB RGB_RED_SHADOW = new RGB(128, 0, 0);

	private static final int MARGIN = 1;

	private double coverage;
	private RGB backgroundRGB;

	public IndicatorLabel(Composite parent, double coverage) {
		super(parent, SWT.NONE);

		this.coverage = coverage;

		GridLayout gl = new GridLayout(2, false);
		gl.verticalSpacing = 0;
		gl.horizontalSpacing = 0;
		gl.marginHeight = MARGIN;
		gl.marginWidth = MARGIN;
		setLayout(gl);

		addPaintListener(this);
	}

	public void setLineNumber(int lineNumber) {
		if ((lineNumber % 2) == 0) {
			backgroundRGB = RGB_LINE_BACKGROUND;
		} else {
			backgroundRGB = RGB_BACKGROUND;
		}
		setBackground(new Color(getDisplay(), backgroundRGB));
	}

	protected void checkSubclass() {
	}

	public Point computeSize(int wHint, int hHint, boolean changed) {
		return getSize();
	}

	public String getText() {
		return "";
	}

	public void setText(String text) {
		return;
	}

	public void paintControl(PaintEvent e) {
		drawRed(e.gc);
		drawGreen(e.gc);
	}

	private void drawRed(GC gc) {

		Rectangle r = getBounds();

		gc.setBackground(new Color(getDisplay(), RGB_RED));
		gc.fillRectangle(MARGIN, 1, r.width - MARGIN, r.height - 2);

		gc.setForeground(new Color(getDisplay(), RGB_RED_SHADOW));
		int[] pointArray = {r.width - 1, 1, r.width - 1, r.height - 1, 2, r.height - 1};
		gc.drawPolyline(pointArray);
	}

	private void drawGreen(GC gc) {

		Rectangle r = getBounds();
		int greenWidth = (int) (r.width * coverage);

		if (greenWidth == 0) return;

		gc.setBackground(new Color(getDisplay(), RGB_GREEN));
		gc.fillRectangle(0, 1, greenWidth, r.height - 2);

		gc.setForeground(new Color(getDisplay(), RGB_GREEN_SHADOW));
		int[] pointArray = {greenWidth - 1, 1, greenWidth - 1, r.height - 1, 2, r.height - 1};
		gc.drawPolyline(pointArray);

	}
}
