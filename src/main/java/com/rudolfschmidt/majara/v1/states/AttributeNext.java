package com.rudolfschmidt.majara.v1.states;

import com.rudolfschmidt.majara.v1.tokenizer.Tokenizer;
import com.rudolfschmidt.majara.v1.tokens.Token;
import com.rudolfschmidt.majara.v1.tokens.TokenType;

public class AttributeNext implements State {

    @Override
    public void onChar(Tokenizer tokenizer, StateCharacter character) {

        if (character.isCloseBracket()) {
            tokenizer.setState(new AttributesEnd());
            return;
        }

        if (character.isLetter() || character.isDash()) {
            tokenizer.addToken(Token.newInstance(TokenType.ATTRIBUTE, character.getCharacter()));
            tokenizer.setState(new AttributeName());
            return;
        }

        if (character.isSpace()) {
            tokenizer.setState(new AttributeValueConcat());
            return;
        }

    }

    @Override
    public void onLineEnd(Tokenizer tokenizer) {
        tokenizer.setState(new AttributeNextLine());
    }
}
