package jp.co.dgic.target;

import java.util.ArrayList;

public class ExceptionCatch {

	public void catchException() throws Exception{
		ArrayList<String> list = new ArrayList<String>();
		list.add("a");
		try{
			//��O�X���[���s����R�[�h�������s����djUnit�̏ꍇcatch����Ȃ�
			//sub(list.get(0)); ��1�s�ŋL�q�����catch�����
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
