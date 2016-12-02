package com.rudolfschmidt.majara.v2.tokens;

import java.util.ArrayList;
import java.util.List;

public class Structure {

	private final Token token;
	private final List<Structure> structures;

	public Structure(Token token) {
		this.token = token;
		this.structures = new ArrayList<>();
	}

	public Token token() {
		return token;
	}

	public List<Structure> structures() {
		return structures;
	}

	@Override
	public String toString() {
		return toString("");
	}

	private String toString(String prefix) {
		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		sb.append(token);
		for (Structure structure : structures) {
			sb.append("\n");
			sb.append(structure.toString(prefix + "\t"));
		}
		return sb.toString();
	}
}
