package com.rudolfschmidt.majara.states;

import com.rudolfschmidt.majara.tokenizer.Tokenizer;

public class Comment implements State {

    private final int indent;

    public Comment(int indent) {
        this.indent = indent;
    }

    @Override
    public void onChar(Tokenizer tokenizer, StateCharacter character) {

    }

    @Override
    public void onLineEnd(Tokenizer tokenizer) {
        tokenizer.setState(new CommentNextLine(indent));
    }
}
