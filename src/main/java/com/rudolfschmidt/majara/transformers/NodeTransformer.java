package com.rudolfschmidt.majara.transformers;

import com.rudolfschmidt.majara.Model;
import com.rudolfschmidt.majara.models.Node;
import com.rudolfschmidt.majara.models.Token;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Deque;
import java.util.List;

public class NodeTransformer {

	public static Deque<Node> transform(Path templatePath, Model model) {

		final List<String> templateLines;
		try {
			templateLines = Files.readAllLines(templatePath);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}

		final List<Token> tokens = TokenTransformer.transform(templateLines);

		Deque<Node> nodes;
		nodes = NodeBuilder.transform(tokens);
//		nodes = CommentsRemover.transform(nodes);
//		nodes = IncludeInterpreter.transform(templatePath, model, nodes);
//		nodes = ExtendsInterpreter.transform(templatePath, model, nodes);
//		nodes = IfInterpreter.transform(new ModelTransformer(model), nodes);

//		System.out.println("#################");
//		System.out.println("NODES");
//		System.out.println("#################");
//		for (Node node : nodes) {
//			System.out.println(node);
//		}

		return nodes;
	}
}
