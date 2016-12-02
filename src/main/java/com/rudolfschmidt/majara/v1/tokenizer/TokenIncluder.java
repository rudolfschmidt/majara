package com.rudolfschmidt.majara.v1.tokenizer;

import com.rudolfschmidt.majara.Code;
import com.rudolfschmidt.majara.v1.tokens.Token;
import com.rudolfschmidt.majara.v1.tokens.TokenType;

import java.io.IOException;
import java.io.UncheckedIOException;
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
				.forEachOrdered(token -> {

					Optional<Path> template = token.getTokens().stream()
							.filter(tok -> tok.getType() == TokenType.ELEMENT_TEXT_VALUE)
							.findAny()
							.map(Token::getValue)
							.map(tokenizer::getPath);

					boolean isFilterPlain = token.getTokens().stream()
							.filter(tok -> tok.getType() == TokenType.FILTER)
							.filter(tok -> "plain".equals(tok.getValue()))
							.findAny()
							.isPresent();

					if (isFilterPlain) {
						Token val = Token.newInstance(TokenType.FILTERED_TEXT_VALUE);
						template.ifPresent(path -> {
							try {
								val.setValue(new String(Files.readAllBytes(path)));
							} catch (IOException e) {
								throw new UncheckedIOException(e);
							}
						});
						token.getTokens().add(val);
					} else {
						template.ifPresent(p -> token.getTokens().addAll(Tokenizer.tokenize(p)));
					}
				});
		tokens.stream().forEachOrdered(tok -> parse(tokenizer, tok.getTokens()));
	}
}
