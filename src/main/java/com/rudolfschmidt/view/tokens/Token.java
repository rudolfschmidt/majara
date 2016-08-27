package com.rudolfschmidt.view.tokens;

import java.util.ArrayList;
import java.util.List;

public class Token {

    public static Token newInstance(TokenType tokenType) {
        return new Token(tokenType, null, new ArrayList<>());
    }

    public static Token newInstance(TokenType tokenType, char character) {
        return new Token(tokenType, String.valueOf(character), new ArrayList<>());
    }

    public static Token newInstance(TokenType tokenType, String value) {
        return new Token(tokenType, value, new ArrayList<>());
    }

    private TokenType type;
    private String value;
    private List<Token> tokens;

    private Token(TokenType type, String value, List<Token> tokens) {
        this.type = type;
        this.value = value;
        this.tokens = tokens;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void addValue(char character) {
        if (value == null) {
            value = "";
        }
        value += character;
    }

    @Override
    public String toString() {
        return string("");
    }

    private String string(String prefix) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append(type);
        if (value != null) {
            sb.append(" : ").append(value);
        }
        tokens.forEach(token -> sb.append("\n").append(token.string(prefix + "\t")));
        return sb.toString();
    }
}
