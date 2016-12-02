package com.rudolfschmidt.majara.v2.tokenizer;

import com.rudolfschmidt.majara.v2.tokens.Structure;
import com.rudolfschmidt.majara.v2.tokens.Token;

import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Tokenizer {

	private static final Logger LOGGER = Logger.getLogger(Tokenizer.class.getName());

	public static List<Structure> tokenize(Path path) {

		List<Token> tokens = Tokens.valueOf(path);
		LOGGER.info("------------Tokens------------\n" +
				tokens.stream().map(Token::toString).collect(Collectors.joining("\n")));

		List<Structure> structures = Structures.valueOf(tokens);
//		LOGGER.info("------------Structures------------\n" +
//				structures.stream().map(Structure::toString).collect(Collectors.joining("\n")));

		structures = IncludeLinker.link(path, structures);
//		LOGGER.info("------------Included Structures------------\n" +
//				structures.stream().map(Structure::toString).collect(Collectors.joining("\n")));

		structures = BlockLinker.link(path, structures);
		LOGGER.info("------------Block Structures------------\n" +
				structures.stream().map(Structure::toString).collect(Collectors.joining("\n")));

		return structures;
	}

}
