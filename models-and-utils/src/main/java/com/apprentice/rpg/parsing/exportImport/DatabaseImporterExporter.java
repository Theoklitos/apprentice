package com.apprentice.rpg.parsing.exportImport;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.dao.simple.NameableVault;
import com.apprentice.rpg.dao.simple.SimpleVault;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.PlayerCharacter;
import com.apprentice.rpg.model.armor.Armor;
import com.apprentice.rpg.model.armor.ArmorPiece;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.Type;
import com.apprentice.rpg.model.weapon.AmmunitionType;
import com.apprentice.rpg.model.weapon.Weapon;
import com.apprentice.rpg.parsing.ApprenticeParser;
import com.apprentice.rpg.parsing.ParsingEx;
import com.apprentice.rpg.strike.StrikeType;
import com.apprentice.rpg.util.ApprenticeCollectionUtils;
import com.apprentice.rpg.util.ApprenticeStringUtils;
import com.apprentice.rpg.util.Box;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.inject.Inject;

/**
 * Uses the {@link ApprenticeParser} to parse json and then imports it in the database, or to export parts of
 * the database as json
 * 
 * @author theoklitos
 * 
 */
public final class DatabaseImporterExporter implements IDatabaseImporterExporter {

	/**
	 * all possible item types that exist in the repository
	 * 
	 * @author theoklitos
	 * 
	 */
	public enum ItemType {
		BODY_PART(BodyPart.class, "bodyParts", false),
		STRIKE_TYPE(StrikeType.class, "strikes", false),
		TYPE(Type.class, "types", false),
		WEAPON(Weapon.class, "weapons", true),
		AMMUNITION(AmmunitionType.class, "ammunitions", false),
		ARMOR(Armor.class, "armors", true),
		ARMOR_PIECE(ArmorPiece.class, "armorPieces", true),
		PLAYER_CHARACTER(PlayerCharacter.class, "playerCharacters", false);

		/**
		 * Matches a {@link Class} to its {@link ItemType}
		 */
		public static ItemType getForClass(final Class<? extends Nameable> nameableClass) {
			for (final ItemType type : values()) {
				if (type.type.equals(nameableClass)) {
					return type;
				}
			}
			throw new ApprenticeEx("No ItemType for class \"" + nameableClass + "\"!");
		}

		private boolean isPrototypical;

		/**
		 * the actual class that this type represents
		 */
		public final Class<? extends Nameable> type;

		/**
		 * under which name are these items are placed in the export/import array
		 */
		public final String jsonArrayName;

		private ItemType(final Class<? extends Nameable> type, final String jsonArrayName, final boolean isPrototypical) {
			this.type = type;
			this.jsonArrayName = jsonArrayName;
			this.isPrototypical = isPrototypical;
		}

		public boolean isPrototypical() {
			return isPrototypical;
		}

		/**
		 * Returns a human-readable representation of this type
		 */
		@Override
		public String toString() {
			return ApprenticeStringUtils.getReadableEnum(this);
		}
	}

	private static Logger LOG = Logger.getLogger(DatabaseImporterExporter.class);

	/**
	 * returns a box with the {@link ItemType} that corresponds to the given class, if any
	 */
	public static Box<ItemType> getTypeForClass(final Nameable nameable) {
		for (final ItemType type : ItemType.values()) {
			if (type.type.equals(nameable.getClass())) {
				return Box.with(type);
			}
		}
		return Box.empty();
	}

	private final Vault vault;
	private final ApprenticeParser parser;

	@Inject
	public DatabaseImporterExporter(final Vault vault, final ApprenticeParser parser) {
		this.vault = vault;
		this.parser = parser;
	}

	/**
	 * adds the specified {@link ItemType} of the database to the parent {@link JsonObject}
	 */
	private void addForType(final JsonObject parent, final ItemType type, final ExportConfigurationObject config) {
		final JsonArray jsonArray =
			parser.getAsJsonArrayTest(getTypesForNames(config.getNamesForType(type), type), type.type);
		if (jsonArray.size() > 0) {
			parent.add(type.jsonArrayName, jsonArray);
		}
	}

	/**
	 * check if any elements exist in both collections and throws exception if so
	 * 
	 * @throws ParsingEx
	 */
	private void checkForConflictingElements(final Collection<? extends Nameable> fileElements,
			final Collection<? extends Nameable> vaultElements) throws ParsingEx {
		final Collection<? extends Nameable> intersection =
			ApprenticeCollectionUtils.getIntersectingNameableElements(fileElements, vaultElements);
		if (intersection.size() > 0) {
			throw new ParsingEx(
					"No data could be imported because there was a conflict due to the following element(s) existing both in the file and in the database: "
						+ Joiner.on(",").join(ApprenticeCollectionUtils.getNamesOfNameables(intersection)));
		}
	}

	@Override
	public void export(final ExportConfigurationObject config) throws ParsingEx {
		if (StringUtils.isBlank(config.getFileLocation())) {
			throw new ParsingEx("No filename for export given");
		}
		LOG.debug("Exporting for configuration:\n" + config.toString());
		final File file = new File(config.getFileLocation());
		final JsonObject parent = new JsonObject();
		for (final ItemType type : ItemType.values()) {
			addForType(parent, type, config);
		}
		try {
			FileUtils.writeStringToFile(file, new JSONObject(parent.toString()).toString(5));
			LOG.info("Exported data to " + config.getFileLocation());
		} catch (final IOException e) {
			throw new ParsingEx(e);
		}
	}

	/**
	 * fetches the nameables for the given names
	 */
	private Collection<Nameable> getTypesForNames(final Set<String> namesForType, final ItemType type) {
		final Collection<Nameable> result = Sets.newHashSet();
		for (final String name : namesForType) {
			final Nameable nameable = vault.getUniqueNamedResult(name, type.type);
			result.add(nameable);
		}
		return result;
	}

	@Override
	public void importFrom(final String fileLocation) {
		final NameableVault result = new SimpleVault();
		try {
			final JSONObject parent = new JSONObject(FileUtils.readFileToString(new File(fileLocation)));
			final List<ItemType> sequenceOfParsing =
				Lists.newArrayList(ItemType.BODY_PART, ItemType.STRIKE_TYPE, ItemType.TYPE, ItemType.WEAPON,
						ItemType.ARMOR_PIECE, ItemType.PLAYER_CHARACTER);
			for (final ItemType type : sequenceOfParsing) {
				parseItemsForType(parent, result, type, type.type);
			}

			for (final ItemType type : sequenceOfParsing) {
				for (final Nameable item : result.getAllNameables(type.type)) {
					vault.update(item);
				}
			}
			// now actually update, store
// for (final Nameable nameable : result.getAllNameables()) {
// if (!vault.exists(nameable)) {
// vault.update(nameable);
// }
// }
			// vault.update(result);

// for (final ItemType type : sequenceOfParsing) {
// final Collection<? extends Nameable> parsedItemsForType = result.getAllNameables(type.type);
// for (final Nameable item : parsedItemsForType) {
// if (!vault.exists(item)) {
// System.out.println("Before create, bodyparts : " + vault.getAllNameables(BodyPart.class).size());
// System.out.println("About to create: " + item.getName());
// vault.create(item);
// System.out.println("After update, bodyparts : " + vault.getAllNameables(BodyPart.class).size());
// } else {
// System.out.println("Already exsits: " + item.getName());
// }
// }
// LOG.debug("Created/updated " + parsedItemsForType.size() + " items.");
// }
		} catch (final JSONException e) {
			throw new ParsingEx(e);
		} catch (final IOException e) {
			throw new ParsingEx(e);
		}
	}

	/**
	 * parses items from a collection for the given type, checks also for conflicts
	 */
	private <T extends Nameable> void parseItemsForType(final JSONObject parent, final NameableVault vault,
			final ItemType type, final Class<T> classType) throws ParsingEx {
		if (parent.has(type.jsonArrayName)) {
			final JSONArray jsonArray = parent.getJSONArray(type.jsonArrayName);
			final Collection<T> parsedItemsForThisType = parser.parseCollection(jsonArray.toString(), vault, type);
			final Collection<T> itemsParsedSoFar = vault.getAllNameables(classType);
			checkForConflictingElements(parsedItemsForThisType, itemsParsedSoFar);
			parsedItemsForThisType.addAll(itemsParsedSoFar);
			vault.addAll(parsedItemsForThisType);
			LOG.debug("Parsed " + parsedItemsForThisType.size() + " items of type " + classType.getSimpleName());
		}
	}
}
