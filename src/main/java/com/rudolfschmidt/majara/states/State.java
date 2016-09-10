package com.rudolfschmidt.majara.states;

import com.rudolfschmidt.majara.tokenizer.Tokenizer;

public interface State {
    void onChar(Tokenizer tokenizer, StateCharacter character);
    void onLineEnd(Tokenizer tokenizer);
}
