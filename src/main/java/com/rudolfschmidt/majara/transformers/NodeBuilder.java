package com.rudolfschmidt.majara.transformers;

import com.rudolfschmidt.majara.exceptions.LexerException;
import com.rudolfschmidt.majara.models.Node;
import com.rudolfschmidt.majara.models.Token;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;

class NodeBuilder {

	static Deque<Node> transform(List<Token> tokens) {
		final Deque<Node> nodes = new ArrayDeque<>();
		for (Token token : tokens) {
			add(nodes, token, 0);
		}
		return nodes;
	}

	private static void add(Deque<Node> nodes, Token token, int count) {
		if (token.getIndent() == count) {
			nodes.add(new Node(token.getValue()));
		} else {
			final Node last;
			try {
				last = nodes.getLast();
			} catch (NoSuchElementException e) {
				throw new LexerException(token.getLine());
			}
			add(last.getNodes(), token, ++count);
		}
	}
}
