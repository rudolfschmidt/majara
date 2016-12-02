package com.rudolfschmidt.majara.v2.compiler;

import com.rudolfschmidt.majara.v2.tokens.Attribute;
import com.rudolfschmidt.majara.v2.tokens.Structure;
import com.rudolfschmidt.majara.v2.tokens.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class LayoutCompiler {

	private final MainCompiler mainCompiler;
	private final ModelCompiler modelCompiler;

	public LayoutCompiler(MainCompiler mainCompiler, ModelCompiler modelCompiler) {
		this.mainCompiler = mainCompiler;
		this.modelCompiler = modelCompiler;
	}

	public void compile(Structure structure, StringBuilder sb) {

		if (structure.token().type() == Types.DOCTYPE) {
			sb.append(Elements.DOCTYPES.get(structure.token().value()));
		}

		if (structure.token().type() == Types.ELEMENT_NAME) {
			sb.append("<");
			sb.append(structure.token().value());
			compileAttributes(sb, structure);
			sb.append(">");

			compileElementChildren(structure.structures(), sb);

			if (Elements.VOIDS.contains(structure.token().value())) {
				return;
			}
			sb.append("</");
			sb.append(structure.token().value());
			sb.append(">");
		}

		if (structure.token().type() == Types.ELEMENT_TEXT_VALUE) {
			sb.append(modelCompiler.replaceModel(structure.token().value()));
		}

		if (structure.token().type() == Types.ELEMENT_MODEL_VALUE) {
			sb.append(modelCompiler.compileModel(structure.token().value()).map(Object::toString).orElse(""));
		}

		if (structure.token().type() == Types.INCLUDE) {
			compileElementChildren(structure.structures(), sb);
		}

		if (structure.token().type() == Types.BLOCK) {
			compileElementChildren(structure.structures(), sb);
		}
	}

	public void compileElementChildren(List<Structure> structures, StringBuilder sb) {
		boolean pipe = false;
		for (Structure structure : structures) {
			if (structure.token().type() == Types.PIPE) {
				if (pipe) {
					sb.append(" ");
				}
				sb.append(structure.token().value());
				pipe = true;
			} else {
				mainCompiler.compile(structure, sb);
				pipe = false;
			}
		}
	}

	private void compileAttributes(StringBuilder sb, Structure structure) {
		List<Attribute> attributes = new ArrayList<>();
		Optional<Attribute> attribute = Optional.empty();
		for (Structure s : structure.structures()) {
			if (s.token().type() == Types.ATTRIBUTE_KEY) {
				Optional<Attribute> last = findLastAttribute(attributes, s);
				if (last.isPresent()) {
					attribute = last;
				} else {
					Attribute a = new Attribute(s.token().value());
					attribute = Optional.of(a);
					attributes.add(a);
				}
			}
			if (s.token().type() == Types.ATTRIBUTE_TEXT_VALUE) {
				attribute.ifPresent(a -> a.getValues().add(modelCompiler.replaceModel(s.token().value())));
			}
			if (s.token().type() == Types.ATTRIBUTE_MODEL_VALUE) {
				attribute.ifPresent(a -> a.getValues().add(modelCompiler.compileModel(s.token().value())
						.map(Object::toString).orElse("")));
			}
		}
		for (Attribute a : attributes) {
			sb.append(" ");
			sb.append(a.getName());
			for (int i = 0; i < a.getValues().size(); i++) {
				String value = a.getValues().get(i);
				sb.append("=\"");
				sb.append(value);
				break;
			}
			for (int i = 1; i < a.getValues().size(); i++) {
				String value = a.getValues().get(i);
				sb.append(" ");
				sb.append(value);
			}
			if (!a.getValues().isEmpty()) {
				sb.append("\"");
			}
		}
	}

	private Optional<Attribute> findLastAttribute(List<Attribute> attributes, Structure s) {
		for (Attribute a : attributes) {
			if (a.getName().equals(s.token().value())) {
				return Optional.of(a);
			}
		}
		return Optional.empty();
	}
}
