package com.rudolfschmidt.majara;

import java.util.HashMap;
import java.util.Map;

public class Model {

	private final Map<String, Object> model;

	private Model(Map<String, Object> model) {
		this.model = model;
	}

	public static Model createModel() {
		return new Model(new HashMap<>());
	}

	public static Model createModel(String key, Object value) {
		return createModel().add(key, value);
	}

	public Model add(String key, Object value) {
		model.put(key, value);
		return this;
	}

	public Object get(String key) {
		return model.get(key);
	}

	public void remove(String key) {
		model.remove(key);
	}

	@Override
	public String toString() {
		return String.format("Model[%s]", model);
	}
}
