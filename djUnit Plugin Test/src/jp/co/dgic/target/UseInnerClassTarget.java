package jp.co.dgic.target;

public class UseInnerClassTarget {

	private class InnerClass {
		public void methodA() {
			return;
		}
		public void methodB() {
			return;
		}
		public void methodC() {
			return;
		}
	}
	
	public void useMethodA() {
		new InnerClass().methodA();
	}

	public void useMethodB() {
		new InnerClass().methodB();
	}
}
