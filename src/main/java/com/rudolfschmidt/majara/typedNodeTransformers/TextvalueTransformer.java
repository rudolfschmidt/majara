package com.rudolfschmidt.majara.typedNodeTransformers;

import com.rudolfschmidt.majara.models.Node;
import com.rudolfschmidt.majara.models.NodeType;

import java.util.Deque;

class TextvalueTransformer {
	static String textValue(Deque<Node> nodes, String str) {
		if (str.matches("\\s.*")) {
			final String value = str.substring(1);
			nodes.add(new Node(NodeType.ELEMENT_TEXT_VALUE, value));
			str = str.substring(str.length());
		}
		return str;
	}
}
