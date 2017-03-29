package com.rudolfschmidt.majara.nodesTransformers;

import com.rudolfschmidt.majara.Model;
import com.rudolfschmidt.majara.models.Node;
import com.rudolfschmidt.majara.transformers.NodeTransformer;

import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;

public class IncludeInterpreter {
	private static final String INCLUDE = "include ";

	public static Deque<Node> transform(Path templatePath, Model model, Deque<Node> pres) {
		final Deque<Node> posts = new ArrayDeque<>();
		for (Node pre : pres) {
			if (pre.getValue().startsWith(INCLUDE)) {
				final String fileName = pre.getValue().substring(INCLUDE.length());
				final String fileSuffix = extractSuffix(templatePath);
				final Path normalized = templatePath.getParent().resolve(fileName.concat(fileSuffix));
				posts.addAll(NodeTransformer.transform(normalized, model));
			} else {
				final Node post = new Node(pre.getValue());
				posts.add(post);
				post.getNodes().addAll(transform(templatePath, model, pre.getNodes()));
			}
		}
		return posts;
	}

	static String extractSuffix(Path templatePath) {
		final String fileName = templatePath.getFileName().toString();
		return fileName.substring(fileName.lastIndexOf("."));
	}
}
