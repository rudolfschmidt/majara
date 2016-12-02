package com.rudolfschmidt.majara.v2.tokenizer;

import com.rudolfschmidt.majara.v2.tokens.Types;
import com.rudolfschmidt.majara.v2.tokens.Structure;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

class IncludeLinker {

	public static List<Structure> link(Path path, List<Structure> structures) {

		structures.stream()
				.filter(s -> Types.INCLUDE.equals(s.token().type()))
				.filter(s -> s.structures().isEmpty())
				.forEach(s -> Optional.of(path)
						.map(Path::getParent)
						.map(p -> p.resolve(s.token().value() + Linker.suffix(path)))
						.map(Tokenizer::tokenize)
						.ifPresent(s.structures()::addAll));

		structures.forEach(s -> link(path, s.structures()));

		return structures;

	}
}
