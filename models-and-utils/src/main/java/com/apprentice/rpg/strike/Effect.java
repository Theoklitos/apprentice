package com.apprentice.rpg.strike;

import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.parsing.ParsingEx;
import com.apprentice.rpg.util.ApprenticeCollectionUtils;
import com.apprentice.rpg.util.Checker;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

/**
 * Effect of a strike; has some bad effect in the player. A "strike" may contain multiple effects, usually
 * more than 1.
 * 
 * @author theoklitos
 * 
 */
public class Effect {

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(Effect.class);

	private final static String EFFECT_TYPE_PREFIX = "[";
	private final static String EFFECT_TYPE_SUFFIX = "]";

	private final static String ROUNDS_KEYWORD = "rounds";
	private final static String ROUNDS_TYPE_PREFIX = "(";
	private final static String ROUNDS_TYPE_SUFFIX = ")";

	private static final String VARIABLES_DELIMINATOR = ",";

	private final String effectType;
	private final List<String> variables;
	private int roundsPassed;

	public Effect(final EffectType effectType, final String... variables) throws NonRecognizedEffectTypeEx {
		this(effectType.toString(), variables);
	}

	/**
	 * Will try and parse the given string into an {@link Effect}
	 * 
	 * @throws ParsingEx
	 */
	public Effect(final String effectAsString) throws ParsingEx {
		roundsPassed = 0;
		final String effectTypeString =
			StringUtils.substringBetween(effectAsString, EFFECT_TYPE_PREFIX, EFFECT_TYPE_SUFFIX);
		try {
			final String effectTypeAsStringModified = effectTypeString.trim().toUpperCase();
			EffectType.valueOf(effectTypeAsStringModified);
			effectType = effectTypeAsStringModified;
		} catch (final IllegalArgumentException e) {
			throw new ApprenticeEx(effectTypeString + " is not a recognized effect type.");
		} catch (final Exception e) {
			throw new ApprenticeEx("Effect type not udnerstood.");
		}
		String effectAsStringWithoutType = StringUtils.remove(effectAsString.trim(), effectTypeString);
		effectAsStringWithoutType = StringUtils.remove(effectAsStringWithoutType, EFFECT_TYPE_PREFIX);
		effectAsStringWithoutType = StringUtils.remove(effectAsStringWithoutType, EFFECT_TYPE_SUFFIX);
		String effectAsStringWithoutTypeAndRounds;
		if (effectAsStringWithoutType.contains(ROUNDS_KEYWORD)) {
			try {
				final String numberAsString =
					StringUtils.substringBetween(effectAsStringWithoutType, ROUNDS_TYPE_PREFIX, ROUNDS_TYPE_SUFFIX);
				roundsPassed = Integer.valueOf(numberAsString);
				effectAsStringWithoutTypeAndRounds = StringUtils.remove(effectAsStringWithoutType, numberAsString);
				effectAsStringWithoutTypeAndRounds =
					StringUtils.remove(effectAsStringWithoutTypeAndRounds, ROUNDS_TYPE_PREFIX);
				effectAsStringWithoutTypeAndRounds =
					StringUtils.remove(effectAsStringWithoutTypeAndRounds, ROUNDS_TYPE_SUFFIX);
				effectAsStringWithoutTypeAndRounds =
					StringUtils.remove(effectAsStringWithoutTypeAndRounds, ROUNDS_KEYWORD).trim();
			} catch (final Exception e) {
				throw new ParsingEx("Coult not understand rounds #");
			}
		} else {
			effectAsStringWithoutTypeAndRounds = effectAsStringWithoutType;
		}
		variables = Lists.newArrayList();
		final StringTokenizer tokenizer =
			new StringTokenizer(effectAsStringWithoutTypeAndRounds, VARIABLES_DELIMINATOR);
		while (tokenizer.hasMoreElements()) {
			variables.add(tokenizer.nextElement().toString());
		}
	}

	public Effect(final String effectType, final String... variables) throws NonRecognizedEffectTypeEx {
		Checker.checkNonNull("Every effect needs an ID", true, effectType);
		try {
			EffectType.valueOf(effectType);
		} catch (final IllegalArgumentException e) {
			throw new NonRecognizedEffectTypeEx("No effect type named \"" + effectType + "\" exists.");
		}
		this.effectType = effectType;
		this.variables = Lists.newArrayList(variables);
		roundsPassed = 0;
	}

	/**
	 * adds the number of rounds to this effect. Must be positive.
	 */
	public void addRounds(final int roundsToAdd) {
		if (roundsToAdd < 0) {
			throw new ApprenticeEx("Cannot add negative rounds");
		} else {
			roundsPassed += roundsToAdd;
		}
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Effect) {
			final Effect otherEffect = (Effect) other;
			return Objects.equal(effectType, otherEffect.effectType)
				&& ApprenticeCollectionUtils.areAllElementsEqual(variables, otherEffect.variables);
		} else {
			return false;
		}
	}

	/**
	 * What type is this effect?
	 */
	public EffectType getEffectType() {
		return EffectType.valueOf(effectType);
	}

	/**
	 * how many rounds have passed since this strike was applied?
	 */
	public int getRounds() {
		return roundsPassed;
	}

	/**
	 * returns the ordered sequence of effect variables
	 */
	public List<String> getVariables() {
		return variables;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(effectType, variables);
	}

	@Override
	public String toString() {
		String roundsSuffix = " ";
		if (roundsPassed != 0) {
			roundsSuffix += ROUNDS_KEYWORD + ROUNDS_TYPE_PREFIX + roundsPassed + ROUNDS_TYPE_SUFFIX;
		}
		return EFFECT_TYPE_PREFIX + getEffectType() + EFFECT_TYPE_SUFFIX
			+ Joiner.on(VARIABLES_DELIMINATOR).join(variables) + roundsSuffix;
	}

}
