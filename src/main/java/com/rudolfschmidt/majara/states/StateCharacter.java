package com.rudolfschmidt.majara.states;

public class StateCharacter {

	public static StateCharacter valueOf(char character) {
		return new StateCharacter(character);
	}

	private final char character;

	public StateCharacter(char character) {
		this.character = character;
	}

	public char getCharacter() {
		return character;
	}

	public boolean isLetter() {
		return Character.isLetter(character);
	}

	public boolean isSpace() {
		return character == ' ';
	}

	public boolean isDot() {
		return character == '.';
	}

	public boolean isOpenBracket() {
		return character == '(';
	}

	public boolean isEqual() {
		return character == '=';
	}

	public boolean isPrime() {
		return character == '\'';
	}

	public boolean isDoublePrime() {
		return character == '"';
	}

	public boolean isCloseBracket() {
		return character == ')';
	}

	public boolean isBackSlash() {
		return character == '\\';
	}

	public boolean isHash() {
		return character == '#';
	}

	public boolean isLetterOrDigit() {
		return Character.isLetterOrDigit(character);
	}

	public boolean isSlash() {
		return character == '/';
	}

	public boolean isDash() {
		return character == '-';
	}

	public boolean isPipe() {
		return character == '|';
	}

	public boolean isTab() {
		return character == '\t';
	}

	public boolean isPlus() {
		return character == '+';
	}

	public boolean isUnderline() {
		return character == '_';
	}

	public boolean isColon() {
		return character == ':';
	}
}
