package com.apprentice.rpg.events;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;

import com.apprentice.rpg.events.type.DatabaseModificationEvent;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.Nameable;
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
		CREATE("Creation"),
		/**
		 * existing item is changed
		 */
		UPDATE("Update"),
		/**
		 * existing item is deleted
		 */
		DELETE("Deletion");

		/**
		 * used in instantiating the appropriate class
		 */
		private final String classDescriptior;

		private EventType(final String classDescriptior) {
			this.classDescriptior = classDescriptior;
		}
	}

	private final static String EVENTS_PACKAGE = "com.apprentice.rpg.events.type";

	private static Logger LOG = Logger.getLogger(PublishSubscribeEventBus.class);

	private final EventBus eventBus;

	public PublishSubscribeEventBus() {
		eventBus = new EventBus();
	}

	@Override
	public void postCreationEvent(final Nameable nameable) {
		postEvent(nameable, EventType.CREATE);
	}

	@Override
	public void postDeleteEvent(final Nameable nameable) {
		postEvent(nameable, EventType.DELETE);
	}

	@Override
	public void postEvent(final DatabaseModificationEvent<?> event) {
		postEventInternal(event, true);
	}

	@Override
	public void postEvent(final Nameable nameable, final EventType eventType) {
		final String className =
			EVENTS_PACKAGE + "." + nameable.getClass().getSimpleName() + eventType.classDescriptior + "Event";
		try {
			final Constructor<?> constructor = Class.forName(className).getConstructors()[0];
			final DatabaseModificationEvent<?> event = (DatabaseModificationEvent<?>) constructor.newInstance(nameable);
			LOG.debug("Posting " + eventType + " event for \"" + nameable.getName() + "\"");
			postEventInternal(event, false);
		} catch (final InstantiationException e) {
			throw new ApprenticeEx(e);
		} catch (final IllegalAccessException e) {
			throw new ApprenticeEx(e);
		} catch (final ClassNotFoundException e) {
			throw new ApprenticeEx(e);
		} catch (final SecurityException e) {
			throw new ApprenticeEx(e);
		} catch (final IllegalArgumentException e) {
			throw new ApprenticeEx(e);
		} catch (final InvocationTargetException e) {
			throw new ApprenticeEx(e);
		}
	}

	private void postEventInternal(final DatabaseModificationEvent<?> event, final boolean shouldLog) {
		if (shouldLog) {
			LOG.debug("Posted [" + event + "]");
		}
		eventBus.post(event);
	}

	@Override
	public void postUpdateEvent(final Nameable nameable) {
		postEvent(nameable, EventType.UPDATE);
	}

	@Override
	public void register(final Object eventHandler) {
		LOG.debug("Registered " + eventHandler.getClass().getSimpleName() + " to handle events.");
		eventBus.register(eventHandler);
	}

}
