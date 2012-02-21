package jp.co.dgic.target;

public class StringBuilderException extends Exception {
	public String getMessage() {
		final StringBuilder build = new StringBuilder();
		build.append("args");
		build.length();
		return build.toString();
	}
}
