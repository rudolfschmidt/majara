package com.rudolfschmidt.majara.typedNodeTransformers;

import com.rudolfschmidt.majara.models.Node;
import com.rudolfschmidt.majara.models.NodeType;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rudolfschmidt.majara.typedNodeTransformers.AttributeKeyTransformer.attributeKey;
import static com.rudolfschmidt.majara.typedNodeTransformers.AttributesTransformer.normalAttributes;
import static com.rudolfschmidt.majara.typedNodeTransformers.IdClassTransformer.idClassAttributes;
import static com.rudolfschmidt.majara.typedNodeTransformers.ModelValueTransformer.modelValue;
import static com.rudolfschmidt.majara.typedNodeTransformers.TextvalueTransformer.textValue;

public class TypedNodeTransformer {

	private boolean attGroup = false;

	public Deque<Node> transform(Deque<Node> pres) {
		final Deque<Node> posts = new ArrayDeque<>();
		for (Node pre : pres) {
			String str = pre.getValue();
			if (attGroup) {
				str = attributeKey(posts, str);
				str = attributesGroupEnd(str);
				str = textValue(posts.getLast().getNodes(), str);
				str = modelValue(posts.getLast().getNodes(), str);
			} else {
				str = pipeValue(posts, pre, str);
				str = classIdElement(posts, pre, str);
				str = element(posts, pre, str);
			}
		}
		return posts;
	}

	private String pipeValue(Deque<Node> posts, Node pre, String str) {
		final Matcher matcher = Pattern.compile("^\\|").matcher(str);
		if (matcher.find()) {
			str = str.substring(matcher.end());
			str = textValue(posts, str);
			str = modelValue(posts, str);
			return str;
		}
		return str;
	}

	private String classIdElement(Deque<Node> posts, Node pre, String str) {
		final Matcher matcher = Pattern.compile("^[.#].*$").matcher(str);
		if (matcher.find()) {
			elementName("div", posts, pre.getNodes(), str);
			return str.substring(matcher.end());
		}
		return str;
	}

	private String element(Deque<Node> posts, Node pre, String str) {
		final Matcher matcher = Pattern.compile("^[\\w-]+").matcher(str);
		if (matcher.find()) {
			final String name = str.substring(matcher.start(), matcher.end());
			final String value = str.substring(matcher.end());
			elementName(name, posts, pre.getNodes(), value);
			return str.substring(matcher.end());
		}
		return str;
	}

	private void elementName(String name, Deque<Node> posts, Deque<Node> pres, String str) {
		final Node post = new Node(NodeType.ELEMENT_NAME, name);
		str = idClassAttributes(post.getNodes(), str);
		str = attributesGroupStart(str);
		str = normalAttributes(post.getNodes(), str);
		str = textValue(post.getNodes(), str);
		str = modelValue(post.getNodes(), str);
		post.getNodes().addAll(transform(pres));
		posts.add(post);
	}

	private String attributesGroupEnd(String str) {
		final Matcher matcher = Pattern.compile("^\\)").matcher(str);
		if (matcher.find()) {
			attGroup = false;
			str = str.substring(1);
		}
		return str;
	}

	private String attributesGroupStart(String str) {
		if (str.matches("\\(")) {
			attGroup = true;
			str = str.substring(1);
		}
		return str;
	}
}
