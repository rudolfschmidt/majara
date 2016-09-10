package com.rudolfschmidt.majara.states;

import com.rudolfschmidt.majara.tokenizer.Tokenizer;

public class ModelValue implements State {
    @Override
    public void onChar(Tokenizer tokenizer, StateCharacter character) {
        if (character.isLetter() || character.isDot()) {
            tokenizer.getLast().addValue(character.getCharacter());
        }
    }
    @Override
    public void onLineEnd(Tokenizer tokenizer) {
        tokenizer.setState(new StartLine());
    }
}
