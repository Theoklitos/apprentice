package com.apprentice.rpg.parsing.exportImport;

import java.util.Map;
import java.util.Set;

import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * Used to define how and where the export should happen
 * 
 * @author theoklitos
 * 
 */
public final class ExportConfigurationObject {

	private String fileLocation;
	private final Map<ItemType, Set<String>> typeToNameMapping;

	public ExportConfigurationObject() {
		typeToNameMapping = Maps.newEnumMap(ItemType.class);
	}

	/**
	 * Adds the given name to the given {@link ItemType} for export
	 */
	public void addNameForExport(final ItemType type, final String selectedName) {
		Set<String> names = typeToNameMapping.get(selectedName);
		if (names == null) {
			names = Sets.newHashSet();
			typeToNameMapping.put(type, names);
		}
		names.add(selectedName);
	}

	/**
	 * where should the information be stored at?
	 */
	public String getFileLocation() {
		return fileLocation;
	}

	/**
	 * Returns the defined names for the {@link Nameable}s names
	 */
	public Set<String> getNamesForType(final ItemType type) {
		return typeToNameMapping.get(type);
	}

	/**
	 * sets where the the information should be stored
	 */
	public void setFileLocation(final String fileLocation) {
		this.fileLocation = fileLocation;
	}

	/**
	 * Add some item names that match what {@link Nameable}s will be exported, for a specific types. Note:
	 * This replaces any previous mappings.
	 */
	public void setNamesForExport(final ItemType type, final Set<String> names) {
		typeToNameMapping.put(type, names);
	}

	@Override
	public String toString() {
		return "File: " + fileLocation + ". Names: "
			+ Joiner.on(",").withKeyValueSeparator(":").join(typeToNameMapping);
	}
}
