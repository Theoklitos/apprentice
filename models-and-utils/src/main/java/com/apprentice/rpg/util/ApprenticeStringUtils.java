package com.apprentice.rpg.util;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

/**
 * various string manipulation utils in one static-method class
 * 
 * @author theoklitos
 * 
 */
public final class ApprenticeStringUtils {

	/**
	 * Returns the number with a + prefixed if positive, or - if negative.
	 */
	public static String getNumberWithOperator(final int number) {
		if (number >= 0) {
			return "+" + number;
		} else {
			return String.valueOf(number);
		}
	}

	/**
	 * sets to lowercase and converts underscores to spaces
	 */
	@SuppressWarnings("rawtypes")
	public static String getReadableEnum(final Enum enumerable) {
		return StringUtils.replace(enumerable.name().toLowerCase(), "_", " ");
	}

	/**
	 * Returns all the values of the enum ass lowercase, capitalzed strings in a list
	 */
	@SuppressWarnings("rawtypes")
	public static Collection<String> getReadableEnumList(final Class<? extends Enum> enumerable) {
		final List<String> result = Lists.newArrayList();
		for (final Object value : enumerable.getEnumConstants()) {
			result.add(StringUtils.capitalize(value.toString().toLowerCase()));
		}
		return result;
	}

	/**
	 * removes html and /html from around the text, if any
	 */
	public static String removeHtmlTags(final String text) {
		if (text.contains("<html>")) {
			final int startpoint = 9;
			final int endpoint = text.length() - 11;
			final String withoutHtmlTags = text.substring(startpoint, endpoint);
			return withoutHtmlTags;
		} else {
			return text;
		}
	}

}
