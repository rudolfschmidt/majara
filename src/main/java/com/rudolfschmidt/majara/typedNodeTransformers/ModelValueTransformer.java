package com.rudolfschmidt.majara.typedNodeTransformers;

import com.rudolfschmidt.majara.models.Node;
import com.rudolfschmidt.majara.models.NodeType;

import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ModelValueTransformer {
	static String modelValue(Deque<Node> nodes, String str) {
		final Matcher matcher = Pattern.compile("^=\\s?(.*)").matcher(str);
		if (matcher.find()) {
			final String value = matcher.group(1);
			nodes.add(new Node(NodeType.ELEMENT_MODEL_VALUE,value));
			str = str.substring(str.length());
		}
		return str;
	}
}
