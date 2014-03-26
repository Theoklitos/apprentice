package com.apprentice.rpg.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;

import org.junit.Test;

import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.damage.DamageRoll;
import com.apprentice.rpg.model.weapon.IWeapon;
import com.apprentice.rpg.model.weapon.Weapon;
import com.apprentice.rpg.strike.StrikeType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
		final IWeapon weapon1 = new Weapon("sword1", 1, new DamageRoll("d8", new StrikeType("slashing")));
		final IWeapon weapon2 = new Weapon("sword2", 1, new DamageRoll("d8", new StrikeType("slashing")));
		final IWeapon weapon3 = new Weapon("sword3", 1, new DamageRoll("d8", new StrikeType("slashing")));
		final IWeapon weapon4 = new Weapon("sword3", 1, new DamageRoll("d8", new StrikeType("slashing")));
		final Collection<IWeapon> col1 = Sets.newHashSet(weapon1, weapon2, weapon3, weapon4);
		final Collection<IWeapon> col2 = Sets.newHashSet(weapon1, weapon3);
		
		final Collection<? extends Nameable> intersection =
			ApprenticeCollectionUtils.getIntersectingNameableElements(col1, col2);		
		assertEquals(2, intersection.size());
		assertTrue(intersection.contains(weapon1));
		assertTrue(intersection.contains(weapon3));
	}

	@Test
	public void mapsAreEqual() {
		final Map<Integer, String> map1 = Maps.newHashMap();
		map1.put(2, "two");
		map1.put(3, "whatever");
		map1.put(1, "one");
		final Map<Integer, String> map2 = Maps.newHashMap();
		map2.put(1, "one");
		map2.put(2, "two");
		map2.put(3, "whatever");

		assertTrue(ApprenticeCollectionUtils.areAllElementsEqual(map1, map2));
	}

	@Test
	public void mapsAreNotEqual() {
		final Map<Integer, String> map1 = Maps.newHashMap();
		map1.put(2, "one");
		map1.put(3, "two");
		map1.put(1, "three");
		final Map<Integer, String> map2 = Maps.newHashMap();
		map2.put(1, "one");
		map2.put(2, "two");
		map2.put(3, "three");

		assertFalse(ApprenticeCollectionUtils.areAllElementsEqual(map1, map2));
	}
}
