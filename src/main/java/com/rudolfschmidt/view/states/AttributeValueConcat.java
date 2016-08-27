package com.rudolfschmidt.view.states;

import com.rudolfschmidt.view.tokenizer.Tokenizer;
import com.rudolfschmidt.view.tokens.Token;
import com.rudolfschmidt.view.tokens.TokenType;

public class AttributeValueConcat implements State {
    @Override
    public void onChar(Tokenizer tokenizer, StateCharacter character) {

        if (character.isLetter() || character.isDash()) {
            tokenizer.addToken(Token.newInstance(TokenType.ATTRIBUTE, character.getCharacter()));
            tokenizer.setState(new AttributeName());
            return;
        }

        if (character.isPlus()) {
            tokenizer.setState(new AttributeValue());
            return;
        }
    }

    @Override
    public void onLineEnd(Tokenizer tokenizer) {

    }
}
