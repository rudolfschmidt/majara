package com.rudolfschmidt.majara.v2.tokenizer;

import com.rudolfschmidt.majara.v2.tokens.Types;
import com.rudolfschmidt.majara.v2.tokens.Structure;
import com.rudolfschmidt.majara.v2.tokens.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Structures {

	public static List<Structure> valueOf(List<Token> tokens) {
		List<Structure> structures = new ArrayList<>();
		for (Token token : tokens) {
			if (token.type() == Types.DOCTYPE) {
				add(structures, token, 0);
			} else if (token.type() == Types.ELEMENT_NAME) {
				add(structures, token, 0);
			} else if (token.type() == Types.ATTRIBUTE_KEY) {
				add(structures, token);
			} else if (token.type() == Types.ATTRIBUTE_TEXT_VALUE) {
				add(structures, token);
			} else if (token.type() == Types.ATTRIBUTE_MODEL_VALUE) {
				add(structures, token);
			} else if (token.type() == Types.ELEMENT_TEXT_VALUE) {
				add(structures, token);
			} else if (token.type() == Types.ELEMENT_MODEL_VALUE) {
				add(structures, token);
			} else if (token.type() == Types.PIPE) {
				add(structures, token, 0);
			} else if (token.type() == Types.COMMENT) {
				add(structures, token, 0);
			} else if (token.type() == Types.INCLUDE) {
				add(structures, token, 0);
			} else if (token.type() == Types.EXTENDS) {
				add(structures, token, 0);
			} else if (token.type() == Types.BLOCK) {
				add(structures, token, 0);
			} else if (token.type() == Types.APPEND) {
				add(structures, token, 0);
			} else if (token.type() == Types.CODE_IF) {
				add(structures, token, 0);
			} else if (token.type() == Types.CODE_ELSE) {
				add(structures, token, 0);
			} else if (token.type() == Types.CODE_EACH) {
				add(structures, token, 0);
			}
		}
		return structures;
	}

	// add element to last element if structure is empty or line is reached
	private static void add(List<Structure> structures, Token token, int count) {
		if (structures.isEmpty() || token.indent() == count) {
			structures.add(new Structure(token));
		} else {
			add(structures.get(structures.size() - 1).structures(), token, ++count);
		}
	}

	// add element to last element if structure is empty or line is reached
	private static void add(List<Structure> structures, Token token) {
		List<Structure> children = structures.get(structures.size() - 1).structures();
		for (Structure structure : children) {
			Types[] natures = {Types.ELEMENT_NAME, Types.COMMENT,
					Types.INCLUDE, Types.CODE_IF, Types.CODE_ELSE, Types.CODE_EACH};
			if (Arrays.asList(natures).contains(structure.token().type())) {
				add(children, token);
				return;
			}
		}
		children.add(new Structure(token));
	}
}
