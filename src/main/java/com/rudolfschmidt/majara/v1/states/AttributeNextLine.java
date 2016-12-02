package com.rudolfschmidt.majara.v1.states;

import com.rudolfschmidt.majara.v1.tokenizer.Tokenizer;
import com.rudolfschmidt.majara.v1.tokens.Token;
import com.rudolfschmidt.majara.v1.tokens.TokenType;

public class AttributeNextLine implements State {

    private int indent;

    @Override
    public void onChar(Tokenizer tokenizer, StateCharacter character) {

        if (character.isTab()) {
            indent++;
            return;
        }

        if (character.isLetter() || character.isDash()) {
            tokenizer.addToken(Token.newInstance(TokenType.ATTRIBUTE));
            tokenizer.setState(new AttributeName());
            tokenizer.onChar(character);
            return;
        }

        if (character.isCloseBracket()) {
            tokenizer.setState(new AttributesEnd());
            return;
        }

    }

    @Override
    public void onLineEnd(Tokenizer tokenizer) {

    }
}
