package com.rudolfschmidt.majara.states;

import com.rudolfschmidt.majara.tokenizer.Tokenizer;
import com.rudolfschmidt.majara.tokens.Token;
import com.rudolfschmidt.majara.tokens.TokenType;

class Name implements State {

	@Override
	public void onChar(Tokenizer tokenizer, StateCharacter character) {

		if (character.isLetterOrDigit() || character.isDash()) {
			tokenizer.getLast().addValue(character.getCharacter());
			return;
		}

		if (character.isSpace()) {
			tokenizer.addToken(Token.newInstance(TokenType.ELEMENT_TEXT_VALUE));
			tokenizer.setState(new TextValue());
			return;
		}

		if (character.isDot()) {
			tokenizer.addToken(Token.newInstance(TokenType.ATTRIBUTE, "class"));
			tokenizer.addToken(Token.newInstance(TokenType.ATTRIBUTE_TEXT_VALUE));
			tokenizer.setState(new StyleClass());
			return;
		}

		if (character.isOpenBracket()) {
			tokenizer.setState(new AttributesStart());
			return;
		}

		if (character.isHash()) {
			tokenizer.addToken(Token.newInstance(TokenType.ATTRIBUTE, "id"));
			tokenizer.addToken(Token.newInstance(TokenType.ATTRIBUTE_TEXT_VALUE));
			tokenizer.setState(new Id());
			return;
		}

		if (character.isEqual()) {
			tokenizer.addToken(Token.newInstance(TokenType.ELEMENT_MODEL_VALUE));
			tokenizer.setState(new ModelValue());
			return;
		}

		if (character.isColon()) {
			tokenizer.addToken(Token.newInstance(TokenType.FILTER));
			tokenizer.setState(new Filter());
			return;
		}
	}

	@Override
	public void onLineEnd(Tokenizer tokenizer) {
		tokenizer.setState(new StartLine());
	}
}
