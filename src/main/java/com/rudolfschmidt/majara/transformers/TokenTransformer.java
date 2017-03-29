package com.rudolfschmidt.majara.transformers;

import com.rudolfschmidt.majara.models.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TokenTransformer {

	static List<Token> transform(List<String> pathLines) {
		final List<Token> tokens = new ArrayList<>();
		for (int index = 0; index < pathLines.size(); index++) {
			final String pathLine = pathLines.get(index);
			compile(tokens, index + 1, pathLine);
		}
		return tokens;
	}

	private static void compile(List<Token> tokens, int line, String str) {
		if (str.matches("\\s*")) {
			return;
		}
		int indent = 0;
		final Matcher matcher = Pattern.compile("^\\s*").matcher(str);
		if (matcher.find()) {
			indent = matcher.end();
			str = str.substring(matcher.end());
		}
		tokens.add(new Token(line, indent, str));
	}
}
