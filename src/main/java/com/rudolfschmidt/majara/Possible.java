package com.rudolfschmidt.majara;

import java.util.Optional;
import java.util.function.Consumer;

public class Possible<T> {

	private final Optional<T> optional;

	private Possible(Optional<T> optional) {
		this.optional = optional;
	}

	public static <T> Possible of(Optional<T> optional) {
		return new Possible<>(optional);
	}

	public Possible<T> ifPresent(Consumer<? super T> consumer) {
		optional.ifPresent(consumer);
		return this;
	}

	public Possible<T> ifNotPresent(Runnable runnable) {
		if (!optional.isPresent()) {
			runnable.run();
		}
		return this;
	}
}
