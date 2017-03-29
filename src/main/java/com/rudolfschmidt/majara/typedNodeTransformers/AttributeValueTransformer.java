package com.rudolfschmidt.majara.typedNodeTransformers;

import com.rudolfschmidt.majara.models.Node;
import com.rudolfschmidt.majara.models.NodeType;

import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rudolfschmidt.majara.typedNodeTransformers.AttributeKeyTransformer.attributeKey;

class AttributeValueTransformer {

	static String attributeValue(Deque<Node> nodes, String str) {

		Matcher matcher;

		if ((matcher = Pattern.compile("^='.*?[^\\\\]'").matcher(str)).find()) {

			String value;
			value = str.substring(matcher.start() + 1, matcher.end());
			value = value.substring(1, value.length() - 1);
			value = value.replaceAll("\"", "'");
			value = value.replaceAll("\\\\", "");
			nodes.add(new Node(NodeType.ATTRIBUTE_TEXT_VALUE, value));
			str = str.substring(matcher.end());

		} else if ((matcher = Pattern.compile("^=\".*?\"").matcher(str)).find()) {

			String value;
			value = str.substring(matcher.start() + 1, matcher.end());
			value = value.substring(1, value.length() - 1);
			value = value.replaceAll("'", "\"");
			nodes.add(new Node(NodeType.ATTRIBUTE_TEXT_VALUE, value));
			str = str.substring(matcher.end());

		} else if ((matcher = Pattern.compile("^=\\w+(?:\\.\\w+)*").matcher(str)).find()) {

			String value = str.substring(matcher.start() + 1, matcher.end());
			nodes.add(new Node(NodeType.ATTRIBUTE_MODEL_VALUE,value));
			str = str.substring(matcher.end());

		}

		return str.startsWith(")")
			? str.substring(1)
			: str.isEmpty()
			? str
			: attributeNext(nodes, str);

	}

	private static String attributeNext(Deque<Node> nodes, String str) {
		final Matcher matcher = Pattern.compile("^\\s").matcher(str);
		if (matcher.find()) {
			str = attributeKey(nodes, str.substring(matcher.end()));
		}
		return str;
	}
}
