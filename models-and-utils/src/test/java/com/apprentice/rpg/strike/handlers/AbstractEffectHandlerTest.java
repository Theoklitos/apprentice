package com.apprentice.rpg.strike.handlers;

import org.junit.Before;

import com.apprentice.rpg.model.factories.DataFactory;
import com.apprentice.rpg.model.playerCharacter.IPlayerCharacter;
import com.apprentice.rpg.model.playerCharacter.PlayerCharacter;
import com.apprentice.rpg.random.ApprenticePseudoRandom;
import com.apprentice.rpg.random.ApprenticeRandom;
import com.apprentice.rpg.strike.EffectManager;
import com.apprentice.rpg.strike.IEffectManager;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * all strike handler tests should inherit this
 * 
 * @author theoklitos
 * 
 */
public abstract class AbstractEffectHandlerTest {

	private IEffectManager manager;
	private DataFactory dataFactory;

	/**
	 * returns a fully functional {@link IEffectManager}
	 */
	public IEffectManager getEffectManager() {
		return manager;
	}

	/**
	 * will always return the same {@link PlayerCharacter}, reset after every test case
	 */
	public IPlayerCharacter getPlayerCharacter() {
		return dataFactory.getPlayerCharacter1();
	}

	@Before
	public void setup() {
		dataFactory = new DataFactory();
		final Injector testInjector = Guice.createInjector(new Module() {

			@Override
			public void configure(final Binder binder) {
				binder.bind(ApprenticeRandom.class).toInstance(new ApprenticePseudoRandom());
			}
		});
		manager = new EffectManager(testInjector);
	}
}
