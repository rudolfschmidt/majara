package com.rudolfschmidt.majara.exceptions;

public class LexerException extends RuntimeException {
	private final int line;

	public LexerException(int line) {
		this.line = line;
	}

	public int getLine() {
		return line;
	}
}
