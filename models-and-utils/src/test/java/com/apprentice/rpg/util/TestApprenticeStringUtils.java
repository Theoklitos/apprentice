package com.apprentice.rpg.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * tests for the {@link ApprenticeStringUtils}
 * 
 * @author theoklitos
 * 
 */
public final class TestApprenticeStringUtils {

	@Test
	public void removeHtmlProperly() {
		assertEquals("internal", ApprenticeStringUtils.removeHtmlTags("<html><b>internal</b></html>"));
	}
}
