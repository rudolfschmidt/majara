package com.rudolfschmidt.majara.stringTransformers.elementTransformer;

import com.rudolfschmidt.majara.transformers.ModelTransformer;
import com.rudolfschmidt.majara.models.Node;

class EachElementCompiler {

	private static final String EACH = "each";

	static boolean compile(Node node, StringBuilder sb, String prefix, boolean pretty, ModelTransformer modelCompiler) {
		if (node.getValue().equals(EACH)) {
			final String[] arr = node.getNodes().getFirst().getValue().split(" in ");
			final Object modelValue = modelCompiler.getObject(arr[1]);
			if (Iterable.class.isInstance(modelValue)) {
				for (Object iterableValue : Iterable.class.cast(modelValue)) {
					modelCompiler.addModel(arr[0], iterableValue);
					for (Node n : node.getNodes()) {
						ElementTransformer.transform(n, sb, prefix + "\t", pretty, modelCompiler);
					}
					modelCompiler.removeModel(arr[0]);
				}
			}
			return true;
		}
		return false;
	}
}
