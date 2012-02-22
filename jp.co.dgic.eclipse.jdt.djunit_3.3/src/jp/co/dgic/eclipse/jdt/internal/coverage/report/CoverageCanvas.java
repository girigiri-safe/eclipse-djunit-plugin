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

import java.util.ArrayList;
import java.util.List;

import jp.co.dgic.eclipse.jdt.internal.coverage.ui.CoverageReportView;
import jp.co.dgic.testing.common.coverage.CoverageEntry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;

public abstract class CoverageCanvas extends Canvas {

	protected CoverageReportView coverageView;
	protected String kindName;
	protected static final int LINE_HEIGHT = 18;

	private int textMaxLength;
	private int rowNumber;
	private List nameFields;

	public CoverageCanvas(CoverageReportView coverageView, String kindName) {
		super(coverageView.getReportCanvas(), SWT.NONE);

		this.coverageView = coverageView;
		this.kindName = kindName;
	}

	public void init() {
		nameFields = new ArrayList();

		GridLayout gl = new GridLayout(7, false);
		gl.verticalSpacing = 0;
		gl.horizontalSpacing = 0;
		gl.marginHeight = 0;
		gl.marginWidth = 0;
		setLayout(gl);

		addHeader();

		buildContents();

		adjustWidth();
	}

	protected abstract void buildContents();

	protected void addHeader() {
		ICoverageLabel[] headerLabels = new ColumnNameLabel[7];
		for (int column = 0; column < 7; column++) {
			if (column == 6) {
				headerLabels[column] = new ColumnNameLabel(this, true);
			} else {
				headerLabels[column] = new ColumnNameLabel(this);
			}
			headerLabels[column].setText(getHeaderTitles()[column]);
			headerLabels[column].setSize(
				getFieldWidths()[column],
				LINE_HEIGHT);
		}

		saveTextMaxLength(getHeaderTitles()[0]);
		nameFields.add(headerLabels[0]);
	}

	protected void addRow(CoverageEntry entry) {

		ICoverageLabel[] rowLabels = new ICoverageLabel[7];

		rowLabels[0] = new ClickableFieldLabel(this, SWT.LEFT);
		rowLabels[1] = new FieldLabel(this, SWT.RIGHT);
		rowLabels[2] = new FieldLabel(this, SWT.RIGHT);
		rowLabels[3] = new FieldLabel(this, SWT.RIGHT);

		rowLabels[4] = new IndicatorLabel(this, entry.getLineCoverage());
		rowLabels[5] = new FieldLabel(this, SWT.RIGHT);
		rowLabels[6] = new IndicatorLabel(this, entry.getBranchCoverage());

		saveTextMaxLength(entry.getName());

		rowLabels[0].setText(entry.getName());
		rowLabels[1].setText("" + entry.getFiles());
		rowLabels[2].setText("" + entry.getLines());
		rowLabels[3].setText(entry.getLineCoverageRate() + "%");
		rowLabels[5].setText(entry.getBranchCoverageRate() + "%");

		for (int column = 0; column < 7; column++) {
			rowLabels[column].setSize(getFieldWidths()[column], LINE_HEIGHT);
			rowLabels[column].setLineNumber(rowNumber);
		}

		rowNumber++;

		((ClickableFieldLabel)rowLabels[0]).addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				ICoverageLabel label = (ICoverageLabel) e.getSource();
				mouseClick(label);
			}
		});

		nameFields.add(rowLabels[0]);
	}

	protected abstract int[] getFieldWidths();

	protected abstract void mouseClick(ICoverageLabel label);

	protected String[] getHeaderTitles() {
		return new String[] {
			kindName,
			"files",
			"lines",
			"%line",
			"indicator",
			"%branch",
			"indicator" };
	}

	private void saveTextMaxLength(String text) {
		if (text == null) return;
		if (textMaxLength < text.length()) {
			textMaxLength = text.length();
		}
	}

	private void adjustWidth() {

		if (nameFields.size() == 0) return;

		int fontWidth = getAverageFontWidth();
		int width = fontWidth * textMaxLength;

		ICoverageLabel aLabel = null;
		for (int index = 0; index < nameFields.size(); index++) {
			aLabel = (ICoverageLabel) nameFields.get(index);
			aLabel.setSize(width, LINE_HEIGHT);
		}
	}

	private int getAverageFontWidth() {
		if (nameFields == null || nameFields.size() == 0) return 0;
		ICoverageLabel firstLabel = (ICoverageLabel) nameFields.get(0);
		ICoverageLabel lastLabel = (ICoverageLabel) nameFields.get(nameFields.size() - 1);

		int firstLabelWidth = getFontWidth(firstLabel);
		int lastLabelWidth = getFontWidth(lastLabel);

		return (firstLabelWidth >= lastLabelWidth ? firstLabelWidth : lastLabelWidth) + 1;
	}

	private int getFontWidth(ICoverageLabel label) {
		try {
			GC gc = new GC((Drawable) label);
			return gc.getFontMetrics().getAverageCharWidth();
		} catch (Throwable t) {
		}
		return 0;
	}
}
