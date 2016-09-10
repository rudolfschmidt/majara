package com.rudolfschmidt.majara.states;

import com.rudolfschmidt.majara.tokenizer.Tokenizer;

public class Slash implements State {

    private final int indent;

    public Slash(int indent) {
        this.indent = indent;
    }

    @Override
    public void onChar(Tokenizer tokenizer, StateCharacter character) {

        if (character.isSlash()) {
            tokenizer.setState(new Comment(indent));
            return;
        }

    }

    @Override
    public void onLineEnd(Tokenizer tokenizer) {

    }
}
