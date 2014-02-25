package com.apprentice.rpg.util;

import org.apache.commons.lang3.StringUtils;

/**
 * various string manipulation utils in one static-method class
 * 
 * @author theoklitos
 * 
 */
public final class ApprenticeStringUtils {

	/**
	 * sets to lowercase and converts underscores to spaces
	 */
	@SuppressWarnings("rawtypes")
	public static String getReadableEnum(final Enum enumerable) {
		return StringUtils.replace(enumerable.name().toLowerCase(), "_", " ");
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
