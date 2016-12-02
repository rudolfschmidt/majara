package com.rudolfschmidt.majara.v1.states;

import com.rudolfschmidt.majara.v1.tokenizer.Tokenizer;

public class TextValue implements State {
	@Override
	public void onChar(Tokenizer tokenizer, StateCharacter character) {
		tokenizer.getLast().addValue(character.getCharacter());
	}

	@Override
	public void onLineEnd(Tokenizer tokenizer) {
		tokenizer.setState(new StartLine());
	}
}
