package com.rudolfschmidt.majara.transformers;

import com.rudolfschmidt.majara.Model;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModelTransformer {

	private final Model model;

	public ModelTransformer(Model model) {
		this.model = model;
	}

	public String getString(String str) {
		final Matcher matcher = Pattern.compile("#\\{(\\w+(?:\\.\\w+)*)\\}").matcher(str);
		while (matcher.find()) {
			final String key = matcher.group(1);
			final Object compiled = getObject(key);
			final String value = compiled != null ? compiled.toString() : matcher.group();
			str = str.replaceAll("#\\{" + key + "\\}", value);
		}
		return str;
	}

	public Object getObject(String key) {
		final Object modelValue = model.get(key);

		if (modelValue != null) {
			return modelValue;
		}

		final String[] paths = key.split("\\.");
		final Object value = model.get(paths[0]);

		if (value == null || paths.length < 2) {
			return null;
		} else {
			return getObject(value, paths, 1);
		}
	}

	private static Object getObject(Object modelValue, String[] paths, int index) {

		final Class<?> modelClass = modelValue.getClass();

		for (Field field : modelClass.getDeclaredFields()) {

			if (field.getName().equals(paths[index])) {

				field.setAccessible(true);

				final Object value;
				try {
					value = field.get(modelValue);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}

				if (value == null) {
					return null;
				}

				final Class[] primitives = {String.class, Number.class};
				if (!Arrays.asList(primitives).contains(value.getClass()) && index + 1 < paths.length) {
					return getObject(value, paths, index + 1);
				} else {
					return value;
				}

			}
		}
		return null;
	}

	public void addModel(String key, Object value) {
		model.add(key, value);
	}

	public void removeModel(String key) {
		model.remove(key);
	}
}
