package com.apprentice.rpg.gui.vault.type;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.dao.ItemAlreadyExistsEx;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.BodyPartMappingEx;
import com.apprentice.rpg.model.body.BodyPartToRangeMap;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.body.Type;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.util.Box;
import com.apprentice.rpg.util.IntegerRange;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * object that encapsulates some variables regarding the state of the {@link exportItems}
 * 
 * @author theoklitos
 * 
 */
public final class TypeAndBodyPartFrameState {

	private final Map<String, BodyPartToRangeMap> temporaryTypes;
	private final List<BodyPart> repositoryBodyParts;
	private String selectedBodyPartName;
	private String selectedTypeName;
	private String activeType;
	private final Map<String, BodyPart> selectedBodyPartForType;
	private boolean shouldProceedWithBodyPartRenamingFlag;

	public TypeAndBodyPartFrameState() {
		temporaryTypes = Maps.newLinkedHashMap();
		repositoryBodyParts = Lists.newArrayList();
		selectedBodyPartForType = Maps.newHashMap();
		shouldProceedWithBodyPartRenamingFlag = true;
	}

	/**
	 * Adds a new part to the given type, makes sure the integer range is unique
	 */
	public Box<IType> addNewPartToType(final String typeName, final BodyPart bodyPart) {
		if (temporaryTypes.containsKey(typeName)) {
			final BodyPartToRangeMap mapping = temporaryTypes.get(typeName);
			if (mapping.getParts().contains(bodyPart)) {
				return Box.empty();
			} else {
				for (int i = 1; i < 101; i++) {
					if (!mapping.hasMapping(i, i)) {
						mapping.setPartForRange(i, i, bodyPart);
						break;
					}
				}
			}
		}
		return Box.empty();
	}

	/**
	 * adds the given {@link BodyPart} to this type. If the resulting type is valid, returns a box with this
	 * new type.
	 */
	public Box<IType> addPartToType(final String typeName, final BodyPart bodyPart, final IntegerRange range) {
		if (temporaryTypes.containsKey(typeName)) {
			final BodyPartToRangeMap mapping = temporaryTypes.get(typeName);
			if (mapping.getParts().contains(bodyPart)
				&& mapping.getRangeForBodyPart(bodyPart).getContent().equals(range)) {
				return Box.empty();
			}
			mapping.setPartForRange(range, bodyPart);
			try {
				final IType newType = new Type(typeName, mapping);
				return Box.with(newType);
			} catch (final BodyPartMappingEx e) {
				return Box.empty();
			}
		}
		return Box.empty();
	}

	/**
	 * changes a temporary type's name
	 */
	public void changeTypeName(final String oldName, final String newName) {
		if (temporaryTypes.containsKey(oldName)) {
			if (temporaryTypes.containsKey(newName)) {
				throw new ItemAlreadyExistsEx();
			} else {
				final Iterator<Entry<String, BodyPartToRangeMap>> iterator = temporaryTypes.entrySet().iterator();
				BodyPartToRangeMap mapping = null;
				while (iterator.hasNext()) {
					final Entry<String, BodyPartToRangeMap> entry = iterator.next();
					if (entry.getKey().equals(oldName)) {
						mapping = entry.getValue();
						iterator.remove();
						break;
					}
				}
				if (mapping != null) {
					temporaryTypes.put(newName, mapping);
					if (getSelectedTypeName().hasContent() && getSelectedTypeName().getContent().equals(oldName)) {
						setSelectedTypeName(newName);
					}
				}
			}
		}
	}

	/**
	 * creates a temporary type
	 * 
	 * @throws ItemAlreadyExistsEx
	 */
	public void createType(final String name) throws ItemAlreadyExistsEx {
		if (temporaryTypes.containsKey(name)) {
			throw new ItemAlreadyExistsEx();
		} else {
			temporaryTypes.put(name, new BodyPartToRangeMap());
		}
	}

	/**
	 * returns any "active" type that is displayed on the middle panel
	 */
	public Box<String> getActiveType() {
		if (activeType == null) {
			return Box.empty();
		} else {
			return Box.with(activeType);
		}
	}

	/**
	 * returns all the body parts
	 */
	public List<BodyPart> getBodyParts() {
		return repositoryBodyParts;
	}

	/**
	 * searches both the temporary and the real (database) {@link IType}s for one with the given name; once
	 * found, returns its {@link BodyPartToRangeMap}
	 */
	public Box<BodyPartToRangeMap> getBodyPartsForTypeName(final String name) {
		for (final String tempName : temporaryTypes.keySet()) {
			if (tempName.equals(name)) {
				return Box.with(temporaryTypes.get(tempName));
			}
		}
		return Box.empty();
	}

	/**
	 * what has the user last clicked on?
	 */
	public Box<ItemType> getLastSelectedItemType() {
		if (StringUtils.isNotEmpty(selectedBodyPartName) && StringUtils.isNotEmpty(selectedTypeName)) {
			throw new ApprenticeEx("Both type and body part were selected. You implemented the state wrong!");
		}
		if (StringUtils.isNotEmpty(selectedBodyPartName)) {
			return Box.with(ItemType.BODY_PART);
		} else if (StringUtils.isNotEmpty(selectedTypeName)) {
			return Box.with(ItemType.TYPE);
		} else {
			return Box.empty();
		}
	}

	/**
	 * Returns which body part for the given type the user clicked on (middle table).
	 */
	public Box<String> getSelectedBodyPartForTypeName(final String typeName) {
		final BodyPart result = selectedBodyPartForType.get(typeName);
		if (result == null) {
			return Box.empty();
		} else {
			return Box.with(result.getName());
		}
	}

	/**
	 * returns a box with the name of the last Body Part that the user selected, if any
	 */
	public Box<String> getSelectedBodyPartName() {
		if (selectedBodyPartName == null) {
			return Box.empty();
		} else {
			return Box.with(selectedBodyPartName);
		}
	}

	/**
	 * returns a box with the name of the last Type that the user selected, if any
	 */
	public Box<String> getSelectedTypeName() {
		if (selectedTypeName == null) {
			return Box.empty();
		} else {
			return Box.with(selectedTypeName);
		}
	}

	/**
	 * merges the temp {@link IType} ones with the repository ones and returns a list of their merged names
	 */
	public List<String> getTypeNames() {
		return Lists.newArrayList(temporaryTypes.keySet());
	}

	/**
	 * returns true if at least one type or one body part is selected
	 */
	public boolean isAnythingSelected() {
		return selectedBodyPartName != null || selectedTypeName != null;
	}

	/**
	 * returns true if such a type exists AND has a valid {@link BodyPartToRangeMap}
	 */
	public Box<Boolean> isMappingCorrectForTypeName(final String name) {
		for (final String tempName : temporaryTypes.keySet()) {
			if (tempName.equals(name)) {
				try {
					new Type("dummy", temporaryTypes.get(tempName));
					return Box.with(true);
				} catch (final BodyPartMappingEx e) {
					return Box.with(false);
				}
			}
		}
		return Box.with(false);
	}

	/**
	 * deletes the given body part from all the temp types
	 */
	public void removePartFromAllTypes(final BodyPart deletedBodyPart) {
		final Iterator<Entry<String, BodyPartToRangeMap>> iterator = temporaryTypes.entrySet().iterator();		
		while (iterator.hasNext()) {
			final Entry<String, BodyPartToRangeMap> entry = iterator.next();
			final BodyPartToRangeMap mapping = entry.getValue();
			mapping.removePart(deletedBodyPart);
		}		
	}

	/**
	 * remove the given {@link BodyPart} from this type. If the resulting type is valid, returns a box with
	 * this new type.
	 */
	public Box<IType> removePartFromType(final String typeName, final BodyPart bodyPart) {
		if (temporaryTypes.containsKey(typeName)) {
			final BodyPartToRangeMap mapping = temporaryTypes.get(typeName);
			mapping.removePart(bodyPart);
			try {
				final IType newType = new Type(typeName, mapping);
				return Box.with(newType);
			} catch (final BodyPartMappingEx e) {
				return Box.empty();
			}
		}
		return Box.empty();
	}

	/**
	 * sets the existing type (from the repository). Will replace.
	 */
	public void setBodyParts(final Collection<BodyPart> bodyParts) {
		repositoryBodyParts.clear();
		repositoryBodyParts.addAll(bodyParts);
	}

	/**
	 * sets which body part per specific type the user clicked on (middle table).
	 */
	public void setSelectedBodyPartForType(final String typeName, final String bodypartName) {
		selectedBodyPartForType.put(typeName, new BodyPart(bodypartName));
	}

	/**
	 * sets the {@link BodyPart} that was selected. This will clear any selected type name
	 */
	public void setSelectedBodyPartName(final String name) {
		selectedBodyPartName = name;
		selectedTypeName = null;
	}
	
	/**
	 * sets the selected item, either a {@link IType} or a {@link BodyPart}
	 */
	public void setSelectedItemName(final String name, final ItemType type) {
		switch (type) {
		case BODY_PART:
			setSelectedBodyPartName(name);
			break;
		case TYPE:
			setSelectedTypeName(name);
			break;
		}
	}
	
	/**
	 * sets the {@link IType} that was selected. This will clear any selected body part name
	 */
	public void setSelectedTypeName(final String name) {
		selectedTypeName = name;
		activeType = name;
		selectedBodyPartName = null;
	}

	/**
	 * hack to faciliate body part confimration dialogs
	 */
	public void setShouldProceedWithRenamingFlag(final boolean value) {
		this.shouldProceedWithBodyPartRenamingFlag = value;
	}

	/**
	 * adds the given types to the existing array
	 */
	public void setTypes(final Collection<IType> types) {
		for (final IType repositoryType : types) {
			if (!temporaryTypes.keySet().contains(repositoryType.getName())) {
				temporaryTypes.put(repositoryType.getName(), repositoryType.getPartMapping());
			}
		}
	}

	/**
	 * hack to faciliate body part confimration dialogs
	 */
	public boolean shouldProceedWithBodyPartRenamingFlag() {
		return shouldProceedWithBodyPartRenamingFlag;
	}
}
