package com.rudolfschmidt.view.states;

import com.rudolfschmidt.view.tokenizer.Tokenizer;
import com.rudolfschmidt.view.tokens.Token;
import com.rudolfschmidt.view.tokens.TokenType;

public class StyleClass implements State {

    @Override
    public void onChar(Tokenizer tokenizer, StateCharacter character) {

        if (character.isLetterOrDigit() || character.isDash()) {
            tokenizer.getLast().addValue(character.getCharacter());
            return;
        }

        if (character.isDot()) {
            tokenizer.addToken(Token.newInstance(TokenType.ATTRIBUTE, "class"));
            tokenizer.addToken(Token.newInstance(TokenType.ATTRIBUTE_TEXT_VALUE));
            return;
        }

        if (character.isSpace()) {
            tokenizer.addToken(Token.newInstance(TokenType.ELEMENT_TEXT_VALUE));
            tokenizer.setState(new TextValue());
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

    }

    @Override
    public void onLineEnd(Tokenizer tokenizer) {
        tokenizer.setState(new StartLine());
    }
}