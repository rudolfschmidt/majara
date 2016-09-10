package com.rudolfschmidt.majara.states;

import com.rudolfschmidt.majara.tokenizer.Tokenizer;
import com.rudolfschmidt.majara.tokens.Token;
import com.rudolfschmidt.majara.tokens.TokenType;

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
