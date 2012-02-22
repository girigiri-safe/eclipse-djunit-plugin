package jp.co.dgic.target;

public class AClass {

	private String name;

	public AClass() {
	}

	public AClass(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public static void main(String[] args) {
		new AClass().exec();
	}
	
	private void exec() {
		InnerClass i = new InnerClass();
		getNumber();
		System.out.println("i.getA = " + i.getA());
		i.setA(10);
		System.out.println("i.getA = " + i.getA());
	}
	
	private int getNumber() {
		return 10;
	}

	public class InnerClass {
		private int a;
		public int getA() {
			return a;
		}
		public void setA(int a) {
			this.a = a;
		}
	}
}
