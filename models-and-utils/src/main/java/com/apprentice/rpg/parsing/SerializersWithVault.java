package com.apprentice.rpg.parsing;

import java.util.Set;

import org.reflections.Reflections;

import com.apprentice.rpg.dao.simple.NameableVault;
import com.apprentice.rpg.dao.simple.SimpleVault;
import com.apprentice.rpg.parsing.gson.ApprenticeParsingComponent;
import com.apprentice.rpg.parsing.gson.IApprenticeParsingComponent;
import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

/**
 * A list of gson {@link JsonSerializer}s and {@link JsonDeserializer}s along with a {@link SimpleVault} from
 * which they draw "prototype" objects from
 * 
 * @author theoklitos
 * 
 */
public final class SerializersWithVault {

	private NameableVault simpleVault;
	private final Set<IApprenticeParsingComponent> parsers;

	public SerializersWithVault() {
		parsers = Sets.newHashSet();
		final Reflections reflections = new Reflections("com.apprentice.rpg.parsing.gson");
		final Set<Class<? extends IApprenticeParsingComponent>> allClasses =
			reflections.getSubTypesOf(IApprenticeParsingComponent.class);
		for (final Class<? extends IApprenticeParsingComponent> parserClass : allClasses) {
			if (parserClass.equals(ApprenticeParsingComponent.class) || parserClass.isInterface()) {
				continue;
			}
			try {
				parsers.add(parserClass.newInstance());
			} catch (final InstantiationException e) {
				throw new ParsingEx("Could not instantiate parsing component " + parserClass.getSimpleName() + ": "
					+ e.getMessage());
			} catch (final IllegalAccessException e) {
				throw new ParsingEx("Could not instantiate parsing component " + parserClass.getSimpleName() + ": "
					+ e.getMessage());
			}
		}
	}

	/**
	 * returns the {@link IApprenticeParsingComponent} set for the given type.
	 * 
	 * @param shouldGetSerializer
	 *            if true, the serializer is returned. Deserializer otherwise
	 */
	public IApprenticeParsingComponent getComponentForType(final java.lang.reflect.Type type,
			final boolean shouldGetSerializer) {
		for (final IApprenticeParsingComponent component : parsers) {
			final Class<?> givenClass = (Class<?>) type;
			final Class<?> componentClass = (Class<?>) component.getType();
			if (componentClass.isAssignableFrom(givenClass)) {
				final String name = component.getClass().getSimpleName();				
				if (shouldGetSerializer && name.contains("Serializer")) {
					return component;
				} else if (!shouldGetSerializer && name.contains("Deserializer")) {
					return component;
				}
			}
		}
		throw new ParsingEx("No parsing component for type \"" + type + "\"");
	}

	/**
	 * returns a {@link Gson} with all the serializers/deserializers created
	 */
	public Gson getGson() {
		final GsonBuilder gsonBuilder = new GsonBuilder();

		for (final IApprenticeParsingComponent parsingComponent : parsers) {
			gsonBuilder.registerTypeAdapter(parsingComponent.getType(), parsingComponent);
		}
		return gsonBuilder.setPrettyPrinting().create();
	}

	/**
	 * use this vault to get existing objects during parsing
	 */
	public NameableVault getSimpleVault() {
		return simpleVault;
	}

	/**
	 * tries to find the appropriate {@link IApprenticeParsingComponent} and if such one exists, sets its
	 * nameLookup
	 */
	public void setNameLookupForType(final java.lang.reflect.Type type, final boolean shouldGetSerializer,
			final boolean shouldEnableNameLookup) {
		try {
			getComponentForType(type, shouldGetSerializer).setNameLookup(shouldEnableNameLookup);
		} catch (final ParsingEx e) {
			// its ok, do nothing
		}
	}

	/**
	 * sets the {@link SimpleVault} to draw objects from
	 */
	public void setSimpleVault(final NameableVault simpleVault) {
		for (final IApprenticeParsingComponent parsingComponent : parsers) {
			parsingComponent.setSimpleVault(simpleVault);
		}
	}

	@Override
	public String toString() {
		String vaultMessage = "";
		if (simpleVault != null) {
			vaultMessage = ", vault size: " + simpleVault.getAllNameables().size() + ".";
		}
		return "Components: " + Joiner.on(",").join(parsers) + vaultMessage;
	}

}
