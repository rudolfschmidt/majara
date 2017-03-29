package com.rudolfschmidt.majara.stringTransformers.attributeTransformer;

import com.rudolfschmidt.majara.transformers.ModelTransformer;
import com.rudolfschmidt.majara.models.Node;
import com.rudolfschmidt.majara.models.NodeType;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;

public class AttributeTransformer {

	private final ModelTransformer modelCompiler;
	private final Deque<Attribute> attributes;

	public AttributeTransformer(ModelTransformer modelCompiler) {
		this.modelCompiler = modelCompiler;
		this.attributes = new ArrayDeque<>();
	}

	public void compile(Node node) {
		if (node.getType() == NodeType.ATTRIBUTE_KEY) {
			final Attribute attribute = new Attribute(node.getValue());
			attributes.add(attribute);
		}
		if (node.getType() == NodeType.ATTRIBUTE_TEXT_VALUE) {
			final String modelString = modelCompiler.getString(node.getValue());
			attributes.getLast().getValues().add(modelString);
		}

		if (node.getType() == NodeType.ATTRIBUTE_MODEL_VALUE) {
			final Object compiled = modelCompiler.getObject(node.getValue());
			attributes.getLast().getValues().add(compiled != null ? compiled.toString() : "");
		}
	}

	public String build() {
		final Deque<Attribute> posts = new ArrayDeque<>();

		for (Attribute pre : attributes) {
			boolean add = true;
			for (Attribute post : posts) {
				if (pre.getName().equals(post.getName())) {
					post.getValues().addAll(pre.getValues());
					add = false;
				}
			}
			if (add) posts.add(pre);
		}

		final StringBuilder sb = new StringBuilder();
		for (Attribute post : posts) {
			sb.append(" ").append(post.getName());
			if (!post.getValues().isEmpty()) {
				sb.append("=\"");
				sb.append(post.getValues().stream().collect(Collectors.joining(" ")));
				sb.append("\"");
			}
		}
		return sb.toString();
	}
}
