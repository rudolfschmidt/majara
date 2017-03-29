package com.rudolfschmidt.majara.stringTransformers.attributeTransformer;

import java.util.ArrayDeque;
import java.util.Deque;

class Attribute {

	private final String name;
	private final Deque<String> values = new ArrayDeque<>();

	Attribute(String name) {
		this.name = name;
	}

	String getName() {
		return name;
	}

	Deque<String> getValues() {
		return values;
	}

}
