package com.rudolfschmidt.view.states;

import com.rudolfschmidt.view.tokenizer.Tokenizer;
import com.rudolfschmidt.view.tokens.Token;
import com.rudolfschmidt.view.tokens.TokenType;

public class AttributeValue implements State {
    @Override
    public void onChar(Tokenizer tokenizer, StateCharacter character) {

        if (character.isPrime() || character.isDoublePrime()) {
            tokenizer.addToken(Token.newInstance(TokenType.ATTRIBUTE_TEXT_VALUE));
            tokenizer.setState(new AttributeTextValue(character.getCharacter()));
            return;
        }

        if (character.isLetter()) {
            Token token = Token.newInstance(TokenType.ATTRIBUTE_MODEL_VALUE, character.getCharacter());
            tokenizer.addToken(token);
            tokenizer.setState(new AttributeModelValue());
            return;
        }
    }

    @Override
    public void onLineEnd(Tokenizer tokenizer) {

    }
}
