package com.apprentice.rpg.strike;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Maps 10+ levels of strike intensity to their appropriate effects
 * 
 * @author theoklitos
 * 
 */
public final class IntensityToEffectMapping {

	private final Map<Integer, List<Effect>> mapping;

	public IntensityToEffectMapping() {
		mapping = Maps.newTreeMap();
	}
}
