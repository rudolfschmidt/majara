package com.rudolfschmidt.majara;

import com.rudolfschmidt.majara.compiler.TokenCompiler;
import com.rudolfschmidt.majara.tokenizer.Tokenizer;
import com.rudolfschmidt.majara.tokens.Token;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.logging.Logger;

public class Majara implements BiFunction<String, Optional<Object>, String> {

	private static final Logger LOGGER = Logger.getLogger(Majara.class.getName());

	private final String templateDirectory;
	private final String templateSuffix;
	private final boolean pretty;

	public Majara(String templateDirectory, String templateSuffix, boolean pretty) {
		this.templateDirectory = templateDirectory;
		this.templateSuffix = templateSuffix;
		this.pretty = pretty;
	}

	@Override
	public String apply(String template, Optional<Object> model) {
		return compile(template, model.map(Model.class::cast).orElseGet(Model::newInstance));
	}

	public String render(String template) {
		return compile(template, Model.newInstance());
	}

	public String render(String template, Model model) {
		return compile(template, model);
	}

	private String compile(String templateFile, Model model) {
		String suffix = templateSuffix.startsWith(".") ? templateSuffix : "." + templateSuffix;
		Path path = Paths.get(templateDirectory, templateFile + suffix);
		List<Token> tokens = Tokenizer.tokenize(path);
		tokens.forEach(token -> LOGGER.config(token.toString()));
		return TokenCompiler.newInstance(model, pretty).compile(tokens);
	}


}
