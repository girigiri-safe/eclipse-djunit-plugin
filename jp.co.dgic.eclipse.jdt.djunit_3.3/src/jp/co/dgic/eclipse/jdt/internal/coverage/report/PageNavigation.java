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

import jp.co.dgic.eclipse.jdt.internal.coverage.ui.CoverageReportView;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public class PageNavigation extends Canvas {

	private static final RGB RGB_BACKGROUND = new RGB(255, 255, 255);
	private static final RGB RGB_FOREGROUND = new RGB(0, 0, 0);
	private static final RGB RGB_CURRENT_FOREGROUND = new RGB(0, 0, 255);

	private static final FontData FONT_PAGE_NUMBER = new FontData("Courier New", 14, SWT.BOLD);

	private int currentPage = 0;
	private int maxPage = 0;

	private CoverageReportView reportView = null;

	public PageNavigation(CoverageReportView reportView, int currentPage, int maxPage) {
		super(reportView.getReportCanvas(), SWT.NONE);

		this.reportView = reportView;
		this.currentPage = currentPage;
		this.maxPage = maxPage;
		GridLayout gl = new GridLayout(maxPage + 2, false);
		gl.verticalSpacing = 0;
		gl.horizontalSpacing = 0;
		gl.marginHeight = 0;
		gl.marginWidth = 0;
		setLayout(gl);

		setBackground(new Color(this.getDisplay(), RGB_BACKGROUND));

		buildButtons();

	}

	private void buildButtons() {
		// Prev button
		Button prevButton = new Button(this, SWT.ARROW|SWT.LEFT|SWT.FLAT);
		prevButton.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				reportView.prevPage();
			}
		});

		if (currentPage == 1) {
			prevButton.setVisible(false);
		}

		PageNumberLabel pageLabel = null;
		for (int i = 1; i <= this.maxPage; i++) {
			pageLabel = new PageNumberLabel(this);
			pageLabel.setPageNumber(i);
			pageLabel.setCurrent((i == currentPage));
		}

		// Next button
		Button nextButton = new Button(this, SWT.ARROW|SWT.RIGHT|SWT.FLAT);
		nextButton.addMouseListener(new MouseAdapter() {
			public void mouseDown(MouseEvent e) {
				reportView.nextPage();
			}
		});

		if (currentPage == maxPage) {
			nextButton.setVisible(false);
		}
	}

	private class PageNumberLabel extends Label {

		private int pageNumber = 0;
		private Font font;
		public PageNumberLabel(Composite parent) {
			super(parent, SWT.NONE);
			setBackground(new Color(this.getDisplay(), RGB_BACKGROUND));
			setForeground(new Color(this.getDisplay(), RGB_FOREGROUND));
			font = new Font(this.getDisplay(), FONT_PAGE_NUMBER);
			setFont(font);
		}

		public void setPageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
			setText(" " + pageNumber + " ");
		}

		public void setCurrent(boolean isCurrent) {
			if (isCurrent) {
				setForeground(new Color(this.getDisplay(), RGB_CURRENT_FOREGROUND));
				return;
			}

			// Not current page
			addMouseListener(new MouseAdapter() {
				public void mouseDown(MouseEvent e) {
					reportView.movePage(pageNumber);
				}
			});

			addMouseTrackListener(new MouseTrackAdapter() {
				public void mouseEnter(MouseEvent e) {
					setCursor(new Cursor(getDisplay(), SWT.CURSOR_HAND));
				}
				public void mouseExit(MouseEvent e) {
					setCursor(new Cursor(getDisplay(), SWT.CURSOR_ARROW));
				}
			});
		}

		protected void checkSubclass() {
		}

		public void dispose() {
			super.dispose();
			if (font != null) {
				font.dispose();
			}
		}
	}
}
