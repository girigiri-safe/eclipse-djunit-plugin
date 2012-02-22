package jp.co.dgic.test;

import java.io.File;

import jp.co.dgic.target.ConstructorTestTarget;
import jp.co.dgic.testing.common.DJUnitRuntimeException;
import jp.co.dgic.testing.common.virtualmock.MockObjectManager;
import junit.framework.TestCase;

public class ConstructorTest extends TestCase {
	
	private class ConstructorTargetOwner {
		public ConstructorTestTarget getTarget() {
			return new ConstructorTestTarget();
		}
	}
	
	private class ThisCallConstructorTarget {
		
		private String name;
		
		public ThisCallConstructorTarget() {
			this("default");
		}
		
		public ThisCallConstructorTarget(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
	}
	
	private class SystemClassOwner {
		public File getFile() {
			return new File("owner");
		}
	}
	
	private ConstructorTargetOwner targetOwner;
	private SystemClassOwner fileOwner;

	protected void setUp() throws Exception {
		super.setUp();
		MockObjectManager.initialize();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public ConstructorTest(String name) {
		super(name);
	}

	public void testNormal001() throws Exception {
		
		targetOwner = new ConstructorTargetOwner();
		
		assertEquals(1, targetOwner.getTarget().getValue());
		assertEquals(1, MockObjectManager.getCallCount(ConstructorTestTarget.class, "<init>"));
	}
	
	public void testNormal002() throws Exception {
		MockObjectManager.addReturnValue(ConstructorTestTarget.class, "<init>");
		
		targetOwner = new ConstructorTargetOwner();
		
		assertEquals(0, targetOwner.getTarget().getValue());
		assertEquals(1, MockObjectManager.getCallCount(ConstructorTestTarget.class, "<init>"));
	}

	public void testNormal003() throws Exception {
		
		ConstructorTestTarget mockTarget = new ConstructorTestTarget(100);
		MockObjectManager.addReturnValue(ConstructorTestTarget.class, "<init>", mockTarget);
		
		targetOwner = new ConstructorTargetOwner();
		
		assertEquals(100, targetOwner.getTarget().getValue());
		assertEquals(2, MockObjectManager.getCallCount(ConstructorTestTarget.class, "<init>"));
	}

	public void testSystemClass001() throws Exception {
		
		fileOwner = new SystemClassOwner();
		
		assertEquals("owner", fileOwner.getFile().getName());
		assertEquals(1, MockObjectManager.getCallCount(File.class, "<init>"));
	}
	
	public void testSystemClas002() throws Exception {
		MockObjectManager.addReturnValue(File.class, "<init>");
		
		fileOwner = new SystemClassOwner();

		try {
			fileOwner.getFile();
			fail("é¿çsÇ≥ÇÍÇ»Ç¢ÇÕÇ∏");
		} catch (DJUnitRuntimeException re) {
			re.printStackTrace();
			assertTrue(re.getMessage().startsWith("Mismatch object type."));
		}
		
		assertEquals(1, MockObjectManager.getCallCount(File.class, "<init>"));
	}

	public void testSystemClas003() throws Exception {
		
		MockObjectManager.addReturnValue(File.class, "<init>", new File("MockOwner"));
		
		fileOwner = new SystemClassOwner();
		
		assertEquals("MockOwner", fileOwner.getFile().getName());
		assertEquals(1, MockObjectManager.getCallCount(File.class, "<init>"));
	}
	
	public void testThisConstructorCall001() throws Exception {
		ThisCallConstructorTarget target = new ThisCallConstructorTarget();
		
		assertEquals("default", target.getName());
		assertEquals(2, MockObjectManager.getCallCount(ConstructorTest.ThisCallConstructorTarget.class, "<init>"));
	}

	public void testThisConstructorCall002() throws Exception {
		ThisCallConstructorTarget target = new ThisCallConstructorTarget("name");
		
		assertEquals("name", target.getName());
		assertEquals(1, MockObjectManager.getCallCount(ConstructorTest.ThisCallConstructorTarget.class, "<init>"));
	}

}
