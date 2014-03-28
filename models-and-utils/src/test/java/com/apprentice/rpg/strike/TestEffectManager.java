package com.apprentice.rpg.strike;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import com.apprentice.rpg.strike.handlers.BleedingEffectHandler;
import com.google.inject.Injector;

/**
 * tests for the {@link EffectManager}
 * 
 * @author theoklitos
 * 
 */
public final class TestEffectManager {

	@Test
	public void areAllEffectsHandled() {
		final Mockery mockery = new Mockery();
		final Injector injector = mockery.mock(Injector.class);
		mockery.checking(new Expectations() {
			{
				oneOf(injector).injectMembers(with(any(BleedingEffectHandler.class)));
			}
		});		
		new EffectManager(injector);
	}
}
