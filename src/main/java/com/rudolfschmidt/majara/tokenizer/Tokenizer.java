package com.rudolfschmidt.majara.tokenizer;

import com.rudolfschmidt.majara.states.StartLine;
import com.rudolfschmidt.majara.states.State;
import com.rudolfschmidt.majara.states.StateCharacter;
import com.rudolfschmidt.majara.tokens.TokenType;
import com.rudolfschmidt.majara.tokens.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Compiles file into tokens
 */
public class Tokenizer {

    public static List<Token> tokenize(Path path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Tokenizer tokenizer = Tokenizer.newInstance(path);
        for (String line : lines) {
            for (char c : line.toCharArray()) {
                tokenizer.onChar(StateCharacter.valueOf(c));
            }
            tokenizer.onLineEnd();
        }
        TokenLinker.linkTokens(tokenizer);
        return tokenizer.getTokens();
    }

    private static Tokenizer newInstance(Path path) {
        return new Tokenizer(path, new ArrayList<>(), new StartLine());
    }

    private final Path path;
    private List<Token> tokens;
    private State state;

    private Tokenizer(Path path, List<Token> tokens, State state) {
        this.path = path;
        this.tokens = tokens;
        this.state = state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Path getPath(String value) {
        return path.getParent().resolve(value + Optional.ofNullable(path)
                .map(Path::getFileName)
                .map(Path::toString)
                .map(str -> str.substring(str.lastIndexOf(".")))
                .orElseThrow(NullPointerException::new));
    }

    public void onChar(StateCharacter character) {
        state.onChar(this, character);
    }

    public void onLineEnd() {
        state.onLineEnd(this);
    }

    public void addToken(Token token) {
        if (token.getType() == TokenType.ELEMENT) {
            addToken(tokens, token, 0, 0);
            return;
        }
        getLastElement().getTokens().add(token);
    }


    public void addToken(int indent, Token token) {
        addToken(tokens, token, indent, 0);
    }

    public Token getLast() {
        return getLast(tokens);
    }

    private Token getLast(List<Token> tokens) {
        Token last = tokens.get(tokens.size() - 1);
        if (last.getTokens().isEmpty()) {
            return last;
        }
        return getLast(last.getTokens());
    }

    public Token getLastElement() {
        return getLastElement(tokens);
    }

    private Token getLastElement(List<Token> tokens) {
        Token token = tokens.get(tokens.size() - 1);
        if (token.getTokens().stream().noneMatch(tok -> tok.getType() == TokenType.ELEMENT)) {
            return token;
        }
        return getLastElement(token.getTokens());
    }

    private void addToken(List<Token> tokens, Token token, int indent, int iterated) {
        if (tokens.isEmpty() || indent == iterated) {
            tokens.add(token);
            return;
        }
        addToken(tokens.get(tokens.size() - 1).getTokens(), token, indent, ++iterated);
    }
}
