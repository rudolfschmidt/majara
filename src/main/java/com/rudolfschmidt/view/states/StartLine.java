package com.rudolfschmidt.view.states;

import com.rudolfschmidt.view.tokenizer.Tokenizer;
import com.rudolfschmidt.view.tokens.Token;
import com.rudolfschmidt.view.tokens.TokenType;

public class StartLine implements State {

    private int indent;

    public StartLine() {
    }

    public StartLine(int indent) {
        this.indent = indent;
    }

    @Override
    public void onChar(Tokenizer tokenizer, StateCharacter character) {

        if (character.isTab()) {
            indent++;
            return;
        }

        if (character.isLetter()) {
            Token token = Token.newInstance(TokenType.ELEMENT, character.getCharacter());
            tokenizer.addToken(indent, token);
            tokenizer.setState(new Name());
            return;
        }

        if (character.isDot()) {
            tokenizer.addToken(indent, Token.newInstance(TokenType.ELEMENT, "div"));
            tokenizer.addToken(Token.newInstance(TokenType.ATTRIBUTE, "class"));
            tokenizer.addToken(Token.newInstance(TokenType.ATTRIBUTE_TEXT_VALUE));
            tokenizer.setState(new StyleClass());
            return;
        }

        if (character.isSlash()) {
            tokenizer.setState(new Slash(indent));
            return;
        }

        if (character.isPipe()) {
            tokenizer.setState(new PipeExpected(indent));
            return;
        }

        if (character.isHash()) {
            tokenizer.addToken(Token.newInstance(TokenType.ELEMENT, "div"));
            tokenizer.addToken(Token.newInstance(TokenType.ATTRIBUTE, "id"));
            tokenizer.addToken(Token.newInstance(TokenType.ATTRIBUTE_TEXT_VALUE));
            tokenizer.setState(new Id());
            return;
        }
    }

    @Override
    public void onLineEnd(Tokenizer tokenizer) {
        tokenizer.setState(new StartLine());
    }
}
