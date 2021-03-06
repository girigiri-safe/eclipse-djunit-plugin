package jp.co.dgic.test;

import java.util.ResourceBundle;

import jp.co.dgic.target.ResourceBundleUser;
import junit.framework.TestCase;

public class ResourceBundleUserTest extends TestCase {

	private ResourceBundleUser target;
	
	public ResourceBundleUserTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		target = new ResourceBundleUser();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		target = null;
	}
	
	public void testGetBundle() throws Throwable {
		try {
			target.getBundle();
		} catch (Throwable t) {
			fail("例外が発生した");
			throw t;
		}
	}

//	public void testTes0() throws Throwable {
//		try {
//			new Tes0();
//		} catch (Throwable t) {
//			t.printStackTrace();
//			fail("例外が発生した");
//			throw t;
//		}
//	}
	
    public class Tes0 {
        Tes0() {
            //ResourceTest$Tes0.propertiesを用意のこと
            ResourceBundle _resBundle = ResourceBundle.getBundle(getClass().getName());
        }
    }
}
