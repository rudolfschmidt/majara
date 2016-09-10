package com.rudolfschmidt.majara.states;

import com.rudolfschmidt.majara.tokenizer.Tokenizer;
import com.rudolfschmidt.majara.tokens.Token;
import com.rudolfschmidt.majara.tokens.TokenType;

public class AttributeName implements State {

    @Override
    public void onChar(Tokenizer tokenizer, StateCharacter character) {

        if (character.isSpace()) {
            tokenizer.addToken(Token.newInstance(TokenType.ATTRIBUTE));
            return;
        }

        if (character.isLetter() || character.isDash()) {
            tokenizer.getLast().addValue(character.getCharacter());
            return;
        }

        if (character.isEqual()) {
            tokenizer.setState(new AttributeValue());
            return;
        }

        if (character.isCloseBracket()) {
            tokenizer.setState(new AttributesEnd());
            return;
        }

    }

    @Override
    public void onLineEnd(Tokenizer tokenizer) {
        tokenizer.setState(new AttributeNextLine());
    }
}
