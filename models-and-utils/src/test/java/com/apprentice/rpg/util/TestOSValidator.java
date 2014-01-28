package com.apprentice.rpg.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * not the most sensible test, but whatever
 * 
 * @author theoklitos
 *
 */
public final class TestOSValidator {

	@Test
	public void testIsMac() {
		OSValidator.OS_STRING = "mac";
		assertTrue(OSValidator.isMac());
		
		assertFalse(OSValidator.isWindows());
		assertFalse(OSValidator.isUnix());
		assertFalse(OSValidator.isSolaris());
	}
	
	@Test
	public void testIsSolaris() {
		OSValidator.OS_STRING = "sunos";		
		assertTrue(OSValidator.isSolaris());
		
		assertFalse(OSValidator.isWindows());
		assertFalse(OSValidator.isUnix());
		assertFalse(OSValidator.isMac());
	}
	
	@Test
	public void testIsUnix() {
		OSValidator.OS_STRING = "linux";
		assertTrue(OSValidator.isUnix());
		
		assertFalse(OSValidator.isWindows());
		assertFalse(OSValidator.isMac());
		assertFalse(OSValidator.isSolaris());
	}
	
	@Test
	public void testIsWindows() {
		OSValidator.OS_STRING = "windows";
		assertTrue(OSValidator.isWindows());		
		
		assertFalse(OSValidator.isMac());
		assertFalse(OSValidator.isUnix());
		assertFalse(OSValidator.isSolaris());
	}
}
