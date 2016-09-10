package com.rudolfschmidt.majara.states;

import com.rudolfschmidt.majara.tokenizer.Tokenizer;
import com.rudolfschmidt.majara.tokens.Token;
import com.rudolfschmidt.majara.tokens.TokenType;

public class AttributesEnd implements State {
    @Override
    public void onChar(Tokenizer tokenizer, StateCharacter character) {

        if (character.isSpace()) {
            tokenizer.addToken(Token.newInstance(TokenType.ELEMENT_TEXT_VALUE));
            tokenizer.setState(new TextValue());
            return;
        }

        if (character.isEqual()) {
            tokenizer.addToken(Token.newInstance(TokenType.ELEMENT_MODEL_VALUE));
            tokenizer.setState(new ModelValue());
            return;
        }

    }

    @Override
    public void onLineEnd(Tokenizer tokenizer) {
        tokenizer.setState(new StartLine());
    }
}