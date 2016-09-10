package com.rudolfschmidt.majara.states;

import com.rudolfschmidt.majara.tokenizer.Tokenizer;

public class CommentNextLine implements State {

    private final int indent;
    private int iterated;

    public CommentNextLine(int indent) {
        this.indent = indent;
    }

    @Override
    public void onChar(Tokenizer tokenizer, StateCharacter character) {

        if (character.isTab()) {
            iterated++;
            return;
        }

        if (indent < iterated) {
            return;
        }

        tokenizer.setState(new StartLine(iterated));
        tokenizer.onChar(character);

    }

    @Override
    public void onLineEnd(Tokenizer tokenizer) {
        iterated = 0;
    }
}
