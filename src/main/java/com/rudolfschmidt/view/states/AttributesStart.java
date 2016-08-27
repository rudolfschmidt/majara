package com.rudolfschmidt.view.states;

import com.rudolfschmidt.view.tokenizer.Tokenizer;
import com.rudolfschmidt.view.tokens.Token;
import com.rudolfschmidt.view.tokens.TokenType;

public class AttributesStart implements State {

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
    }

    @Override
    public void onLineEnd(Tokenizer tokenizer) {
        tokenizer.setState(new AttributeNextLine());
    }
}
