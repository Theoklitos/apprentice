package com.apprentice.rpg.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Test;

import com.apprentice.rpg.model.body.BodyPart;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Tets for the {@link ApprenticeCollectionUtils}
 * 
 * @author theoklitos
 * 
 */
public final class TestApprenticeCollectionUtils {

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

		assertTrue(ApprenticeCollectionUtils.areAllElementsEqual(col1, col2));
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

		assertFalse(ApprenticeCollectionUtils.areAllElementsEqual(col1, col2));
	}

	@Test
	public void getNamesOfNamebales() {
		final BodyPart part1 = new BodyPart("name1");
		final BodyPart part3 = new BodyPart("name3");
		final BodyPart part2 = new BodyPart("name2");
		final Collection<String> result =
			ApprenticeCollectionUtils.getNamesOfNameables(Sets.newHashSet(part1, part2, part3));

		assertEquals(3, result.size());
		assertTrue(result.contains("name1"));
		assertTrue(result.contains("name2"));
		assertTrue(result.contains("name3"));
	}

	@Test
	public void intersection() {
		final Collection<String> col1 = Sets.newHashSet("el1", "el2", "el3", "el4");
		final Collection<String> col2 = Sets.newHashSet("el6", "el2", "el5", "el1");
		final Collection<String> result = ApprenticeCollectionUtils.getIntersectingElements(col1, col2);

		assertEquals(2, result.size());
		assertTrue(result.contains("el1"));
		assertTrue(result.contains("el2"));
	}

	@Test
	public void intersectionOfNamebales() {
		fail("implement this with weapons");
	}
}
