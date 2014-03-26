package com.apprentice.rpg.parsing.exportImport;

import static org.junit.Assert.assertEquals;

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
		config.addNames(factory.getWeaponPrototypes());
		config.addNames(factory.getArmorPieces());
		assertEquals(13, config.size());

		final Set<String> weaponNames = config.getNamesForType(ItemType.WEAPON);
		assertEquals(5, weaponNames.size());

		final Set<String> armorPieceNames = config.getNamesForType(ItemType.ARMOR_PIECE);
		assertEquals(8, armorPieceNames.size());
	}

	@Before
	public void setup() {
		factory = new DataFactory();
		config = new ExportConfigurationObject();
	}
}
