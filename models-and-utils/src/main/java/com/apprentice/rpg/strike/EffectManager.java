package com.apprentice.rpg.strike;

import java.util.Collection;
import java.util.Set;

import org.reflections.Reflections;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.playerCharacter.IPlayerCharacter;
import com.apprentice.rpg.strike.handlers.AbstractEffectTypeHandler;
import com.apprentice.rpg.strike.handlers.IEffectTypeHandler;
import com.apprentice.rpg.strike.handlers.SingleEffectTypeHandler;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Used to initialize, verify and manage all {@link Effect}s
 * 
 * @author theoklitos
 * 
 */
public final class EffectManager implements IEffectManager {

	private final Collection<SingleEffectTypeHandler> handlers;

	@Inject
	public EffectManager(final Injector injector) {
		handlers = getCheckAllEffectTypes(injector);
	}

	@Override
	public void applyContinuousEffectToPlayer(final Effect effect, final IPlayerCharacter pc) {
		getHandlerForType(effect.getEffectType()).applyContinuousEffectToPlayer(effect, pc);
	}

	@Override
	public void applyInitialEffectToPlayer(final Effect effect, final IPlayerCharacter pc)
			throws NonRecognizedEffectTypeEx {
		getHandlerForType(effect.getEffectType()).applyInitialEffectToPlayer(effect, pc);
	}

	/**
	 * checks if every effect type is handled by exactly one {@link IEffectTypeHandler} and
	 * 
	 * @throws NonRecognizedEffectTypeEx
	 */
	protected Collection<SingleEffectTypeHandler> getCheckAllEffectTypes(final Injector injector)
			throws NonRecognizedEffectTypeEx {
		final Set<SingleEffectTypeHandler> result = Sets.newHashSet();
		final Reflections reflections = new Reflections("com.apprentice.rpg.strike.handlers");
		final Set<Class<? extends SingleEffectTypeHandler>> allClasses = reflections.getSubTypesOf(SingleEffectTypeHandler.class);
		for (final Class<? extends SingleEffectTypeHandler> handlerClass : allClasses) {
			if (handlerClass.equals(AbstractEffectTypeHandler.class) || handlerClass.isInterface()) {
				continue;
			} else {
				SingleEffectTypeHandler handler;
				try {
					handler = handlerClass.newInstance();
					injector.injectMembers(handler);
					result.add(handler);
				} catch (final InstantiationException e) {
					throw new ApprenticeEx(e);
				} catch (final IllegalAccessException e) {
					throw new ApprenticeEx(e);
				}
			}
		}
		if (EffectType.values().length != result.size()) {
			//throw new NonRecognizedEffectTypeEx("There exists at least one effect type that is not handled!");
		}
		return result;
	}

	protected SingleEffectTypeHandler getHandlerForType(final EffectType type) throws NonRecognizedEffectTypeEx {
		for (final SingleEffectTypeHandler handler : handlers) {
			if (handler.getHandledType().equals(type)) {
				return handler;
			}
		}
		throw new NonRecognizedEffectTypeEx("Type " + type + " cannot be handled.");
	}

	@Override
	public void removeEffectFromPlayer(final Effect effect, final IPlayerCharacter pc) throws NonRecognizedEffectTypeEx {
		getHandlerForType(effect.getEffectType()).removeEffectFromPlayer(effect, pc);
	}
}
