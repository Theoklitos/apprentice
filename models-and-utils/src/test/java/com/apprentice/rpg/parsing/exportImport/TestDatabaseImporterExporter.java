package com.apprentice.rpg.parsing.exportImport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.model.factories.DataFactory;
import com.apprentice.rpg.parsing.ApprenticeParser;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;

/**
 * tests for the {@link DatabaseImporterExporter}
 * 
 * @author theoklitos
 * 
 */
public final class TestDatabaseImporterExporter {

	private DatabaseImporterExporter ei;
	private Mockery mockery;
	private Vault vault;
	private ApprenticeParser parser;
	private DataFactory factory;

	@Test
	public void classToTypeConversion() {
		assertEquals(ItemType.BODY_PART, DatabaseImporterExporter.getTypeForClass(factory.getBodyParts().get(0))
				.getContent());
		assertEquals(ItemType.TYPE, DatabaseImporterExporter.getTypeForClass(factory.getTypes().get(0)).getContent());
		assertEquals(ItemType.WEAPON, DatabaseImporterExporter.getTypeForClass(factory.getWeapons().get(0))
				.getContent());
		assertEquals(ItemType.ARMOR_PIECE, DatabaseImporterExporter.getTypeForClass(factory.getArmorPieces().get(0))
				.getContent());
	}

	@Before
	public void setup() {
		mockery = new Mockery();
		vault = mockery.mock(Vault.class);
		parser = mockery.mock(ApprenticeParser.class);
		factory = new DataFactory();
		ei = new DatabaseImporterExporter(vault, parser);
	}

	@Test
	public void something() {
		fail("something");
	}

}
