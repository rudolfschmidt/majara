package com.rudolfschmidt.majara.v2.tokenizer;

import com.rudolfschmidt.majara.v2.tokens.Types;
import com.rudolfschmidt.majara.v2.tokens.Structure;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class BlockLinker {

	public static List<Structure> link(Path path, List<Structure> structures) {

		return structures.stream()
				.filter(s -> Types.EXTENDS.equals(s.token().type()))
				.findFirst()
				.flatMap(s -> Optional.of(path)
						.map(Path::getParent)
						.map(p -> p.resolve(s.token().value() + Linker.suffix(path)))
						.map(Tokenizer::tokenize)
				)
				.map(s -> iterate(s, structures))
				.orElse(structures);
	}

	private static List<Structure> iterate(List<Structure> a, List<Structure> b) {
		a.stream()
				.filter(s -> Types.BLOCK.equals(s.token().type()))
				.forEach(x -> {
					b.stream()
							.filter(s -> Types.BLOCK.equals(s.token().type()))
							.filter(s -> s.token().value().equals(x.token().value()))
							.forEach(y -> {
								x.structures().clear();
								x.structures().addAll(y.structures());
							});
					b.stream()
							.filter(s -> Types.APPEND.equals(s.token().type()))
							.filter(s -> s.token().value().equals(x.token().value()))
							.forEach(y -> {
								x.structures().clear();
								x.structures().addAll(y.structures());
							});
					b.forEach(y -> iterate(a, y.structures()));
				});
		a.forEach(x -> iterate(x.structures(), b));
		return a;
	}
}
