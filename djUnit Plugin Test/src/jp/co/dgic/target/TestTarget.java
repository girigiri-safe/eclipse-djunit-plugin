package jp.co.dgic.target;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

/**
 * このTestTargetクラスを材料に、Aspectのテストをおこなう
 * jp.co.dgic.testパッケージ以下のクラスは、Aspectの対象外としているので、
 * 本jp.co.dgic.targetパッケージに実装します。
 * @author kataoka
 */
public class TestTarget implements ITestTarget {

	private int field1;
	private String field2;
	private Hashtable field3;
	
	static {
		int i = getStaticField();
	}

	public int getField1() {
		return field1;
	}
	public String getField2() {
		return field2;
	}
	public Hashtable getField3() {
		return field3;
	}
	public static int getStaticField() {
		return 0;
	}

	public boolean getBooleanValue() {
		return false;
	}
	
	public byte getByteValue() {
		return 0;
	}
	
	public char getCharValue() {
		return 0;
	}
	
	public short getShortValue() {
		return 0;
	}
	
	public int getIntValue() {
		return 0;
	}
	
	public long getLongValue() {
		return 0L;
	}
	
	public float getFloatValue() {
		return 0F;
	}
	
	public double getDoubleValue() {
		return 0;
	}

	public boolean isOld() {
		long time = 100000;
		Date currentDate = new Date();
		long now = new Date().getTime();
		long currentTime = currentDate.getTime();
		return (now - currentTime > time);
	}

	public void setField1(int i) {
		field1 = i;
	}
	public void setField2(String string) {
		field2 = string;
	}
	public void setField3(Hashtable hashtable) {
		field3 = hashtable;
	}

	public void throwException() throws Exception {
		if (false)
			throw new Exception();
	}

	public void throwIOException() throws IOException {
		if (false)
			throw new IOException();
	}

	public void throwIOAndSQLException() throws IOException, SQLException {
		if (false)
			throw new IOException();
		if (false)
			throw new SQLException();
	}

	public void exit(int i) {
		if (i == 0)
			return;
		System.exit(i);
	}

	public void readObject() throws IOException, ClassNotFoundException {

		String string = "test";

		ByteArrayOutputStream byteOut = null;
		ObjectOutputStream objOut = null;

		byteOut = new ByteArrayOutputStream();
		objOut = new ObjectOutputStream(byteOut);
		objOut.writeObject(string);

		objOut.flush();
		byteOut.flush();

		byte[] bytes = byteOut.toByteArray();
		ObjectInputStream os =
			new ObjectInputStream(new ByteArrayInputStream(bytes));

		Object o = null;
		o = os.readObject();
		String outString = (String) o;
		System.out.println(outString);
	}
	
	public void longMethod(long l1, long l2) {
		return;
	}
	
	public void doubleMethod(double d1, double d2) {
		return;
	}
	
	public void longDoubleMethod(long l, double d) {
		return;
	}
	

}
