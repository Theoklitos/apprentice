package com.apprentice.rpg.dao.simple;

import java.util.Collection;
import java.util.Map;

import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.NoResultsFoundEx;
import com.apprentice.rpg.dao.TooManyResultsEx;
import com.apprentice.rpg.model.Nameable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Basically a simpler Vault that exists in memory. Stores only {@link Nameable}s
 * 
 * @author theoklitos
 * 
 */
public class SimpleVault implements NameableVault {

	@SuppressWarnings("rawtypes")
	private final Map<Class, Collection> data;

	public SimpleVault() {
		data = Maps.newHashMap();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends Nameable> void add(final Nameable object) {
		final Class type = object.getClass();
		Collection result = data.get(type);
		if (result == null) {
			result = Lists.newArrayList();
			data.put(type, result);
		}
		result.add(object);
	}

	@Override
	public void addAll(final Collection<? extends Nameable> nameables) {
		for (final Nameable nameable : nameables) {
			update(nameable);
		}
	}

	@Override
	public boolean doesNameExist(final String name, final Class<? extends Nameable> memberOf) {
		for (final Nameable nameable : getAllNameables(memberOf)) {
			if (nameable.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<Nameable> getAllNameables() {
		final Collection<Nameable> all = Lists.newArrayList();
		for (final Class type : data.keySet()) {
			all.addAll(data.get(type));
		}
		return all;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T extends Nameable> Collection<T> getAllNameables(final Class<T> nameableType) {
		final Collection<T> result = Lists.newArrayList();
		for (final Class clazz : data.keySet()) {
			if (nameableType.isAssignableFrom(clazz)) {
				final Collection nameablesForType = data.get(clazz);
				if (nameablesForType != null) {
					result.addAll(nameablesForType);
				}
			}
		}
		return result;
	}

	@Override
	public <T extends Nameable> T getUniqueNamedResult(final String name, final Class<T> typeToQueryFor)
			throws TooManyResultsEx, NoResultsFoundEx {
		for (final T object : getAllNameables(typeToQueryFor)) {
			if (object.getName().equals(name)) {
				return object;
			}
		}
		throw new NoResultsFoundEx(name + " does not exist");
	}

	@Override
	public void update(final Nameable item) throws NameAlreadyExistsEx {
		add(item);
	}
}
