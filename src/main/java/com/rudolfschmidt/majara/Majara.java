package com.rudolfschmidt.majara;

import com.rudolfschmidt.majara.v2.compiler.MainCompiler;
import com.rudolfschmidt.majara.v2.tokens.Structure;
import com.rudolfschmidt.majara.v2.tokenizer.Tokenizer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

public class Majara {

	private static final Logger LOGGER = Logger.getLogger(Majara.class.getName());

	private final String templateDirectory;
	private final String templateSuffix;
	private final boolean pretty;

	public Majara(String templateDirectory, String templateSuffix, boolean pretty) {
		this.templateDirectory = templateDirectory;
		this.templateSuffix = templateSuffix;
		this.pretty = pretty;
	}

	public String render(String templateFile) {
		return render(templateFile, Model.get());
	}

	public String render(String templateFile, Model model) {

		String suffix = templateSuffix.startsWith(".") ? templateSuffix : "." + templateSuffix;
		Path path = Paths.get(templateDirectory, templateFile + suffix);

		List<Structure> structures = Tokenizer.tokenize(path);

		String html = MainCompiler.instance(structures, model, pretty).compile();
		return html;

//		tokens.forEach(token -> LOGGER.config(token.toString()));
//		return TokenCompiler.newInstance(model, pretty).compile(tokens);
	}
}
