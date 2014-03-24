package com.apprentice.rpg;

/**
 * Will be used when the dekstop app is terminated
 * 
 * @author theoklitos
 * 
 */
public interface IShutdownHook {

	/**
	 * Save leftover state, close the app gracefully.
	 */
	void shutdownGracefully();

}
