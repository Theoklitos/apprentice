package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.dao.simple.NameableVault;
import com.apprentice.rpg.dao.simple.SimpleVault;
import com.apprentice.rpg.parsing.ParsingEx;
import com.google.gson.Gson;

/**
 * Provides serialization and deserialization for a specific type, in {@link Gson}
 * 
 * @author theoklitos
 *
 */
public interface IApprenticeParsingComponent {

	/**
	 * get the set {@link NameableVault} to draw namebale objects from
	 * 
	 * @throws ParsingEx
	 *             if no vault was defind
	 */
	NameableVault getNameableVault() throws ParsingEx;

	/**
	 * what {@link Type} is this parsing component for?
	 */
	Type getType();

	/**
	 * returns true if this component should lookup names in the provided {@link SimpleVault}
	 */
	boolean isNameLookupEnabled();

	/**
	 * if set to false, the types this parser parsers will be transformed with a {@link Gson} without any type adapters
	 */
	void setNameLookup(boolean isEnabled);

	/**
	 * will draw nameable objects from this vault
	 */
	public void setSimpleVault(final NameableVault vault);
}
