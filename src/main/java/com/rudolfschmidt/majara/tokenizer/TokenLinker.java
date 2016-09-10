package com.rudolfschmidt.majara.tokenizer;

import com.rudolfschmidt.majara.Code;
import com.rudolfschmidt.majara.tokens.Token;
import com.rudolfschmidt.majara.tokens.TokenType;

import java.nio.file.Path;
import java.util.List;

public class TokenLinker {

    static void linkTokens(Tokenizer tokenizer) {
        tokenizer.getTokens().stream().findFirst()
                .filter(token -> Code.EXTENDS.equals(token.getValue()))
                .ifPresent(token -> {
                    Path path = tokenizer.getPath(token.getTokens().get(0).getValue());
                    List<Token> tokens = Tokenizer.tokenize(path);
                    iterate(tokens, tokenizer.getTokens());
                    tokenizer.setTokens(tokens);
                });
    }

    private static void iterate(List<Token> a, List<Token> b) {
        a.stream()
                .filter(TokenLinker::isElement)
                .filter(TokenLinker::isBlock)
                .forEachOrdered(x -> {
                    b.stream()
                            .filter(TokenLinker::isElement)
                            .filter(TokenLinker::isBlock)
                            .filter(y -> valuesEqual(x, y))
                            .forEachOrdered(y -> x.setTokens(y.getTokens()));
                    b.stream()
                            .filter(TokenLinker::isElement)
                            .filter(TokenLinker::isAppend)
                            .filter(y -> valuesEqual(x, y))
                            .forEachOrdered(y -> x.getTokens().addAll(y.getTokens()));
                    b.stream().forEachOrdered(y -> iterate(a, y.getTokens()));
                });
        a.stream().forEachOrdered(x -> iterate(x.getTokens(), b));
    }

    private static boolean isAppend(Token token) {
        return token.getValue().equals(Code.APPEND);
    }

    private static boolean isBlock(Token token) {
        return token.getValue().equals(Code.BLOCK);
    }

    private static boolean isElement(Token token) {
        return token.getType() == TokenType.ELEMENT;
    }

    private static boolean valuesEqual(Token a, Token b) {
        return a.getTokens().stream().findFirst().filter(x -> b.getTokens().stream().findFirst().filter(y -> x.getValue().equals(y.getValue())).isPresent()).isPresent();
    }
}