package com.rudolfschmidt.view;

import com.mappointer.view.compiler.TokenCompiler;
import com.mappointer.view.tokenizer.Tokenizer;
import com.mappointer.view.tokens.Token;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

public class TemplateEngine {

    private static final Logger LOGGER = Logger.getLogger(TemplateEngine.class.getName());

    private final String base;
    private final String suffix;
    private final boolean pretty;

    public TemplateEngine(String base, String suffix, boolean pretty) {
        this.base = base;
        this.suffix = suffix;
        this.pretty = pretty;
    }

    public String render(String template, Model model) {
        return compile(template, model);
    }

    private String compile(String template, Model model) {
        String suffix = this.suffix.startsWith(".") ? this.suffix : "." + this.suffix;
        Path path = Paths.get(base, template + suffix);
        List<Token> tokens = Tokenizer.tokenize(path);
        tokens.forEach(token -> LOGGER.config(token.toString()));
        return TokenCompiler.newInstance(model, pretty).compile(tokens);
    }
}
