package com.rudolfschmidt.majara.v2.compiler;

import com.rudolfschmidt.majara.Model;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class ModelCompiler {

	final Model model;

	public ModelCompiler(Model model) {
		this.model = model;
	}

	public String replaceModel(String text) {
		Matcher matcher = Pattern.compile("#\\{(\\w+(?:\\.\\w+)*)\\}").matcher(text);
		while (matcher.find()) {
			String key = matcher.group(1);
			String value = compileModel(key).map(Object::toString).orElse(matcher.group());
			text = text.replaceAll("#\\{" + key + "\\}", value);
		}
		return text;
	}

	public Optional<Object> compileModel(String key) {

		Optional<Object> value = model.get(key);
		if (value.isPresent()) {
			return value;
		}

		String[] paths = key.split("\\.");
		value = model.get(paths[0]);
		if (!value.isPresent() || paths.length < 2) {
			return Optional.empty();
		} else {
			return compileModel(value.get(), paths, 1);
		}
	}

	private Optional<Object> compileModel(Object model, String[] paths, int index) {

		return Optional.of(model)
				.map(Object::getClass)
				.map(Class::getDeclaredFields)
				.map(Stream::of)
				.orElseGet(Stream::empty)
				.filter(field -> field.getName().equals(paths[index]))
				.peek(field -> field.setAccessible(true))
				.map(field -> {
					try {
						return field.get(model);
					} catch (IllegalAccessException e) {
						return null;
					}
				})
				.map(value -> {
					Class[] primitives = {String.class, Number.class};
					if (!Arrays.asList(primitives).contains(value.getClass()) && index + 1 < paths.length) {
						return compileModel(value, paths, index + 1);
					} else {
						return value;
					}
				})
				.findAny();
	}


}
