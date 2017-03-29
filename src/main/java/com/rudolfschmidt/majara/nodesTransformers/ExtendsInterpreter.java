package com.rudolfschmidt.majara.nodesTransformers;

import com.rudolfschmidt.majara.Model;
import com.rudolfschmidt.majara.models.Node;
import com.rudolfschmidt.majara.transformers.NodeTransformer;

import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;

import static com.rudolfschmidt.majara.nodesTransformers.IncludeInterpreter.extractSuffix;

public class ExtendsInterpreter {
	private static final String EXTENDS = "extends ";
	private static final String BLOCK = "block ";
	private static final String APPEND = "append ";

	public static Deque<Node> transform(Path templatePath, Model model, Deque<Node> pres) {
		final Deque<Node> posts = new ArrayDeque<>();
		for (Node pre : pres) {
			if (pre.getValue().startsWith(EXTENDS)) {
				final String fileName = pre.getValue().substring(EXTENDS.length());
				final String fileSuffix = extractSuffix(templatePath);
				final Path normalized = templatePath.getParent().resolve(fileName.concat(fileSuffix));
				final Deque<Node> extended = NodeTransformer.transform(normalized, model);
				extended(extended, pres, posts);
				return posts;
			} else {
				final Node post = new Node(pre.getValue());
				posts.add(post);
				post.getNodes().addAll(transform(templatePath, model, pre.getNodes()));
			}
		}
		return posts;
	}

	private static void extended(Deque<Node> templateNodes, Deque<Node> pres, Deque<Node> posts) {
		for (Node templateNode : templateNodes) {
			if (templateNode.getValue().startsWith(BLOCK)) {
				for (Node pre : pres) {
					if (pre.getValue().startsWith(BLOCK)) {
						final String templateBlockName = templateNode.getValue().substring(BLOCK.length());
						final String preName = pre.getValue().substring(BLOCK.length());
						if (templateBlockName.equals(preName)) {
							posts.addAll(pre.getNodes());
						}
					}
				}
				for (Node pre : pres) {
					if (pre.getValue().startsWith(APPEND)) {
						final String templateBlockName = templateNode.getValue().substring(BLOCK.length());
						final String preName = pre.getValue().substring(APPEND.length());
						if (templateBlockName.equals(preName)) {
							posts.addAll(pre.getNodes());
						}
					}
				}
			} else {
				final Node post = new Node(templateNode.getValue());
				posts.add(post);
				extended(templateNode.getNodes(), pres, post.getNodes());
			}
		}
	}
}
