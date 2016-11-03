package com.rudolfschmidt.majara.states;

import com.rudolfschmidt.majara.tokenizer.Tokenizer;
import com.rudolfschmidt.majara.tokens.Token;
import com.rudolfschmidt.majara.tokens.TokenType;

public class Filter implements State {

	@Override
	public void onChar(Tokenizer tokenizer, StateCharacter character) {

		if (character.isLetter() || character.isDot()) {
			tokenizer.getLast().addValue(character.getCharacter());
			return;
		}

		if (character.isSpace()) {
			tokenizer.addToken(Token.newInstance(TokenType.ELEMENT_TEXT_VALUE));
			tokenizer.setState(new TextValue());
			return;
		}

	}

	@Override
	public void onLineEnd(Tokenizer tokenizer) {
	}
}
