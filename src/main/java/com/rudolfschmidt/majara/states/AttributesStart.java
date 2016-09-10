package com.rudolfschmidt.majara.states;

import com.rudolfschmidt.majara.tokenizer.Tokenizer;
import com.rudolfschmidt.majara.tokens.Token;
import com.rudolfschmidt.majara.tokens.TokenType;

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
