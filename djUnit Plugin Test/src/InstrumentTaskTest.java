import java.util.Properties;

import jp.co.dgic.testing.framework.DJUnitTestCase;
import junit.framework.TestCase;
import org.apache.tools.ant.Main;

/**
 * This file is part of djUnit plug-in.
 *
 * you can redistribute it and/or modify
 * it under the terms of the CPL Common Public License.
 *
 * See the CPL Common Public License for more details.
 */

public class InstrumentTaskTest extends DJUnitTestCase {

	/**
	 * Constructor for InstrumentTaskTest.
	 * @param name
	 */
	public InstrumentTaskTest(String name) {
		super(name);
	}

	public void testInstrument() {
		
		if (true) return;

		String[] args = new String[3];
		args[0] = "-f";
		args[1] = "D:\\Projects\\djUnitPluginTest\\build.xml";
		args[2] = "djunit";
		
		Properties p = new Properties();
		p.put("project.dir", "D:/Projects/NID/SFAMobile");
		p.put("jcoverage.dir", "D:/Projects/NID/com.jcoverage_1.0.5");
		p.put("report.output.dir", ".");

		Main.start(args, p, null);
	}

}
