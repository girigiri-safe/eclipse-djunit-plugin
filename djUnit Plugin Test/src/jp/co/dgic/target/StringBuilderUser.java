package jp.co.dgic.target;

public class StringBuilderUser {

	public String useStringBuilder() {
		StringBuilder sb = new StringBuilder();
		sb.append("a");
		sb.append("b");
		sb.append("c");
		return sb.toString();
	}
	
	public int useLength() {
		StringBuilder sb = new StringBuilder();
		sb.append("a");
		sb.append("b");
		sb.append("c");
		sb.append("d");
		return sb.length();
	}
}
