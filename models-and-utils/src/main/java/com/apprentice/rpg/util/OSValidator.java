package com.apprentice.rpg.util;

/**
 * tells us which OS is running
 * 
 * @author theoklitos
 * 
 */
public final class OSValidator {

	public static final String OS_STRING = System.getProperty("os.name").toLowerCase();

	public static boolean isMac() {
		return OS_STRING.contains("mac");
	}

	public static boolean isSolaris() {
		return OS_STRING.contains("sunos");
	}

	public static boolean isUnix() {
		return OS_STRING.contains("nix") || OS_STRING.contains("nux") || OS_STRING.contains("aix");
	}

	public static boolean isWindows() {
		return OS_STRING.contains("win");
	}

}
