package com.rudolfschmidt.majara.models;

import java.util.ArrayDeque;
import java.util.Deque;

public class Node {

	private final NodeType type;
	private final String value;
	private final Deque<Node> nodes;

	public Node(NodeType type, String value) {
		this.type = type;
		this.value = value;
		this.nodes = new ArrayDeque<>();
	}

	public Node(String value) {
		this(null, value);
	}

	public NodeType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public Deque<Node> getNodes() {
		return nodes;
	}

	@Override
	public String toString() {
		return toString("");
	}

	private String toString(String prefix) {
		final StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		sb.append(type);
		sb.append(" : ");
		sb.append(value);
		for (Node node : nodes) {
			sb.append("\n");
			sb.append(node.toString(prefix + "\t"));
		}
		return sb.toString();
	}
}
