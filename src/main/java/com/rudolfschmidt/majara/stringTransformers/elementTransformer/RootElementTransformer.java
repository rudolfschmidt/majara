package com.rudolfschmidt.majara.stringTransformers.elementTransformer;

import com.rudolfschmidt.majara.models.Node;
import com.rudolfschmidt.majara.transformers.ModelTransformer;

public class RootElementTransformer {
	public static void transform(Node node, StringBuilder sb, boolean pretty, ModelTransformer modelCompiler) {
		if (DocTypeElementCompiler.compile(node, sb, pretty)) return;
		ElementTransformer.transform(node, sb, "", pretty, modelCompiler);
	}
}
