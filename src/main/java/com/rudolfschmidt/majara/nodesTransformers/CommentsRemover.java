package com.rudolfschmidt.majara.nodesTransformers;

import com.rudolfschmidt.majara.models.Node;

import java.util.ArrayDeque;
import java.util.Deque;

public class CommentsRemover {
	public static Deque<Node> transform(Deque<Node> pres) {
		final Deque<Node> posts = new ArrayDeque<>();
		for (Node pre : pres) {
			if (!pre.getValue().startsWith("//")) {
				final Node post = new Node(pre.getValue());
				posts.add(post);
				post.getNodes().addAll(transform(pre.getNodes()));
			}
		}
		return posts;
	}
}
