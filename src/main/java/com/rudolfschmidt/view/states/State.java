package com.rudolfschmidt.view.states;

import com.rudolfschmidt.view.tokenizer.Tokenizer;

public interface State {
    void onChar(Tokenizer tokenizer, StateCharacter character);
    void onLineEnd(Tokenizer tokenizer);
}
