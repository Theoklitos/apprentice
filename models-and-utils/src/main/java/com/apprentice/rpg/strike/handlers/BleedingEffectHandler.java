package com.apprentice.rpg.strike.handlers;

import com.apprentice.rpg.model.playerCharacter.IPlayerCharacter;
import com.apprentice.rpg.random.ApprenticeRandom;
import com.apprentice.rpg.strike.Effect;
import com.apprentice.rpg.strike.EffectType;
import com.apprentice.rpg.strike.NonRecognizedEffectTypeEx;
import com.google.inject.Inject;

/**
 * Handles bleedings
 * 
 * @author theoklitos
 * 
 */
public class BleedingEffectHandler extends AbstractEffectTypeHandler implements IEffectTypeHandler {

	private ApprenticeRandom random;

	public BleedingEffectHandler() {
		super(EffectType.BLEEDING);
	}

	@Override
	public void applyContinuousEffectToPlayer(final Effect effect, final IPlayerCharacter pc) {
		final int level = getLevel(effect);
		String bleedingName = "";
		int hitpointLoss = 0;
		if (level == 0) { // minor
			bleedingName = "minor bleeding";
			if (effect.getRounds() % 10 == 0) {
				hitpointLoss = 1;
			}
		} else if (level == 1) { // major
			bleedingName = "major bleeding";
			hitpointLoss = 1;
		} else if (level == 2) { // severe
			bleedingName = "severe bleeding";
			hitpointLoss = random.getRandomInteger(40) + 1 * pc.getHitPoints().getMaximumHitPoints();
		} else {
			throw new EffectApplicationEx("Bleeding with level #" + level + " cannot be handled.");
		}
		if (hitpointLoss != 0) {
			pc.getHitPoints().setCurrentHitPoints(pc.getHitPoints().getCurrentHitPoints() - hitpointLoss);
			pc.getAuditTrail().addMessage("Lost " + hitpointLoss + " hitpoints due to " + bleedingName);
		}
	}

	@Override
	public void applyInitialEffectToPlayer(final Effect effect, final IPlayerCharacter pc)
			throws NonRecognizedEffectTypeEx {
		// no initial effect
	}

	@Override
	public void removeEffectFromPlayer(final Effect effect, final IPlayerCharacter pc) throws NonRecognizedEffectTypeEx {
		// nothing to undo
	}

	@Inject
	protected void setApprenticeRandom(final ApprenticeRandom random) {
		this.random = random;
	}

}
