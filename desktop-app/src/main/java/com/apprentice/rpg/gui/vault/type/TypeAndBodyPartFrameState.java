package com.apprentice.rpg.gui.vault.type;

import java.util.List;
import java.util.Map;

import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.BodyPartToRangeMap;
import com.apprentice.rpg.model.body.IType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * object that encapsulates some variables regarding the state of the {@link TypeAndBodyPartFrame}
 * 
 * @author theoklitos
 * 
 */
public final class TypeAndBodyPartFrameState {

	public final Map<String, BodyPartToRangeMap> temporaryTypes;
	public final List<IType> repositoryTypes;
	public final List<BodyPart> repositoryBodyParts;
	public String selectedBodyPartName;
	public String selectedBodyTypeName;

	public TypeAndBodyPartFrameState() {
		temporaryTypes = Maps.newLinkedHashMap();
		repositoryTypes = Lists.newArrayList();
		repositoryBodyParts = Lists.newArrayList();
	}

	/**
	 * returns true if at least one type or one body part is selected
	 */
	public boolean isAnythingSelected() {
		return selectedBodyPartName != null || selectedBodyTypeName != null;
	}

	/**
	 * sets the existing type (from the repository). Will replace.
	 */
	public void setBodyParts(final List<BodyPart> bodyParts) {
		repositoryBodyParts.clear();
		repositoryBodyParts.addAll(bodyParts);
	}

	/**
	 * sets the {@link BodyPart} that was selected. This will clear any selected type name
	 */
	public void setSelectedBodyPartName(final String name) {
		selectedBodyPartName = name;
		selectedBodyTypeName = null;
	}

	/**
	 * sets the {@link IType} that was selected. This will clear any selected body part name
	 */
	public void setSelectedTypeName(final String name) {
		selectedBodyPartName = null;
		selectedBodyTypeName = name;
	}

	/**
	 * sets the existing type (from the repository). Will replace.
	 */
	public void setTypes(final List<IType> types) {
		repositoryTypes.clear();
		repositoryTypes.addAll(types);
	}
}
