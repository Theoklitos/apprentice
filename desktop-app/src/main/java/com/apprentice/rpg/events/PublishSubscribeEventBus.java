package com.apprentice.rpg.events;

import org.apache.log4j.Logger;

import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.model.playerCharacter.Nameable;
import com.google.common.eventbus.EventBus;

/**
 * Publish/subscribe event bus encapsulating guava's {@link EventBus}
 * 
 * @author theoklitos
 * 
 */
public class PublishSubscribeEventBus implements ApprenticeEventBus {

	/**
	 * describes the type of this event
	 * 
	 * @author theoklitos
	 * 
	 */
	public enum EventType {
		/**
		 * new item
		 */
		CREATE(),
		/**
		 * existing item is changed
		 */
		UPDATE(),
		/**
		 * existing item is deleted
		 */
		DELETE();

	}

	private static Logger LOG = Logger.getLogger(PublishSubscribeEventBus.class);

	private final EventBus eventBus;

	public PublishSubscribeEventBus() {
		eventBus = new EventBus();
	}

	@Override
	public void postApprenticeEvent(final ApprenticeEvent event) {
		postEventAndLog(event, true);
	}

	@Override
	public void postApprenticeEvent(final Nameable nameable, final EventType eventType) {
		postEventAndLog(new ApprenticeEvent(eventType, nameable), true);

	}

	@Override
	public void postCreationEvent(final Nameable nameable) {
		postApprenticeEvent(nameable, EventType.CREATE);
	}

	@Override
	public void postDeleteEvent(final Nameable nameable) {
		postApprenticeEvent(nameable, EventType.DELETE);
	}

	/**
	 * self-explanatory
	 */
	private void postEventAndLog(final Object eventObject, final boolean shouldLog) {
		if (shouldLog) {
			LOG.debug("Posted [" + eventObject + "]");
		}
		eventBus.post(eventObject);
	}
	
	@Override
	public void postShowFrameEvent(final Class<? extends ApprenticeInternalFrame<?>> frameToShow) {
		postEventAndLog(new ShowFrameEvent(frameToShow), true);				
	}

	@Override
	public void postShowFrameEvent(final Class<? extends ApprenticeInternalFrame<?>> frameToShow, final String parameter) {
		postEventAndLog(new ShowFrameEvent(frameToShow, parameter), true);			
	}

	@Override
	public void postShutdownEvent(final boolean shouldReopen) {
		final ShutdownEvent event = new ShutdownEvent(shouldReopen);				
		eventBus.post(event);
		LOG.debug("Posted [" + event + "]");
	}

	@Override
	public void postUpdateEvent(final Nameable nameable) {
		postApprenticeEvent(nameable, EventType.UPDATE);
	}

	@Override
	public void register(final Object eventHandler) {
		LOG.debug("Registered " + eventHandler.getClass().getSimpleName() + " to handle events.");
		eventBus.register(eventHandler);
	}

}
