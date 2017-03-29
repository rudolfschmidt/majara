package com.rudolfschmidt.majara;

import com.rudolfschmidt.majara.transformers.Transformer;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Majara {

	private final String fileDirectory;
	private final String fileSuffix;
	private final boolean pretty;

	public Majara(String fileDirectory, String fileSuffix, boolean pretty) {
		this.fileDirectory = fileDirectory;
		this.fileSuffix = fileSuffix;
		this.pretty = pretty;
	}

	public String render(String templateFile) {
		return render(templateFile, Model.createModel());
	}

	public String render(String templateFile, Model model) {
		final String suffix = fileSuffix.startsWith(".") ? fileSuffix : ".".concat(fileSuffix);
		final Path templatePath = Paths.get(fileDirectory, templateFile.concat(suffix));
		return Transformer.compile(templatePath, model, pretty);
	}
}
