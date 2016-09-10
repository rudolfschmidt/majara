package com.rudolfschmidt.majara.states;

import com.rudolfschmidt.majara.tokenizer.Tokenizer;
import com.rudolfschmidt.majara.tokens.Token;
import com.rudolfschmidt.majara.tokens.TokenType;

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
