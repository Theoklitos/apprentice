package com.apprentice.rpg.parsing.exportImport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.factories.DataFactory;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;

/**
 * tests for the {@link ExportConfigurationObject}
 * 
 * @author theoklitos
 * 
 */
public final class TestExportConfigurationObject {

	private ExportConfigurationObject config;
	private DataFactory factory;

	@Test
	public void namesAddedFromNamebales() {
		config.addNames(factory.getWeapons());
		config.addNames(factory.getArmorPieces());
		assertEquals(4, config.size());

		final Set<String> weaponNames = config.getNamesForType(ItemType.WEAPON);
		assertEquals(2, weaponNames.size());
		assertTrue(weaponNames.contains(factory.getWeapons().get(0).getName()));
		assertTrue(weaponNames.contains(factory.getWeapons().get(1).getName()));

		final Set<String> armorNames = config.getNamesForType(ItemType.ARMOR_PIECE);
		assertEquals(2, armorNames.size());
		assertTrue(armorNames.contains(factory.getArmorPieces().get(0).getName()));
		assertTrue(armorNames.contains(factory.getArmorPieces().get(1).getName()));
	}

	@Before
	public void setup() {
		factory = new DataFactory();
		config = new ExportConfigurationObject();
	}
}
