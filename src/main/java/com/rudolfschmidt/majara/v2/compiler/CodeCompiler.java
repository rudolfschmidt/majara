package com.rudolfschmidt.majara.v2.compiler;

import com.rudolfschmidt.majara.Model;
import com.rudolfschmidt.majara.Possible;
import com.rudolfschmidt.majara.v2.tokens.Structure;
import com.rudolfschmidt.majara.v2.tokens.Types;

import java.util.Collections;
import java.util.Optional;

class CodeCompiler {

	private final Model model;
	private final ModelCompiler modelCompiler;
	private final LayoutCompiler layoutCompiler;

	public CodeCompiler(Model model, ModelCompiler modelCompiler, LayoutCompiler layoutCompiler) {
		this.model = model;
		this.modelCompiler = modelCompiler;
		this.layoutCompiler = layoutCompiler;
	}

	public void compile(Structure structure, StringBuilder sb) {
		if (structure.token().type() == Types.CODE_IF) {
			String value = structure.token().value();
			if (value.startsWith("!")) {
				Optional<Object> model = modelCompiler.compileModel(value.substring(1));
				Possible
						.of(model)
						.ifNotPresent(() -> layoutCompiler.compileElementChildren(structure.structures(), sb));
				model
						.filter(v -> Boolean.class.equals(v.getClass()))
						.map(Object::toString)
						.map(Boolean::valueOf)
						.filter(b -> !b)
						.ifPresent(v -> layoutCompiler.compileElementChildren(structure.structures(), sb));
				model
						.filter(v -> String.class.equals(v.getClass()))
						.map(Object::toString)
						.filter(String::isEmpty)
						.ifPresent(v -> layoutCompiler.compileElementChildren(structure.structures(), sb));
			} else {
				Optional<Object> model = modelCompiler.compileModel(value);
				model
						.filter(v -> !Boolean.class.equals(v.getClass()))
						.filter(v -> !String.class.equals(v.getClass()))
						.ifPresent(v -> layoutCompiler.compileElementChildren(structure.structures(), sb));
				model
						.filter(v -> Boolean.class.equals(v.getClass()))
						.map(Object::toString)
						.map(Boolean::valueOf)
						.filter(b -> b)
						.ifPresent(v -> layoutCompiler.compileElementChildren(structure.structures(), sb));
				model
						.filter(v -> String.class.equals(v.getClass()))
						.map(Object::toString)
						.filter(str -> !str.isEmpty())
						.ifPresent(v -> layoutCompiler.compileElementChildren(structure.structures(), sb));
			}
		}
		if (structure.token().type() == Types.CODE_EACH) {
			compileIterable(structure, sb);
		}
	}

	@SuppressWarnings("unchecked")
	private void compileIterable(Structure structure, StringBuilder sb) {
		String[] arr = structure.token().value().split(" in ");
		modelCompiler.compileModel(arr[1])
				.filter(Iterable.class::isInstance)
				.map(Iterable.class::cast)
				.orElseGet(Collections::emptyList)
				.forEach(item -> {
					model.put(arr[0], item);
					layoutCompiler.compileElementChildren(structure.structures(), sb);
					model.remove(arr[0]);
				});
	}
}
