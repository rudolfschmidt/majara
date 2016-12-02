package com.rudolfschmidt.majara.v2.tokenizer;

import com.rudolfschmidt.majara.v2.tokens.Types;
import com.rudolfschmidt.majara.v2.tokens.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Tokens {

	public static List<Token> valueOf(Path path) {
		List<Token> tokens = new ArrayList<>();
		try {
			compile(tokens, Files.readAllLines(path));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		return tokens;
	}

	private static void compile(List<Token> tokens, List<String> lines) {
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			compile(tokens, i + 1, line);
		}
	}

	private static void compile(List<Token> tokens, int line, String str) {

		if (str.matches("\\s*")) {
			return;
		}

		int indent = 0;
		Matcher matcher = Pattern.compile("^\\s*").matcher(str);
		if (matcher.find()) {
			indent = matcher.end();
			str = str.substring(matcher.end());
		}

		str = comment(tokens, line, indent, str);
		str = extend(tokens, line, indent, str);
		str = block(tokens, line, indent, str);
		str = append(tokens, line, indent, str);
		str = include(tokens, line, indent, str);
		str = ifCondition(tokens, line, indent, str);
		str = elseCondition(tokens, line, indent, str);
		str = each(tokens, line, indent, str);
		str = doctype(tokens, line, str);
		str = pipe(tokens, line, indent, str);

		if (attributesStart(tokens)) {

			str = attributeKey(tokens, line, indent, str);
			str = attributesEnd(tokens, line, str);
			str = textValue(tokens, line, str);
			str = modelValue(tokens, line, str);

		} else {

			str = name(tokens, line, indent, str);

			while (str.startsWith("#") || str.startsWith(".")) {
				str = id(tokens, line, str);
				str = dot(tokens, line, str);
			}

			str = attributesStart(tokens, line, str);
			str = attributes(tokens, line, str);
			str = textValue(tokens, line, str);
			str = modelValue(tokens, line, str);

		}
	}


	private static String comment(List<Token> tokens, int line, int indent, String str) {
		Matcher matcher = Pattern.compile("^//").matcher(str);
		if (matcher.find()) {
			tokens.add(new Token(Types.COMMENT, line, indent, str.substring(matcher.end(), str.length())));
			str = str.substring(str.length());
		}
		return str;
	}

	private static String extend(List<Token> tokens, int line, int indent, String str) {
		Matcher matcher = Pattern.compile("^extends\\s").matcher(str);
		if (matcher.find()) {
			tokens.add(new Token(Types.EXTENDS, line, indent, str.substring(matcher.end(), str.length())));
			str = str.substring(str.length());
		}
		return str;
	}

	private static String block(List<Token> tokens, int line, int indent, String str) {
		Matcher matcher = Pattern.compile("^block\\s").matcher(str);
		if (matcher.find()) {
			tokens.add(new Token(Types.BLOCK, line, indent, str.substring(matcher.end(), str.length())));
			str = str.substring(str.length());
		}
		return str;
	}

	private static String append(List<Token> tokens, int line, int indent, String str) {
		Matcher matcher = Pattern.compile("^append\\s").matcher(str);
		if (matcher.find()) {
			tokens.add(new Token(Types.APPEND, line, indent, str.substring(matcher.end(), str.length())));
			str = str.substring(str.length());
		}
		return str;
	}

	private static String include(List<Token> tokens, int line, int indent, String str) {
		Matcher matcher = Pattern.compile("^include\\s").matcher(str);
		if (matcher.find()) {
			tokens.add(new Token(Types.INCLUDE, line, indent, str.substring(matcher.end(), str.length())));
			str = str.substring(str.length());
		}
		return str;
	}

	private static String ifCondition(List<Token> tokens, int line, int indent, String str) {
		Matcher matcher = Pattern.compile("^if\\s").matcher(str);
		if (matcher.find()) {
			tokens.add(new Token(Types.CODE_IF, line, indent, str.substring(matcher.end(), str.length())));
			str = str.substring(str.length());
		}
		return str;
	}

	private static String elseCondition(List<Token> tokens, int line, int indent, String str) {
		Matcher matcher = Pattern.compile("^else\\s").matcher(str);
		if (matcher.find()) {
			tokens.add(new Token(Types.CODE_ELSE, line, indent, str.substring(matcher.end(), str.length())));
			str = str.substring(str.length());
		}
		return str;
	}

	private static String each(List<Token> tokens, int line, int indent, String str) {
		Matcher matcher = Pattern.compile("^each\\s").matcher(str);
		if (matcher.find()) {
			tokens.add(new Token(Types.CODE_EACH, line, indent, str.substring(matcher.end(), str.length())));
			str = str.substring(str.length());
		}
		return str;
	}


	private static String pipe(List<Token> tokens, int line, int indent, String str) {
		Matcher matcher = Pattern.compile("^\\|\\s.*").matcher(str);
		if (matcher.find()) {
			String value = str.substring(matcher.start() + 2, matcher.end());
			tokens.add(new Token(Types.PIPE, line, indent, value));
			str = str.substring(matcher.end());
		}
		return str;
	}

	private static String doctype(List<Token> tokens, int line, String str) {
		Matcher matcher = Pattern.compile("^doctype\\s").matcher(str);
		if (matcher.find()) {
			tokens.add(new Token(Types.DOCTYPE, line, 0, str.substring(matcher.end(), str.length())));
			str = str.substring(str.length());
		}
		return str;
	}

	private static String attributesEnd(List<Token> tokens, int line, String str) {
		Matcher matcher = Pattern.compile("^\\)").matcher(str);
		if (matcher.find()) {
			tokens.add(new Token(Types.ATTRIBUTES_END, line, 0));
			str = str.substring(matcher.end());
		}
		return str;
	}

	private static String attributesStart(List<Token> tokens, int line, String str) {
		Matcher matcher = Pattern.compile("^\\($").matcher(str);
		if (matcher.find()) {
			tokens.add(new Token(Types.ATTRIBUTES_START, line, 0));
			str = str.substring(matcher.end());
		}
		return str;
	}

	private static boolean attributesStart(List<Token> tokens) {
		for (int i = tokens.size() - 1; i >= 0; i--) {
			Token token = tokens.get(i);
			if (token.type() == Types.ATTRIBUTES_END) {
				return false;
			}
			if (token.type() == Types.ATTRIBUTES_START) {
				return true;
			}
		}
		return false;
	}


	private static String name(List<Token> tokens, int line, int indent, String str) {
		Matcher matcher = Pattern.compile("^[\\w-]+").matcher(str);
		if (matcher.find()) {
			String value = str.substring(matcher.start(), matcher.end());
			tokens.add(new Token(Types.ELEMENT_NAME, line, indent, value));
			str = str.substring(matcher.end());
		} else if (str.matches("^[.#].*")) {
			tokens.add(new Token(Types.ELEMENT_NAME, line, indent, "div"));
		}
		return str;
	}

	private static String id(List<Token> tokens, int line, String str) {
		Matcher matcher = Pattern.compile("^#[\\w-]+").matcher(str);
		if (matcher.find()) {
			String value = str.substring(matcher.start(), matcher.end());
			tokens.add(new Token(Types.ATTRIBUTE_KEY, line, 0, "id"));
			tokens.add(new Token(Types.ATTRIBUTE_TEXT_VALUE, line, 0, value.substring(1)));
			str = str.substring(matcher.end());
		}
		return str;
	}

	private static String dot(List<Token> tokens, int line, String str) {
		Matcher matcher = Pattern.compile("^\\.[\\w-]+").matcher(str);
		if (matcher.find()) {
			String value = str.substring(matcher.start(), matcher.end());
			tokens.add(new Token(Types.ATTRIBUTE_KEY, line, 0, "class"));
			tokens.add(new Token(Types.ATTRIBUTE_TEXT_VALUE, line, 0, value.substring(1)));
			str = str.substring(matcher.end());
		}
		return str;
	}

	private static String attributes(List<Token> tokens, int line, String str) {
		Matcher matcher = Pattern.compile("^\\(").matcher(str);
		if (matcher.find()) {
			str = attributeKey(tokens, line, 0, str.substring(matcher.end()));
		}
		return str;
	}

	private static String attributeKey(List<Token> tokens, int line, int indent, String str) {
		Matcher matcher = Pattern.compile("^[\\w-]+").matcher(str);
		if (matcher.find()) {
			String value = str.substring(matcher.start(), matcher.end());
			tokens.add(new Token(Types.ATTRIBUTE_KEY, line, indent, value));
			str = attributeValue(tokens, line, str.substring(matcher.end()));
		}
		return str;
	}

	private static String attributeValue(List<Token> tokens, int line, String str) {
		Matcher matcher;
		if ((matcher = Pattern.compile("^='.*?[^\\\\]'").matcher(str)).find()) {

			String value;
			value = str.substring(matcher.start() + 1, matcher.end());
			value = value.substring(1, value.length() - 1);
			value = value.replaceAll("\"", "'");
			value = value.replaceAll("\\\\", "");
			tokens.add(new Token(Types.ATTRIBUTE_TEXT_VALUE, line, 0, value));
			str = str.substring(matcher.end());

		} else if ((matcher = Pattern.compile("^=\".*?\"").matcher(str)).find()) {

			String value;
			value = str.substring(matcher.start() + 1, matcher.end());
			value = value.substring(1, value.length() - 1);
			value = value.replaceAll("'", "\"");
			tokens.add(new Token(Types.ATTRIBUTE_TEXT_VALUE, line, 0, value));
			str = str.substring(matcher.end());

		} else if ((matcher = Pattern.compile("^=\\w+(?:\\.\\w+)*").matcher(str)).find()) {

			String value = str.substring(matcher.start() + 1, matcher.end());
			tokens.add(new Token(Types.ATTRIBUTE_MODEL_VALUE, line, 0, value));
			str = str.substring(matcher.end());

		}

		return str.startsWith(")") ? str.substring(1) : str.isEmpty() ? str : attributeNext(tokens, line, str);
	}

	private static String attributeNext(List<Token> tokens, int line, String str) {
		Matcher matcher = Pattern.compile("^\\s").matcher(str);
		if (matcher.find()) {
			str = attributeKey(tokens, line, 0, str.substring(matcher.end()));
		}
		return str;
	}

	private static String textValue(List<Token> tokens, int line, String str) {
		Matcher matcher = Pattern.compile("^\\s.*").matcher(str);
		if (matcher.find()) {
			String value = str.substring(matcher.start() + 1, matcher.end());
			tokens.add(new Token(Types.ELEMENT_TEXT_VALUE, line, 0, value));
			str = str.substring(str.length());
		}
		return str;
	}

	private static String modelValue(List<Token> tokens, int line, String str) {
		Matcher matcher = Pattern.compile("^=\\s?(.*)").matcher(str);
		if (matcher.find()) {
			tokens.add(new Token(Types.ELEMENT_MODEL_VALUE, line, 0, matcher.group(1)));
			str = str.substring(str.length());
		}
		return str;
	}
}
