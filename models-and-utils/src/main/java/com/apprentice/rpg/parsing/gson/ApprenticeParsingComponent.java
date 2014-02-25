package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.dao.simple.NameableVault;
import com.apprentice.rpg.parsing.ParsingEx;

/**
 * parent class of all serializers/deserializers that need a {@link NameableVault}
 * 
 * @author theoklitos
 * 
 */
public abstract class ApprenticeParsingComponent implements IApprenticeParsingComponent {

	private NameableVault nameableVault;
	private boolean isLookupEnabled;
	private final Type type;

	public ApprenticeParsingComponent(final Type type) {
		this(type, true);
	}

	public ApprenticeParsingComponent(final Type type, final boolean isNameLookupEnabled) {
		this.type = type;
		this.isLookupEnabled = isNameLookupEnabled;
	}

	@Override
	public NameableVault getNameableVault() throws ParsingEx {
		if (nameableVault == null) {
			throw new ParsingEx("No namebale vault set for " + getClass().getSimpleName() + "!");
		} else {
			return nameableVault;
		}
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public boolean isNameLookupEnabled() {
		return isLookupEnabled;
	}

	@Override
	public void setNameLookup(final boolean isLookupEnabled) {
		this.isLookupEnabled = isLookupEnabled;
	}

	@Override
	public void setSimpleVault(final NameableVault nameableVault) {
		this.nameableVault = nameableVault;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
