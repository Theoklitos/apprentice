package com.apprentice.rpg.strike.handlers;

import com.apprentice.rpg.model.playerCharacter.IPlayerCharacter;
import com.apprentice.rpg.strike.Effect;
import com.apprentice.rpg.strike.EffectType;
import com.apprentice.rpg.strike.NonRecognizedEffectTypeEx;

/**
 * handles penalty (-1, -2, -10, etc) effect types
 * 
 * @author theoklitos
 * 
 */
public class PenaltyEffectHandler extends AbstractEffectTypeHandler implements IEffectTypeHandler {

	public PenaltyEffectHandler() {
		super(EffectType.PENALTY);
	}

	@Override
	public void applyContinuousEffectToPlayer(final Effect effect, final IPlayerCharacter pc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void applyInitialEffectToPlayer(final Effect effect, final IPlayerCharacter pc) throws NonRecognizedEffectTypeEx {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeEffectFromPlayer(final Effect effect, final IPlayerCharacter pc) throws NonRecognizedEffectTypeEx {
		// TODO Auto-generated method stub

	}

}
