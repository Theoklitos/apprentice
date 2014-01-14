package com.apprentice.rpg.util;

import org.apache.commons.lang.StringUtils;

import com.apprentice.rpg.ApprenticeEx;

public final class Checker {

	/**
	 * Checks if the given parameters are null, and if so, throws an unchecked {@link ApprenticeEx}. Also
	 * checks for non-empty string.
	 * 
	 */
	public static void checkNonNull(final String errorMessage, final boolean checkForEmptyStrings,
			final Object... toCheck) throws ApprenticeEx {
		for (final Object object : toCheck) {
			if (object == null) {
				throw new ApprenticeEx(errorMessage + ". Null parameter");
			} else {
				if (checkForEmptyStrings && object instanceof String) {
					final String objectStringified = (String) object;
					if (StringUtils.isBlank(objectStringified)) {
						throw new ApprenticeEx(errorMessage + ". Empty string");
					}
				}
			}
		}
	}

}
