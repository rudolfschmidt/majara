package com.rudolfschmidt.majara.stringTransformers;

import com.rudolfschmidt.majara.transformers.ModelTransformer;
import com.rudolfschmidt.majara.models.Node;
import com.rudolfschmidt.majara.models.NodeType;

public class ModelValueTransformer {
	public static void transform(Node node, StringBuilder sb, ModelTransformer modelCompiler) {
		if (node.getType() != NodeType.ELEMENT_MODEL_VALUE) {
			return;
		}
		final Object modelObject = modelCompiler.getObject(node.getValue());
		if (modelObject != null) {
			sb.append(modelObject.toString());
		}
	}
}
