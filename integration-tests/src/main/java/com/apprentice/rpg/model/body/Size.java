package com.apprentice.rpg.model.body;

import org.apache.commons.lang3.StringUtils;


/**
 * All beings (NPCs and PCs) have a size which dictates how big or small they are
 * 
 * @author theoklitos
 * 
 */
public enum Size {
	DIMUNITIVE,
	TINY,
	SMALL,
	MEDIUM,
	LARGE,
	HUGE,
	GARGANTUAN;
	
	@Override
	public String toString() {
		return StringUtils.capitalize(name().toLowerCase());
	}
}
