package com.apprentice.rpg.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for the {@link Box} class
 * 
 * @author theoklitos
 * 
 */
public final class TestBox {

	@Test
	public void hasContent() {
		final String payload = "string";
		final Box<String> full = Box.with(payload);
		assertFalse(full.isEmpty());
		assertTrue(full.hasContent());
		assertEquals(payload, full.getContent());
	}

	@Test
	public void hasNoContent() {
		final Box<String> emtpy = Box.empty();
		assertTrue(emtpy.isEmpty());
		assertFalse(emtpy.hasContent());
	}
	
	@Test
	public void testEquality() {
		final Box<String> box1 = Box.with("1");
		final Box<String> box2 = Box.with("1");
		final Box<String> box3 = Box.empty();
		
		assertEquals(box1,box2);
		assertNotSame(box2,box3);
	}
}
