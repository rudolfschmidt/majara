package com.rudolfschmidt.majara.v1.states;

import com.rudolfschmidt.majara.v1.tokenizer.Tokenizer;

public interface State {
    void onChar(Tokenizer tokenizer, StateCharacter character);
    void onLineEnd(Tokenizer tokenizer);
}
