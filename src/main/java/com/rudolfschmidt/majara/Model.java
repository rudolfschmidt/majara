package com.rudolfschmidt.majara;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Model {

	private final Map<String, Object> model;

	public static Model newInstance() {
		return new Model(new HashMap<>());
	}

	public static Model newInstance(String key, Object value) {
		Map<String, Object> map = new HashMap<>();
		map.put(key, value);
		return new Model(map);
	}

	private Model(Map<String, Object> model) {
		this.model = model;
	}

	public Model put(String key, Object value) {
		model.put(key, value);
		return this;
	}

	public Set<String> keySet() {
		return model.keySet();
	}

	public Object get(String key) {
		return model.get(key);
	}

	public void remove(String key) {
		model.remove(key);
	}
}
