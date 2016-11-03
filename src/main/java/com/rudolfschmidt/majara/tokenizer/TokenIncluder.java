package com.rudolfschmidt.majara.tokenizer;

import com.rudolfschmidt.majara.Code;
import com.rudolfschmidt.majara.tokens.Token;
import com.rudolfschmidt.majara.tokens.TokenType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Link tokens interpreting keyword include, apply optional filter
 */
public class TokenIncluder {

	static void includeTokens(Tokenizer tokenizer) {
		parse(tokenizer, tokenizer.getTokens());
	}

	private static void parse(Tokenizer tokenizer, List<Token> tokens) {
		tokens.stream()
				.filter(tok -> tok.getType() == TokenType.ELEMENT)
				.filter(tok -> Code.INCLUDE.equals(tok.getValue()))
				.findAny()
				.ifPresent(token -> {

					Optional<Path> path = token.getTokens().stream()
							.filter(tok -> tok.getType() == TokenType.ELEMENT_TEXT_VALUE)
							.findAny()
							.map(Token::getValue)
							.map(tokenizer::getPath);

					boolean plain = token.getTokens().stream()
							.filter(tok -> tok.getType() == TokenType.FILTER)
							.filter(tok -> "plain".equals(tok.getValue()))
							.findAny()
							.isPresent();

					if (plain) {
						Token val = Token.newInstance(TokenType.FILTERED_TEXT_VALUE);
						path.ifPresent(p -> {
							try {
								val.setValue(new String(Files.readAllBytes(p)));
							} catch (IOException e) {
								throw new RuntimeException(e);
							}
						});
						token.getTokens().add(val);
					} else {
						path.ifPresent(p -> token.getTokens().addAll(Tokenizer.tokenize(p)));
					}
				});
		tokens.stream().forEachOrdered(tok -> parse(tokenizer, tok.getTokens()));
	}
}
