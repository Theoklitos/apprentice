package com.apprentice.rpg.model;


/**
 * A prototype object that can copy itself and is guaranteed to be unique (by name) in the DB
 * 
 * @author theoklitos
 * 
 */
public interface Prototypical<T> extends Cloneable {
	/**
	 * deep copies this prototype into a new object that effectively is an instance, ready to be handed out to
	 * a {@link PlayerCharacter}
	 */
	public T clone();

}
