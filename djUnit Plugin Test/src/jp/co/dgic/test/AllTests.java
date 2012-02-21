package jp.co.dgic.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		
		TestSuite suite = new TestSuite("Test for " + AllTests.class.getPackage().getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(OverloadMethodCallTest.class);
		suite.addTestSuite(MockObjectManagerTest.class);	// ClassArgumentTest
		suite.addTestSuite(ClassArgumentTest.class);	// ClassArgumentTest
		suite.addTestSuite(AClassTest.class);
		suite.addTestSuite(UseSystemExitTest.class);
		suite.addTestSuite(HelloWorldTest.class);
		suite.addTestSuite(OwnMethodCallTest.class);
		suite.addTestSuite(NewFileObjectTest.class);
		suite.addTestSuite(UseSwingOptionPaneTest.class);
		suite.addTestSuite(ReturnValueProviderTest.class);
		suite.addTestSuite(ConstructorTest.class);
		suite.addTestSuite(ReturnValueProviderForSystemClassTest.class);
		suite.addTestSuite(MethodCallTest.class);
		suite.addTestSuite(MisatchExceptionTypeTest.class);
		suite.addTestSuite(MismatchReturnTypeTest.class);
		suite.addTestSuite(MismatchReturnTypeForNewExprTest.class);
		suite.addTestSuite(MismatchReturnTypeForMethodCallTest.class);
		suite.addTestSuite(UseInnerClassTest.class);
		suite.addTestSuite(UseAnonymousClassTest.class);
		suite.addTestSuite(UseOtherClassTest.class);
		suite.addTestSuite(NonReceiveInstanceNewExprTest.class);
		suite.addTestSuite(SuperClassArgumentTest.class);
		suite.addTestSuite(ReturnValueIsNotNullTest.class);
		suite.addTestSuite(PrefixTargetClassNameText.class);
		suite.addTestSuite(ClassArgumentTest.class);
		suite.addTestSuite(SubClassTest.class);
		suite.addTestSuite(BTagClassTest.class);
		suite.addTestSuite(OutputStreamCloseTest.class);
		suite.addTestSuite(SampleUTF8Test.class);
		suite.addTestSuite(SampleTest.class);
		suite.addTestSuite(AEnumUserTest.class);
		suite.addTestSuite(InterfaceTest.class);
		suite.addTestSuite(ExceptionCatchTest.class);
		suite.addTestSuite(StringBuilderUserTest.class);
		//$JUnit-END$
		return suite;
	}
}
