package jp.co.dgic.target;

public class UseAnonymousClassTarget {

	private class InnerClass {
		public void doSomething() {
			return;
		}
	}
	
	public void methodA() {

		InnerClass iClass = new InnerClass() {
			public void doSomething() {
				System.out.println("doSomething");
				return;
			}
			public void doSomethingNew() {
				System.out.println("doSomethingNew");
				return;
			}
		};
		
		iClass.doSomething();

	}

	public void methodB() {

		InnerClass iClass = new InnerClass() {
			public void doSomething() {
				System.out.println("doSomething");
				doSomethingNew();
				return;
			}
			public void doSomethingNew() {
				System.out.println("doSomethingNew");
				return;
			}
		};
		
		iClass.doSomething();

	}
}
