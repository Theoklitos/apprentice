package com.apprentice.rpg.random;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;

import com.apprentice.rpg.model.damage.Damage;
import com.apprentice.rpg.model.damage.DamageRoll;
import com.apprentice.rpg.random.dice.Roll;

/**
 * timestamp pseudorandom implementation
 * 
 * @author theoklitos
 * 
 */
public class ApprenticePseudoRandom implements ApprenticeRandom {

	private static Logger LOG = Logger.getLogger(ApprenticePseudoRandom.class);

	@Override
	public int getRandomInteger(final int max) {
		final int result = RandomUtils.nextInt(max);
		LOG.debug("Rolled from 0 to " + (max - 1) + ": " + result);
		return result;
	}

	@Override
	public Damage roll(final DamageRoll damageRoll) {
		final int result = roll(damageRoll.getRoll());
		return new Damage(result, damageRoll.getPenetration(), damageRoll.getType());
	}

	@Override
	public int roll(final Roll roll) {
		return roll.roll(this);
	}

}
