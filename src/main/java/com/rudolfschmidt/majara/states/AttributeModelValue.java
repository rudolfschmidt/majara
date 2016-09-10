package com.rudolfschmidt.majara.states;

import com.rudolfschmidt.majara.tokenizer.Tokenizer;

public class AttributeModelValue implements State {
    @Override
    public void onChar(Tokenizer tokenizer, StateCharacter character) {

        if (character.isCloseBracket()) {
            tokenizer.setState(new AttributesEnd());
            return;
        }

        if (character.isSpace()) {
            tokenizer.setState(new AttributeValueConcat());
            return;
        }

        if (character.isLetter() || character.isDot()) {
            tokenizer.getLast().addValue(character.getCharacter());
            return;
        }
    }

    @Override
    public void onLineEnd(Tokenizer tokenizer) {
        tokenizer.setState(new AttributeNextLine());
    }
}
