/*
 * ÇcÇhÇbã éËî†Å@äàìÆèÓïÒã§óLÇrÇeÇ`ÉVÉXÉeÉÄ
 * @(#)ITestTarget.java 2004/03/26
 *
 * Copyright 2004 by Dainippon Ink and Chemicals, Incorporated
 *
 * First Code
 * 2004/03/26 kataoka
*/
package jp.co.dgic.target;

import java.awt.Color;
import java.awt.Font;
import java.util.Hashtable;

/**
 * <PRE>
 * <PRE>
 * @version 01-00
 * @see
 * @since 2004/03/26
 */
public interface ITestTarget {

	String TARGET_CONST_1 = "1";
	String TARGET_CONST_2 = "2";
	int TARGET_CONST_3 = 0;
	String STANDARD_FONT_NAME = "ÇlÇr ÉSÉVÉbÉN";
	int STANDARD_FONT_SIZE = 12;
	int STANDARD_FONT_STYLE = Font.PLAIN;

	Font STANDARD_FONT =
		new Font(STANDARD_FONT_NAME, STANDARD_FONT_STYLE, STANDARD_FONT_SIZE);

	Color STANDARD_BG_COLOR = Color.white;

	Color LINK_FG_COLOR = Color.blue;
	Color LINK_FOCUS_COLOR = Color.red;

	Color TABLE_BORDER_LINE_COLOR = Color.lightGray;

	Color TAB_LINK_FRONT_ACTIVE_COLOR = new Color(164, 172, 253);
	Color TAB_LINK_FORNT_INACTIVE_COLOR = new Color(113, 136, 247);
	Color TAB_LINK_BEHIND_COLOR = new Color(200, 208, 251);

	int HISSU_FONT_STYLE = Font.BOLD;
	Color HISSU_BG_COLOR = new Color(233, 233, 233);
		public int getField1();
		public String getField2();
		public Hashtable getField3();
}
