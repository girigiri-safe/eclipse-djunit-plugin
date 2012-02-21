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
package jp.co.dgic.eclipse.jdt.internal.junit.ui;

import java.util.StringTokenizer;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ExcludePropertyPanel extends Composite {

	public static final String INVALID_MARK = "{{INVALID}}";

	public static final String PATH_DELIMITOR = ";";

	private Label titleLabel;

	private Table propertyTable;

	private Button addButton;

	private Button editButton;

	private Button removeButton;

	private Button useDefaultButton;

	private boolean hasDefaultCheck;

	private String defaultValues;

	public ExcludePropertyPanel(Composite parent) {
		this(parent, false);
	}

	public ExcludePropertyPanel(Composite parent, int width, int height) {
		this(parent, false, width, height);
	}

	public ExcludePropertyPanel(Composite parent, boolean hasDefaultCheck) {
		this(parent, hasDefaultCheck, 300, 300);
	}

	public ExcludePropertyPanel(Composite parent, boolean hasDefaultCheck, int width, int height) {
		super(parent, SWT.NONE);
		this.hasDefaultCheck = hasDefaultCheck;
		initialize(width, height);
	}

	private void initialize(int width, int height) {

		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		setLayout(gl);

		// Label
		titleLabel = new Label(this, SWT.NONE);

		// use Default Check
		if (hasDefaultCheck) {
			useDefaultButton = new Button(this, SWT.CHECK);
			useDefaultButton.setText(DJUnitMessages.getString("ExcludePropertyPanel.label.usedefault"));
			useDefaultButton.setSelection(true);
			useDefaultButton.addSelectionListener(new SelectionAdapter() {

				public void widgetSelected(SelectionEvent e) {
					boolean isSelected = ((Button) e.getSource()).getSelection();
					setUseDefault(isSelected);
				}
			});
		} else {
			new Label(this, SWT.NONE);
		}

		// table
		propertyTable = new Table(this, SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER | SWT.CHECK);

		propertyTable.setLinesVisible(true);

		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = width;
		gd.heightHint = height;
		gd.verticalSpan = 4;
		propertyTable.setLayoutData(gd);

		TableColumn column = new TableColumn(propertyTable, SWT.NONE);
		column.setText(DJUnitMessages.getString("ExcludePropertyPanel.label.excluded"));
		column.setWidth(300);

		// add button
		addButton = new Button(this, SWT.NONE);
		addButton.setText(DJUnitMessages.getString("ExcludePropertyPanel.label.add"));
		addButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		addButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				InputDialog input = new InputDialog(addButton.getShell(),
													DJUnitMessages.getString("ExcludePropertyPanel.label.add"),
													DJUnitMessages.getString("ExcludePropertyPanel.message.add"), "",
													null);
				int code = input.open();
				if (code == InputDialog.CANCEL) {
					return;
				}
				if (input.getValue() != null && !"".equals(input.getValue())) {
					TableItem newItem = new TableItem(propertyTable, SWT.NONE);
					newItem.setChecked(true);
					newItem.setText(0, input.getValue());
				}
			}
		});

		// edit button
		editButton = new Button(this, SWT.NONE);
		editButton.setText(DJUnitMessages.getString("ExcludePropertyPanel.label.edit"));
		editButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		editButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				int index = propertyTable.getSelectionIndex();
				if (index < 0) return;
				String text = propertyTable.getItem(index).getText();
				InputDialog input = new InputDialog(editButton.getShell(),
													DJUnitMessages.getString("ExcludePropertyPanel.label.edit"),
													DJUnitMessages.getString("ExcludePropertyPanel.message.edit"),
													text, null);
				int code = input.open();
				if (code == InputDialog.CANCEL) {
					return;
				}
				if (input.getValue() != null && !"".equals(input.getValue())) {
					propertyTable.getItem(index).setText(0, input.getValue());
				} else {
					propertyTable.remove(index);
				}
			}
		});

		// remove button
		removeButton = new Button(this, SWT.NONE);
		removeButton.setText(DJUnitMessages.getString("ExcludePropertyPanel.label.remove"));
		removeButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		removeButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				int index = propertyTable.getSelectionIndex();
				if (index < 0) return;
				propertyTable.remove(index);
			}
		});

		setEnabled(!hasDefaultCheck);
	}

	public void setText(String text) {
		titleLabel.setText(text);
	}

	public void setDefaultValues(String defaultValues) {
		this.defaultValues = defaultValues;
	}

	public void setEnabled(boolean isEnable) {

		propertyTable.setEnabled(isEnable);
		addButton.setEnabled(isEnable);
		editButton.setEnabled(isEnable);
		removeButton.setEnabled(isEnable);
	}

	public void clear() {
		propertyTable.removeAll();
	}

	public boolean getUseDefault() {
		if (useDefaultButton == null) return false;
		return useDefaultButton.getSelection();
	}

	public void setUseDefault(boolean isDefault) {
		if (useDefaultButton != null) {
			useDefaultButton.setSelection(isDefault);
		}
		setEnabled(!isDefault);
		if (isDefault) {
			setValue(defaultValues);
		}
	}

	public void setValue(String[] values) {

		propertyTable.removeAll();

		if (values == null) return;

		String value = null;
		boolean invalid = false;
		for (int index = 0; index < values.length; index++) {
			TableItem item = new TableItem(propertyTable, SWT.NONE);
			invalid = false;
			value = values[index];
			if (values[index].startsWith(INVALID_MARK)) {
				invalid = true;
				value = values[index].substring(INVALID_MARK.length());
			}
			item.setChecked(!invalid);
			item.setText(0, value);
		}
	}

	public void setValue(String value) {
		setValue(splitValue(value));
	}

	public String getValue() {
		TableItem[] items = propertyTable.getItems();

		if (items == null) return "";

		StringBuffer sb = new StringBuffer();
		for (int index = 0; index < items.length; index++) {
			if (!items[index].getChecked()) {
				sb.append(INVALID_MARK);
			}
			sb.append(items[index].getText(0));
			sb.append(";");
		}
		return sb.toString();
	}

	private String[] splitValue(String value) {
		if (value == null) return null;

		StringTokenizer st = new StringTokenizer(value, ";");
		String[] values = new String[st.countTokens()];
		for (int index = 0; index < values.length; index++) {
			values[index] = st.nextToken();
		}
		return values;
	}
}