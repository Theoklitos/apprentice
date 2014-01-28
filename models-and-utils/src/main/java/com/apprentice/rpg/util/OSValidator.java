package com.apprentice.rpg.util;

/**
 * tells us which OS is running
 * 
 * @author theoklitos
 * 
 */
public final class OSValidator {

	protected static String OS_STRING = System.getProperty("os.name").toLowerCase();

	/**
	 * returns a string that describes the OS
	 */
	public static String getOperatingSystem() {
		return OS_STRING;
	}

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
