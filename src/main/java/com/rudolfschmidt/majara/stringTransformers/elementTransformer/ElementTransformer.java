package com.rudolfschmidt.majara.stringTransformers.elementTransformer;

import com.rudolfschmidt.majara.transformers.ModelTransformer;
import com.rudolfschmidt.majara.models.Node;

class ElementTransformer {
	public static void transform(Node node, StringBuilder sb, String prefix, boolean pretty, ModelTransformer modelCompiler) {
		if (EachElementCompiler.compile(node, sb, prefix, pretty, modelCompiler)) return;
		NameElementCompiler.compile(node, sb, prefix, pretty, modelCompiler);
	}
}
