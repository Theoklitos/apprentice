package com.apprentice.rpg.model;

/**
 * Common interface for objects that are expected to have a unique name, at least in their class
 * 
 * @author theoklitos
 * 
 */
public interface Nameable {

	/**
	 * returns a small user-defined text that describes this object
	 */
	public String getDescription();

	/**
	 * returns the "name" of this obejct that is expected to be somewhat unique
	 */
	public String getName();

	/**
	 * sets the description of this object
	 */
	public void setDescription(final String description);

	/**
	 * Changes the name of this object. There is no guarantee that it has remained unique; the DB must check
	 * that
	 */
	public void setName(String newName);
}
