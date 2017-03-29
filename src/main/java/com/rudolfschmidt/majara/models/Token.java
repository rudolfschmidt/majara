package com.rudolfschmidt.majara.models;

public class Token {
	private final int line;
	private final int indent;
	private final String value;

	public Token(int line, int indent, String value) {
		this.line = line;
		this.indent = indent;
		this.value = value;
	}

	@Override
	public String toString() {
		return line + ":" + indent + " : " + value;
	}

	public int getLine() {
		return line;
	}

	public int getIndent() {
		return indent;
	}

	public String getValue() {
		return value;
	}
}
