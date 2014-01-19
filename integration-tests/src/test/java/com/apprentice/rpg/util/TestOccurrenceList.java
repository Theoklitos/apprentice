package com.apprentice.rpg.util;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public final class TestOccurrenceList {

	private OccurrenceList<String> list;

	@Before
	public void setUp() {
		list = new OccurrenceList<String>();
	}

	@Test
	public void testAdd() {
		list.add("red");
		list.add("red");

		list.add(1, "red");

		assertEquals(3, list.getOccurencesOf("red"));

		list.clear();

		list.add("element");
		list.add("element");
		list.add("element");
	}

	@Test
	public void testCorrectValues() {
		// we have two reds and one blue
		list.add("red");
		list.add("blue");
		list.add("red");

		assertEquals(2, list.getOccurencesOf("red"));
		assertEquals(1, list.getOccurencesOf("blue"));
	}

	@Test
	public void testGetAllOccurrences() {
		list.add("foo");
		list.add("bar");
		list.add("foo");
		list.add("nothing");
		list.add("nill");

		final List<String> allFoos = list.getAllOccurrencesOfElement("foo");
		assertEquals(2, allFoos.size());
		assertEquals("foo", allFoos.get(0));
		assertEquals("foo", allFoos.get(0));

		final List<String> all = list.getAllElements();
		assertEquals(5, all.size());
		assertEquals("foo", all.get(0));
		assertEquals("foo", all.get(1));
		assertEquals("bar", all.get(2));
		assertEquals("nothing", all.get(3));
		assertEquals("nill", all.get(4));
	}

	@Test
	public void testGetSizes() {
		list.add("red");
		list.add("blue");
		list.add("red");
		list.add("green");
		list.add("green");

		assertEquals(3, list.size());
		assertEquals(5, list.totalSize());
	}

	@Test
	public void testRemove() {
		list.add("red");
		list.add("blue");
		list.add("red");

		assertEquals(2, list.size());
		assertEquals(3, list.totalSize());

		final int index = list.remove("red");

		assertEquals(1, list.size());
		assertEquals(1, list.totalSize());
		assertEquals(0, index);

		list.remove("blue");
		assertEquals(0, list.size());
		assertEquals(0, list.totalSize());

		assertEquals(-1, list.remove("non existant"));
	}

	@Test
	public void testSet() {
		list.add("red");
		list.add("blue");
		list.add("red");
		list.add("green");

		// blue occurs once
		assertEquals(3, list.size());
		assertEquals(1, list.getOccurencesOf("blue"));

		// now if we change a "green" to "blue", we should get 2 blues and 0
		// green.
		list.set(2, "blue");

		assertEquals(2, list.getOccurencesOf("blue"));
		assertEquals(2, list.size());

		// and if we change the first red to blue..
		list.set(0, "blue");
		assertEquals(4, list.getOccurencesOf("blue"));
		assertEquals(1, list.size());
	}
}
