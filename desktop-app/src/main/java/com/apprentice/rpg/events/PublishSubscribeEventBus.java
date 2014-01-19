package com.apprentice.rpg.events;

import com.google.common.eventbus.EventBus;

/**
 * Publish/subscribe event bus encapsulating guava's code
 * 
 * @author theoklitos
 * 
 */
public class PublishSubscribeEventBus implements ApprenticeEventBus {

	private final EventBus eventBus;

	public PublishSubscribeEventBus() {
		eventBus = new EventBus();
	}

	/**
	 * Registes this class which will listen to events via the @Subscribe annotation
	 */
	public void register(final Object eventHandler) {
		eventBus.register(eventHandler);
	}

}
