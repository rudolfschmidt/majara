package com.rudolfschmidt.majara.v2.compiler;

import com.rudolfschmidt.majara.Model;
import com.rudolfschmidt.majara.v2.tokens.Structure;

import java.util.List;

public class MainCompiler {

	public static MainCompiler instance(List<Structure> structures, Model model, boolean pretty) {
		ModelCompiler modelCompiler = new ModelCompiler(model);
		MainCompiler mainCompiler = new MainCompiler(structures, pretty);
		LayoutCompiler layoutCompiler = new LayoutCompiler(mainCompiler, modelCompiler);
		mainCompiler.codeCompiler = new CodeCompiler(model, modelCompiler, layoutCompiler);
		mainCompiler.layoutCompiler = layoutCompiler;
		return mainCompiler;
	}

	private final List<Structure> structures;
	private final boolean pretty;
	private CodeCompiler codeCompiler;
	private LayoutCompiler layoutCompiler;

	private MainCompiler(List<Structure> structures, boolean pretty) {
		this.structures = structures;
		this.pretty = pretty;
	}

	public String compile() {
		StringBuilder sb = new StringBuilder();
		structures.forEach(s -> {
			compile(s, sb);
			if (pretty) {
				sb.append("\n");
			}
		});
		return sb.toString();
	}

	void compile(Structure structure, StringBuilder sb) {
		codeCompiler.compile(structure, sb);
		layoutCompiler.compile(structure, sb);
	}
}
