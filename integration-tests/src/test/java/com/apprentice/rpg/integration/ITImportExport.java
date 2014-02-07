package com.apprentice.rpg.integration;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.dao.NameableVault;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.parsing.exportImport.ExportConfigurationObject;
import com.apprentice.rpg.util.ApprenticeMatcher;
import com.google.common.collect.Sets;

/**
 * Tests for the json import/export functionality
 * 
 * @author theoklitos
 * 
 */
public final class ITImportExport extends AbstractIntegrationTest {

	public final static File TMP_EXPORT_IMPORT_FILE = new File(TMP_DIR + "/apprenticeTestData");

	private static Logger LOG = Logger.getLogger(ITImportExport.class);

	private DatabaseImporterExporter ie;

	@After
	public void after() {
		TMP_EXPORT_IMPORT_FILE.delete();
	}

	@Test
	public void importTypeAndBodyPartsToEmptyDatabase() throws IOException {
		saveAllFactoryDataToDatbase();
		final ExportConfigurationObject config = new ExportConfigurationObject();
		setAllTypeAndBodyPartNames(config);
		ie.export(config);

		LOG.info("Exported all type and body part data to " + config.getFileLocation() + ":\n"
			+ FileUtils.readFileToString(TMP_EXPORT_IMPORT_FILE));

		emptyDatabase();

		final NameableVault simpleVault = ie.importFrom(TMP_EXPORT_IMPORT_FILE.getAbsolutePath());
		vault.update(simpleVault);

		final List<IType> expectedTypes = factory.getTypes();
		final List<BodyPart> expectedBodyParts = factory.getBodyParts();
		final Collection<IType> importedTypes = vault.getAll(IType.class);
		final Collection<BodyPart> importedBodyParts = vault.getAll(BodyPart.class);

		assertTrue(ApprenticeMatcher.areAllElementsEqual(expectedTypes, importedTypes));
		assertTrue(ApprenticeMatcher.areAllElementsEqual(expectedBodyParts, importedBodyParts));
		
		LOG.info("Succesfully imported " + importedTypes.size() + " types, " + importedBodyParts.size()
			+ " body parts.");
	}
	
	/**
	 * sets the "Human" and "Daemon" name stuff in the configuration object
	 */
	private void setAllTypeAndBodyPartNames(final ExportConfigurationObject config) {
		config.setFileLocation(TMP_EXPORT_IMPORT_FILE.getAbsolutePath());
		final Set<String> typeNames = Sets.newHashSet();
		typeNames.add(factory.getTypes().get(0).getName());
		typeNames.add(factory.getTypes().get(1).getName());
		config.setNamesForExport(ItemType.TYPE, typeNames);
		final Set<String> bodyPartNames = Sets.newHashSet();
		bodyPartNames.add(factory.getBodyParts().get(0).getName());
		bodyPartNames.add(factory.getBodyParts().get(1).getName());
		bodyPartNames.add(factory.getBodyParts().get(2).getName());
		bodyPartNames.add(factory.getBodyParts().get(3).getName());
		bodyPartNames.add(factory.getBodyParts().get(4).getName());
		bodyPartNames.add(factory.getBodyParts().get(5).getName());
		bodyPartNames.add(factory.getBodyParts().get(6).getName());
		bodyPartNames.add(factory.getBodyParts().get(7).getName());
		bodyPartNames.add(factory.getBodyParts().get(8).getName());
		config.setNamesForExport(ItemType.BODY_PART, bodyPartNames);
	}

	@Before
	public void setup() {		
		ie = new DatabaseImporterExporter(vault, parser);
	}

}
