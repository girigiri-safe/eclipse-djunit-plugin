package jp.co.dgic.target;

public class Kinki {
	
	byte[] aaa = new byte[1];
	byte[] bbb = (byte[]) aaa.clone();
		
	Izumi izumi = new Izumi();
	Izumi[] izumis = new Izumi[2];
	Izumi[] izumis2 = izumis.clone();

	public class Izumi{
		byte[] aaa = new byte[1];
		byte[] bbb = (byte[]) aaa.clone();
	}

}
