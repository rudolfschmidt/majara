package com.rudolfschmidt.majara;

import com.rudolfschmidt.majara.compiler.TokenCompiler;
import com.rudolfschmidt.majara.tokenizer.Tokenizer;
import com.rudolfschmidt.majara.tokens.Token;

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
		return render(templateFile, Model.newInstance());
	}

	public String render(String templateFile, Model model) {
		String suffix = templateSuffix.startsWith(".") ? templateSuffix : "." + templateSuffix;
		Path path = Paths.get(templateDirectory, templateFile + suffix);
		List<Token> tokens = Tokenizer.tokenize(path);
		tokens.forEach(token -> LOGGER.config(token.toString()));
		return TokenCompiler.newInstance(model, pretty).compile(tokens);
	}
}
