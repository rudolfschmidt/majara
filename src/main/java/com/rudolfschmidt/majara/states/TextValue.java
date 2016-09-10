package com.rudolfschmidt.majara.states;

import com.rudolfschmidt.majara.Code;
import com.rudolfschmidt.majara.tokenizer.Tokenizer;

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
