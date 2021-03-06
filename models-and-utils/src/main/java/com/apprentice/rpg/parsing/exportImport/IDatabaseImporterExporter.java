package com.apprentice.rpg.parsing.exportImport;

import com.apprentice.rpg.dao.Vault;
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
	 * 
	 * @throws ParsingEx
	 *             error during parsing
	 */
	void export(ExportConfigurationObject config) throws ParsingEx;

	/**
	 * reads the file at the given location and stores the data into the {@link Vault}
	 */
	void importFrom(final String fileLocation);

}
