package com.rudolfschmidt.view.states;

import com.rudolfschmidt.view.tokenizer.Tokenizer;

public class AttributeTextValue implements State {

    private final char prime;

    public AttributeTextValue(char prime) {
        this.prime = prime;
    }

    @Override
    public void onChar(Tokenizer tokenizer, StateCharacter character) {

        if (character.getCharacter() == prime) {
            tokenizer.setState(new AttributeNext());
            return;
        }

        if (character.isBackSlash()) {
            tokenizer.setState(new AttributeTextValueEscaped(prime));
            return;
        }

        if (prime == '\'' && character.isDoublePrime()) {
            tokenizer.getLast().addValue('\'');
            return;
        }

        if (prime == '"' && character.isPrime()) {
            tokenizer.getLast().addValue('"');
            return;
        }

        tokenizer.getLast().addValue(character.getCharacter());
    }

    @Override
    public void onLineEnd(Tokenizer tokenizer) {
    }
}
