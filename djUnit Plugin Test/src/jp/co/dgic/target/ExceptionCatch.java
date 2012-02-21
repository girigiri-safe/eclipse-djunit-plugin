package jp.co.dgic.target;

import java.util.ArrayList;

public class ExceptionCatch {

	public void catchException() throws Exception{
		ArrayList<String> list = new ArrayList<String>();
		list.add("a");
		try{
			//例外スローが行われるコードが複数行だとdjUnitの場合catchされない
			//sub(list.get(0)); と1行で記述するとcatchされる
			sub(list
					.get(0));
		} catch(Exception e){
			//OK
			System.out.println("OK");
		} 
		finally {
			System.out.println("finally");
			System.out.println("finally");
			System.out.println("finally");
		}
	}
	
	protected void sub(String arg) throws Exception{
		throw new Exception("");
	}

}
