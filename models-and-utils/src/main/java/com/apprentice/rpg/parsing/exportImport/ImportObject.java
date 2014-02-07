package com.apprentice.rpg.parsing.exportImport;

import java.util.Collection;
import java.util.Set;

import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.NameableVault;
import com.apprentice.rpg.dao.NoResultsFoundEx;
import com.apprentice.rpg.dao.TooManyResultsEx;
import com.apprentice.rpg.model.Nameable;
import com.google.common.collect.Sets;

/**
 * Object with various objects that are the result of parsing.
 * 
 * @author theoklitos
 * 
 */
public final class ImportObject implements NameableVault {

	private final Set<Nameable> nameables;

	public ImportObject() {
		nameables = Sets.newHashSet();
	}

	public void addAll(final Collection<? extends Nameable> nameables) {
		for (final Nameable nameable : nameables) {
			update(nameable);
		}
	}

	@Override
	public boolean doesNameExist(final String name, final Class<? extends Nameable> memberOf) {
		for (final Nameable nameable : nameables) {
			if (nameable.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Collection<Nameable> getAll() {
		return nameables;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Nameable> T getUniqueNamedResult(final String name, final Class<T> typeToQueryFor)
			throws TooManyResultsEx, NoResultsFoundEx {
		int count = 0;
		T result = null;
		for (final Nameable nameable : nameables) {
			if (nameable.getName().equals(name) && nameable.getClass().isAssignableFrom(typeToQueryFor)) {
				result = (T) nameable;
				count++;
				if (count > 1) {
					throw new TooManyResultsEx("More than one items have the name \"" + name + "\"");
				}
			}
		}
		if (result != null) {
			return result;
		} else {
			throw new NoResultsFoundEx("No item named \"" + name + "\" found");
		}
	}

	@Override
	public void update(final Nameable item) throws NameAlreadyExistsEx {
		nameables.add(item);
	}

}
