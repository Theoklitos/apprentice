package com.apprentice.rpg.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import com.apprentice.rpg.model.body.BodyPart;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Tets for the {@link ApprenticeMatcher}
 * 
 * @author theoklitos
 * 
 */
public final class TestApprenticeMatcher {

	@Test
	public void collectionsAreEqual() {
		final Collection<BodyPart> col1 = Sets.newHashSet();
		col1.add(new BodyPart("part1"));
		col1.add(new BodyPart("part2"));
		col1.add(new BodyPart("part3"));
		final Collection<BodyPart> col2 = Sets.newHashSet();
		col2.add(new BodyPart("part3"));
		col2.add(new BodyPart("part1"));
		col2.add(new BodyPart("part2"));
		
		assertTrue(ApprenticeMatcher.areAllElementsEqual(col1, col2));
	}

	@Test
	public void collectionsAreNotEqual() {
		final Collection<String> col1 = Sets.newHashSet();
		col1.add("string1");
		col1.add("string2");
		final Collection<String> col2 = Lists.newArrayList();
		col1.add("string1");
		col1.add("string2");
		col1.add("string2");

		assertFalse(ApprenticeMatcher.areAllElementsEqual(col1, col2));
	}
}
