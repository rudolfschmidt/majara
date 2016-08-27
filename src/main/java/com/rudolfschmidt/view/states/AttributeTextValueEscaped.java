package com.rudolfschmidt.view.states;

import com.rudolfschmidt.view.tokenizer.Tokenizer;

public class AttributeTextValueEscaped implements State {

    private final char prime;

    public AttributeTextValueEscaped(char prime) {
        this.prime = prime;
    }

    @Override
    public void onChar(Tokenizer tokenizer, StateCharacter character) {

        if (character.getCharacter() == prime) {
            tokenizer.getLast().addValue(character.getCharacter());
            tokenizer.setState(new AttributeTextValue(prime));
            return;
        }

        tokenizer.getLast().addValue('\\');
        tokenizer.getLast().addValue(character.getCharacter());
        tokenizer.setState(new AttributeTextValue(prime));

    }

    @Override
    public void onLineEnd(Tokenizer tokenizer) {

    }
}
