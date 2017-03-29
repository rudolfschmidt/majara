package com.rudolfschmidt.majara.stringTransformers.elementTransformer;

import com.rudolfschmidt.majara.models.Node;
import com.rudolfschmidt.majara.models.NodeType;
import com.rudolfschmidt.majara.stringTransformers.ModelValueTransformer;
import com.rudolfschmidt.majara.stringTransformers.TextValueTransformer;
import com.rudolfschmidt.majara.stringTransformers.attributeTransformer.AttributeTransformer;
import com.rudolfschmidt.majara.transformers.ModelTransformer;

import java.util.Arrays;
import java.util.List;

class NameElementCompiler {

	private static final List<String> VOIDS;

	static {
		VOIDS = Arrays.asList(
			"area",
			"base",
			"br",
			"col",
			"command",
			"embed",
			"hr",
			"img",
			"input",
			"keygen",
			"link",
			"meta",
			"param",
			"source",
			"track",
			"wbr"
		);
	}

	public static void compile(Node node, StringBuilder sb,
							   String prefix, boolean pretty,
							   ModelTransformer modelCompiler) {

		if (node.getType() != NodeType.ELEMENT_NAME) {
			return;
		}

		if (pretty) {
			sb.append(prefix);
		}

		sb.append("<");
		sb.append(node.getValue());

		final AttributeTransformer attributesCompiler = new AttributeTransformer(modelCompiler);
		node.getNodes().forEach(attributesCompiler::compile);
		sb.append(attributesCompiler.build());

		sb.append(">");

		if (VOIDS.contains(node.getValue())) {
			if (pretty) {
				sb.append("\n");
			}
			return;
		}

		if (pretty && hasElements(node)) {
			sb.append("\n");
		}

		for (Node n : node.getNodes()) {
			ElementTransformer.transform(n, sb, prefix + "\t", pretty, modelCompiler);
			TextValueTransformer.transform(n, sb, modelCompiler);
			ModelValueTransformer.transform(n, sb, modelCompiler);
		}

		if (pretty && hasElements(node)) {
			sb.append(prefix);
		}

		sb.append("</");
		sb.append(node.getValue());
		sb.append(">");

		if (pretty) {
			sb.append("\n");
		}
	}

	private static boolean hasElements(Node node) {
		for (Node n : node.getNodes()) {
			if (n.getType() == NodeType.ELEMENT_NAME) {
				return true;
			}
		}
		return false;
	}
}
