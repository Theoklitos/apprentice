package com.apprentice.rpg.gui.vault.type;

import java.util.Collection;

import com.apprentice.rpg.dao.ItemNotFoundEx;
import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.gui.ControlWithVault;
import com.apprentice.rpg.gui.ControlForView;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.parsing.ParsingEx;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.parsing.exportImport.ExportConfigurationObject;

/**
 * Controls the {@link exportItems}
 * 
 * @author theoklitos
 * 
 */
public interface ITypeAndBodyPartFrameControl extends ControlForView<ITypeAndBodyPartFrame>, ControlWithVault {

	/**
	 * used to update an existing body part or a type
	 * 
	 * @throws NameAlreadyExistsEx
	 */
	void createOrUpdate(Nameable item, ItemType itemType) throws NameAlreadyExistsEx;

	/**
	 * Deletes a type or body part which is identified by the given name.
	 * 
	 * @throws ItemNotFoundEx
	 *             if such an item does not eixst
	 */
	void deleteByName(String name, ItemType type) throws ItemNotFoundEx;

	/**
	 * returns true if a {@link IType} with the given name exists in the database
	 */
	boolean doesTypeNameExist(String name);

	/**
	 * Uses the {@link ExportConfigurationObject} in order to write types+body parts to a file. Overwrites.
	 * 
	 * @throws ItemNotFoundEx
	 *             if some name does not match an item in the vault
	 * 
	 * @throws ParsingEx
	 *             error during parsing
	 */
	void exportForConfiguration(final ExportConfigurationObject config) throws ItemNotFoundEx, ParsingEx;

	/**
	 * Searches in the vault and returns a {@link BodyPart} with the given name
	 * 
	 * @throws ItemNotFoundEx
	 *             if such a body part does not exist
	 */
	BodyPart getBodyPartForName(String bodyPartName) throws ItemNotFoundEx;

	/**
	 * returns all the existing {@link BodyPart}s
	 */
	Collection<BodyPart> getBodyParts();

	/**
	 * returns a reference to the global {@link ApprenticeEventBus}
	 */
	@Override
	ApprenticeEventBus getEventBus();

	/**
	 * returns a string that describes the last update tim for the given item (and type)
	 */
	String getLastUpdateTime(String typeName, ItemType type);

	/**
	 * Searches in the vault and returns a {@link IType} with the given name
	 * 
	 * @throws ItemNotFoundEx
	 *             if such a type does not exist
	 */
	IType getTypeForName(String typeName) throws ItemNotFoundEx;

	/**
	 * returns either a {@link IType} or a {@link BodyPart}, based on what is requested
	 * 
	 * @throws ItemNotFoundEx
	 */
	Nameable getTypeOrBodyPartForName(String name, ItemType type) throws ItemNotFoundEx;

	/**
	 * returns all the existing {@link IType}s
	 */
	Collection<IType> getTypes();

	/**
	 * returns a reference to the global {@link Vault}
	 */
	@Override
	Vault getVault();

	/**
	 * will import body parts and types from the given file location
	 * 
	 * @throws {@link ParsingEx}
	 */
	void importFromFile(String fileLocation) throws ParsingEx;

}
