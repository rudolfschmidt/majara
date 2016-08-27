package com.rudolfschmidt.view.states;

import com.rudolfschmidt.view.tokenizer.Tokenizer;
import com.rudolfschmidt.view.tokens.Token;
import com.rudolfschmidt.view.tokens.TokenType;

class PipeExpected implements State {

    private final int indent;

    public PipeExpected(int indent) {
        this.indent = indent;
    }

    @Override
    public void onChar(Tokenizer tokenizer, StateCharacter character) {

        if (character.isSpace()) {
            tokenizer.addToken(indent, Token.newInstance(TokenType.ELEMENT_TEXT_VALUE));
            tokenizer.setState(new TextValue());
        }

    }

    @Override
    public void onLineEnd(Tokenizer tokenizer) {
        tokenizer.setState(new StartLine());
    }
}
