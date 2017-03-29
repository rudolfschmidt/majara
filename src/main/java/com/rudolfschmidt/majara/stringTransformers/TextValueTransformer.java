package com.rudolfschmidt.majara.stringTransformers;

import com.rudolfschmidt.majara.transformers.ModelTransformer;
import com.rudolfschmidt.majara.models.Node;
import com.rudolfschmidt.majara.models.NodeType;

public class TextValueTransformer {
	public static void transform(Node node, StringBuilder sb, ModelTransformer modelCompiler) {
		if (node.getType() != NodeType.ELEMENT_TEXT_VALUE) {
			return;
		}
		sb.append(modelCompiler.getString(node.getValue()));
	}
}
