package com.apprentice.rpg.parsing.exportImport;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.apprentice.rpg.dao.ItemNotFoundEx;
import com.apprentice.rpg.dao.NameableVault;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.parsing.ApprenticeParser;
import com.apprentice.rpg.parsing.ParsingEx;
import com.google.common.collect.Sets;
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
		BODY_PART(BodyPart.class, "bodyParts"),
		TYPE(IType.class, "types");

		/**
		 * the actual class that this type represents
		 */
		public final Class<? extends Nameable> type;

		/**
		 * under which name are these items are placed in the export/import array
		 */
		public final String jsonArrayName;

		private ItemType(final Class<? extends Nameable> type, final String jsonArrayName) {
			this.type = type;
			this.jsonArrayName = jsonArrayName;
		}

		@Override
		public String toString() {
			if (name().equals(ItemType.TYPE.name())) {
				return "type";
			} else if (name().equals(ItemType.BODY_PART.name())) {
				return "body part";
			} else {
				return "Unimplemented";
			}
		}
	}

	private static Logger LOG = Logger.getLogger(DatabaseImporterExporter.class);

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
		parent.add(type.jsonArrayName,
				parser.getAsJsonArray(getTypesForNames(config.getNamesForType(type), type), type));
	}

	@Override
	public void export(final ExportConfigurationObject config) throws ItemNotFoundEx, ParsingEx {
		LOG.debug("Exporting for configuration:\n" + config.toString());
		final File file = new File(config.getFileLocation());
		final JsonObject parent = new JsonObject();
		addForType(parent, ItemType.TYPE, config);
		addForType(parent, ItemType.BODY_PART, config);
		try {
			FileUtils.writeStringToFile(file, new JSONObject(parent.toString()).toString(5));
			LOG.info("Exported data to " + config.getFileLocation() + ".");
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
	public NameableVault importFrom(final String fileLocation) {
		final ImportObject result = new ImportObject();
		try {
			final JSONObject parent = new JSONObject(FileUtils.readFileToString(new File(fileLocation)));
			if (parent.has(ItemType.BODY_PART.jsonArrayName)) {
				final JSONArray bodyPartObject = parent.getJSONArray(ItemType.BODY_PART.jsonArrayName);
				final Collection<BodyPart> parts = parser.parseBodyParts(bodyPartObject.toString());
				result.addAll(parts);
			}
			if (parent.has(ItemType.TYPE.jsonArrayName)) {
				final JSONArray typeObject = parent.getJSONArray(ItemType.TYPE.jsonArrayName);
				final Collection<IType> types = parser.parseTypes(typeObject.toString(), result);
				result.addAll(types);
			}
		} catch (final JSONException e) {
			throw new ParsingEx(e);
		} catch (final IOException e) {
			throw new ParsingEx(e);
		}
		return result;
	}
}
