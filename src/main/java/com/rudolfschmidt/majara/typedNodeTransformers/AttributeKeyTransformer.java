package com.rudolfschmidt.majara.typedNodeTransformers;

import com.rudolfschmidt.majara.models.Node;
import com.rudolfschmidt.majara.models.NodeType;

import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rudolfschmidt.majara.typedNodeTransformers.AttributeValueTransformer.attributeValue;

class AttributeKeyTransformer {
	static String attributeKey(Deque<Node> nodes, String str) {
		final Matcher matcher = Pattern.compile("^[\\w-]+").matcher(str);
		if (matcher.find()) {
			String value = str.substring(matcher.start(), matcher.end());
			nodes.add(new Node(NodeType.ATTRIBUTE_KEY, value));
			str = attributeValue(nodes, str.substring(matcher.end()));
		}
		return str;
	}
}
