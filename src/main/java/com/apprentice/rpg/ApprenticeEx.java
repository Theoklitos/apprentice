package com.apprentice.rpg;

public class ApprenticeEx extends Error {

	private static final long serialVersionUID = 2109727805184723899L;

	public ApprenticeEx(final String reason) {
		super(reason);
	}

	public ApprenticeEx(final Throwable e) {
		super(e);
	}

}
