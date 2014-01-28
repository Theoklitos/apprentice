package com.apprentice.rpg.events;

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

	private final EventBus eventBus;

	public PublishSubscribeEventBus() {
		eventBus = new EventBus();
	}

	@Override
	public void objectDeleteEvent(final BodyPart bodyPart) {
		eventBus.post(new BodyPartDeletionEvent(bodyPart));
	}

	@Override
	public void objectDeleteEvent(final IType type) {
		eventBus.post(new TypeDeletionEvent(type));
	}

	@Override
	public void objectUpdateEvent(final BodyPart bodyPart) {
		eventBus.post(new BodyPartUpdateEvent(bodyPart));
	}

	@Override
	public void objectUpdateEvent(final IType type) {
		eventBus.post(new TypeUpdateEvent(type));
	}

	@Override
	public void postEvent(final DatabaseModificationEvent event) {
		eventBus.post(event);
	}

	@Override
	public void register(final Object eventHandler) {
		eventBus.register(eventHandler);
	}

}
