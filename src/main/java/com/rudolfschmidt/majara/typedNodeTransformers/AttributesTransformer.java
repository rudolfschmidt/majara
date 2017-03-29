package com.rudolfschmidt.majara.typedNodeTransformers;

import com.rudolfschmidt.majara.models.Node;

import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rudolfschmidt.majara.typedNodeTransformers.AttributeKeyTransformer.attributeKey;

class AttributesTransformer {
	static String normalAttributes(Deque<Node> nodes, String str) {
		final Matcher matcher = Pattern.compile("^\\(").matcher(str);
		if (matcher.find()) {
			str = attributeKey(nodes, str.substring(matcher.end()));
		}
		return str;
	}
}
