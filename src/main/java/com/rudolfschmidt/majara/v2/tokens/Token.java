package com.rudolfschmidt.majara.v2.tokens;

public class Token {

	private final Types type;
	private final int line;
	private final int indent;
	private final String value;

	public Token(Types type, int line, int indent, String value) {
		this.type = type;
		this.line = line;
		this.indent = indent;
		this.value = value;

	}

	public Token(Types type, int line, int indent) {
		this(type, line, indent, null);
	}

	public Types type() {
		return type;
	}

	public int line() {
		return line;
	}

	public int indent() {
		return indent;
	}

	public String value() {
		return value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(line);
		sb.append(" : ");
		sb.append(indent);
		sb.append(" : ");
		sb.append(type);
		sb.append(" : ");
		sb.append(value);
		return sb.toString();
	}
}
