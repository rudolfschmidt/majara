package com.rudolfschmidt.majara;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Model {

	public static Model get() {
		return new Model(new HashMap<>());
	}

	public static Model get(String key, Object value) {
		Map<String, Object> map = new HashMap<>();
		map.put(key, value);
		return new Model(map);
	}

	private final Map<String, Object> model;

	private Model(Map<String, Object> model) {
		this.model = model;
	}

	public Model put(String key, Object value) {
		model.put(key, value);
		return this;
	}

	public Optional<Object> get(String key) {
		return Optional.ofNullable(model.get(key));
	}

	public void remove(String key) {
		model.remove(key);
	}
}
