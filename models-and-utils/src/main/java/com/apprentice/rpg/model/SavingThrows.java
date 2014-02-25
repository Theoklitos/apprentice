package com.apprentice.rpg.model;

import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.parsing.ParsingEx;
import com.google.common.base.Objects;

/**
 * Every character has a fort/ref/will saving throw
 */
public final class SavingThrows {

	private static final String SAVING_THROW_SEPARATOR = "/";

	private final int fortitutde;
	private final int reflex;
	private final int will;

	public SavingThrows(final int fortitutde, final int reflex, final int will) {
		this.fortitutde = fortitutde;
		this.reflex = reflex;
		this.will = will;
	}

	/**
	 * @throws ParsingEx
	 */
	public SavingThrows(final String savingThrowsAsString) throws ParsingEx {
		final StringTokenizer tokenizer = new StringTokenizer(savingThrowsAsString, SAVING_THROW_SEPARATOR);
		if (tokenizer.countTokens() != 3) {
			throw new ParsingEx("Saving throws can't be initialized without exactly 3 values: " + savingThrowsAsString);
		}
		try {
			this.fortitutde = removeOperator(tokenizer.nextToken());
			this.reflex = removeOperator(tokenizer.nextToken());
			this.will = removeOperator(tokenizer.nextToken());
		} catch (final IllegalArgumentException e) {
			throw new ParsingEx("Save value not a number: " + e.getMessage());
		}
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof SavingThrows) {
			final SavingThrows otherSaves = (SavingThrows) other;
			return fortitutde == otherSaves.fortitutde && reflex == otherSaves.reflex
				&& will == otherSaves.will;
		} else {
			return false;
		}
	}

	/**
	 * Fort
	 */
	public int getFortitude() {
		return fortitutde;
	}

	/**
	 * Ref
	 */
	public int getReflex() {
		return reflex;
	}

	/**
	 * adds a "+" if the value is positive
	 */
	private String getValuePrefixedWithOperator(final int value) {
		if (value < 0) {
			return "+" + value;
		} else {
			return Integer.toString(value);
		}
	}

	/**
	 * Will
	 */
	public int getWill() {
		return will;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(fortitutde, reflex, will);
	}

	/**
	 * removes any "+" operator and tries to return as string
	 */
	private int removeOperator(final String value) throws IllegalArgumentException {
		return Integer.valueOf(StringUtils.remove(value, "+").trim());
	}

	@Override
	public String toString() {
		return getValuePrefixedWithOperator(fortitutde) + SAVING_THROW_SEPARATOR + getValuePrefixedWithOperator(reflex)
			+ SAVING_THROW_SEPARATOR + getValuePrefixedWithOperator(will);
	}
}
