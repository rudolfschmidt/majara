package com.rudolfschmidt.majara.v2.tokens;

import java.util.ArrayList;
import java.util.List;

public class Attribute {

	private final String name;
	private final List<String> values;

	public Attribute(String name) {
		this.name = name;
		this.values = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public List<String> getValues() {
		return values;
	}
}
