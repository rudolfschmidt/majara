package com.rudolfschmidt.majara.states;

import com.rudolfschmidt.majara.tokenizer.Tokenizer;
import com.rudolfschmidt.majara.tokens.Token;
import com.rudolfschmidt.majara.tokens.TokenType;

public class Id implements State {
    @Override
    public void onChar(Tokenizer tokenizer, StateCharacter character) {

        if (character.isOpenBracket()) {
            tokenizer.setState(new AttributesStart());
            return;
        }

        if (character.isDot()) {
            tokenizer.addToken(Token.newInstance(TokenType.ATTRIBUTE, "class"));
            tokenizer.addToken(Token.newInstance(TokenType.ATTRIBUTE_TEXT_VALUE));
            tokenizer.setState(new StyleClass());
            return;
        }

        if (character.isLetter() || character.isDash() || character.isUnderline()) {
            tokenizer.getLast().addValue(character.getCharacter());
            return;
        }
    }

    @Override
    public void onLineEnd(Tokenizer tokenizer) {
        tokenizer.setState(new StartLine());
    }
}
