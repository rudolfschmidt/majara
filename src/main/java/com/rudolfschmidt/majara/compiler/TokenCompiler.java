package com.rudolfschmidt.majara.compiler;

import com.rudolfschmidt.majara.Code;
import com.rudolfschmidt.majara.Model;
import com.rudolfschmidt.majara.tokens.Token;
import com.rudolfschmidt.majara.tokens.TokenType;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Compiles tokens into html string
 */
public class TokenCompiler {

	public static TokenCompiler newInstance(Model model, boolean pretty) {
		return new TokenCompiler(model, pretty);
	}

	private final Model model;
	private final boolean pretty;

	private TokenCompiler(Model model, boolean pretty) {
		this.model = model;
		this.pretty = pretty;
	}

	public String compile(List<Token> tokens) {
		StringBuilder sb = new StringBuilder();
		tokens.stream().forEachOrdered(token -> compile(token, "", false, sb));
		return sb.toString();
	}

	private void compile(Token token, String prefix, boolean inline, StringBuilder sb) {

		if (token.getType() == TokenType.ATTRIBUTE
				|| token.getType() == TokenType.ATTRIBUTE_TEXT_VALUE
				|| token.getType() == TokenType.ATTRIBUTE_MODEL_VALUE) {
			return;
		}

		if (token.getType() == TokenType.ELEMENT_MODEL_VALUE) {
			compileElementModelValue(token.getValue(), sb);
			return;
		}

		if (token.getType() == TokenType.ELEMENT_TEXT_VALUE) {
			compileElementTextValue(token.getValue(), sb);
			return;
		}

		if (Code.DOCTYPE.equals(token.getValue())) {
			compileDocType(token, sb);
			return;
		}

		if (Code.BLOCK.equals(token.getValue())) {
			compileWithoutElementValue(token.getTokens(), prefix, inline, sb);
			return;
		}

		if (Code.INCLUDE.equals(token.getValue())) {
			compileWithoutElementValue(token.getTokens(), prefix, inline, sb);
			return;
		}

		if (Code.IF.equals(token.getValue())) {
			compileCondition(token.getTokens(), prefix, inline, sb);
			return;
		}

		if (Code.EACH.equals(token.getValue())) {
			compileList(token.getTokens(), prefix, inline, sb);
			return;
		}

		if (pretty && !inline) {
			sb.append(prefix);
		}
		sb.append("<");
		sb.append(token.getValue());

		compileAttributes(token.getTokens(), sb);

		sb.append(">");

		if (Stream.of(Code.VOID_ELEMENTS).anyMatch(token.getValue()::equals)) {
			if (pretty) {
				sb.append("\n");
			}
			return;
		}

		if (pretty && !isInline(token)) {
			sb.append("\n");
		}

		for (Token tok : token.getTokens()) {
			compile(tok, prefix + "\t", pretty && isInline(token), sb);
		}

		if (pretty && !isInline(token)) {
			sb.append(prefix);
		}

		sb.append("</");
		sb.append(token.getValue());
		sb.append(">");

		if (pretty && !inline) {
			sb.append("\n");
		}
	}


	private void compileList(List<Token> tokens, String prefix, boolean inline, StringBuilder sb) {
		tokens.stream().findFirst().ifPresent(token -> {
			String[] arr = token.getValue().split(" in ");
			Object list = model.get(arr[1]);
			if (!(list instanceof Iterable)) {
				return;
			}
			((Iterable) list).forEach(obj -> {
				model.put(arr[0], obj);
				compileWithoutElementValue(tokens, prefix, inline, sb);
				model.remove(arr[0]);
			});
		});
	}

	private void compileCondition(List<Token> tokens, String prefix, boolean inline, StringBuilder sb) {
		tokens.stream().findFirst().ifPresent(token -> {
			String condition = token.getValue();
			if (condition.startsWith("!")) {
				Object value = parseModel(condition.substring(1));
				if (value == null) {
					compileWithoutElementValue(tokens, prefix, inline, sb);
				} else if (value.getClass() == Boolean.class && !Boolean.valueOf(value.toString())) {
					compileWithoutElementValue(tokens, prefix, inline, sb);
				} else if (value.getClass() == String.class && value.toString().isEmpty()) {
					compileWithoutElementValue(tokens, prefix, inline, sb);
				}
			} else {
				Object value = parseModel(condition);
				if (value == null) {
					return;
				}
				if (value.getClass() == Boolean.class) {
					if (Boolean.valueOf(value.toString())) {
						compileWithoutElementValue(tokens, prefix, inline, sb);
					}
				} else if (value.getClass() == String.class) {
					if (!value.toString().isEmpty()) {
						compileWithoutElementValue(tokens, prefix, inline, sb);
					}
				} else {
					compileWithoutElementValue(tokens, prefix, inline, sb);
				}
			}
		});
	}

	private void compileDocType(Token token, StringBuilder sb) {
		token.getTokens().stream().findFirst().ifPresent(child -> {
			sb.append("<!DOCTYPE ").append(child.getValue()).append(">");
			if (pretty) {
				sb.append("\n");
			}
		});
	}

	private void compileElementTextValue(String text, StringBuilder sb) {
		Optional.ofNullable(text).ifPresent(value -> {
			for (String key : model.keySet()) {
				Pattern pattern = Pattern.compile("#\\{" + key + "\\}");
				Object mod = model.get(key);
				if (mod != null) {
					value = pattern.matcher(value).replaceAll(mod.toString());
				}
			}
			sb.append(value);
		});
	}

	private void compileElementModelValue(String key, StringBuilder sb) {
		sb.append(Optional.ofNullable(parseModel(key)).map(Object::toString).orElse(""));
	}

	private void compileWithoutElementValue(List<Token> tokens, String prefix, boolean inline, StringBuilder sb) {
		tokens.stream().filter(child -> child.getType() != TokenType.ELEMENT_TEXT_VALUE)
				.forEachOrdered(child -> compile(child, prefix, inline, sb));
	}

	private boolean isInline(Token token) {
		for (Token tok : token.getTokens()) {
			if (tok.getType() == TokenType.ELEMENT_TEXT_VALUE) {
				return true;
			} else if (tok.getType() == TokenType.ELEMENT) {
				return false;
			}
		}
		return true;
	}

	private void compileAttributes(List<Token> tokens, StringBuilder sb) {

		Map<String, List<String>> attributes = new LinkedHashMap<>();
		Optional<String> name = Optional.empty();
		boolean lastValue = false;

		for (Token token : tokens) {

			if (token.getType() == TokenType.ATTRIBUTE) {
				if (!attributes.containsKey(token.getValue())) {
					attributes.put(token.getValue(), new ArrayList<>());
				}
				name = Optional.ofNullable(token.getValue());
				lastValue = false;
			}

			if (token.getType() == TokenType.ATTRIBUTE_TEXT_VALUE) {
				if (lastValue) {
					name.ifPresent(key -> {
						List<String> values = attributes.get(key);
						values.set(values.size() - 1, values.get(values.size() - 1) + token.getValue());
					});
				} else {
					name.ifPresent(key -> attributes.get(key).add(token.getValue()));
					lastValue = true;
				}
			}

			if (token.getType() == TokenType.ATTRIBUTE_MODEL_VALUE) {
				String value = Optional.ofNullable(parseModel(token.getValue()))
						.map(Object::toString).orElse("");
				if (lastValue) {
					name.ifPresent(key -> {
						List<String> values = attributes.get(key);
						values.set(values.size() - 1, values.get(values.size() - 1) + value);
					});
				} else {
					name.ifPresent(key -> attributes.get(key).add(value));
				}
			}
		}

		attributes.forEach((key, values) -> {
			sb.append(" ").append(key);
			if (!values.isEmpty()) {
				sb.append("=");
				sb.append("\"");
				sb.append(values.stream().collect(Collectors.joining(" ")));
				sb.append("\"");
			}
		});

	}

	private Object parseModel(String key) {

		Object value = model.get(key);
		if (value != null) {
			return value;
		}

		String[] paths = key.split("\\.");
		value = model.get(paths[0]);
		if (value == null || paths.length < 2) {
			return null;
		}

		return parseModel(value, paths, 1);
	}

	private Object parseModel(Object model, String[] paths, int index) {

		String path = paths[index];

		List<Field> fields = Stream.of(model.getClass().getDeclaredFields())
				.filter(field -> field.getName().equals(path))
				.collect(Collectors.toList());

		for (Field field : fields) {
			Object value;
			field.setAccessible(true);
			try {
				value = field.get(model);
			} catch (IllegalAccessException e) {
				throw new IllegalStateException(e);
			}
			if (value == null) {
				return null;
			}
			Class[] primes = {String.class, Number.class};
			if (!Arrays.asList(primes).contains(value.getClass()) && index + 1 < paths.length) {
				return parseModel(value, paths, ++index);
			} else {
				return value;
			}
		}
		return null;
	}
}
