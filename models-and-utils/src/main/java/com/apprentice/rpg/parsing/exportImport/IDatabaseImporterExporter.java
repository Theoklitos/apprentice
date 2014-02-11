package com.apprentice.rpg.parsing.exportImport;

import com.apprentice.rpg.dao.ItemNotFoundEx;
import com.apprentice.rpg.dao.simple.NameableVault;
import com.apprentice.rpg.parsing.ApprenticeParser;
import com.apprentice.rpg.parsing.ParsingEx;

/**
 * Uses the {@link ApprenticeParser} to parse json and then imports it in the database, or to export parts of
 * the database as json
 * 
 * @author theoklitos
 * 
 */
public interface IDatabaseImporterExporter {

	/**
	 * will write the information definde in the {@link ExportConfigurationObject} to a file
	 * 
	 * @throws ItemNotFoundEx
	 *             if some name does not match an item in the vault
	 * 
	 * @throws ParsingEx
	 *             error during parsing
	 */
	void export(ExportConfigurationObject config) throws ItemNotFoundEx, ParsingEx;

	/**
	 * reads the file at the given location and parses the data into an {@link NameableVault}
	 */
	NameableVault importFrom(final String fileLocation);

}
