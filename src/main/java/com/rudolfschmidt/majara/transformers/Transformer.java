package com.rudolfschmidt.majara.transformers;

import com.rudolfschmidt.majara.Model;
import com.rudolfschmidt.majara.models.Node;
import com.rudolfschmidt.majara.stringTransformers.elementTransformer.RootElementTransformer;
import com.rudolfschmidt.majara.typedNodeTransformers.TypedNodeTransformer;

import java.nio.file.Path;
import java.util.Deque;

public class Transformer {

	public static String compile(Path templatePath, Model model, boolean pretty) {

		Deque<Node> nodes;
		nodes = NodeTransformer.transform(templatePath, model);
		nodes = new TypedNodeTransformer().transform(nodes);

//		System.out.println("#################");
//		System.out.println("TYPED_NODES");
//		System.out.println("#################");
//		for (Node n : nodes) {
//			System.out.println(n);
//		}

		final StringBuilder sb = new StringBuilder();
		for (Node node : nodes) {
			RootElementTransformer.transform(node, sb, pretty, new ModelTransformer(model));
		}
		return sb.toString();
	}
}
