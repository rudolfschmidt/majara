package com.rudolfschmidt.majara.v2.tokenizer;

import java.nio.file.Path;
import java.util.Optional;

public class Linker {

	static String suffix(Path fullPath) {
		return Optional.of(fullPath)
				.map(Path::getFileName)
				.map(Path::toString)
				.map(s -> s.substring(s.lastIndexOf(".")))
				.orElseThrow(IllegalArgumentException::new);
	}

}
