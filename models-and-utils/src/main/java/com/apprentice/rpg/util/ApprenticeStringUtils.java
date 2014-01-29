package com.apprentice.rpg.util;

/**
 * various string manipulation utils in one static-method class
 * 
 * @author theoklitos
 * 
 */
public final class ApprenticeStringUtils {

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
