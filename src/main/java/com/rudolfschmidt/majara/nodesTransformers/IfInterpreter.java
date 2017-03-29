package com.rudolfschmidt.majara.nodesTransformers;

import com.rudolfschmidt.majara.transformers.ModelTransformer;
import com.rudolfschmidt.majara.models.Node;

import java.util.ArrayDeque;
import java.util.Deque;

public class IfInterpreter {

	private static final String IF = "if ";

	public static Deque<Node> transform(ModelTransformer modelTransformer, Deque<Node> pres) {
		final Deque<Node> posts = new ArrayDeque<>();
		for (Node pre : pres) {
			if (pre.getValue().startsWith(IF)) {
				final String value = pre.getValue().substring(IF.length());
				boolean result;
				if (value.startsWith("!")) {
					result = evaluateNegative(modelTransformer.getObject(value.substring(1)));
				} else {
					result = evaluatePositive(modelTransformer.getObject(value));
				}
				if (result) {
					posts.addAll(pre.getNodes());
				}
			} else {
				final Node post = new Node(pre.getValue());
				posts.add(post);
				post.getNodes().addAll(transform(modelTransformer, pre.getNodes()));
			}
		}
		return posts;
	}

	private static boolean evaluatePositive(Object model) {
		if (model == null) {
			return false;
		}
		if (Boolean.class.equals(model.getClass())) {
			final String modelString = model.toString();
			final Boolean modelBoolean = Boolean.valueOf(modelString);
			if (modelBoolean) {
				return true;
			}
		} else if (String.class.equals(model.getClass())) {
			final String modelString = model.toString();
			if (!modelString.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	private static boolean evaluateNegative(Object model) {
		if (model == null) {
			return true;
		} else if (Boolean.class.equals(model.getClass())) {
			final String modelString = model.toString();
			final Boolean modelBoolean = Boolean.valueOf(modelString);
			if (!modelBoolean) {
				return true;
			}
		} else if (String.class.equals(model.getClass())) {
			final String modelString = model.toString();
			if (modelString.isEmpty()) {
				return true;
			}
		}
		return false;
	}
}
