package com.rudolfschmidt.majara.typedNodeTransformers;

import com.rudolfschmidt.majara.models.Node;
import com.rudolfschmidt.majara.models.NodeType;

import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class IdClassTransformer {

	static String idClassAttributes(Deque<Node> nodes, String str) {
		while (str.startsWith("#") || str.startsWith(".")) {
			str = idAttribute(nodes, str);
			str = classAttribute(nodes, str);
		}
		return str;
	}

	private static String idAttribute(Deque<Node> nodes, String str) {
		final Matcher matcher = Pattern.compile("^#[\\w-]+").matcher(str);
		if (matcher.find()) {
			String value = str.substring(matcher.start(), matcher.end());
			nodes.add(new Node(NodeType.ATTRIBUTE_KEY, "id"));
			nodes.add(new Node(NodeType.ATTRIBUTE_TEXT_VALUE, value.substring(1)));
			str = str.substring(matcher.end());
		}
		return str;
	}

	private static String classAttribute(Deque<Node> nodes, String str) {
		final Matcher matcher = Pattern.compile("^\\.[\\w-]+").matcher(str);
		if (matcher.find()) {
			String value = str.substring(matcher.start(), matcher.end());
			nodes.add(new Node(NodeType.ATTRIBUTE_KEY, "class"));
			nodes.add(new Node(NodeType.ATTRIBUTE_TEXT_VALUE, value.substring(1)));
			str = str.substring(matcher.end());
		}
		return str;
	}
}
