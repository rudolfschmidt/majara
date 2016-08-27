package com.rudolfschmidt.view.states;

import com.rudolfschmidt.view.tokenizer.Tokenizer;

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
