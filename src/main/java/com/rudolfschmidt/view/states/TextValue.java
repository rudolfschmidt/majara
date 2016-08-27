package com.rudolfschmidt.view.states;

import com.rudolfschmidt.view.Code;
import com.rudolfschmidt.view.tokenizer.Tokenizer;

import java.nio.file.Path;

public class TextValue implements State {
    @Override
    public void onChar(Tokenizer tokenizer, StateCharacter character) {
        tokenizer.getLast().addValue(character.getCharacter());
    }
    @Override
    public void onLineEnd(Tokenizer tokenizer) {
        if (Code.INCLUDE.equals(tokenizer.getLastElement().getValue())) {
            Path path = tokenizer.getPath(tokenizer.getLast().getValue());
            tokenizer.getLastElement().getTokens().addAll(Tokenizer.tokenize(path));
        }
        tokenizer.setState(new StartLine());
    }
}
