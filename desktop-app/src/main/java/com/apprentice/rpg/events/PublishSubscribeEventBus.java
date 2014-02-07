package com.apprentice.rpg.events;

import org.apache.log4j.Logger;

import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.google.common.eventbus.EventBus;

/**
 * Publish/subscribe event bus encapsulating guava's {@link EventBus}
 * 
 * @author theoklitos
 * 
 */
public class PublishSubscribeEventBus implements ApprenticeEventBus {

	private static Logger LOG = Logger.getLogger(PublishSubscribeEventBus.class);

	private final EventBus eventBus;

	public PublishSubscribeEventBus() {
		eventBus = new EventBus();
	}

	@Override
	public void objectDeleteEvent(final BodyPart bodyPart) {
		postEventInternal(new BodyPartDeletionEvent(bodyPart));
	}

	@Override
	public void objectDeleteEvent(final IType type) {
		postEventInternal(new TypeDeletionEvent(type));
	}

	@Override
	public void objectUpdateEvent(final BodyPart updatedBodyPart) {
		postEventInternal(new BodyPartUpdateEvent(updatedBodyPart));
	}

	@Override
	public void objectUpdateEvent(final IType updatedType) {
		postEventInternal(new TypeUpdateEvent(updatedType));
	}

	@Override
	public void postEvent(final DatabaseModificationEvent<?> event) {
		postEventInternal(event);
	}

	private void postEventInternal(final DatabaseModificationEvent<?> event) {
		LOG.debug("Posted " + event.getClass().getSimpleName() + event);
		eventBus.post(event);
	}

	@Override
	public void register(final Object eventHandler) {
		LOG.debug("Registered " + eventHandler.getClass().getSimpleName() + " to handle events.");
		eventBus.register(eventHandler);
	}

}
