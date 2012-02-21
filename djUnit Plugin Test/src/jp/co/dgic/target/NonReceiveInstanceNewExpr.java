package jp.co.dgic.target;

public class NonReceiveInstanceNewExpr {
	
	private class PrintThread extends Thread {
		
		public PrintThread() {
			start();
		}
		
		public void run() {
			for (int i = 0; i < 10; i++) {
				System.out.println("Printing...");
			}
		}
	}

	public void createThread() {
		
		String s1 = new String("s1");
		
		new PrintThread();
		
		String s2 = new String("s2");
	}
	
	public static void main(String[] args) {
		new NonReceiveInstanceNewExpr().createThread();
	}
}
